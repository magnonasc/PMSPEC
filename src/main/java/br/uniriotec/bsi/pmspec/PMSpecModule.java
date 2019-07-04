package br.uniriotec.bsi.pmspec;

import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.api.LeitorMunicipios;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.impl.GerenciadorMunicipiosImpl;
import br.uniriotec.bsi.pmspec.impl.LeitorMunicipiosArquivoKML;
import br.uniriotec.bsi.pmspec.impl.ServicosPMSPECOverpassImpl;
import com.google.inject.AbstractModule;

import javax.annotation.Nonnull;
import java.net.URL;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Classe de configuração de dependências do projeto.
 *
 * @author Magno Nascimento
 */
public class PMSpecModule extends AbstractModule {

    public static final URL CAMINHO_PADRAO_RECURSO_MUNICIPIOS = PMSpecModule.class.getResource("municipios.kml");

    private final URL caminhoArquivoKML;

    /**
     * Construtor do módulo.
     *
     * @implNote {@link #CAMINHO_PADRAO_RECURSO_MUNICIPIOS} será utilizado.
     */
    public PMSpecModule() {
        this(CAMINHO_PADRAO_RECURSO_MUNICIPIOS);
    }

    /**
     * Construtor do módulo.
     *
     * @param caminhoArquivoKML O caminho do arquivo contendo as informações do município.
     */
    public PMSpecModule(@Nonnull final URL caminhoArquivoKML) {
        checkNotNull(caminhoArquivoKML, "O arquivo do caminho KML contendo os dados do município não pode ser null.");

        this.caminhoArquivoKML = caminhoArquivoKML;
    }

    @Override
    protected void configure() {
        bind(LeitorMunicipios.class).toInstance(new LeitorMunicipiosArquivoKML(caminhoArquivoKML));
        bind(GerenciadorMunicipios.class).to(GerenciadorMunicipiosImpl.class);
        bind(ServicosPMSPEC.class).to(ServicosPMSPECOverpassImpl.class);
    }
}
