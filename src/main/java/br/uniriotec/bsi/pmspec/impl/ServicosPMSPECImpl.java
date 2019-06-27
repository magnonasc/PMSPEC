package br.uniriotec.bsi.pmspec.impl;

import br.uniriotec.bsi.pmspec.api.GerenciadorMunicipios;
import br.uniriotec.bsi.pmspec.api.ServicosPMSPEC;
import br.uniriotec.bsi.pmspec.model.Municipio;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.*;

/**
 * Implementação dos serviços do sistema PMSPEC.
 *
 * @author Magno Nascimento
 */
public class ServicosPMSPECImpl implements ServicosPMSPEC {

    private final GerenciadorMunicipios gerenciadorMunicipios;

    // TODO
    public ServicosPMSPECImpl(@Nonnull final GerenciadorMunicipios gerenciadorMunicipios) {
        this.gerenciadorMunicipios = checkNotNull(gerenciadorMunicipios);
    }

    @Override
    public Stream<String> buscarPontosInteresse(final String siglaUF, final String nomeMunicipio){
        return buscarPontosInteresse(gerenciadorMunicipios.buscarMunicipio(siglaUF, nomeMunicipio).orElseThrow(() -> new IllegalArgumentException("O município com o nome ou UF fornecido não foi encontrado.")));
    }

   @Override
    public Stream<String> buscarPontosInteresse(final long geocodigo){
       return buscarPontosInteresse(gerenciadorMunicipios.buscarMunicipio(geocodigo).orElseThrow(() -> new IllegalArgumentException("O município com o código fornecido não foi encontrado.")));
   }

    /**
     * Método auxiliar para a busca por pontos de interesse.
     *
     * @param municipio O município onde a busca será realizada.
     * @return Os pontos de interesse encontrados.
     */
   private Stream<String> buscarPontosInteresse(@Nonnull final Municipio municipio) {
        return null; // TODO implementar
   }

}
