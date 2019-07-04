package br.uniriotec.bsi.pmspec.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.List;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.uniriotec.bsi.pmspec.model.PontoInteresse;
import br.uniriotec.bsi.pmspec.model.PontoInteresse.Tipo;

/**
 * Implementação da Leitura do retorno da API Overpass e criação dos pontos de
 * interesse do município.
 * 
 * @author Vinícius Zamith, Magno Nascimento e Roberta Dias
 */
public class LeitorOSMOverpass {

	private final InputStream inputStream;

	/**
	 * Construtor do leitor do OSM do Overpass.
	 * 
	 * @param inputStream é o retorno da API quando se busca pela determinada
	 *                    BoudingBox
	 */
	public LeitorOSMOverpass(@Nonnull final InputStream inputStream) {
		this.inputStream = checkNotNull(inputStream);
	}

	/**
	 * Leitor do OSM. Procura a tag way para identificar as informações dos pontos
	 * de interesse.
	 * 
	 * @return O conjunto de pontos de interesse.
     * @throws IOException Se algum erro de entrada/saída ocorrer.
	 */
	public Set<PontoInteresse> lerOSM() throws IOException {
		final Set<PontoInteresse> pontosInteresse = new HashSet<PontoInteresse>();
		
		final Document documentoOSM;

		try {
			documentoOSM = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
		} catch (ParserConfigurationException | SAXException exception) {
			throw new IOException("Um erro ocorreu na leitura do arquivo OSM.", exception);
		}

		final Element elementoRaiz = documentoOSM.getDocumentElement();

		NodeList elementoRaizChildNodes = elementoRaiz.getChildNodes();
		for (int i = 0; i < elementoRaizChildNodes.getLength(); i++) {
			final Node elementoRaizChildNodesItem = elementoRaizChildNodes.item(i);
			if (elementoRaizChildNodesItem.getNodeName() == "way") {
				final PontoInteresse pontoInteresse = lerTag(elementoRaizChildNodesItem);
				
				if(pontoInteresse != null) {
					pontosInteresse.add(pontoInteresse);
				}
				
			}
		}
		
		return pontosInteresse;
	}

	/**
	 * Função que lê os nós filhos da nó way para buscar por pontos de interesse.
	 * 
	 * @param noWay tag que será efetuada a busca por pontos de interesse.
	 * @return um Set com os pontos de interesse daquele município.
	 */
	public PontoInteresse lerTag(Node noWay) {
		NodeList noWayChildNodes = noWay.getChildNodes();
		for (int i = 0; i < noWayChildNodes.getLength(); i++) {
			final Node noWayChildNodesItem = noWayChildNodes.item(i);

			if (noWayChildNodesItem.getNodeType() == Node.ELEMENT_NODE) {
				Element elementTag = (Element) noWayChildNodesItem;
				if (elementTag.hasAttribute("k")) {
					if (elementTag.getAttribute("k").equalsIgnoreCase("highway")
							&& elementTag.getAttribute("v").equalsIgnoreCase("primary")) {
						return new PontoInteresse(Tipo.RODOVIA, lerAtributoNome(noWayChildNodes));
					} else if (elementTag.getAttribute("k").contentEquals("aeroway")
							&& elementTag.getAttribute("v").contentEquals("aerodrome")) {
						return new PontoInteresse(Tipo.AEROPORTO, lerAtributoNome(noWayChildNodes));
					} else if (elementTag.getAttribute("k").contentEquals("railway")
							&& elementTag.getAttribute("v").contentEquals("rail")) {
						return new PontoInteresse(Tipo.FERROVIA, lerAtributoNome(noWayChildNodes));
					} else if (elementTag.getAttribute("k").contentEquals("landuse")
							&& elementTag.getAttribute("v").contentEquals("harbour")) {
						return new PontoInteresse(Tipo.PORTO, lerAtributoNome(noWayChildNodes));
					}

				}
			}
		}

		return null;
	}

	/**
	 * Efetua a busca e leitura das keys com value name.
	 * 
	 * @param noWayChildNodes nós filhos do nó way
	 * @return retorna o value da key name para o método de cima, que é utilizado
	 *         para a criação de um objeto da classe PontoInteresse.
	 */
	public String lerAtributoNome(NodeList noWayChildNodes) {
		for (int i = 0; i < noWayChildNodes.getLength(); i++) {
			final Node noWayChildNodesItem = noWayChildNodes.item(i);

			if (noWayChildNodesItem.getNodeType() == Node.ELEMENT_NODE) {
				final Element elementNoWay = (Element) noWayChildNodesItem;

				if (elementNoWay.getAttribute("k").equalsIgnoreCase("name")) {
					return elementNoWay.getAttribute("v");
				}
			}
		}
		return null;
	}

	public static ArrayList<String> removerDuplicados(ArrayList<String> pontosInteresse) {
		ArrayList<String> pontosInteresseSemDuplicados = new ArrayList<String>();
		for (int i = 0; i < pontosInteresse.size(); i++) {
			for (int j = (i + 1); j < pontosInteresse.size(); j++) {
				if (!pontosInteresseSemDuplicados.get(i).equals(pontosInteresse.get(j))) {
					pontosInteresseSemDuplicados.add(pontosInteresse.get(j));
				}
			}
		}
		

		return pontosInteresseSemDuplicados;
	}

}
