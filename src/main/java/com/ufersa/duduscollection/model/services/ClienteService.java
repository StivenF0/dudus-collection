package com.ufersa.duduscollection.model.services;

import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.entities.Cliente;

import java.util.List;
import java.util.Optional;

public class ClienteService {
    private final ClienteDAO clienteDAO;

    public ClienteService (ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteDAO.findByNomeContaining(nome);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteDAO.findByCpf(cpf);
    }

    public void alterarCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    public void deletarCliente(Cliente cliente) {
        clienteDAO.deleteById(cliente.getId());
    }

    public void adicionarCliente(Cliente cliente) {
        clienteDAO.save(cliente);
    }
}
