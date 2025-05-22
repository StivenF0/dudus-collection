package br.com.ufersa.duduscollection.Relatorio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import br.com.ufersa.duduscollection.Aluguel.*;
import br.com.ufersa.duduscollection.Cliente.*;

public class Relatorio {
    // TODO: Implementar classe
    public static void relatorioMensal(List<Aluguel> aluguelList, int mes) {
        Calendar calendar = Calendar.getInstance();
        double total = 0;
        int items = 0;

        for (Aluguel aluguel : aluguelList) {
            calendar.setTime(aluguel.getDataInicio());
            int mesAluguel = calendar.get(Calendar.MONTH);

            if (mesAluguel == mes) {
                total += aluguel.getValorTotal();
                items++;
            }
        }

        System.out.println("Total de alugueis feitos no mês " + mes + ": " + items);
        System.out.println("Receita do mês " + mes + ": R$ " + total);
    }

    public void aluguelPorCliente(List<Aluguel> aluguelList, Cliente cliente) {
        double total = 0;
        int items = 0;

        for (Aluguel aluguel : aluguelList) {
            if (aluguel.getCliente().equals(cliente)) {
                total += aluguel.getValorTotal();
                items++;
            }
        }

        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Alugueis: " + items);
        System.out.println("Gasto: " + total);
    }

    public void aluguelPorPeriodo(List<Aluguel> aluguelList, Date dataInicial, Date dataFinal) {
        double total = 0;
        int items = 0;

        for (Aluguel aluguel : aluguelList) {
            if (aluguel.getDataInicio().after(dataInicial) && aluguel.getDataInicio().before(dataFinal)) {
                total += aluguel.getValorTotal();
                items++;
            }
        }

        System.out.println("Alugueis no período de " + dataInicial + " até " + dataFinal);
        System.out.println("Alugueis: " + items);
        System.out.println("Receita: " + total);
    }

    public void aluguelTotal(List<Aluguel> aluguelList) {
        double total = 0;
        int items = 0;

        for (Aluguel aluguel : aluguelList) {
            total += aluguel.getValorTotal();
            items++;
        }
    }
}