package com.srm.mapper;

import com.srm.config.MapStructConfig;
import com.srm.dto.MoedaDTO;
import com.srm.entity.Moeda;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = MapStructConfig.class)
public interface MoedaMapper {
    MoedaDTO toDTO(Moeda moeda);
    Moeda toEntity(MoedaDTO dto);
} 