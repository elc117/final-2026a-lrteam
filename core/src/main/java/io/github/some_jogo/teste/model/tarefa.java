package io.github.some_jogo.teste.model;

public class Tarefa {

    private String nome;
    private String habilidadeNecessaria;

    public Tarefa(String nome, String habilidadeNecessaria) {
        this.nome = nome;
        this.habilidadeNecessaria = habilidadeNecessaria;
    }

    public String getNome() {
        return nome;
    }

    public String getHabilidadeNecessaria() {
        return habilidadeNecessaria;
    }

    public boolean podeSerRealizadaPor(Personagem personagem) {
        return personagem.getHabilidade()
                .equalsIgnoreCase(habilidadeNecessaria);
    }
}