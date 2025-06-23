package com.ufersa.duduscollection.dao;

import com.ufersa.duduscollection.entities.Aluguel;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AluguelDAOImpl implements AluguelDAO {

    @Override
    public void registrarAluguel(Aluguel aluguel) {
        EntityManager em = JPAUtil.getEntityManager();
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
    public void registrarDevolucao(Aluguel aluguel) {
        EntityManager em = JPAUtil.getEntityManager();
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
}
