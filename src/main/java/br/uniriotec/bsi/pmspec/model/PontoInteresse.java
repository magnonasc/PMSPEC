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

    private final Tipo tipo;
    private final String nome;

    /**
     * Construtor do ponto de interesse.
     *
     * @param tipo O tipo do ponto de interesse.
     * @param nome O nome do ponto de interesse, <code>null</code> caso não haja ou não tenha sido encontrado, neste caso, {@link #SEM_NOME} será utilizado.
     */
    public PontoInteresse(@Nonnull final Tipo tipo, @Nullable final String nome) {
        checkNotNull(tipo, "O tipo do ponto de interesse não pode ser null.");

        this.tipo = tipo;
        this.nome = nome != null ? nome : SEM_NOME;
    }

    /** @return O tipo do ponto de interesse. */
    public Tipo getTipo() {
        return tipo;
    }

    /** @return O nome do ponto de interesse. */
    public String getNome() {
        return nome;
    }

    public String toString() {
        return String.format("Tipo: %s, Nome: %s", tipo, nome);
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof PontoInteresse) {
        	PontoInteresse outroPontoInteresse = (PontoInteresse) object;

            if (this.getNome().equals(outroPontoInteresse.getNome()) && this.getTipo() == outroPontoInteresse.getTipo()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getNome(), getTipo());
    }
    
    /**
     * Enumeração dos tipos de um ponto de interesse.
     *
     * @author Magno Nascimento
     */
    public enum Tipo {
        FERROVIA, PORTO, AEROPORTO, RODOVIA
    }

}
