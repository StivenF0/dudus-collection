package br.com.ufersa.duduscollection.Aluguel;

import java.util.Date;
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

    // Sem implementação planejada
    public void registrarAluguel() {}

    // Sem implementação planejada
    public void registrarDevolucao() {}

    // Rever este metodo, nao faz sentido
    public Aluguel retornarAluguel() {
        return this;
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