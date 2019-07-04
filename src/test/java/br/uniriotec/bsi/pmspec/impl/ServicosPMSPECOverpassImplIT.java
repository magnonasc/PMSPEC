package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.PMSpecModule;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ServicosPMSPECOverpassImplIT {

	/**
	 * Teste de integração do serviço com a API Overpass.
	 *
	 * @throws IOException        Se algum erro de entrada/saída ocorrer.
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

}
