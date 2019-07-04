package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.PMSpecModule;
import br.uniriotec.bsi.pmspec.api.LeitorMunicipios;
import br.uniriotec.bsi.pmspec.api.LeitorOSM;
import br.uniriotec.bsi.pmspec.model.Municipio;
import br.uniriotec.bsi.pmspec.model.PontoInteresse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LeitorOSMOverpassTest {

    private static final String NOME_RECURSO_PONTOS_INTERESSE_CABO_FRIO = "CaboFrio.osm";

    /**
     * Testa o leitor OSM retornado pela API Overpass.
     *
     * @throws IOException Se algum erro de entrada/saída ocorrer.
     */
    @Test
    public void testeLeitorArquivoOSM() throws IOException {
        final LeitorOSM leitorOSM = new LeitorOSMOverpass(getClass().getResourceAsStream(NOME_RECURSO_PONTOS_INTERESSE_CABO_FRIO));

        assertThat(leitorOSM.lerOSM(), containsInAnyOrder(new PontoInteresse(PontoInteresse.Tipo.RODOVIA, "Rua Henrique Terra"), new PontoInteresse(PontoInteresse.Tipo.AEROPORTO, "Base Aeronaval de São Pedro da Aldeia")));
    }

}
