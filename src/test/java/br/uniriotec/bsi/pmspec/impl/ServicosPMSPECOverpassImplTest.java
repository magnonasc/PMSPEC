package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.api.LeitorMunicipios;
import br.uniriotec.bsi.pmspec.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * Testes para a classe {@link ServicosPMSPECOverpassImpl}.
 *
 * @author Magno Nascimento, Roberta Dias e Vinícius Zamith
 */
public class ServicosPMSPECOverpassImplTest {

    /**
     * Testa o serviço sem se conectar com a API Overpass.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void buscarPontoInteresseTest() throws IOException {
        final AreaMunicipio areaMunicipio = Mockito.mock(AreaMunicipio.class);
        Mockito.when(areaMunicipio.calcularBoundingBox()).thenReturn(new BoundingBox(1, 2, 1, 2));

        final LeitorMunicipios leitorMunicipios = Mockito.mock(LeitorMunicipios.class);
        Mockito.when(leitorMunicipios.lerMunicipios()).thenReturn(Arrays.asList(new Municipio(123, "RJ", "Cabo Frio", areaMunicipio)));

        final GerenciadorMunicipios gerenciadorMunicipios = new GerenciadorMunicipiosImpl(leitorMunicipios);

        final FakeServicosPMSPECOverpassImpl servicosPMSPEC = Mockito.spy(new FakeServicosPMSPECOverpassImpl(gerenciadorMunicipios));
        servicosPMSPEC.inicializar();

        assertThat(servicosPMSPEC.buscarPontosInteresse("RJ", "Cabo Frio"), containsInAnyOrder(new PontoInteresse(PontoInteresse.Tipo.RODOVIA, "Rua Henrique Terra"), new PontoInteresse(PontoInteresse.Tipo.AEROPORTO, "Base Aeronaval de São Pedro da Aldeia")));

        Mockito.verify(servicosPMSPEC, Mockito.times(1)).obterDadosMunicipio(new BoundingBox(1, 2, 1, 2));
    }

}
