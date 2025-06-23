package com.ufersa.duduscollection.model.dao;

import com.ufersa.duduscollection.model.entities.Usuario;

import java.util.Optional;

public interface UsuarioDAO {
    /**
     * Procura o usuário no banco
     * @param nome Nome digitado
     * @return um Optional que retorna o Usuario se encontrado no banco
     */
    Optional<Usuario> findByNome(String nome);
}
