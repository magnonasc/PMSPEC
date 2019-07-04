package br.uniriotec.bsi.pmspec;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import com.google.inject.Guice;
import com.google.inject.Injector;

import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.PontoInteresse;

/**
 * Classe principal da interface do sistema com o usuário.
 * 
 * @implNote A comunicação feita por essa implementação será via linha de
 *           comando.
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

		} catch (final IOException ioException) {
			System.err.println(ioException.getMessage());
			// ioException.printStackTrace(System.err);
			System.exit(1);
		}

		// TODO tratar os argumentos e chamar servicosPMSPEC.buscarPontosInteresse()
	}
}
