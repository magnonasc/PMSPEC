package br.uniriotec.bsi.impl;

import br.uniriotec.bsi.api.GerenciadorMunicipios;
import br.uniriotec.bsi.api.LeitorMunicipios;
import br.uniriotec.bsi.model.Municipio;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.*;

/**
 * Implementação do gerenciador de municípios.
 *
 * @author Magno Nascimento
 */
public class GerenciadorMunicipiosImpl implements GerenciadorMunicipios {

    private static final long GEOCODIGO_INVALIDO = -1;

    private final LeitorMunicipios leitorMunicipios;

    private List<Municipio> municipios;

    /**
     * Construtor do gerenciador de municípios.
     *
     * @param leitorMunicipios O leitor de municípios.
     */
    public GerenciadorMunicipiosImpl(@Nonnull final LeitorMunicipios leitorMunicipios) {
        this.leitorMunicipios = checkNotNull(leitorMunicipios, "O leitor de municípios não pode ser null.");
    }

    /**
     * {@inheritDoc}
     * @implNote Este método precisa ser chamado antes que qualquer operação de leitura seja realizada.
     */
    public void carregarGerenciadorMunicipios() throws IOException {
        municipios = leitorMunicipios.lerMunicipios();
    }

    @Override
    public Optional<Municipio> buscarMunicipio(final long geocodigo) {
        return municipios.stream().filter(municipio -> municipio.getGeocodigo() == geocodigo).findFirst();
    }

    @Override
    public Optional<Municipio> buscarMunicipio(final String siglaUF, final String nomeMunicipio) {
        return municipios.stream().filter(municipio -> municipio.getSiglaUF().equals(siglaUF) && municipio.getNome().equals(nomeMunicipio)).findFirst();
    }

}
