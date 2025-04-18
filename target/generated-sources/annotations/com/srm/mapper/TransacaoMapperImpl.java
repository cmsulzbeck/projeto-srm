package com.srm.mapper;

import com.srm.dto.TransacaoDTO;
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
public class TransacaoMapperImpl implements TransacaoMapper {

    @Override
    public TransacaoDTO toDTO(Transacao entity) {
        if ( entity == null ) {
            return null;
        }

        TransacaoDTO transacaoDTO = new TransacaoDTO();

        transacaoDTO.setProdutoId( entityProdutoId( entity ) );
        transacaoDTO.setId( entity.getId() );
        transacaoDTO.setQuantidade( entity.getQuantidade() );
        transacaoDTO.setValorTotal( entity.getValorTotal() );
        transacaoDTO.setDataTransacao( entity.getDataTransacao() );
        if ( entity.getTipo() != null ) {
            transacaoDTO.setTipo( entity.getTipo().name() );
        }

        return transacaoDTO;
    }

    @Override
    public Transacao toEntity(TransacaoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transacao transacao = new Transacao();

        transacao.setQuantidade( dto.getQuantidade() );
        transacao.setValorTotal( dto.getValorTotal() );
        transacao.setDataTransacao( dto.getDataTransacao() );
        if ( dto.getTipo() != null ) {
            transacao.setTipo( Enum.valueOf( TipoTransacao.class, dto.getTipo() ) );
        }

        return transacao;
    }

    private Long entityProdutoId(Transacao transacao) {
        if ( transacao == null ) {
            return null;
        }
        Produto produto = transacao.getProduto();
        if ( produto == null ) {
            return null;
        }
        Long id = produto.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
