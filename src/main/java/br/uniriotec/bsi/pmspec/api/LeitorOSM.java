package br.uniriotec.bsi.pmspec.api;

import java.io.IOException;

/**
 * API do leitor dos pontos de interesse em um arquivo OSM.
 * 
 * @author Vinícius Zamith, Roberta Dias e Magno Nascimento
 */
public interface LeitorOSM {

    /**
     * Lê os pontos de interesse no arquivo OSM fornecido.
     *
     * @return Todos os pontos de interesse encontrados no arquivo.
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     * */
	public void lerOSM() throws IOException;
}
