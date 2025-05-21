package br.com.ufersa.duduscollection.Cliente;
import java.util.List;

public class Cliente {
    private String nome;
    private String endereco;
    private int cpf;

    public Cliente() {}

    public Cliente(String nome, String endereco, int cpf) {
        setNome(nome);
        setEndereco(endereco);
        setCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getCpf() {
        return cpf;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio.");
        }
        this.endereco = endereco;
    }

    public void setCpf(int cpf) {
        if (cpf <= 0 || String.valueOf(cpf).length() > 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter até 11 dígitos numéricos e ser positivo.");
        }
        this.cpf = cpf;
    }

    public static Cliente pesquisarPorNome(List<Cliente> clientes, String nome) {
        if (nome == null) return null;
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                return cliente;
            }
        }
        return null;
    }

    public static Cliente pesquisarPorCpf(List<Cliente> clientes, int cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf() == cpf) {
                return cliente;
            }
        }
        return null;
    }
}
