package com.srm.mapper;

import com.srm.dto.MoedaDTO;
import com.srm.entity.Moeda;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-18T03:31:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Oracle Corporation)"
)
@Component
public class MoedaMapperImpl implements MoedaMapper {

    @Override
    public MoedaDTO toDTO(Moeda moeda) {
        if ( moeda == null ) {
            return null;
        }

        MoedaDTO moedaDTO = new MoedaDTO();

        moedaDTO.setId( moeda.getId() );
        moedaDTO.setNome( moeda.getNome() );
        moedaDTO.setCodigo( moeda.getCodigo() );
        moedaDTO.setTaxaCambio( moeda.getTaxaCambio() );
        moedaDTO.setDataAtualizacao( moeda.getDataAtualizacao() );

        return moedaDTO;
    }

    @Override
    public Moeda toEntity(MoedaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Moeda.MoedaBuilder moeda = Moeda.builder();

        moeda.id( dto.getId() );
        moeda.nome( dto.getNome() );
        moeda.codigo( dto.getCodigo() );
        moeda.taxaCambio( dto.getTaxaCambio() );
        moeda.dataAtualizacao( dto.getDataAtualizacao() );

        return moeda.build();
    }
}
