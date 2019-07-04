package br.uniriotec.bsi.pmspec.api;

import br.uniriotec.bsi.pmspec.model.Municipio;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Optional;

/**
 * API do gerenciador de municípios do sistema.
 *
 * @author Magno Nascimento
 */
public interface GerenciadorMunicipios {

    /**
     * Inicializa o gerenciador, realizando todas as operações de leitura iniciais, caso necessário.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    public void carregarGerenciadorMunicipios() throws IOException;

    /**
     * Busca pelo município a partir de seu código do IBGE fornecido.
     *
     * @param geocodigo O código do IBGE do município.
     * @return O município, caso encontrado.
     */
    public Optional<Municipio> buscarMunicipio(@Nonnegative long geocodigo);

    /**
     * Busca pelo município a partir de seu código do IBGE fornecido.
     *
     * @param siglaUF       A sigla da UF na qual o município se encontra.
     * @param nomeMunicipio O nome do município.
     * @return O município, caso encontrado.
     */
    public Optional<Municipio> buscarMunicipio(@Nonnull final String siglaUF, @Nonnull final String nomeMunicipio);

}
