package br.uniriotec.bsi.pmspec;

import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.api.LeitorMunicipios;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.impl.GerenciadorMunicipiosImpl;
import br.uniriotec.bsi.pmspec.impl.LeitorMunicipiosArquivoKML;
import br.uniriotec.bsi.pmspec.impl.ServicosPMSPECOverpassImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Classe principal da interface do sistema com o usuário.
 * @implNote A comunicação feita por essa implementação será via linha de comando.
 *
 * @author Magno Nascimento
 */
public class PMSpec {

    /**
     * Método principal de execução da aplicação.
     *
     * @param args Os argumentos de entrada do programa.
     */
    public static void main(final String[] args) {
        final Injector injector = Guice.createInjector(new PMSpecModule());

        final ServicosPMSPEC servicosPMSPEC = injector.getInstance(ServicosPMSPEC.class);

        // TODO tratar os argumentos e chamar servicosPMSPEC.buscarPontosInteresse()
    }

}
