package br.com.ufersa.duduscollection.Livro;

public class Livro {
    private String nome, genero;
    private int anoLancamento, qtdPaginas, qtdExemplares;
    private double valorAluguel;

    public Livro() {}

    public Livro(String nome, String genero, int anoLancamento, int qtdPaginas, int qtdExemplares, double valorAluguel) {
        setNome(nome);
        setGenero(genero);
        setAnoLancamento(anoLancamento);
        setQtdPaginas(qtdPaginas);
        setQtdExemplares(qtdExemplares);
        setValorAluguel(valorAluguel);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public void setQtdPaginas(int qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    public void setQtdExemplares(int qtdExemplares) {
        this.qtdExemplares = qtdExemplares;
    }

    public void setValorAluguel(double valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public String getNome() {
        return nome;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public int getQtdPaginas() {
        return qtdPaginas;
    }

    public int getQtdExemplares() {
        return qtdExemplares;
    }

    public double getValorAluguel() {
        return valorAluguel;
    }
}
