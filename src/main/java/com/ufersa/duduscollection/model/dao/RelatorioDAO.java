package com.ufersa.duduscollection.model.dao;

import java.util.Date;

public interface RelatorioDAO {
    void relatorioPorPeriodo(Date inicio, Date fim);
    void aluguelPorCliente(String cpf);
}


