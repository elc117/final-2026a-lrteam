package io.github.some_jogo.teste.model;

public class Personagem {

    private String nome;
    private String habilidade;

    public Personagem(String nome, String habilidade) {
        this.nome = nome;
        this.habilidade = habilidade;
    }

    public String getNome() {
        return nome;
    }

    public String getHabilidade() {
        return habilidade;
    }
}