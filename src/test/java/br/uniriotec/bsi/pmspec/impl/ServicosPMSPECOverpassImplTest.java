package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.PMSpecModule;
import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.api.LeitorMunicipios;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.xebialabs.restito.builder.stub.StubHttp;
import com.xebialabs.restito.server.StubServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp;
import static com.xebialabs.restito.semantics.Action.ok;
import static com.xebialabs.restito.semantics.Condition.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

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
