package com.srm.mapper;

import com.srm.dto.ReinoDTO;
import com.srm.entity.Reino;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReinoMapper {
    
    Reino toEntity(ReinoDTO reinoDTO);
    
    ReinoDTO toDTO(Reino reino);
    
    void updateEntity(ReinoDTO reinoDTO, @MappingTarget Reino reino);
} 