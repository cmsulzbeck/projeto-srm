package com.srm.mapper;

import com.srm.dto.TaxaCambioProdutoDTO;
import com.srm.entity.TaxaCambioProduto;
import org.springframework.stereotype.Component;

@Component
public class TaxaCambioProdutoMapper {
    
    public TaxaCambioProdutoDTO toDTO(TaxaCambioProduto entity) {
        if (entity == null) {
            return null;
        }

        TaxaCambioProdutoDTO dto = new TaxaCambioProdutoDTO();
        dto.setId(entity.getId());
        dto.setProdutoId(entity.getProduto().getId());
        dto.setProdutoNome(entity.getProduto().getNome());
        dto.setMoedaOrigemId(entity.getMoedaOrigem().getId());
        dto.setMoedaOrigemCodigo(entity.getMoedaOrigem().getCodigo());
        dto.setMoedaDestinoId(entity.getMoedaDestino().getId());
        dto.setMoedaDestinoCodigo(entity.getMoedaDestino().getCodigo());
        dto.setTaxa(entity.getTaxa());
        dto.setDataAtualizacao(entity.getDataAtualizacao());
        
        return dto;
    }
} 