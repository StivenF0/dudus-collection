package com.ufersa.duduscollection.model.services;

import com.ufersa.duduscollection.model.dao.LivroDAO;
import com.ufersa.duduscollection.model.entities.Livro;

import java.util.List;
import java.util.Optional;

public class LivroService {
    private final LivroDAO livroDAO;

    public LivroService(LivroDAO livroDAO) {
        this.livroDAO = livroDAO;
    }

    public List<Livro> buscarPorNome(String nome) {
        return livroDAO.findByNomeContaining(nome);
    }

    public List<Livro> buscarPorGenero(String genero) {
        return livroDAO.findByGenero(genero);
    }

    public List<Livro> buscarTodos() {
        return livroDAO.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroDAO.findById(id);
    }

    public void alterarLivro(Livro livro) {
        livroDAO.update(livro);
    }

    public void deletarLivro(Livro livro) {
        livroDAO.deleteById(livro.getId());
    }

    public void adicionarLivro(Livro livro) {
        livroDAO.save(livro);
    }

    public long contarTodos() {
        return livroDAO.count();
    }
}
