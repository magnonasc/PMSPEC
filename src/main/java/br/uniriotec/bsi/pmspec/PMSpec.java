package br.uniriotec.bsi.pmspec;

import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.PontoInteresse;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

/**
 * Classe principal da interface do sistema com o usuário.
 *
 * @author Magno Nascimento
 * @implNote A comunicação feita por essa implementação será via linha de
 * comando.
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

        try {

            servicosPMSPEC.inicializar();
            try (final Scanner scanner = new Scanner(System.in)) {

                System.out.println("Digite nome (com letra maiúscula) ou código do município");
                if (scanner.hasNextLong()) {
                    Long geoCodigo = scanner.nextLong();
                    servicosPMSPEC.buscarPontosInteresse(geoCodigo);
                } else {
                    String municipio = scanner.nextLine();
                    System.out.println("Digite o nome da UF");
                    String uf = scanner.nextLine();
                    Set<PontoInteresse> pontosInteresse = servicosPMSPEC.buscarPontosInteresse(uf.toUpperCase(), municipio);
                    pontosInteresse.forEach(System.out::println);
                }
            }

        } catch (final IllegalArgumentException illegalArgumentException) {
            System.err.println(illegalArgumentException.getMessage());
            System.exit(0);
        } catch (final IOException ioException) {
            System.err.println(ioException.getMessage());
            System.exit(1);
        }

        // TODO tratar os argumentos e chamar servicosPMSPEC.buscarPontosInteresse()
    }
}
