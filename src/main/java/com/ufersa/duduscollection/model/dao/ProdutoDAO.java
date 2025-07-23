package com.ufersa.duduscollection.model.dao;

import com.ufersa.duduscollection.model.entities.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoDAO {
    void save(Produto produto);
    void update(Produto produto);
    void deleteById(Long id);
    Optional<Produto> findById(Long id);
    List<Produto> findByNomeContaining(String nome);
    List<Produto> findAll();
}
