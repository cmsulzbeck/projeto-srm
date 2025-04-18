package com.srm.mapper;

import com.srm.dto.MoedaDTO;
import com.srm.entity.Moeda;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoedaMapper {
    MoedaDTO toDTO(Moeda moeda);
    Moeda toEntity(MoedaDTO dto);
} 