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

    /**
     * Construtor sem argumentos.
     * Mantido como protected para ser usado pelo framework JPA, mas não pelo código da aplicação.
     */
    protected Livro() {}

    /**
     * Construtor principal agora é PRIVADO.
     * Isso força a criação de objetos Livro exclusivamente através do Builder.
     */
    private Livro(String nome, Date dataLancamento, int qtdExemplares, BigDecimal valorAluguel, String genero, int qtdPaginas) {
        super(nome, dataLancamento, qtdExemplares, valorAluguel);
        this.setGenero(genero);
        this.setQtdPaginas(qtdPaginas);
    }

    /**
     * Ponto de entrada estático e limpo para iniciar a construção de um Livro.
     * @param nome O nome do livro (obrigatório).
     * @param genero O gênero do livro (obrigatório).
     * @return Uma nova instância do Builder.
     */
    public static Builder builder(String nome, String genero) {
        return new Builder(nome, genero);
    }

    // GETTERS E SETTERS ORIGINAIS
    // Setters são mantidos para compatibilidade com JPA e para permitir a edição de objetos já criados.
    // Para imutabilidade total, os setters poderiam ser removidos.

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero != null && genero.isEmpty()) {
            return;
        }
        this.genero = genero;
    }

    public int getQtdPaginas() {
        return qtdPaginas;
    }

    public void setQtdPaginas(int qtdPaginas) {
        if (qtdPaginas < 1) {
            return;
        }
        this.qtdPaginas = qtdPaginas;
    }

    @Override
    public String toString() {
        return "Livro{" + this.getNome() + "}";
    }


    /**
     * A classe Builder aninhada e estática.
     * Responsável por coletar os dados e construir o objeto Livro.
     */
    public static class Builder {
        // Campos espelhados para armazenar os dados de construção
        private String nome;
        private Date dataLancamento;
        private int qtdExemplares = 0; // Valor padrão para exemplares
        private BigDecimal valorAluguel;
        private String genero;
        private int qtdPaginas;

        /**
         * Construtor do Builder. Recebe os campos considerados obrigatórios.
         */
        public Builder(String nome, String genero) {
            this.nome = nome;
            this.genero = genero;
        }

        /**
         * Métodos "fluentes" para configurar os atributos opcionais.
         * Cada método retorna 'this' para permitir o encadeamento de chamadas.
         */
        public Builder dataLancamento(Date data) {
            this.dataLancamento = data;
            return this;
        }

        public Builder qtdExemplares(int qtd) {
            this.qtdExemplares = qtd;
            return this;
        }

        public Builder valorAluguel(BigDecimal valor) {
            this.valorAluguel = valor;
            return this;
        }

        public Builder qtdPaginas(int paginas) {
            this.qtdPaginas = paginas;
            return this;
        }

        /**
         * Método final que constrói e retorna o objeto Livro.
         * Chama o construtor privado da classe Livro.
         */
        public Livro build() {
            // Validações podem ser adicionadas aqui antes da construção final
            if (nome == null || nome.trim().isEmpty() || genero == null || genero.trim().isEmpty()) {
                throw new IllegalStateException("Nome e Gênero são campos obrigatórios para construir um Livro.");
            }
            return new Livro(nome, dataLancamento, qtdExemplares, valorAluguel, genero, qtdPaginas);
        }
    }
}