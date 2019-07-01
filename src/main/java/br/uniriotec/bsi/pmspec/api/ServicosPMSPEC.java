package br.uniriotec.bsi.pmspec.api;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * API do sistema PMSPEC.
 *
 * @author Magno Nascimento
 */
public interface ServicosPMSPEC {

    /**
     * Inicializa o serviço.
     * @apiNote Implementações podem requerir que a execução deste método seja obrigatória antes de sua utilização.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    public void inicializar() throws IOException;

    /**
     * Busca pelos pontos de interesse do município especificado.
     *
     * @param siglaUF A sigla do município.
     * @param nomeMunicipio O nome do município.
     * @return Os pontos de interesse encontrados.
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     * @throws IllegalArgumentException Se o município com o nome fornecido não existe.
     */
    public Stream<String> buscarPontosInteresse(@Nonnull String siglaUF, @Nonnull String nomeMunicipio) throws IOException;

    /**
     * Busca pelos pontos de interesse do município especificado.
     *
     * @param geocodigo O código do município de acordo com o IBGE.
     * @return Os pontos de interesse encontrados.
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     * @throws IllegalArgumentException Se o município com o código fornecido não existe.
     */
    public Stream<String> buscarPontosInteresse(@Nonnegative long geocodigo) throws IOException;

}
