package com.ufersa.duduscollection.model.dao;

import com.ufersa.duduscollection.model.entities.Disco;

import java.util.List;
import java.util.Optional;

public interface DiscoDAO {
    void save(Disco disco);
    void update(Disco disco);
    void deleteById(Long id);
    Optional<Disco> findById(Long id);
    List<Disco> findAll();
    List<Disco> findByNomeContaining(String nome);
    List<Disco> findByEstilo(String estilo);
}
