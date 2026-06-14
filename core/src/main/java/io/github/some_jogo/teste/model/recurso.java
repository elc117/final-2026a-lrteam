package io.github.some_jogo.teste.model;

public abstract class recurso {

    protected String nome;
    protected int quantidadeDisponivel;

    public Recurso(String nome, int quantidadeDisponivel) {
        this.nome = nome;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public int coletar(int quantidade) {
        int coletado = Math.min(quantidade, quantidadeDisponivel);
        quantidadeDisponivel -= coletado;
        return coletado;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
}