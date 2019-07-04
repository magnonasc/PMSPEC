package br.uniriotec.bsi.pmspec.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
     *
     * @param poligonos Os polígonos formando a área.
     * @implSpec Esse método simplesmente delega à {@link #AreaMunicipio(List)}.
     */
    // TODO transformar em private quando o builder for implementado.
    public AreaMunicipio(@Nonnull final Poligono... poligonos) {
        this(Arrays.asList(poligonos));
    }

    /**
     * Construtor da área do município.
     *
     * @param poligonos Os polígonos formando a área.
     */
    // TODO transformar em private quando o builder for implementado.
    public AreaMunicipio(@Nonnull final List<Poligono> poligonos) {
        checkNotNull(poligonos, "A lista de polígonos fornecidos não pode ser null.");
        checkArgument(!poligonos.isEmpty(), "A área do município deve ser formada por pelo menos um polígono.");

        this.poligonos = poligonos;
    }

    /** @return Os polígonos formando a área. */
    public List<Poligono> getPoligonos() {
        return Collections.unmodifiableList(poligonos);
    }

    /** @return A bounding box do município. */
    public BoundingBox calcularBoundingBox() {
        final List<Coordenada> somaCoordenadas = new ArrayList<>();

        poligonos.forEach(poligono -> somaCoordenadas.addAll(poligono.getCoordenadas()));

        final double minLatitude = somaCoordenadas.stream().min(Coordenada.COMPARADOR_POR_LATITUDE).orElseThrow(() -> new IllegalStateException("A latitude mínima do município não pôde ser encontrada.")).getLatitude();
        final double maxLatitude = somaCoordenadas.stream().max(Coordenada.COMPARADOR_POR_LATITUDE).orElseThrow(() -> new IllegalStateException("A latitude máxima do município não pôde ser encontrada.")).getLatitude();
        final double minLongitude = somaCoordenadas.stream().min(Coordenada.COMPARADOR_POR_LONGITUDE).orElseThrow(() -> new IllegalStateException("A longitude mínima do município não pôde ser encontrada.")).getLongitude();
        final double maxLongitude = somaCoordenadas.stream().max(Coordenada.COMPARADOR_POR_LONGITUDE).orElseThrow(() -> new IllegalStateException("A longitude máxima do município não pôde ser encontrada.")).getLongitude();

        return new BoundingBox(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof AreaMunicipio) {
            AreaMunicipio outraArea = (AreaMunicipio) object;

            if (this.getPoligonos().containsAll(outraArea.getPoligonos()) && outraArea.getPoligonos().containsAll(this.getPoligonos())) { // TODO verificar método mais eficiente.
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getPoligonos());
    }

    @Override
    public String toString() {
        return getPoligonos().toString();
    }

    /**
     * Estratégia de construção da classe {@link AreaMunicipio}.
     *
     * @apiNote Essa classe simplifica a criação de instâncias de {@link AreaMunicipio} ao transformar as chamadas:
     * <pre><code>
     *     new AreaMunicipio(new Poligono(new Coordenada(), ..., new Coordenada()), new Poligono(new Coordenada(), ..., new Coordenada()), new Poligono(new Coordenada(), ..., new Coordenada()));
     * </code></pre>
     * em:
     * <pre><code>
     *     final AreaMunicipioBuilder areaMunicipioBuilder = AreaMunicipio.builder();
     *     areaMunicipioBuilder.criarPoligono();
     *     areaMunicipioBuilder.adicionarCoordenadas(new Coordenada(...), ..., new Coordenada(...));
     *     areaMunicipioBuilder.criarPoligono();
     *     areaMunicipioBuilder.adicionarCoordenadas(new Coordenada(...), ..., new Coordenada(...));
     *     areaMunicipioBuilder.criarPoligono();
     *     areaMunicipioBuilder.adicionarCoordenadas(new Coordenada(...), ..., new Coordenada(...));
     *     final AreaMunicipio areaMunicipio = areaMunicipioBuilder.build;
     * </code></pre>
     *
     * @author Magno Nascimento
     */
    public static class AreaMunicipioBuilder {

        // TODO implementar

    }
}
