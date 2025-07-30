package com.ufersa.duduscollection.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="Livros")
@PrimaryKeyJoinColumn(name="produto_id")
public class Livro extends Produto {
    @Column(length=100)
    private String genero;

    @Column(name="qtd_paginas")
    private int qtdPaginas;

    public Livro() {}

    public Livro(String nome, Date dataLancamento, int qtdExemplares, BigDecimal valorAluguel, String genero, int qtdPaginas) {
        super(nome, dataLancamento, qtdExemplares, valorAluguel);
        setGenero(genero);
        setQtdPaginas(qtdPaginas);
    }

    // Getters e Setters

    public void setGenero(String genero) {
        if (genero.isEmpty()) {
            return;
        }
        this.genero = genero;
    }

    public void setQtdPaginas(int qtdPaginas) {
        if (qtdPaginas < 1) {
            return;
        }
        this.qtdPaginas = qtdPaginas;
    }

    public String getGenero() {
        return genero;
    }

    public int getQtdPaginas() {
        return qtdPaginas;
    }

    @Override
    public String toString() {
        return "Livro{" + this.getNome() + "}";
    }
}
