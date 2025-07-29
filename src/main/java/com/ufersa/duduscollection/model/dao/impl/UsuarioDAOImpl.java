package com.ufersa.duduscollection.model.dao.impl;

import com.ufersa.duduscollection.model.dao.UsuarioDAO;
import com.ufersa.duduscollection.model.entities.Disco;
import com.ufersa.duduscollection.model.entities.Livro;
import com.ufersa.duduscollection.model.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UsuarioDAOImpl implements UsuarioDAO {
    private EntityManager em;

    public UsuarioDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Usuario usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
    }

    @Override
    public void update(Usuario usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
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

    public List<Usuario> findAll() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    public void deleteById(Long id) {
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.getTransaction().begin();
            em.remove(usuario);
            em.getTransaction().commit();
        }
    }
}
