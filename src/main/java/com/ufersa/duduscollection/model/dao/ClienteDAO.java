package com.ufersa.duduscollection.model.dao;

import com.ufersa.duduscollection.model.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO {
    void save(Cliente cliente);
    void update(Cliente cliente);
    void deleteById(Long id);
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByCpf(String cpf);
    List<Cliente> findByNomeContaining(String nome);
    List<Cliente> findAll();
}
