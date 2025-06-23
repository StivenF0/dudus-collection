package com.ufersa.duduscollection.model.dao.impl;

import com.ufersa.duduscollection.model.entities.Aluguel;
import com.ufersa.duduscollection.util.JPAUtil;
import com.ufersa.duduscollection.model.dao.RelatorioDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class RelatorioDAOImpl implements RelatorioDAO {
    @Override
    public void relatorioPorPeriodo(Date inicio, Date fim) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT a FROM Aluguel a WHERE a.dataInicio >= :inicio AND a.dataFim <= :fim";
            TypedQuery<Aluguel> query = em.createQuery(jpql, Aluguel.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fim", fim);

            List<Aluguel> resultados = query.getResultList();
            resultados.forEach(System.out::println); // ou montar estrutura de relatório

        } finally {
            em.close();
        }
    }

    @Override
    public void aluguelPorCliente(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            String jpql = "SELECT a FROM Aluguel a WHERE a.cliente.cpf = :cpf";
            TypedQuery<Aluguel> query = em.createQuery(jpql, Aluguel.class);
            query.setParameter("cpf", cpf);

            List<Aluguel> resultados = query.getResultList();
            resultados.forEach(System.out::println);

        } finally {
            em.close();
        }
    }
}
