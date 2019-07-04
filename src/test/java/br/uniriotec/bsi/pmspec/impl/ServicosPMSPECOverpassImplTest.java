package br.uniriotec.bsi.pmspec.impl;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp;
import static com.xebialabs.restito.semantics.Action.*;
import static com.xebialabs.restito.semantics.Condition.*;
import static io.restassured.RestAssured.expect;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.*;
import com.google.common.net.HttpHeaders;
import com.xebialabs.restito.builder.stub.StubHttp;
import com.xebialabs.restito.server.StubServer;
import io.restassured.internal.http.URIBuilder;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

public class ServicosPMSPECOverpassImplTest {

	private StubServer server;

	@BeforeEach
	public void start() {
		server = new StubServer().run();
	}

	@AfterEach
	public void stop() {
		server.stop();
	}

	@Ignore
	@Test
	public void buscarPontoInteresseTest() throws IOException {
		StubHttp.whenHttp(server).
				match(get("/api/map?bbox=1.0,0.0,3.0,1.0")).
				then(ok());

		final GerenciadorMunicipios gerenciadorMunicipios = Mockito.mock(GerenciadorMunicipios.class);

		final AreaMunicipio areaMunicipio = Mockito.mock(AreaMunicipio.class);
		Mockito.when(areaMunicipio.calcularBoundingBox()).thenReturn(new BoundingBox(0,1,1,3));

		final Municipio municipio = Mockito.mock(Municipio.class);
		Mockito.when(municipio.getArea()).thenReturn(areaMunicipio);

		Mockito.when(gerenciadorMunicipios.buscarMunicipio(1)).thenReturn(Optional.of(new Municipio(1, "AA", "Cidade Teste", new AreaMunicipio(new Poligono(new Coordenada(0, 1), new Coordenada(1, 3), new Coordenada(0, 1))))));

		final ServicosPMSPEC servicosPMSPEC = new ServicosPMSPECOverpassImpl(gerenciadorMunicipios, URI.create("http://localhost:" + server.getPort()));

		try {
			servicosPMSPEC.buscarPontosInteresse(1);
		} catch(final IOException ioException) {
			verifyHttp(server).once(get("/api/map?bbox=1.0,0.0,3.0,1.0"));
		}
	}

}
