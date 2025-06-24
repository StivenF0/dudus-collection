package com.ufersa.duduscollection.model.dao.impl;

import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.entities.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ClienteDAOImpl implements ClienteDAO {
    private final EntityManager em;

    public ClienteDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Cliente cliente) {
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }

    @Override
    public void update(Cliente cliente) {
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente != null) {
            em.getTransaction().begin();
            em.remove(cliente);
            em.getTransaction().commit();
        }
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        return Optional.ofNullable(cliente);
    }

    @Override
    public List<Cliente> findByNomeContaining(String nome) {
        TypedQuery<Cliente> query =
                em.createQuery("SELECT c FROM Cliente c WHERE nome LIKE :nome", Cliente.class);
        query.setParameter("nome", nome);

        return query.getResultList();
    }

    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        try {
            TypedQuery<Cliente> query =
                    em.createQuery("SELECT c FROM Cliente c WHERE cpf = :cpf", Cliente.class);
            query.setParameter("cpf", cpf);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Cliente> findAll() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }
}
