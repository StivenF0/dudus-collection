package com.ufersa.duduscollection.model.services;

import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.entities.Cliente;
import com.ufersa.duduscollection.model.exception.CpfDuplicadoException;

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

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteDAO.findById(id);
    }

    public List<Cliente> buscarTodos() {
        return clienteDAO.findAll();
    }

    public void alterarCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    public void deletarCliente(Cliente cliente) {
        clienteDAO.deleteById(cliente.getId());
    }

    public void adicionarCliente(Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteDAO.findByCpf(cliente.getCpf());

        if (clienteExistente.isPresent()) {
            throw new CpfDuplicadoException("O CPF " + cliente.getCpf() + " já está cadastrado no sistema.");
        }

        clienteDAO.save(cliente);
    }

    public long contarTodos() {
        return clienteDAO.count();
    }
}
