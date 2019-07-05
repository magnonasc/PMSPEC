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
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementação dos serviços do sistema PMSPEC utilizando a API Overpass.
 *
 * @author Magno Nascimento
 */
@Singleton
public class ServicosPMSPECOverpassImpl implements ServicosPMSPEC {

    public static final URI OVERPASS_API_BASE_URI = URI.create("https://overpass-api.de/api/interpreter");

    private final URI overpassURI;
    private final GerenciadorMunicipios gerenciadorMunicipios;

    /**
     * O construtor do serviço principal do sistema.
     *
     * @param gerenciadorMunicipios O gerenciador de municípios a ser utilizado.
     */
    @Inject
    public ServicosPMSPECOverpassImpl(@Nonnull final GerenciadorMunicipios gerenciadorMunicipios) {
        this(gerenciadorMunicipios, OVERPASS_API_BASE_URI);
    }

    /**
     * O construtor do serviço principal do sistema.
     *
     * @param gerenciadorMunicipios O gerenciador de municípios a ser utilizado.
     */
    public ServicosPMSPECOverpassImpl(@Nonnull final GerenciadorMunicipios gerenciadorMunicipios, @Nonnull final URI overpassURI) {
        this.gerenciadorMunicipios = checkNotNull(gerenciadorMunicipios);
        this.overpassURI = checkNotNull(overpassURI);
    }

    /**
     * {@inheritDoc}
     *
     * @implNote A execução deste método é obrigatória antes da utilização do
     * serviço.
     */
    @Override
    public void inicializar() throws IOException {
        gerenciadorMunicipios.carregarGerenciadorMunicipios();
    }

    @Override
    public Set<PontoInteresse> buscarPontosInteresse(final String siglaUF, final String nomeMunicipio) throws IOException {
        return buscarPontosInteresse(gerenciadorMunicipios.buscarMunicipio(checkNotNull(siglaUF), checkNotNull(nomeMunicipio)).orElseThrow(
                () -> new IllegalArgumentException("O município com o nome ou UF fornecido não foi encontrado.")));
    }

    @Override
    public Set<PontoInteresse> buscarPontosInteresse(final long geocodigo) throws IOException {
        return buscarPontosInteresse(gerenciadorMunicipios.buscarMunicipio(geocodigo).orElseThrow(
                () -> new IllegalArgumentException("O município com o código fornecido não foi encontrado.")));
    }

    /**
     * Método auxiliar para a busca por pontos de interesse.
     *
     * @param municipio O município onde a busca será realizada.
     * @return Os pontos de interesse encontrados.
     */
    private Set<PontoInteresse> buscarPontosInteresse(@Nonnull final Municipio municipio) throws IOException {
        try (final InputStream inputStream = obterDadosMunicipio(municipio.getArea().calcularBoundingBox())) {
            LeitorOSMOverpass leitorOSM = new LeitorOSMOverpass(inputStream);
            Set<PontoInteresse> pontosInteresse = leitorOSM.lerOSM();

            return pontosInteresse;
        }
    }

    /**
     * Cria o corpo da requisição a ser efetuada com a API Overpass.
     *
     * @param boundingBox A bounding box do município.
     * @return O corpo da requisição pronto para ser enviado.
     * @see <a href="https://wiki.openstreetmap.org/wiki/Overpass_API">Overpass
     * API</a>
     */
    private String criarQueryRequisicao(@Nonnull final BoundingBox boundingBox) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/api/map?bbox=");
        stringBuilder.append(boundingBox.getMinLongitude()).append(",");
        stringBuilder.append(boundingBox.getMinLatitude()).append(",");
        stringBuilder.append(boundingBox.getMaxLongitude()).append(",");
        stringBuilder.append(boundingBox.getMaxLatitude());
        return stringBuilder.toString();
    }

    /**
     * Realiza a conexão com a API Overpass e obtém os dados do município de acordo
     * com o corpo da requisição.
     * 
     * @param boundingBox A bounding box do município.
     * @return A resposta obtida pelo servidor.
     */
    InputStream obterDadosMunicipio(@Nonnull final BoundingBox boundingBox) throws IOException {
        final HttpURLConnection overpassConnection = (HttpURLConnection) overpassURI.resolve(criarQueryRequisicao(boundingBox))
                .toURL().openConnection();
        overpassConnection.setRequestMethod("GET");
        overpassConnection.setRequestProperty("charset", "utf-8");
        overpassConnection.setRequestProperty("Content-Type", "text/plain");

        final int responseCode = overpassConnection.getResponseCode();

        if (responseCode == 400) {
            throw new IOException("O corpo da requisição não foi reconhecido.");
        }
        if (responseCode == 404) {
            throw new IOException("A página para a query requisitada não pôde ser encontrada.");
        }
        if (responseCode == 429) {
            throw new IOException("A requisição feita é maior do que o suportado pela API Overpass.");
        } else if (responseCode == -1) {
            throw new IOException("Um erro ocorreu ao tentar se conectar com a API.");
        }

        return overpassConnection.getInputStream();
    }

}
