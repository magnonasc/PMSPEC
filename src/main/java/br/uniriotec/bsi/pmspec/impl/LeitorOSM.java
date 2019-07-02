package br.uniriotec.bsi.pmspec.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import br.uniriotec.bsi.pmspec.model.Coordenada;
import br.uniriotec.bsi.pmspec.model.PontoInteresse;
import br.uniriotec.bsi.pmspec.model.PontoInteresse.Tipo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LeitorOSM {

	private final InputStream retornoAPI;

	public LeitorOSM(@Nonnull final InputStream retornoAPI) {
		this.retornoAPI = checkNotNull(retornoAPI);
	}

	public void lerOSM() throws IOException {
		Document documentoOSM = null;

		try {
			documentoOSM = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(retornoAPI);
		} catch (ParserConfigurationException | SAXException exception) {
			throw new IOException("Um erro ocorreu na leitura do arquivo OSM.", exception);
		}

		final Element elementoRaiz = documentoOSM.getDocumentElement();

		NodeList nosCoordenadas = elementoRaiz.getChildNodes();
		for (int i = 0; i < nosCoordenadas.getLength(); i++) {
			final Node noCoordenada = nosCoordenadas.item(i);
			Element elementCoordenada = null;
			if (noCoordenada.getNodeType() == Node.ELEMENT_NODE) {
				elementCoordenada = (Element) noCoordenada;
			}
			final NodeList nosCoordenadas2 = noCoordenada.getChildNodes();

			for (int j = 0; j < nosCoordenadas2.getLength(); j++) {
				final Node noTag = nosCoordenadas2.item(j);
				if (noTag.getNodeType() == Node.ELEMENT_NODE) {
					Element elementTag = (Element) noTag;
					lerTag(elementTag, elementCoordenada.getAttribute("lat"), elementCoordenada.getAttribute("lon"));
				}
			}
		}
	}

	public List<PontoInteresse> lerTag(Element elementTag, String latitudeString, String longitudeString) {
		double latitude = Double.parseDouble(latitudeString);
		double longitude = Double.parseDouble(longitudeString);
		final List<PontoInteresse> pontosInteresse = new ArrayList<PontoInteresse>();
		
		if (elementTag.hasAttribute("k")) {
			if (elementTag.getAttribute("k").contentEquals("highway")) {
				Coordenada coordenada = new Coordenada(latitude, longitude);
				PontoInteresse pontoInteresse = new PontoInteresse(coordenada, Tipo.RODOVIA, null);
				pontosInteresse.add(pontoInteresse);
			}

			if (elementTag.getAttribute("k").contentEquals("aeroway")) {
				Coordenada coordenada = new Coordenada(latitude, longitude);
				PontoInteresse pontoInteresse = new PontoInteresse(coordenada, Tipo.AEROPORTO, null);
				pontosInteresse.add(pontoInteresse);
			}

			if (elementTag.getAttribute("k").contentEquals("railway")) {
				Coordenada coordenada = new Coordenada(latitude, longitude);
				PontoInteresse pontoInteresse = new PontoInteresse(coordenada, Tipo.FERROVIA, null);
				pontosInteresse.add(pontoInteresse);
			}

			if (elementTag.getAttribute("k").contentEquals("port")) {
				System.out.println("Possuo porto");
				Coordenada coordenada = new Coordenada(latitude, longitude);
				PontoInteresse pontoInteresse = new PontoInteresse(coordenada, Tipo.PORTO, null);
				pontosInteresse.add(pontoInteresse);
			}

		}
		
		for (int i = 0; i < pontosInteresse.size(); i++) {
			System.out.println(pontosInteresse.get(i).toString());
		}

		return pontosInteresse;
	}
}
