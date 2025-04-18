package com.srm.builder;

import com.srm.entity.Produto;
import com.srm.entity.Transacao;
import com.srm.entity.TipoTransacao;
import java.time.LocalDateTime;

public class TransacaoBuilder {
    private Transacao transacao;

    public TransacaoBuilder() {
        this.transacao = new Transacao();
    }

    public TransacaoBuilder comProduto(Produto produto) {
        this.transacao.setProduto(produto);
        return this;
    }

    public TransacaoBuilder comQuantidade(Integer quantidade) {
        this.transacao.setQuantidade(quantidade);
        return this;
    }

    public TransacaoBuilder comValorTotal(Double valorTotal) {
        this.transacao.setValorTotal(valorTotal);
        return this;
    }

    public TransacaoBuilder comDataTransacao(LocalDateTime dataTransacao) {
        this.transacao.setDataTransacao(dataTransacao);
        return this;
    }

    public TransacaoBuilder comTipo(TipoTransacao tipo) {
        this.transacao.setTipo(tipo);
        return this;
    }

    public Transacao build() {
        return this.transacao;
    }
} 