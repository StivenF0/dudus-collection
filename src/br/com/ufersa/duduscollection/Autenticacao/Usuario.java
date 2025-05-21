package br.com.ufersa.duduscollection.Autenticacao;

public class Usuario {
    private String nome;
    private String senha;
    private boolean logado = false;

    public Usuario() {}

    public Usuario(String nome, String senha) {
        setNome(nome);
        setSenha(senha);
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
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

    public boolean login(String nome, String senha) {
        if (this.nome.equals(nome) && this.senha.equals(senha)) {
            logado = true;
            System.out.println("Login realizado com sucesso.");
            return true;
        } else {
            System.out.println("Nome ou senha incorretos.");
            return false;
        }
    }

    public void logout() {
        if (logado) {
            logado = false;
            System.out.println("Logout realizado com sucesso.");
        } else {
            System.out.println("Usuário não está logado.");
        }
    }

    public boolean isLogado() {
        return logado;
    }
}
