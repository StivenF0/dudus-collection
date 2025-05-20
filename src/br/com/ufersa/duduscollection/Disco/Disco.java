package br.com.ufersa.duduscollection.Disco;

public class Disco {
    private String titulo, estilo;
    private int anoLancamento;
    private int qtdExemplares;
    private double valorAluguel;

    public Disco() {}

    public Disco(String titulo, String estilo, int anoLancamento, int qtdExemplares, double valorAluguel) {
        setTitulo(titulo);
        setEstilo(estilo);
        setQtdExemplares(qtdExemplares);
        setValorAluguel(valorAluguel);
        setAnoLancamento(anoLancamento);
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public void setQtdExemplares(int qtdExemplares) {
        if (qtdExemplares >= 0) {
            this.qtdExemplares = qtdExemplares;
        }
    }

    public void setValorAluguel(double valorAluguel) {
        if (valorAluguel >= 0) {
            this.valorAluguel = valorAluguel;
        }
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getEstilo() {
        return this.estilo;
    }

    public int getQtdExemplares() {
        return this.qtdExemplares;
    }

    public double getValorAluguel() {
        return this.valorAluguel;
    }

    public int getAnoLancamento() {
        return this.anoLancamento;
    }
}
