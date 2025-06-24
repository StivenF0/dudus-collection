package com.ufersa.duduscollection.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "alugueis")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente", referencedColumnName = "cliente_id")
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio", nullable = false)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fim", nullable = false)
    private Date dataFim;

    @Column(name = "valor_total", nullable = false)
    private double valorTotal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "aluguel_produto",
            joinColumns = @JoinColumn(name = "aluguel_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos = new ArrayList<>();

    public Aluguel() {}

    public Aluguel(Cliente cliente, Date dataInicio, Date dataFim, double valorTotal) {
        setCliente(cliente);
        setDataInicio(dataInicio);
        setDataFim(dataFim);
        setValorTotal(valorTotal);
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        this.cliente = cliente;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        if (dataInicio == null) {
            throw new IllegalArgumentException("Data de início não pode ser nula.");
        }
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        if (dataFim == null || (dataInicio != null && dataFim.before(dataInicio))) {
            throw new IllegalArgumentException("Data de fim inválida.");
        }
        this.dataFim = dataFim;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        if (valorTotal < 0) {
            throw new IllegalArgumentException("Valor total não pode ser negativo.");
        }
        this.valorTotal = valorTotal;
    }
}
