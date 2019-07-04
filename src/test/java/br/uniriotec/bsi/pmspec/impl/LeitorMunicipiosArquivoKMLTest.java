package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.PMSpecModule;
import br.uniriotec.bsi.pmspec.api.LeitorMunicipios;
import br.uniriotec.bsi.pmspec.model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Testes para a classe {@link LeitorMunicipiosArquivoKML}.
 *
 * @author Magno Nascimento
 */
public class LeitorMunicipiosArquivoKMLTest {

    private static final String NOME_RECURSO_ARQUIVO_SEM_MUNICIPIOS = "arquivoSemMunicipios.kml";
    private static final String NOME_RECURSO_MUNICIPIO_ARTIFICIAL_SIMPLES = "municipioArtificialSimples.kml";
    private static final String NOME_RECURSO_MUNICIPIO_ARTIFICIAL_COMPLEXO = "municipioArtificialComplexo.kml";

    private static final String NOME_RECURSO_MUNICIPIO_SIMPLES = "municipioSimples.kml";
    private static final String NOME_RECURSO_MUNICIPIO_COMPLEXO = "municipioComplexo.kml";
    private static final String NOME_RECURSO_MUNICIPIOS_MISTOS = "municipiosMistos.kml";

    /**
     * Testa o leitor de municípios de arquivos KML com o arquivo oficial.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKML() throws IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(PMSpecModule.CAMINHO_PADRAO_RECURSO_MUNICIPIOS);

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(5566)); // Isso assegura de que os municípios foram corretamente criados.

        // TODO Simplificar arquivo KML para adicionar mais asserções
    }

    /**
     * Testa o leitor de municípios de arquivos KML sem municípios.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException        Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLSemMunicipios() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(getClass().getResource(NOME_RECURSO_ARQUIVO_SEM_MUNICIPIOS));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(0));
    }

    /**
     * Testa o leitor de municípios de arquivos KML com um município com apenas um polígono em sua área criado para fins de testes.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException        Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLMunicipioArtificialSimples() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(getClass().getResource(NOME_RECURSO_MUNICIPIO_ARTIFICIAL_SIMPLES));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(1));

        final Municipio municipioTeste = municipios.get(0);

        assertThat(municipioTeste.getGeocodigo(), equalTo(1234567L));
        assertThat(municipioTeste.getSiglaUF(), equalTo("AA"));
        assertThat(municipioTeste.getNome(), equalTo("Município de Teste Simples"));
        assertThat(municipioTeste.getArea(), equalTo(new AreaMunicipio(new Poligono(new Coordenada(-11.86729196449489, -62.18254881082289), new Coordenada(-11.8718313187172, -62.16275528088946), new Coordenada(-11.87084789249371, -62.15752323637058), new Coordenada(-11.86729196449489, -62.18254881082289)))));
    }

    /**
     * Testa o leitor de municípios de arquivos KML com um município com múltiplos polígonos em sua área criado para fins de testes.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException        Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLMunicipioArtificialComplexo() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(getClass().getResource(NOME_RECURSO_MUNICIPIO_ARTIFICIAL_COMPLEXO));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(1));

        final Municipio municipioTeste = municipios.get(0);

        assertThat(municipioTeste.getGeocodigo(), equalTo(7654321L));
        assertThat(municipioTeste.getSiglaUF(), equalTo("BB"));
        assertThat(municipioTeste.getNome(), equalTo("Município de Teste Complexo"));
        assertThat(municipioTeste.getArea(), equalTo(new AreaMunicipio(new Poligono(new Coordenada(-22.79838576200411, -43.3074829311733), new Coordenada(-22.80616242357089, -43.29975885701564), new Coordenada(-22.80999110186433, -43.29118562687095), new Coordenada(-22.79838576200411, -43.3074829311733)), new Poligono(new Coordenada(-22.77597940879172, -43.17254038371946), new Coordenada(-22.78304794842813, -43.17129244194599), new Coordenada(-22.77709621027228, -43.16589949433747), new Coordenada(-22.77597940879172, -43.17254038371946)))));
    }

    /**
     * Testa o leitor de municípios de arquivos KML com um município com apenas um polígono em sua área.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException        Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLMunicipioSimples() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(getClass().getResource(NOME_RECURSO_MUNICIPIO_SIMPLES));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(1));

        final Municipio municipioTeste = municipios.get(0);

        assertThat(municipioTeste.getGeocodigo(), equalTo(1100015L));
        assertThat(municipioTeste.getSiglaUF(), equalTo("RO"));
        assertThat(municipioTeste.getNome(), equalTo("Alta Floresta D\'Oeste"));
        assertThat(municipioTeste.getArea().calcularBoundingBox(), equalTo(new BoundingBox(-13.11898044537588, -11.82790901360960, -62.89308946060282, -61.88644092317787)));
    }

    /**
     * Testa o leitor de municípios de arquivos KML com um município com múltiplos polígonos em sua área.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException        Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLMunicipioComplexo() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(getClass().getResource(NOME_RECURSO_MUNICIPIO_COMPLEXO));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(1));

        final Municipio municipioTeste = municipios.get(0);

        assertThat(municipioTeste.getGeocodigo(), equalTo(3304557L));
        assertThat(municipioTeste.getSiglaUF(), equalTo("RJ"));
        assertThat(municipioTeste.getNome(), equalTo("Rio de Janeiro"));
        assertThat(municipioTeste.getArea().calcularBoundingBox(), equalTo(new BoundingBox(-23.08256212592847, -22.74808655108407, -43.79498293343551, -43.10320838961272)));
    }

    /**
     * Testa o leitor de municípios de arquivos KML com múltiplos municípios com apenas um e múltiplos polígonos em sua área.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException        Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLMunicipiosMistos() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(getClass().getResource(NOME_RECURSO_MUNICIPIOS_MISTOS));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(2));

        final Municipio municipioTesteSimples = municipios.get(0);

        assertThat(municipioTesteSimples.getGeocodigo(), equalTo(1100015L));
        assertThat(municipioTesteSimples.getSiglaUF(), equalTo("RO"));
        assertThat(municipioTesteSimples.getNome(), equalTo("Alta Floresta D\'Oeste"));
        assertThat(municipioTesteSimples.getArea().calcularBoundingBox(), equalTo(new BoundingBox(-13.11898044537588, -11.82790901360960, -62.89308946060282, -61.88644092317787)));

        final Municipio municipioTesteComplexo = municipios.get(1);

        assertThat(municipioTesteComplexo.getGeocodigo(), equalTo(3304557L));
        assertThat(municipioTesteComplexo.getSiglaUF(), equalTo("RJ"));
        assertThat(municipioTesteComplexo.getNome(), equalTo("Rio de Janeiro"));
        assertThat(municipioTesteComplexo.getArea().calcularBoundingBox(), equalTo(new BoundingBox(-23.08256212592847, -22.74808655108407, -43.79498293343551, -43.10320838961272)));
    }

}
