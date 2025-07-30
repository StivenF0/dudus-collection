package com.ufersa.duduscollection.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="Discos")package com.ufersa.duduscollection.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="Discos")
@PrimaryKeyJoinColumn(name="produto_id")
public class Disco extends Produto {

    @Column
    private String estilo;

    /**
     * Construtor sem argumentos.
     * Mantido como protected para ser usado pelo framework JPA.
     */
    protected Disco() {}

    /**
     * Construtor principal agora é PRIVADO.
     * Força a criação de objetos Disco exclusivamente através do Builder.
     */
    private Disco(String nome, Date dataLancamento, int qtdExemplares, BigDecimal valorAluguel, String estilo) {
        super(nome, dataLancamento, qtdExemplares, valorAluguel);
        this.setEstilo(estilo);
    }

    /**
     * Ponto de entrada estático para iniciar a construção de um Disco.
     * @param nome O nome do disco (obrigatório).
     * @param estilo O estilo musical do disco (obrigatório).
     * @return Uma nova instância do Builder.
     */
    public static Builder builder(String nome, String estilo) {
        return new Builder(nome, estilo);
    }

    // GETTERS E SETTERS ORIGINAIS
    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        if (estilo != null && estilo.isEmpty()) {
            return;
        }
        this.estilo = estilo;
    }

    @Override
    public String toString() {
        return "Disco{" + this.getNome() + "}";
    }

    /**
     * A classe Builder aninhada e estática para Disco.
     */
    public static class Builder {
        // Campos espelhados para armazenar os dados de construção
        private String nome;
        private Date dataLancamento;
        private int qtdExemplares = 0;
        private BigDecimal valorAluguel;
        private String estilo;

        /**
         * Construtor do Builder com campos obrigatórios.
         */
        public Builder(String nome, String estilo) {
            this.nome = nome;
            this.estilo = estilo;
        }

        /**
         * Métodos fluentes para configurar atributos opcionais.
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

        /**
         * Método final que constrói e retorna o objeto Disco.
         */
        public Disco build() {
            if (nome == null || nome.trim().isEmpty() || estilo == null || estilo.trim().isEmpty()) {
                throw new IllegalStateException("Nome e Estilo são campos obrigatórios para construir um Disco.");
            }
            return new Disco(nome, dataLancamento, qtdExemplares, valorAluguel, estilo);
        }
    }
}
@PrimaryKeyJoinColumn(name="produto_id")
public class Disco extends Produto {
    @Column
    private String estilo;

    public Disco() {}

    public Disco(String nome, Date dataLancamento, int qtdExemplares, BigDecimal valorAluguel, String estilo) {
        super(nome, dataLancamento, qtdExemplares, valorAluguel);
        setEstilo(estilo);
    }

    public void setEstilo(String estilo) {
        if (estilo.isEmpty()) {
            return;
        }
        this.estilo = estilo;
    }

    public String getEstilo() {
        return estilo;
    }

    @Override
    public String toString() {
        return "Disco{" + this.getNome() + "}";
    }
}
