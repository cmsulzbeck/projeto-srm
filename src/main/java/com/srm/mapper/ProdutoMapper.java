package com.srm.mapper;

import com.srm.dto.ProdutoDTO;
import com.srm.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    @Mapping(source = "moeda.codigo", target = "moedaCodigo")
    @Mapping(source = "quantidadeEstoque", target = "quantidadeEstoque")
    ProdutoDTO toDTO(Produto produto);
    
    @Mapping(target = "moeda", ignore = true)
    @Mapping(source = "quantidadeEstoque", target = "quantidadeEstoque")
    Produto toEntity(ProdutoDTO dto);
    
    @Mapping(target = "moeda", ignore = true)
    @Mapping(source = "quantidadeEstoque", target = "quantidadeEstoque")
    void updateEntityFromDTO(ProdutoDTO dto, @MappingTarget Produto produto);
} 