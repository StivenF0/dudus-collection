package com.ufersa.duduscollection.model.services;

import com.ufersa.duduscollection.model.dao.UsuarioDAO;
import com.ufersa.duduscollection.model.entities.Livro;
import com.ufersa.duduscollection.model.entities.Usuario;

import java.util.List;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Usuario> buscarTodos() {
        return usuarioDAO.findAll();
    }

    public void deletarUsuario(Usuario usuario) {
        usuarioDAO.deleteById(usuario.getId());
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarioDAO.save(usuario);
    }

    public void alterarUsuario(Usuario usuario) {
        usuarioDAO.update(usuario);
    }


}
