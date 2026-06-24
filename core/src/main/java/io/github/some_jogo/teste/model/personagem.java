package io.github.some_jogo.teste.model;

import io.github.some_jogo.teste.enums.Habilidade;

public class Personagem {

    private String nome;
    private Habilidade habilidade;

    public Personagem(String nome, Habilidade habilidade) {
        this.nome = nome;
        this.habilidade = habilidade;
    }

    public String getNome() {
        return nome;
    }

    public Habilidade getHabilidade() {
        return habilidade;
    }
}