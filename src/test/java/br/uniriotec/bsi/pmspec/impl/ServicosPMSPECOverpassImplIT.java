package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.PMSpecModule;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.PontoInteresse;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes de integração para a classe {@link ServicosPMSPECOverpassImpl}.
 *
 * @author Magno Nascimento, Roberta Dias e Vinícius Zamith
 */
public class ServicosPMSPECOverpassImplIT {

    /**
     * Teste de integração do serviço com a API Overpass.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void buscarPontoInteresseTest() throws IOException {
        final Injector injector = Guice.createInjector(new PMSpecModule());
        final ServicosPMSPEC servicosPMSPEC = injector.getInstance(ServicosPMSPEC.class);
        servicosPMSPEC.inicializar();

        final Set<PontoInteresse> pontosInteresse = servicosPMSPEC.buscarPontosInteresse("RJ", "Cabo Frio");

        assertThat(
                pontosInteresse, hasItems(new PontoInteresse(PontoInteresse.Tipo.AEROPORTO, "Base Aeronaval de São Pedro da Aldeia"), new PontoInteresse(PontoInteresse.Tipo.RODOVIA, "Estrada Porto do Carro")));
    }

    /**
     * Teste de integração do serviço com a API Overpass.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void buscarPontoInteresseFailTest() throws IOException {
        final Injector injector = Guice.createInjector(new PMSpecModule());
        final ServicosPMSPEC servicosPMSPEC = injector.getInstance(ServicosPMSPEC.class);
        servicosPMSPEC.inicializar();

        assertThrows(IllegalArgumentException.class, () -> servicosPMSPEC.buscarPontosInteresse("AA", "NÃO EXISTE"));
    }

    /**
     * Teste de integração do serviço com a API Overpass.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void buscarPontoInteresseNullUFFailTest() throws IOException {
        final Injector injector = Guice.createInjector(new PMSpecModule());
        final ServicosPMSPEC servicosPMSPEC = injector.getInstance(ServicosPMSPEC.class);
        servicosPMSPEC.inicializar();

        assertThrows(NullPointerException.class, () -> servicosPMSPEC.buscarPontosInteresse(null, "NÃO EXISTE"));
    }

    /**
     * Teste de integração do serviço com a API Overpass.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void buscarPontoInteresseNullNomeFailTest() throws IOException {
        final Injector injector = Guice.createInjector(new PMSpecModule());
        final ServicosPMSPEC servicosPMSPEC = injector.getInstance(ServicosPMSPEC.class);
        servicosPMSPEC.inicializar();

        assertThrows(NullPointerException.class, () -> servicosPMSPEC.buscarPontosInteresse("AA", null));
    }

    /**
     * Teste de integração do serviço com a API Overpass.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void buscarPontoInteresseNullAmbosFailTest() throws IOException {
        final Injector injector = Guice.createInjector(new PMSpecModule());
        final ServicosPMSPEC servicosPMSPEC = injector.getInstance(ServicosPMSPEC.class);
        servicosPMSPEC.inicializar();

        assertThrows(NullPointerException.class, () -> servicosPMSPEC.buscarPontosInteresse(null, null));
    }

}
