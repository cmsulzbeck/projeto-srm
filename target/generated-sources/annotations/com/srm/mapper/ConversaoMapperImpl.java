package com.srm.mapper;

import com.srm.dto.ConversaoDTO;
import com.srm.entity.Produto;
import com.srm.entity.TipoTransacao;
import com.srm.entity.Transacao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-18T03:31:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Oracle Corporation)"
)
@Component
public class ConversaoMapperImpl implements ConversaoMapper {

    @Override
    public Transacao toEntity(ConversaoDTO conversaoDTO) {
        if ( conversaoDTO == null ) {
            return null;
        }

        Transacao transacao = new Transacao();

        transacao.setDataTransacao( java.time.LocalDateTime.now() );
        transacao.setQuantidade( 1 );
        transacao.setValorTotal( conversaoDTO.getValorConvertido().doubleValue() );
        transacao.setTipo( TipoTransacao.CONVERSAO );

        return transacao;
    }

    @Override
    public ConversaoDTO toDTO(Transacao transacao) {
        if ( transacao == null ) {
            return null;
        }

        ConversaoDTO conversaoDTO = new ConversaoDTO();

        conversaoDTO.setProduto( transacaoProdutoNome( transacao ) );

        conversaoDTO.setValorOriginal( java.math.BigDecimal.valueOf(transacao.getValorTotal()) );
        conversaoDTO.setValorConvertido( java.math.BigDecimal.valueOf(transacao.getValorTotal()) );

        return conversaoDTO;
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
