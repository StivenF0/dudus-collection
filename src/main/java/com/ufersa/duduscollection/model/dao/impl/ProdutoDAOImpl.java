package com.ufersa.duduscollection.model.dao.impl;

import com.ufersa.duduscollection.model.dao.ProdutoDAO;
import com.ufersa.duduscollection.model.entities.Cliente;
import com.ufersa.duduscollection.model.entities.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ProdutoDAOImpl implements ProdutoDAO {
    private final EntityManager em;

    public ProdutoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Produto produto) {
        em.getTransaction().begin();
        em.persist(produto);
        em.getTransaction().commit();
    }

    @Override
    public void update(Produto produto) {
        em.getTransaction().begin();
        em.merge(produto);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        Produto produto = em.find(Produto.class, id);
        if (produto != null) {
            em.getTransaction().begin();
            em.remove(produto);
            em.getTransaction().commit();
        }
    }

    @Override
    public Optional<Produto> findById(Long id) {
        Produto produto = em.find(Produto.class, id);
        return Optional.ofNullable(produto);
    }

    @Override
    public List<Produto> findByNomeContaining(String nome) {
        TypedQuery<Produto> query =
                em.createQuery("SELECT p FROM Produto p WHERE nome LIKE :nome", Produto.class);
        query.setParameter("nome", nome);

        return query.getResultList();
    }

    @Override
    public List<Produto> findAll() {
        return em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
    }
}
