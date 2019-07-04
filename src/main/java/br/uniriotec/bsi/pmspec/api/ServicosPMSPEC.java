package br.uniriotec.bsi.pmspec.api;

import br.uniriotec.bsi.pmspec.model.PontoInteresse;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Set;

/**
 * API do sistema PMSPEC.
 *
 * @author Magno Nascimento
 */
public interface ServicosPMSPEC {

    /**
     * Inicializa o serviço.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     * @apiNote Implementações podem requerir que a execução deste método seja obrigatória antes de sua utilização.
     */
    public void inicializar() throws IOException;

    /**
     * Busca pelos pontos de interesse do município especificado.
     *
     * @param siglaUF       A sigla do município.
     * @param nomeMunicipio O nome do município.
     * @return Os pontos de interesse encontrados.
     * @throws IOException              Se algum erro de entrada/saída ocorrer.
     * @throws IllegalArgumentException Se o município com o nome fornecido não existe.
     */
    public Set<PontoInteresse> buscarPontosInteresse(@Nonnull String siglaUF, @Nonnull String nomeMunicipio) throws IOException;

    /**
     * Busca pelos pontos de interesse do município especificado.
     *
     * @param geocodigo O código do município de acordo com o IBGE.
     * @return Os pontos de interesse encontrados.
     * @throws IOException              Se algum erro de entrada/saída ocorrer.
     * @throws IllegalArgumentException Se o município com o código fornecido não existe.
     */
    public Set<PontoInteresse> buscarPontosInteresse(@Nonnegative long geocodigo) throws IOException;

}
