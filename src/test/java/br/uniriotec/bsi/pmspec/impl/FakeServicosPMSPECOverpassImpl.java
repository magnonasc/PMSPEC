package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.model.BoundingBox;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implementação falsificada dos serviços do sistema PMSPEC utilizando a API Overpass.
 *
 * @author Magno Nascimento
 */
public class FakeServicosPMSPECOverpassImpl extends ServicosPMSPECOverpassImpl {

    private static final String NOME_RECURSO_PONTOS_INTERESSE_CABO_FRIO = "CaboFrio.osm";

    /**
     * Construtor do gerenciador de municípios.
     *
     * @param gerenciadorMunicipios O gerenciador de municípios.
     */
    public FakeServicosPMSPECOverpassImpl(@Nonnull final GerenciadorMunicipios gerenciadorMunicipios) {
        super(gerenciadorMunicipios);
    }

    @Override
    InputStream obterDadosMunicipio(@Nonnull final BoundingBox boundingBox) throws IOException {
        return getClass().getResourceAsStream(NOME_RECURSO_PONTOS_INTERESSE_CABO_FRIO);
    }

}
