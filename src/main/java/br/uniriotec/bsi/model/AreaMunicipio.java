package br.uniriotec.bsi.model;

import javax.annotation.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.*;

/**
 * Classe criada para representar a área de um município.
 *
 * @author Magno Nascimento
 */
public class AreaMunicipio {

    private final List<Coordenada> coordenadas;

    /**
     * Construtor da área do município.
     * @implSpec Esse método simplesmente delega à {@link #AreaMunicipio(List)}.
     *
     * @param coordenadas As coordenadas da área.
     */
    public AreaMunicipio(@Nonnull final Coordenada... coordenadas) {
        this(Arrays.asList(coordenadas));
    }

    /**
     * Construtor da área do município.
     *
     * @param coordenadas As coordenadas da área.
     * @throws IllegalArgumentException Se as coordenadas fornecidas forem insuficientes para formar a área de um município.
     */
    public AreaMunicipio(@Nonnull final List<Coordenada> coordenadas) {
        checkNotNull(coordenadas, "O conjunto de coordenadas para formar a área do município não pode ser null.");
        checkArgument(coordenadas.size() < 3, "O conjunto de coordenadas para formar a área do município é insuficiente");
        //TODO tratar caso onde a área fornecida é uma linha reta: área = 0.
        this.coordenadas = coordenadas;
    }

    /** @return As coordenadas do município, não modificáveis. */
    public List<Coordenada> getCoordenadas() {
        return Collections.unmodifiableList(coordenadas);
    }

    /** @return A bounding box do município. */
    public BoundingBox calculateBoundingBox() {
        final double minLatitude = getCoordenadas().stream().min(Coordenada.COMPARADOR_POR_LATITUDE).orElseThrow(() -> new IllegalStateException("A latitude mínima do município não pôde ser encontrada.")).getLatitude();
        final double maxLatitude = getCoordenadas().stream().max(Coordenada.COMPARADOR_POR_LATITUDE).orElseThrow(() -> new IllegalStateException("A latitude máxima do município não pôde ser encontrada.")).getLatitude();
        final double minLongitude = getCoordenadas().stream().min(Coordenada.COMPARADOR_POR_LONGITUDE).orElseThrow(() -> new IllegalStateException("A longitude mínima do município não pôde ser encontrada.")).getLongitude();
        final double maxLongitude = getCoordenadas().stream().min(Coordenada.COMPARADOR_POR_LONGITUDE).orElseThrow(() -> new IllegalStateException("A longitude máxima do município não pôde ser encontrada.")).getLongitude();

        return new BoundingBox(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

}
