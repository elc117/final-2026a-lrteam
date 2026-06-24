package io.github.some_jogo.teste.model;

import io.github.some_jogo.teste.enums.Habilidade;

public class Tarefa {

    private String nome;
    private Habilidade habilidadeNecessaria;

    public Tarefa(String nome, Habilidade habilidadeNecessaria) {
        this.nome = nome;
        this.habilidadeNecessaria = habilidadeNecessaria;
    }

    public String getNome() {
        return nome;
    }

    public Habilidade getHabilidadeNecessaria() {
        return habilidadeNecessaria;
    }

    public boolean podeSerRealizadaPor(Personagem personagem) {
        return personagem.getHabilidade() == habilidadeNecessaria;
    }
}