package io.github.some_jogo.teste.model;

public class fogueira extends Estrutura {

    private boolean acesa;

    public fogueira() {
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
