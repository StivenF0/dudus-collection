package com.ufersa.duduscollection.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="Produtos")
@Inheritance(strategy=InheritanceType.JOINED)
public class Produto {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nome;

    @Column(name="data_lancamento")
    private Date dataLancamento;

    @Column(name="qtd_exemplares", nullable=false, columnDefinition="int default 0")
    private int qtdExemplares;

    @Column(name="valor_aluguel", nullable=false, precision=10, scale=2)
    private BigDecimal valorAluguel;

    public Produto() {}

    public Produto(String nome, Date dataLancamento, int qtdExemplares, BigDecimal valorAluguel) {
        setNome(nome);
        setDataLancamento(dataLancamento);
        setQtdExemplares(qtdExemplares);
        setValorAluguel(valorAluguel);
    }

    public String getNome() {
        return nome;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public int getQtdExemplares() {
        return qtdExemplares;
    }

    public Long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public void setQtdExemplares(int qtdExemplares) {
        this.qtdExemplares = qtdExemplares;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }
}
