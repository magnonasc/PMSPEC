package br.uniriotec.bsi.model;

import java.util.Comparator;

/**
 * Classe criada para representar uma coordenada.
 *
 * @author Magno Nascimento
 */
public class Coordenada {

    public static final Comparator<Coordenada> COMPARADOR_POR_LATITUDE;
    public static final Comparator<Coordenada> COMPARADOR_POR_LONGITUDE;

    static{
        COMPARADOR_POR_LATITUDE = Comparator.comparingDouble(Coordenada::getLatitude);
        COMPARADOR_POR_LONGITUDE = Comparator.comparingDouble(Coordenada::getLongitude);
    }

    private final double latitude;
    private final double longitude;

    /**
     * Construtor da coordenadas.
     *
     * @param latitude A latitude da coordenada.
     * @param longitude A longitude da coordenada.
     */
    public Coordenada(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /** @return A latitude da coordenada. */
    public double getLatitude() {
        return latitude;
    }

    /** @return A longitude da coordenada. */
    public double getLongitude() {
        return longitude;
    }

}
