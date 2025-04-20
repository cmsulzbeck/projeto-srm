package com.srm.mapper;

import com.srm.config.MapStructConfig;
import com.srm.dto.ProdutoDTO;
import com.srm.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface ProdutoMapper {
    @Mapping(source = "moeda.codigo", target = "moedaCodigo")
    @Mapping(source = "quantidadeEstoque", target = "quantidadeEstoque")
    @Mapping(target = "reinoId", source = "reino.id")
    ProdutoDTO toDTO(Produto produto);
    
    @Mapping(target = "moeda", ignore = true)
    @Mapping(target = "reino.id", source = "reinoId")
    @Mapping(target = "transacoes", ignore = true)
    @Mapping(source = "quantidadeEstoque", target = "quantidadeEstoque")
    Produto toEntity(ProdutoDTO dto);
    
    @Mapping(target = "moeda", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "transacoes", ignore = true)
    @Mapping(source = "quantidadeEstoque", target = "quantidadeEstoque")
    void updateEntityFromDTO(ProdutoDTO dto, @MappingTarget Produto produto);
} 