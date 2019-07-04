package br.uniriotec.bsi.pmspec.model;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Classe criada para representar um município do IBGE.
 *
 * @author Magno Nascimento
 */
public class Municipio {

    private final long geocodigo;
    private final String siglaUF;
    private final String nome;
    private final AreaMunicipio area;

    /**
     * Construtor do município.
     *
     * @param geocodigo O código do IBGE correspondente ao município.
     * @param siglaUF   A sigla da UF na qual o município se encontra.
     * @param nome      O nome do município.
     * @param area      A área do município.
     */
    public Municipio(@Nonnegative final long geocodigo, @Nullable final String siglaUF, @Nullable final String nome, @Nonnull final AreaMunicipio area) {
        checkNotNull(area, "A área do município não pode ser null.");

        checkArgument(geocodigo > 0, "O código do município não pode ser 0 ou negativo.");

        this.geocodigo = geocodigo;
        this.siglaUF = siglaUF;
        this.nome = nome;
        this.area = area;
    }

    /** @return O código do IBGE correspondente ao município. */
    public long getGeocodigo() {
        return geocodigo;
    }

    /** @return A sigla da UF na qual o município se encontra. */
    public String getSiglaUF() {
        return siglaUF;
    }

    /** @return O nome do município. */
    public String getNome() {
        return nome;
    }

    /** @return A área do município. */
    public AreaMunicipio getArea() {
        return area;
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Municipio) {
            Municipio outroMunicipio = (Municipio) object;

            if (this.getGeocodigo() == outroMunicipio.getGeocodigo() && this.getSiglaUF().equals(outroMunicipio.getSiglaUF()) && this.getNome().equals(outroMunicipio.getNome()) && this.getArea().equals(outroMunicipio.getArea())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(getGeocodigo(), getSiglaUF(), getNome(), getArea());
    }

    @Override
    public String toString() {
        return String.format("Código IBGE: %d, Nome: %s, UF: %s, Área: %s", getGeocodigo(), getNome(), getSiglaUF(), getArea());
    }
}
