package br.uniriotec.bsi.pmspec.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Classe criada para representar uma área em forma de polígono com base em suas coordenadas.
 *
 * @author Magno Nascimento
 */
public class Poligono {

    private final List<Coordenada> coordenadas;

    /**
     * Construtor do polígono.
     *
     * @param coordenadas As coordenadas do polígono.
     * @implSpec Esse método simplesmente delega à {@link #Poligono(List)}.
     */
    public Poligono(@Nonnull final Coordenada... coordenadas) {
        this(Arrays.asList(coordenadas));
    }

    /**
     * Construtor do polígono.
     *
     * @param coordenadas As coordenadas do polígono.
     * @throws IllegalArgumentException Se as coordenadas fornecidas forem insuficientes para formar um polígono.
     */
    public Poligono(@Nonnull final List<Coordenada> coordenadas) {
        checkNotNull(coordenadas, "O conjunto de coordenadas para formar um polígono não pode ser null.");
        checkArgument(coordenadas.size() >= 3, "O conjunto de coordenadas para formar um polígono é insuficiente");
        //TODO tratar caso onde a área fornecida é uma linha reta: área = 0.
        this.coordenadas = coordenadas;
    }

    /** @return As coordenadas do polígono, não modificáveis. */
    public List<Coordenada> getCoordenadas() {
        return Collections.unmodifiableList(coordenadas);
    }

    /**
     * {@inheritDoc}
     * Esta implementação desconsidera a ordem das coordenadas no polígono.
     */
    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Poligono) {
            Poligono outraArea = (Poligono) object;

            if (this.getCoordenadas().containsAll(outraArea.getCoordenadas()) && outraArea.getCoordenadas().containsAll(this.getCoordenadas())) { // TODO verificar método mais eficiente.
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getCoordenadas());
    }

    @Override
    public String toString() {
        return getCoordenadas().toString();
    }
}
