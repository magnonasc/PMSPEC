package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.BoundingBox;
import br.uniriotec.bsi.pmspec.model.Municipio;
import br.uniriotec.bsi.pmspec.model.PontoInteresse;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.*;

/**
 * Implementação dos serviços do sistema PMSPEC utilizando a API Overpass.
 *
 * @author Magno Nascimento
 */
@Singleton
public class ServicosPMSPECOverpassImpl implements ServicosPMSPEC {

    public static final URI OVERPASS_API_BASE_URI = URI.create("https://lz4.overpass-api.de/api/interpreter");

    private final GerenciadorMunicipios gerenciadorMunicipios;

    /**
     * O construtor do serviço principal do sistema.
     *
     * @param gerenciadorMunicipios O gerenciador de municípios a ser utilizado.
     */
    @Inject
    public ServicosPMSPECOverpassImpl(@Nonnull final GerenciadorMunicipios gerenciadorMunicipios) {
        this.gerenciadorMunicipios = checkNotNull(gerenciadorMunicipios);
    }

    /**
     * {@inheritDoc}
     * @implNote A execução deste método é obrigatória antes da utilização do serviço.
     */
    @Override
    public void inicializar() throws IOException {
        gerenciadorMunicipios.carregarGerenciadorMunicipios();
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
       try(final InputStream inputStream = obterDadosMunicipio(criarCorpoRequisicao(municipio.getArea().calculateBoundingBox()))) {
           LeitorOSM leitorOSM = new LeitorOSM(inputStream);
           return (Stream<String>) leitorOSM.lerOSM();
       }
   }
   
    /**
     * Cria o corpo da requisição a ser efetuada com a API Overpass.
     *
     * @param boundingBox A bounding box do município.
     * @return O corpo da requisição pronto para ser enviado.
     * @see <a href="https://wiki.openstreetmap.org/wiki/Overpass_API">Overpass API</a>
     */
   private String criarCorpoRequisicao(@Nonnull final BoundingBox boundingBox) {
       final StringBuilder stringBuilder = new StringBuilder();
       stringBuilder.append("node(");
       stringBuilder.append(boundingBox.getMinLatitude()).append(",");
       stringBuilder.append(boundingBox.getMinLongitude()).append(",");
       stringBuilder.append(boundingBox.getMaxLatitude()).append(",");
       stringBuilder.append(boundingBox.getMaxLongitude()).append(");");
       stringBuilder.append("out;");

       return stringBuilder.toString();
   }

    /**
     * Realiza a conexão com a API Overpass e obtém os dados do município de acordo com o corpo da requisição.
     *
     * @param corpoRequisicao O corpo a ser enviado na requisição, geralmente gerado por {@link #criarCorpoRequisicao(BoundingBox)}.
     * @return A resposta obtida pelo servidor.
     */
   private InputStream obterDadosMunicipio(@Nonnull final String corpoRequisicao) throws IOException {
       final HttpURLConnection overpassConnection =
               (HttpURLConnection) OVERPASS_API_BASE_URI.toURL().openConnection();
       overpassConnection.setRequestMethod("POST");
       overpassConnection.setRequestProperty("charset", "utf-8");
       overpassConnection.setRequestProperty("Content-Type", "text/plain");
       overpassConnection.setDoOutput(true);
       
       try (final OutputStreamWriter writer = new OutputStreamWriter(overpassConnection.getOutputStream(), "UTF-8")) {
           writer.write(corpoRequisicao);
       }
       
       final int responseCode = overpassConnection.getResponseCode();
       
       if (responseCode == 400) {
           throw new IOException("O corpo da requisição não foi reconhecido.");
       } if (responseCode == 404) {
           throw new IOException("A página para a query requisitada não pôde ser encontrada.");
       } else if (responseCode == -1) {
           throw new IOException("Um erro ocorreu ao tentar se conectar com a API.");
       }
       
       return overpassConnection.getInputStream();
   }

}
