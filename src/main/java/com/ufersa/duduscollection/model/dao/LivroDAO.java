package com.ufersa.duduscollection.model.dao;

import com.ufersa.duduscollection.model.entities.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroDAO {
    void save(Livro livro);
    void update(Livro livro);
    void deleteById(Long id);
    Optional<Livro> findById(Long id);
    List<Livro> findAll();
    List<Livro> findByNomeContaining(String nome);
    List<Livro> findByGenero(String genero);
    long count();
}
