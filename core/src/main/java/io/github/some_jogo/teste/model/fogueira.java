package io.github.some_jogo.teste.model;

public class Fogueira extends Estrutura {

    private boolean acesa;

    public Fogueira() {
        super("Fogueira");
        this.acesa = false;
    }

    public void acender() {
        acesa = true;
    }

    public void apagar() {
        acesa = false;
    }

    public boolean isAcesa() {
        return acesa;
    }
}
