package com.ufersa.duduscollection.model.dao.impl;

import com.ufersa.duduscollection.model.dao.DiscoDAO;
import com.ufersa.duduscollection.model.entities.Disco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class DiscoDAOImpl implements DiscoDAO {
    private final EntityManager em;

    public DiscoDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Disco disco) {
        em.getTransaction().begin();
        em.persist(disco);
        em.getTransaction().commit();
    }

    @Override
    public void update(Disco disco) {
        em.getTransaction().begin();
        em.merge(disco);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        Disco disco = em.find(Disco.class, id);
        if (disco != null) {
            em.getTransaction().begin();
            em.remove(disco);
            em.getTransaction().commit();
        }
    }

    @Override
    public Optional<Disco> findById(Long id) {
        Disco disco = em.find(Disco.class, id);
        return Optional.ofNullable(disco);
    }

    @Override
    public List<Disco> findAll() {
        return em.createQuery("SELECT l FROM Disco l", Disco.class).getResultList();
    }

    @Override
    public List<Disco> findByNomeContaining(String nome) {
        TypedQuery<Disco> query =
                em.createQuery("SELECT l FROM Disco l WHERE nome LIKE :nome", Disco.class);
        query.setParameter("nome", nome);

        return query.getResultList();
    }

    @Override
    public List<Disco> findByEstilo(String estilo) {
        TypedQuery<Disco> query =
                em.createQuery("SELECT l FROM Disco l WHERE nome = :estilo", Disco.class);
        query.setParameter("estilo", estilo);

        return query.getResultList();
    }

    @Override
    public long count() {
        return em.createQuery("SELECT COUNT(d) FROM Disco d", Long.class).getSingleResult();
    }
}
