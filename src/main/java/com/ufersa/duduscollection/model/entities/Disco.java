package com.ufersa.duduscollection.model.entities;

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
}
