package com.ufersa.duduscollection.model.dao;

import com.ufersa.duduscollection.model.entities.Aluguel;
import com.ufersa.duduscollection.model.entities.Cliente;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AluguelDAO {
    void save(Aluguel aluguel);
    void update(Aluguel aluguel);
    void deleteById(Long id);
    Optional<Aluguel> findById(Long id);
    List<Aluguel> findAll();
    List<Aluguel> findAllByCliente(Cliente cliente);
    List<Aluguel> findByDateRange(Date inicio, Date fim);
    long countAtivos();
}
