package br.uniriotec.bsi.pmspec.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Testes para a classe {@link AreaMunicipio}.
 *
 * @author Magno Nascimento
 */
public class AreaMunicipioTest {

	
	public void inicializandoVariaveisParaTestes() throws IOException {
		List<Coordenada> listaCoordenada1 = new ArrayList<Coordenada>();
		List<Coordenada> listaCoordenada2 = new ArrayList<Coordenada>();

		Coordenada coordenada1 = new Coordenada(10, 12);
		Coordenada coordenada2 = new Coordenada(1000, -30);
		Coordenada coordenada3 = new Coordenada(0, 12.3);
		Coordenada coordenada4 = new Coordenada(-4.76, -100.2);
		Coordenada coordenada5 = new Coordenada(-500, 1.38238);
		Coordenada coordenada6 = new Coordenada(12313, 120);
		Coordenada coordenada7 = new Coordenada(10.2938, -123);
		Coordenada coordenada8 = new Coordenada(12.76, 123.2);
		Coordenada coordenada9 = new Coordenada(15.7293, 98.2039);
		Coordenada coordenada10 = new Coordenada(0, 0);

		listaCoordenada1.add(coordenada1);
		listaCoordenada1.add(coordenada2);
		listaCoordenada1.add(coordenada3);
		listaCoordenada1.add(coordenada4);
		listaCoordenada1.add(coordenada5);
		listaCoordenada2.add(coordenada6);
		listaCoordenada2.add(coordenada7);
		listaCoordenada2.add(coordenada8);
		listaCoordenada2.add(coordenada9);
		listaCoordenada2.add(coordenada10);
		
		Poligono poligono1 = new Poligono(listaCoordenada1);
		Poligono poligono2 = new Poligono(listaCoordenada2);
		
		testeCalcularBoudingBox(poligono1, poligono2);
	}
	@Test
	public void testeCalcularBoudingBox(Poligono poligono1, Poligono poligono2) throws IOException {
		List<Poligono> listaPoligono = new ArrayList<Poligono>();
		listaPoligono.add(poligono1);
		listaPoligono.add(poligono2);
		AreaMunicipio areaMunicipio = new AreaMunicipio(listaPoligono);
		
		BoundingBox respostaBoundingBox = new BoundingBox(-500, 12313, -123, 123.2);
		
		//assertThat(areaMunicipio.calcularBoundingBox(), equals(respostaBoundingBox));
	}
}
