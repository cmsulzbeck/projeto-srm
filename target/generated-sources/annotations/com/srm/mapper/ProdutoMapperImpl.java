package com.srm.mapper;

import com.srm.dto.ProdutoDTO;
import com.srm.entity.Moeda;
import com.srm.entity.Produto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-18T03:31:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Oracle Corporation)"
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
        produtoDTO.id( produto.getId() );
        produtoDTO.nome( produto.getNome() );
        produtoDTO.descricao( produto.getDescricao() );
        produtoDTO.preco( produto.getPreco() );

        return produtoDTO.build();
    }

    @Override
    public Produto toEntity(ProdutoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Produto.ProdutoBuilder produto = Produto.builder();

        produto.quantidadeEstoque( dto.getQuantidadeEstoque() );
        produto.id( dto.getId() );
        produto.nome( dto.getNome() );
        produto.descricao( dto.getDescricao() );
        produto.preco( dto.getPreco() );

        return produto.build();
    }

    @Override
    public void updateEntityFromDTO(ProdutoDTO dto, Produto produto) {
        if ( dto == null ) {
            return;
        }

        produto.setQuantidadeEstoque( dto.getQuantidadeEstoque() );
        produto.setId( dto.getId() );
        produto.setNome( dto.getNome() );
        produto.setDescricao( dto.getDescricao() );
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
}
