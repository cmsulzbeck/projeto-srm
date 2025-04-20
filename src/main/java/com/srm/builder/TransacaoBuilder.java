package com.srm.builder;

import com.srm.entity.Produto;
import com.srm.entity.Transacao;
import com.srm.enums.TipoTransacao;
import java.math.BigDecimal;
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

    public TransacaoBuilder comValor(BigDecimal valor) {
        this.transacao.setValor(valor);
        return this;
    }
    public TransacaoBuilder comData(LocalDateTime data) {
        this.transacao.setDataTransacao(data);
        return this;
    }
    public TransacaoBuilder comTipo(TipoTransacao tipo) {
        this.transacao.setTipoTransacao(tipo);
        return this;
    }

    public TransacaoBuilder comDescricao(String descricao) {
        this.transacao.setDescricao(descricao);
        return this;
    }

    public Transacao build() {
        return this.transacao;
    }
} 