package com.ufersa.duduscollection.model.dao.impl;

import com.ufersa.duduscollection.model.dao.LivroDAO;
import com.ufersa.duduscollection.model.entities.Livro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class LivroDAOImpl implements LivroDAO {
    private final EntityManager em;

    public LivroDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Livro livro) {
        em.getTransaction().begin();
        em.persist(livro);
        em.getTransaction().commit();
    }

    @Override
    public void update(Livro livro) {
        em.getTransaction().begin();
        em.merge(livro);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        Livro livro = em.find(Livro.class, id);
        if (livro != null) {
            em.getTransaction().begin();
            em.remove(livro);
            em.getTransaction().commit();
        }
    }

    @Override
    public Optional<Livro> findById(Long id) {
        Livro livro = em.find(Livro.class, id);
        return Optional.ofNullable(livro);
    }

    @Override
    public List<Livro> findAll() {
        return em.createQuery("SELECT l FROM Livro l", Livro.class).getResultList();
    }

    @Override
    public List<Livro> findByNomeContaining(String nome) {
        TypedQuery<Livro> query =
                em.createQuery("SELECT l FROM Livro l WHERE nome LIKE :nome", Livro.class);
        query.setParameter("nome", nome);

        return query.getResultList();
    }

    @Override
    public List<Livro> findByGenero(String genero) {
        TypedQuery<Livro> query =
                em.createQuery("SELECT l FROM Livro l WHERE genero = :genero", Livro.class);
        query.setParameter("genero", genero);

        return query.getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(l) FROM Livro l", Long.class).getSingleResult();
    }
}
