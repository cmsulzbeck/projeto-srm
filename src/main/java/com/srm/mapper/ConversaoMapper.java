package com.srm.mapper;

import com.srm.config.MapStructConfig;
import com.srm.dto.ConversaoDTO;
import com.srm.entity.Transacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class, MoedaMapper.class}, config = MapStructConfig.class)
public interface ConversaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "dataTransacao", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "valor", source = "valorConvertido")
    @Mapping(target = "tipoTransacao", constant = "TRANSFERENCIA")
    @Mapping(target = "moedaOrigem", ignore = true)
    @Mapping(target = "moedaDestino", ignore = true)
    @Mapping(target = "descricao", expression = "java(\"Conversão de \" + conversaoDTO.getMoedaOrigem() + \" para \" + conversaoDTO.getMoedaDestino() + \" com taxa de \" + conversaoDTO.getTaxaCambio())")
    Transacao toEntity(ConversaoDTO conversaoDTO);

    @Mapping(target = "moedaOrigem", source = "moedaOrigem.codigo")
    @Mapping(target = "moedaDestino", source = "moedaDestino.codigo")
    @Mapping(target = "produto", source = "produto.nome")
    @Mapping(target = "valorOriginal", source = "valor")
    @Mapping(target = "valorConvertido", source = "valor")
    @Mapping(target = "taxaCambio", constant = "1.0")
    ConversaoDTO toDTO(Transacao transacao);
} 