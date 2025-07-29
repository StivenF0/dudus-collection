package com.ufersa.duduscollection.model.dao.impl;

import com.ufersa.duduscollection.model.entities.Aluguel;
import com.ufersa.duduscollection.model.entities.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import com.ufersa.duduscollection.model.dao.AluguelDAO;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AluguelDAOImpl implements AluguelDAO {
    private final EntityManager em;

    public AluguelDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Aluguel aluguel) {
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();

            em.persist(aluguel); // salva o novo aluguel

            tx.commit();
            System.out.println("Aluguel registrado com sucesso.");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Erro ao registrar aluguel: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Aluguel aluguel) {
        EntityTransaction tx = null;

        try {
            tx = em.getTransaction();
            tx.begin();

            em.merge(aluguel); // atualiza o aluguel existente com data de devolução/valor

            tx.commit();
            System.out.println("Devolução registrada com sucesso.");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        Aluguel aluguel = em.find(Aluguel.class, id);
        if (aluguel != null) {
            em.getTransaction().begin();
            em.remove(aluguel);
            em.getTransaction().commit();
        }
    }

    @Override
    public Optional<Aluguel> findById(Long id) {
        Aluguel aluguel = em.find(Aluguel.class, id);
        return Optional.ofNullable(aluguel);
    }

    @Override
    public List<Aluguel> findAll() {
        return em.createQuery("SELECT a FROM Aluguel a", Aluguel.class).getResultList();
    }

    @Override
    public List<Aluguel> findAllByCliente(Cliente cliente) {
        TypedQuery<Aluguel> query =
                em.createQuery("SELECT a FROM Aluguel a WHERE cliente = :cliente", Aluguel.class);
        query.setParameter("cliente", cliente);

        return query.getResultList();
    }

    @Override
    public List<Aluguel> findByDateRange(Date inicio, Date fim) {
        TypedQuery<Aluguel> query = em.createQuery(
                "SELECT a FROM Aluguel a WHERE a.dataInicio BETWEEN :inicio AND :fim", Aluguel.class);
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);
        return query.getResultList();
    }

    @Override
    public long countAtivos() {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(a) FROM Aluguel a WHERE a.dataFim >= :hoje", Long.class);
        query.setParameter("hoje", new Date());
        return query.getSingleResult();
    }
}
