package br.uniriotec.bsi.pmspec.api;

import br.uniriotec.bsi.pmspec.model.Municipio;

import java.io.IOException;
import java.util.List;

/**
 * API do leitor das informações de um município.
 *
 * @author Magno Nascimento
 */
public interface LeitorMunicipios {

    /**
     * Lê os municípios no arquivo de caminho fornecido.
     *
     * @return Todos os municípios encontrados no arquivo.
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    public List<Municipio> lerMunicipios() throws IOException;

}
