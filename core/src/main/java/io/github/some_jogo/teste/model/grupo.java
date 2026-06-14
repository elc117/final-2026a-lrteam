package io.github.some_jogo.teste.model;

import java.util.ArrayList;

public class grupo {

    private ArrayList<Personagem> membros;

    public Grupo() {
        membros = new ArrayList<>();
    }

    public boolean adicionar(Personagem personagem) {

        if (membros.size() < 5) {
            membros.add(personagem);
            return true;
        }

        return false;
    }

    public ArrayList<Personagem> getMembros() {
        return membros;
    }

    public int getQuantidadeMembros() {
        return membros.size();
    }
}