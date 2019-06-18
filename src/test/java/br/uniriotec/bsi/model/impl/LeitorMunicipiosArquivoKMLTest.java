package br.uniriotec.bsi.model.impl;

import br.uniriotec.bsi.api.LeitorMunicipios;
import br.uniriotec.bsi.impl.LeitorMunicipiosArquivoKML;
import br.uniriotec.bsi.model.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Testes para a classe {@link LeitorMunicipiosArquivoKML}.
 *
 * @author Magno Nascimento
 */
public class LeitorMunicipiosArquivoKMLTest {

    private static final String NOME_RECURSO_MUNICIPIO_SIMPLES = "municipioSimples.kml";
    private static final String NOME_RECURSO_MUNICIPIO_COMPLEXO = "municipioComplexo.kml";
    private static final String NOME_RECURSO_MUNICIPIOS_MISTOS = "municipiosMistos.kml";

    /**
     * Testa o leitor de municípios de arquivos KML com um município com apenas um polígono em sua área.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLMunicipioSimples() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(Paths.get(getClass().getResource(NOME_RECURSO_MUNICIPIO_SIMPLES).toURI()));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(1));

        final Municipio municipioTeste = municipios.get(0);

        assertThat(municipioTeste.getGeocodigo(), equalTo(1100015L));
        assertThat(municipioTeste.getSiglaUF(), equalTo("RO"));
        assertThat(municipioTeste.getNome(), equalTo("Alta Floresta D\'Oeste"));
        assertThat(municipioTeste.getArea().calculateBoundingBox(), equalTo(new BoundingBox(-13.11898044537588, -11.82790901360960, -62.89308946060282, -61.88644092317787)));
    }

    /**
     * Testa o leitor de municípios de arquivos KML com um município com múltiplos polígonos em sua área.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLMunicipioComplexo() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(Paths.get(getClass().getResource(NOME_RECURSO_MUNICIPIO_COMPLEXO).toURI()));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(1));

        final Municipio municipioTeste = municipios.get(0);

        assertThat(municipioTeste.getGeocodigo(), equalTo(3304557L));
        assertThat(municipioTeste.getSiglaUF(), equalTo("RJ"));
        assertThat(municipioTeste.getNome(), equalTo("Rio de Janeiro"));
        assertThat(municipioTeste.getArea().calculateBoundingBox(), equalTo(new BoundingBox(-23.08256212592847, -22.74808655108407, -43.79498293343551, -43.10320838961272)));
    }

    /**
     * Testa o leitor de municípios de arquivos KML com múltiplos municípios com apenas um e múltiplos polígonos em sua área.
     *
     * @throws URISyntaxException Se a URI retornada para o recurso KML não for válida, esse caso nunca deve acontecer.
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorMunicipiosArquivoKMLMunicipiosMistos() throws URISyntaxException, IOException {
        final LeitorMunicipios leitorMunicipios = new LeitorMunicipiosArquivoKML(Paths.get(getClass().getResource(NOME_RECURSO_MUNICIPIOS_MISTOS).toURI()));

        final List<Municipio> municipios = leitorMunicipios.lerMunicipios();

        assertThat(municipios, hasSize(2));

        final Municipio municipioTesteSimples = municipios.get(0);

        assertThat(municipioTesteSimples.getGeocodigo(), equalTo(1100015L));
        assertThat(municipioTesteSimples.getSiglaUF(), equalTo("RO"));
        assertThat(municipioTesteSimples.getNome(), equalTo("Alta Floresta D\'Oeste"));
        assertThat(municipioTesteSimples.getArea().calculateBoundingBox(), equalTo(new BoundingBox(-13.11898044537588, -11.82790901360960, -62.89308946060282, -61.88644092317787)));

        final Municipio municipioTesteComplexo = municipios.get(1);

        assertThat(municipioTesteComplexo.getGeocodigo(), equalTo(3304557L));
        assertThat(municipioTesteComplexo.getSiglaUF(), equalTo("RJ"));
        assertThat(municipioTesteComplexo.getNome(), equalTo("Rio de Janeiro"));
        assertThat(municipioTesteComplexo.getArea().calculateBoundingBox(), equalTo(new BoundingBox(-23.08256212592847, -22.74808655108407, -43.79498293343551, -43.10320838961272)));
    }

}
