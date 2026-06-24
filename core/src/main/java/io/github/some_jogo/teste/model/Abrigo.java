package io.github.some_jogo.teste.model;

public class Abrigo extends Estrutura {

    private boolean construido;

    public Abrigo() {
        super("Abrigo");
        this.construido = false;
    }

    public void construir() {
        construido = true;
    }

    public boolean isConstruido() {
        return construido;
    }
}