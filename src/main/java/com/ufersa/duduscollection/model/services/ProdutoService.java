package com.ufersa.duduscollection.model.services;

import com.ufersa.duduscollection.model.dao.ProdutoDAO;
import com.ufersa.duduscollection.model.entities.Produto;

import java.util.List;
import java.util.Optional;

public class ProdutoService {
    private final ProdutoDAO produtoDAO;

    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public List<Produto> buscarTodos() {
        return produtoDAO.findAll();
    }
}
