package com.ufersa.duduscollection.model.services;

import com.ufersa.duduscollection.model.dao.AluguelDAO;
import com.ufersa.duduscollection.model.entities.Aluguel;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AluguelService {
    private final AluguelDAO aluguelDAO;

    public AluguelService(AluguelDAO aluguelDAO) {
        this.aluguelDAO = aluguelDAO;
    }

    public Optional<Aluguel> buscarPorId(Long id) {
        return aluguelDAO.findById(id);
    }

    public void registrarAluguel(Aluguel aluguel) {
        aluguelDAO.save(aluguel);
    }

    public void registrarDevolucao(Aluguel aluguel) {
        aluguelDAO.update(aluguel);
    }

    public void deletarAluguel(Aluguel aluguel) {
        aluguelDAO.deleteById(aluguel.getId());
    }

    public List<Aluguel> buscarTodos() {
        return aluguelDAO.findAll();
    }

    public long contarAtivos() {
        return aluguelDAO.countAtivos();
    }

    public List<Aluguel> buscarPorIntervaloDeDatas(LocalDate inicio, LocalDate fim) {
        Date dataInicio = Date.valueOf(inicio);
        Date dataFim = Date.valueOf(fim);
        return aluguelDAO.findByDateRange(dataInicio, dataFim);
    }
}
