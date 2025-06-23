package com.ufersa.duduscollection.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name="Usuarios", uniqueConstraints=@UniqueConstraint(columnNames="nome"))
public class Usuario {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String nome;

    @Column(nullable=false)
    private String senha;

    public Usuario() {}

    public Usuario(Long id, String nome, String senha) {
        setNome(nome);
        setSenha(senha);
        setId(id);
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
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

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        }
        this.senha = senha;
    }

    public void setId(Long id) {
        if (id == null) {
            throw  new IllegalArgumentException("O id não pode ser nulo ou vazio.");
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{nome='" + nome +"'}";
    }
}