package com.srm.mapper;

import com.srm.dto.TransacaoDTO;
import com.srm.entity.Transacao;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {
    
    public TransacaoDTO toDTO(Transacao entity) {
        if (entity == null) {
            return null;
        }

        TransacaoDTO dto = new TransacaoDTO();
        dto.setId(entity.getId());
        dto.setProdutoId(entity.getProduto().getId());
        dto.setProdutoNome(entity.getProduto().getNome());
        dto.setReinoId(entity.getReino().getId());
        dto.setReinoNome(entity.getReino().getNome());
        dto.setMoedaOrigemId(entity.getMoedaOrigem().getId());
        dto.setMoedaOrigemCodigo(entity.getMoedaOrigem().getCodigo());
        dto.setMoedaDestinoId(entity.getMoedaDestino().getId());
        dto.setMoedaDestinoCodigo(entity.getMoedaDestino().getCodigo());
        dto.setValor(entity.getValor());
        dto.setDataTransacao(entity.getDataTransacao());
        dto.setTipoTransacao(entity.getTipoTransacao());
        dto.setDescricao(entity.getDescricao());
        
        return dto;
    }

    public Transacao toEntity(TransacaoDTO dto) {
        if (dto == null) {
            return null;
        }

        Transacao entity = new Transacao();
        entity.setId(dto.getId());
        entity.setValor(dto.getValor());
        entity.setDataTransacao(dto.getDataTransacao());
        entity.setTipoTransacao(dto.getTipoTransacao());
        entity.setDescricao(dto.getDescricao());
        
        return entity;
    }
} 