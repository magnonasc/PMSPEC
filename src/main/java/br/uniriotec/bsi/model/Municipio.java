package br.uniriotec.bsi.model;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.*;

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
     * @param siglaUF A sigla da UF na qual o município se encontra.
     * @param nome O nome do município.
     * @param area A área do município.
     */
    public Municipio(@Nonnegative final long geocodigo, @Nonnull final String siglaUF, @Nonnull final String nome, @Nonnull final AreaMunicipio area) {
        checkNotNull(siglaUF, "A sigla da UF não pode ser null.");
        checkNotNull(nome, "O nome do município não pode ser null.");
        checkNotNull(area, "A área do município não pode ser null.");

        checkArgument(geocodigo > 0, "O código do município não pode ser negativo.");
        checkArgument(!siglaUF.isEmpty(), "A sigla da UF não pode ser vazia.");
        checkArgument(!nome.isEmpty(), "O nome do município não pode ser vazio.");

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

}
