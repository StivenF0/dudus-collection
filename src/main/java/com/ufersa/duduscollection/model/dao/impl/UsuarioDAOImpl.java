package com.ufersa.duduscollection.model.dao.impl;

import com.ufersa.duduscollection.model.dao.UsuarioDAO;
import com.ufersa.duduscollection.model.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class UsuarioDAOImpl implements UsuarioDAO {
    private EntityManager em;

    public UsuarioDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Usuario> findByNome(String nome) {
        // Criar uma query e procura pelo campo nome
        TypedQuery<Usuario> query =
                em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :nome", Usuario.class);
        // Definir parâmetro ":nome"
        query.setParameter("nome", nome);

        try {
            Usuario usuario = query.getSingleResult();
            return Optional.of(usuario);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}
