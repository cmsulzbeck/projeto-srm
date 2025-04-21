package com.srm.mapper;

import com.srm.dto.ReinoDTO;
import com.srm.entity.Reino;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-21T15:54:56-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class ReinoMapperImpl implements ReinoMapper {

    @Override
    public Reino toEntity(ReinoDTO reinoDTO) {
        if ( reinoDTO == null ) {
            return null;
        }

        Reino reino = new Reino();

        reino.setDescricao( reinoDTO.getDescricao() );
        reino.setId( reinoDTO.getId() );
        reino.setNome( reinoDTO.getNome() );

        return reino;
    }

    @Override
    public ReinoDTO toDTO(Reino reino) {
        if ( reino == null ) {
            return null;
        }

        ReinoDTO reinoDTO = new ReinoDTO();

        reinoDTO.setDescricao( reino.getDescricao() );
        reinoDTO.setId( reino.getId() );
        reinoDTO.setNome( reino.getNome() );

        return reinoDTO;
    }

    @Override
    public void updateEntity(ReinoDTO reinoDTO, Reino reino) {
        if ( reinoDTO == null ) {
            return;
        }

        if ( reinoDTO.getDescricao() != null ) {
            reino.setDescricao( reinoDTO.getDescricao() );
        }
        if ( reinoDTO.getId() != null ) {
            reino.setId( reinoDTO.getId() );
        }
        if ( reinoDTO.getNome() != null ) {
            reino.setNome( reinoDTO.getNome() );
        }
    }
}
