package br.com.ufersa.duduscollection.Aluguel;

import java.util.Date;
import java.util.List;

import br.com.ufersa.duduscollection.Cliente.*;

public class Aluguel {
    private Cliente cliente;
    private Date dataInicio, dataFim;
    private double valorTotal;

    public Aluguel() {}

    public Aluguel(Cliente cliente, Date dataInicio, Date dataFim, double valorTotal) {
        setCliente(cliente);
        setDataInicio(dataInicio);
        setDataFim(dataFim);
        setValorTotal(valorTotal);
    }

    public void registrarAluguel(List<Aluguel> aluguelList) {
        aluguelList.add(this);
        System.out.println("Aluguel registrado com sucesso!");
    }

    public void registrarDevolucao(List<Aluguel> aluguelList) {
        aluguelList.remove(this);
        System.out.println("Aluguel devolvido com sucesso!");
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public void setValorTotal(double valorTotal) {
        if (valorTotal >= 0) {
            this.valorTotal = valorTotal;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}