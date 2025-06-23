package com.ufersa.duduscollection.model.dao;

import com.ufersa.duduscollection.model.entities.Aluguel;

public interface AluguelDAO {
    void registrarAluguel(Aluguel aluguel);
    void registrarDevolucao(Aluguel aluguel);
}
