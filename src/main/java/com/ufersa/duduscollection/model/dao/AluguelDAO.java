package com.ufersa.duduscollection.dao;

import com.ufersa.duduscollection.entities.Aluguel;

public interface AluguelDAO {
    void registrarAluguel(Aluguel aluguel);
    void registrarDevolucao(Aluguel aluguel);
}
