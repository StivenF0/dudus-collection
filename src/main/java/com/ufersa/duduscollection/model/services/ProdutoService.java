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

    public List<Produto> buscarPorNome(String nome) {
        return produtoDAO.findByNomeContaining(nome);
    }

    public List<Produto> buscarTodos() {
        return produtoDAO.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoDAO.findById(id);
    }

    public void alterarProduto(Produto produto) {
        produtoDAO.update(produto);
    }

    public void deletarProduto(Produto produto) {
        produtoDAO.deleteById(produto.getId());
    }

    public void adicionarProduto(Produto produto) {
        produtoDAO.save(produto);
    }
}
