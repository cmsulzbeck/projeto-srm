package com.srm.mapper;

import com.srm.dto.ConversaoDTO;
import com.srm.entity.Moeda;
import com.srm.entity.Produto;
import com.srm.entity.Transacao;
import com.srm.enums.TipoTransacao;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-20T02:13:23-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class ConversaoMapperImpl implements ConversaoMapper {

    @Override
    public Transacao toEntity(ConversaoDTO conversaoDTO) {
        if ( conversaoDTO == null ) {
            return null;
        }

        Transacao.TransacaoBuilder transacao = Transacao.builder();

        transacao.valor( conversaoDTO.getValorConvertido() );

        transacao.dataTransacao( java.time.LocalDateTime.now() );
        transacao.tipoTransacao( TipoTransacao.TRANSFERENCIA );
        transacao.descricao( "Conversão de " + conversaoDTO.getMoedaOrigem() + " para " + conversaoDTO.getMoedaDestino() + " com taxa de " + conversaoDTO.getTaxaCambio() );

        return transacao.build();
    }

    @Override
    public ConversaoDTO toDTO(Transacao transacao) {
        if ( transacao == null ) {
            return null;
        }

        ConversaoDTO conversaoDTO = new ConversaoDTO();

        conversaoDTO.setMoedaOrigem( transacaoMoedaOrigemCodigo( transacao ) );
        conversaoDTO.setMoedaDestino( transacaoMoedaDestinoCodigo( transacao ) );
        conversaoDTO.setProduto( transacaoProdutoNome( transacao ) );
        conversaoDTO.setValorOriginal( transacao.getValor() );
        conversaoDTO.setValorConvertido( transacao.getValor() );

        conversaoDTO.setTaxaCambio( new BigDecimal( "1.0" ) );

        return conversaoDTO;
    }

    private String transacaoMoedaOrigemCodigo(Transacao transacao) {
        if ( transacao == null ) {
            return null;
        }
        Moeda moedaOrigem = transacao.getMoedaOrigem();
        if ( moedaOrigem == null ) {
            return null;
        }
        String codigo = moedaOrigem.getCodigo();
        if ( codigo == null ) {
            return null;
        }
        return codigo;
    }

    private String transacaoMoedaDestinoCodigo(Transacao transacao) {
        if ( transacao == null ) {
            return null;
        }
        Moeda moedaDestino = transacao.getMoedaDestino();
        if ( moedaDestino == null ) {
            return null;
        }
        String codigo = moedaDestino.getCodigo();
        if ( codigo == null ) {
            return null;
        }
        return codigo;
    }

    private String transacaoProdutoNome(Transacao transacao) {
        if ( transacao == null ) {
            return null;
        }
        Produto produto = transacao.getProduto();
        if ( produto == null ) {
            return null;
        }
        String nome = produto.getNome();
        if ( nome == null ) {
            return null;
        }
        return nome;
    }
}
