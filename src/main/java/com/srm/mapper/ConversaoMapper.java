package com.srm.mapper;

import com.srm.config.MapStructConfig;
import com.srm.dto.ConversaoDTO;
import com.srm.entity.Transacao;
import com.srm.entity.TipoTransacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class}, config = MapStructConfig.class)
public interface ConversaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "dataTransacao", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "quantidade", constant = "1")
    @Mapping(target = "valorTotal", expression = "java(conversaoDTO.getValorConvertido().doubleValue())")
    @Mapping(target = "tipo", constant = "CONVERSAO")
    Transacao toEntity(ConversaoDTO conversaoDTO);

    @Mapping(target = "moedaOrigem", ignore = true)
    @Mapping(target = "moedaDestino", ignore = true)
    @Mapping(target = "produto", source = "produto.nome")
    @Mapping(target = "valorOriginal", expression = "java(java.math.BigDecimal.valueOf(transacao.getValorTotal()))")
    @Mapping(target = "valorConvertido", expression = "java(java.math.BigDecimal.valueOf(transacao.getValorTotal()))")
    @Mapping(target = "taxaCambio", ignore = true)
    ConversaoDTO toDTO(Transacao transacao);
} 