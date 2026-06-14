package io.github.some_jogo.teste.model;

public class personagem {
    private String nome;
    private String habilidade;
}

public personagem(String nome, String habilidade) {
    this.nome = nome;
    this.habilidade = habilidade;
}

public String getNome() {
    return nome;
}

public String getHabilidade() {
    return habilidade;
}