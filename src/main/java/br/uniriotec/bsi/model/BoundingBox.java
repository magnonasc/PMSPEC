package br.uniriotec.bsi.model;

import static com.google.common.base.Preconditions.*;

/**
 * Classe criada para representar uma bounding box baseada em coordenadas.
 *
 * @author Magno Nascimento
 */
public class BoundingBox {

    private final double minLatitude;
    private final double maxLatitude;
    private final double minLongitude;

    private final double maxLongitude;

    /**
     * Construtor da bounding box.
     *
     * @param minLatitude O menor valor para a latitude da bounding box.
     * @param maxLatitude O maior valor para a latitude da bounding box.
     * @param minLongitude O menor valor para a longitude da bounding box.
     * @param maxLongitude O maior valor para a longitude da bounding box.
     */
    public BoundingBox(final double minLatitude, final double maxLatitude, final double minLongitude, final double maxLongitude) {
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
    }

    /** @return O valor mínimo de latitude da bounding box. */
    public double getMinLatitude() {
        return minLatitude;
    }

    /** @return O valor máximo de latitude da bounding box. */
    public double getMaxLatitude() {
        return maxLatitude;
    }

    /** @return O valor mínimo de longitude da bounding box. */
    public double getMinLongitude() {
        return minLongitude;
    }

    /** @return O valor máximo de longitude da bounding box. */
    public double getMaxLongitude() {
        return maxLongitude;
    }

}
