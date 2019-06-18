package br.uniriotec.bsi.model;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.*;

/**
 * Classe criada para representar a área de um município com base em polígonos.
 *
 * @author Magno Nascimento
 */
public class AreaMunicipio {

    private final List<Poligono> poligonos;

    /**
     * Construtor da área do município.
     * @implSpec Esse método simplesmente delega à {@link #AreaMunicipio(List)}.
     *
     * @param poligonos Os polígonos formando a área.
     */
    public AreaMunicipio(@Nonnull final Poligono... poligonos) {
        this(Arrays.asList(poligonos));
    }

    /**
     * Construtor da área do município.
     *
     * @param poligonos Os polígonos formando a área.
     */
    public AreaMunicipio(@Nonnull final List<Poligono> poligonos) {
        checkNotNull(poligonos, "A lista de polígonos fornecidos não pode ser null.");
        checkArgument(!poligonos.isEmpty(), "A área do município deve ser formada por pelo menos um polígono.");

        this.poligonos = poligonos;
    }

    /** @return A bounding box do município. */
    public BoundingBox calculateBoundingBox() {
        final List<Coordenada> somaCoordenadas = new ArrayList<>();

        poligonos.forEach(poligono -> somaCoordenadas.addAll(poligono.getCoordenadas()));

        final double minLatitude = somaCoordenadas.stream().min(Coordenada.COMPARADOR_POR_LATITUDE).orElseThrow(() -> new IllegalStateException("A latitude mínima do município não pôde ser encontrada.")).getLatitude();
        final double maxLatitude = somaCoordenadas.stream().max(Coordenada.COMPARADOR_POR_LATITUDE).orElseThrow(() -> new IllegalStateException("A latitude máxima do município não pôde ser encontrada.")).getLatitude();
        final double minLongitude = somaCoordenadas.stream().min(Coordenada.COMPARADOR_POR_LONGITUDE).orElseThrow(() -> new IllegalStateException("A longitude mínima do município não pôde ser encontrada.")).getLongitude();
        final double maxLongitude = somaCoordenadas.stream().max(Coordenada.COMPARADOR_POR_LONGITUDE).orElseThrow(() -> new IllegalStateException("A longitude máxima do município não pôde ser encontrada.")).getLongitude();

        return new BoundingBox(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

}
