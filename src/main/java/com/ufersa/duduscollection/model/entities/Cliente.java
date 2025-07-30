package com.ufersa.duduscollection.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="Clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String nome;

    @Column
    private String endereco;

    @Column(nullable=false, unique=true)
    private String cpf;

    public Cliente() {}

    public Cliente(String nome, String endereco, String cpf) {
        setNome(nome);
        setEndereco(endereco);
        setCpf(cpf);
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public Long getId() {
        return id;
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

    public void setCpf(String cpf) {
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve ter 11 caracteres.");
        }
        this.cpf = cpf;
    }

    public void setId(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Id não pode ser nulo, nem negativo");
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getNome(); // Retorna o nome do cliente
    }
}
