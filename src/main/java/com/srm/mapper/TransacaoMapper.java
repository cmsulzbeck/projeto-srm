package com.srm.mapper;

import com.srm.dto.TransacaoDTO;
import com.srm.entity.Transacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {
    @Mapping(target = "produtoId", source = "produto.id")
    TransacaoDTO toDTO(Transacao entity);

    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "id", ignore = true)
    Transacao toEntity(TransacaoDTO dto);
} 