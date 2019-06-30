package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.BoundingBox;
import br.uniriotec.bsi.pmspec.model.Municipio;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.*;

/**
 * Implementação dos serviços do sistema PMSPEC utilizando a API Overpass.
 *
 * @author Magno Nascimento
 */
public class ServicosPMSPECOverpassImpl implements ServicosPMSPEC {

    public static final URI OVERPASS_API_BASE_URI = URI.create("https://lz4.overpass-api.de/api/interpreter");

    private final GerenciadorMunicipios gerenciadorMunicipios;

    // TODO
    public ServicosPMSPECOverpassImpl(@Nonnull final GerenciadorMunicipios gerenciadorMunicipios) {
        this.gerenciadorMunicipios = checkNotNull(gerenciadorMunicipios);
    }

    @Override
    public Stream<String> buscarPontosInteresse(final String siglaUF, final String nomeMunicipio) throws IOException {
        return buscarPontosInteresse(gerenciadorMunicipios.buscarMunicipio(siglaUF, nomeMunicipio).orElseThrow(() -> new IllegalArgumentException("O município com o nome ou UF fornecido não foi encontrado.")));
    }

   @Override
    public Stream<String> buscarPontosInteresse(final long geocodigo) throws IOException {
       return buscarPontosInteresse(gerenciadorMunicipios.buscarMunicipio(geocodigo).orElseThrow(() -> new IllegalArgumentException("O município com o código fornecido não foi encontrado.")));
   }

    /**
     * Método auxiliar para a busca por pontos de interesse.
     *
     * @param municipio O município onde a busca será realizada.
     * @return Os pontos de interesse encontrados.
     */
   private Stream<String> buscarPontosInteresse(@Nonnull final Municipio municipio) throws IOException {

       final HttpURLConnection overpassConnection =
               (HttpURLConnection) OVERPASS_API_BASE_URI.toURL().openConnection();
       overpassConnection.setRequestMethod("GET");
       overpassConnection.setRequestProperty( "charset", "utf-8");

       try(final DataOutputStream writer = new DataOutputStream(overpassConnection.getOutputStream())) {
           writer.writeUTF(criarCorpoRequisicao(municipio.getArea().calculateBoundingBox()));
       }

       final int responseCode = overpassConnection.getResponseCode();

       if (responseCode == 404) {
           throw new IOException("A página para a query requisitada não pôde ser encontrada");
       }

       overpassConnection.getInputStream().transferTo(System.out); // TODO tratar retorno da API

       return null;
   }

    /**
     * Cria o corpo da requisição a ser efetuada com a API Overpass.
     *
     * @param boundingBox A bounding box do município.
     * @return O corpo da requisição pronto para ser enviado.
     * @see <a href="https://wiki.openstreetmap.org/wiki/Overpass_API">Overpass API</a>
     */
   private String criarCorpoRequisicao(@Nonnull final BoundingBox boundingBox) {
       return String.format("node(%f,%f,%f,%f); out;", boundingBox.getMinLatitude(), boundingBox.getMinLongitude(), boundingBox.getMaxLatitude(), boundingBox.getMaxLongitude());
   }

}
