package com.ufersa.duduscollection.model.services;

import com.ufersa.duduscollection.model.dao.DiscoDAO;
import com.ufersa.duduscollection.model.entities.Disco;

import java.util.List;
import java.util.Optional;

public class DiscoService {
    private final DiscoDAO discoDAO;

    public DiscoService(DiscoDAO discoDAO) {
        this.discoDAO = discoDAO;
    }

    public List<Disco> buscarPorNome(String nome) {
        return discoDAO.findByNomeContaining(nome);
    }

    public List<Disco> buscarPorEstilo(String estilo) {
        return discoDAO.findByEstilo(estilo);
    }

    public List<Disco> buscarTodos() {
        return discoDAO.findAll();
    }

    public Optional<Disco> buscarPorId(Long id) {
        return discoDAO.findById(id);
    }

    public void alterarDisco(Disco livro) {
        discoDAO.update(livro);
    }

    public void deletarDisco(Disco livro) {
        discoDAO.deleteById(livro.getId());
    }

    public void adicionarDisco(Disco livro) {
        discoDAO.save(livro);
    }

    public long contarTodos() {
        return discoDAO.count();
    }
}
