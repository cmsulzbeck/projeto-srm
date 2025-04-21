package com.srm.mapper;

import com.srm.dto.ProdutoDTO;
import com.srm.entity.Moeda;
import com.srm.entity.Produto;
import com.srm.entity.Reino;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-21T15:54:55-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public ProdutoDTO toDTO(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        ProdutoDTO.ProdutoDTOBuilder produtoDTO = ProdutoDTO.builder();

        produtoDTO.moedaCodigo( produtoMoedaCodigo( produto ) );
        produtoDTO.quantidadeEstoque( produto.getQuantidadeEstoque() );
        produtoDTO.reinoId( produtoReinoId( produto ) );
        produtoDTO.descricao( produto.getDescricao() );
        produtoDTO.id( produto.getId() );
        produtoDTO.nome( produto.getNome() );
        produtoDTO.preco( produto.getPreco() );

        return produtoDTO.build();
    }

    @Override
    public Produto toEntity(ProdutoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Produto.ProdutoBuilder produto = Produto.builder();

        produto.reino( produtoDTOToReino( dto ) );
        produto.quantidadeEstoque( dto.getQuantidadeEstoque() );
        produto.descricao( dto.getDescricao() );
        produto.id( dto.getId() );
        produto.nome( dto.getNome() );
        produto.preco( dto.getPreco() );

        return produto.build();
    }

    @Override
    public void updateEntityFromDTO(ProdutoDTO dto, Produto produto) {
        if ( dto == null ) {
            return;
        }

        produto.setQuantidadeEstoque( dto.getQuantidadeEstoque() );
        produto.setDescricao( dto.getDescricao() );
        produto.setNome( dto.getNome() );
        produto.setPreco( dto.getPreco() );
    }

    private String produtoMoedaCodigo(Produto produto) {
        if ( produto == null ) {
            return null;
        }
        Moeda moeda = produto.getMoeda();
        if ( moeda == null ) {
            return null;
        }
        String codigo = moeda.getCodigo();
        if ( codigo == null ) {
            return null;
        }
        return codigo;
    }

    private Long produtoReinoId(Produto produto) {
        if ( produto == null ) {
            return null;
        }
        Reino reino = produto.getReino();
        if ( reino == null ) {
            return null;
        }
        Long id = reino.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Reino produtoDTOToReino(ProdutoDTO produtoDTO) {
        if ( produtoDTO == null ) {
            return null;
        }

        Reino reino = new Reino();

        reino.setId( produtoDTO.getReinoId() );

        return reino;
    }
}
