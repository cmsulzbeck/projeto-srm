package com.srm.mapper;

import com.srm.dto.MoedaDTO;
import com.srm.entity.Moeda;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-20T02:13:23-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class MoedaMapperImpl implements MoedaMapper {

    @Override
    public MoedaDTO toDTO(Moeda moeda) {
        if ( moeda == null ) {
            return null;
        }

        MoedaDTO moedaDTO = new MoedaDTO();

        moedaDTO.setCodigo( moeda.getCodigo() );
        moedaDTO.setDataAtualizacao( moeda.getDataAtualizacao() );
        moedaDTO.setId( moeda.getId() );
        moedaDTO.setNome( moeda.getNome() );
        moedaDTO.setTaxaCambio( moeda.getTaxaCambio() );

        return moedaDTO;
    }

    @Override
    public Moeda toEntity(MoedaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Moeda.MoedaBuilder moeda = Moeda.builder();

        moeda.codigo( dto.getCodigo() );
        moeda.dataAtualizacao( dto.getDataAtualizacao() );
        moeda.id( dto.getId() );
        moeda.nome( dto.getNome() );
        moeda.taxaCambio( dto.getTaxaCambio() );

        return moeda.build();
    }
}
