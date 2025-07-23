package com.ufersa.duduscollection.model.services;

import com.ufersa.duduscollection.model.dao.UsuarioDAO;
import com.ufersa.duduscollection.model.entities.Usuario;

import java.util.Optional;

public class LoginService {
    private final UsuarioDAO usuarioDAO;

    public LoginService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Optional<Usuario> autenticar(String nome, String senhaDigitada) {
        // Usar DAO para pesquisar pelo usuário
        Optional<Usuario> usuarioOpt = usuarioDAO.findByNome(nome);

        // Verifica se foi encontrado um usuário
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verifica se a senha é igual a armazenada no banco de dados
            if (usuario.getSenha().equals(senhaDigitada)) {
                return Optional.of(usuario);
            }
        }

        return Optional.empty();
    }
}
