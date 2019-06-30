package br.uniriotec.bsi.pmspec.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.*;

/**
 * Classe representando os pontos de interesse encontrados em uma determinada área.
 *
 * @author Magno Nascimento
 */
public class PontoInteresse {

    public static final String SEM_NOME = "SEM NOME";

    private final Coordenada coordenada;
    private final Tipo tipo;
    private final String nome;

    /**
     * Construtor do ponto de interesse.
     *
     * @param coordenada A coordenada do ponto de interesse.
     * @param tipo O tipo do ponto de interesse.
     * @param nome O nome do ponto de interesse, <code>null</code> caso não haja ou não tenha sido encontrado, neste caso, {@link #SEM_NOME} será utilizado.
     */
    public PontoInteresse(@Nonnull final Coordenada coordenada, @Nonnull final Tipo tipo, @Nullable final String nome) {
        checkNotNull(coordenada, "A coordenada do ponto de interesse não pode ser null.");
        checkNotNull(tipo, "O tipo do ponto de interesse não pode ser null.");

        this.coordenada = coordenada;
        this.tipo = tipo;
        this.nome = nome != null ? nome : SEM_NOME;
    }

    /** @return A coordenada do ponto de interesse. */
    public Coordenada getCoordenada() {
        return coordenada;
    }

    /** @return O tipo do ponto de interesse. */
    public Tipo getTipo() {
        return tipo;
    }

    /** @return O nome do ponto de interesse. */
    public String getNome() {
        return nome;
    }

    /**
     * Enumeração dos tipos de um ponto de interesse.
     *
     * @author Magno Nascimento
     */
    public enum Tipo {
        FERROVIA, PORTO, AEROPORTO, ESTRADA
    }

}
