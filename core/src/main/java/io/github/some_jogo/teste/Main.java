package io.github.some_jogo.teste;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;

import io.github.some_jogo.teste.model.Personagem;
import io.github.some_jogo.teste.model.Tarefa;
import io.github.some_jogo.teste.model.Grupo;
import io.github.some_jogo.teste.model.Arvore;
import io.github.some_jogo.teste.model.Rio;

public class Main implements ApplicationListener {

    Texture florestaTexture, rioTexture, arvoreTexture;
    Texture baldeVazioTexture, baldeCheioTexture, madeiraTexture;
    Texture playerSheet;
    Texture fogoTexture;
    Personagem personagemAgua;
    Personagem personagemFogo;
    Personagem personagemAtual;

    Texture playerUmTexture;
    Texture playerDoisTexture;

    Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    TextureRegion idleFrame;

    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    FitViewport viewport;
    BitmapFont font;

    Grupo grupo;
    ArrayList<Personagem> sobreviventesDisponiveis;

    // personagem
    float px = 1f, py = 2f;
    String direcao = "down";
    float stateTime = 0f;
    boolean moving = false;

    // recursos
    Rio rio;
    Arvore arvore;
    int aguaColetada = 0;
    int madeiraColetada = 0;

    // mapa
    int mapaAtual = 1;
    float arvoreX = 4.5f, arvoreY = 3.5f;
    float rioX = 6.0f,    rioY = 1.0f;
    float fogueiraX = 3.4f, fogueiraY = 1.9f;
    float distanciaInteracao = 1.0f;

    boolean fogueiraAcesa = false;
    boolean baldeCheio = false;
    String mensagem = "";
    float mensagemTimer = 0f;

    @Override
    public void create() {
        spriteBatch   = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        viewport      = new FitViewport(8, 5);
        font          = new BitmapFont();
        font.getData().setScale(0.03f);

        florestaTexture   = new Texture("grama.png");
        rioTexture        = new Texture("rio.png");
        arvoreTexture     = new Texture("arvore.png");
        madeiraTexture    = new Texture("madeira.png");
        baldeVazioTexture = new Texture("baldeVazio.png");
        baldeCheioTexture = new Texture("baldeCheio.png");
        fogoTexture = new Texture("fogo.png");

        playerSheet = new Texture("personagemDois.png");

        rio    = new Rio(50);
        arvore = new Arvore(30);

        grupo = new Grupo();
        sobreviventesDisponiveis = new ArrayList<>();

        Personagem joao   = new Personagem("Joao",   "Pesca");
        Personagem ana    = new Personagem("Ana",    "Cura");
        Personagem pedro  = new Personagem("Pedro",  "Fogo");
        Personagem cintia = new Personagem("Cintia", "Danca");
        Personagem leo    = new Personagem("Leo",    "Construcao");
        Personagem rita   = new Personagem("Rita",   "Piloto");
        Personagem felipe = new Personagem("Felipe", "Agua");
        Personagem anita  = new Personagem("Anita",  "Canto");

        personagemAgua = felipe;
        personagemFogo = pedro;

        personagemAtual = personagemAgua;

        playerUmTexture = new Texture("personagem.png");
        playerDoisTexture = new Texture("personagemDois.png");

        sobreviventesDisponiveis.add(joao); sobreviventesDisponiveis.add(ana);
        sobreviventesDisponiveis.add(pedro); sobreviventesDisponiveis.add(cintia);
        sobreviventesDisponiveis.add(leo); sobreviventesDisponiveis.add(rita);
        sobreviventesDisponiveis.add(felipe); sobreviventesDisponiveis.add(anita);

        grupo.adicionar(joao); grupo.adicionar(ana);
        grupo.adicionar(pedro); grupo.adicionar(cintia); grupo.adicionar(leo);

        Tarefa pescar = new Tarefa("Pescar", "Pesca");
        System.out.println("Joao pode pescar? " + pescar.podeSerRealizadaPor(joao));
        System.out.println("Ana pode pescar? "  + pescar.podeSerRealizadaPor(ana));
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 3f;
        float delta = Gdx.graphics.getDeltaTime();
        moving = false;

        if (Gdx.input.isKeyPressed(Keys.A)) { px -= speed * delta; direcao = "left";  moving = true; }
        if (Gdx.input.isKeyPressed(Keys.D)) { px += speed * delta; direcao = "right"; moving = true; }
        if (Gdx.input.isKeyPressed(Keys.W)) { py += speed * delta; direcao = "up";    moving = true; }
        if (Gdx.input.isKeyPressed(Keys.S)) { py -= speed * delta; direcao = "down";  moving = true; }

        if (Gdx.input.isKeyJustPressed(Keys.E)) interagir();
        if (Gdx.input.isKeyJustPressed(Keys.F)) acenderFogueira();

        if (mapaAtual == 1 && py < 0) { mapaAtual = 2; py = viewport.getWorldHeight() - 1; }
        if (mapaAtual == 2 && py > viewport.getWorldHeight()) { mapaAtual = 1; py = 0; }

        if (Gdx.input.isKeyJustPressed(Keys.TAB)) {

            if (personagemAtual == personagemAgua) {
            personagemAtual = personagemFogo;
            mostrarMensagem("Personagem: Pedro (Fogo)");
        } else {
        personagemAtual = personagemAgua;
        mostrarMensagem("Personagem: Felipe (Agua)");
        }

}
    }

    private void logic() {
        if (moving) stateTime += Gdx.graphics.getDeltaTime();
        else stateTime = 0f;

        if (px < 0) px = 0;
        if (px > viewport.getWorldWidth() - 1) px = viewport.getWorldWidth() - 1;

        if (mensagemTimer > 0) mensagemTimer -= Gdx.graphics.getDeltaTime();
    }

    private void interagir() {
        if (mapaAtual == 1 && distancia(px, py, arvoreX, arvoreY) <= distanciaInteracao) {
            int madeira = arvore.coletar(3);
            madeiraColetada += madeira;
            mostrarMensagem(madeira > 0
                ? "Coletou 3 madeiras! Total: " + madeiraColetada
                : "Arvore sem madeira!");
        }

        if (mapaAtual == 2 && distancia(px, py, rioX, rioY) <= distanciaInteracao) {

            if (!personagemAtual.getHabilidade().equals("Agua")) {
            mostrarMensagem("Somente Felipe pode coletar agua!");
            return;
        }

        int agua = rio.coletar(5);

        if (agua > 0) {
            aguaColetada += agua;
            baldeCheio = true;
        }

        mostrarMensagem(
                agua > 0
                ? "Agua coletada!"
                : "Rio seco!"
        );
}
    }

    private void acenderFogueira() {

        if (!personagemAtual.getHabilidade().equals("Fogo")) {
            mostrarMensagem("Somente Pedro pode acender a fogueira!");
            return;
        }

        if (mapaAtual == 1 && distancia(px, py, fogueiraX, fogueiraY) <= distanciaInteracao) {
            if (fogueiraAcesa) {
                mostrarMensagem("A fogueira ja esta acesa!");
            } else if (madeiraColetada >= 3) {
                fogueiraAcesa = true;
                madeiraColetada -= 3;
                mostrarMensagem("Fogueira acesa! Usou 3 madeiras.");
            } else {
                mostrarMensagem("Precisa de 3 madeiras! Voce tem: " + madeiraColetada);
            }
        }
    }

    private String direcaoFogueira() {
    float dx = fogueiraX - px;
    float dy = fogueiraY - py;
    float dist = distancia(px, py, fogueiraX, fogueiraY);

    if (dist <= distanciaInteracao) return "Pressione F para acender a fogueira!";

    // descobre a direção predominante
    if (Math.abs(dx) > Math.abs(dy)) {
        return "Fogueira fica para a " + (dx > 0 ? "DIREITA" : "ESQUERDA") + " (" + (int)dist + "m)";
    } else {
        return "Fogueira fica para " + (dy > 0 ? "CIMA" : "BAIXO") + " (" + (int)dist + "m)";
    }
}

    private float distancia(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2, dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void mostrarMensagem(String msg) {
        mensagem = msg;
        mensagemTimer = 10f;
        System.out.println(msg);
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float ww = viewport.getWorldWidth();
        float wh = viewport.getWorldHeight();

        if (mapaAtual == 1) {
            spriteBatch.draw(florestaTexture, 0, 0, ww, wh);
            spriteBatch.draw(arvoreTexture, arvoreX, arvoreY, 0.8f, 0.8f);
            
            spriteBatch.draw(madeiraTexture, fogueiraX, fogueiraY, 0.8f, 0.4f);
            if (fogueiraAcesa){
                spriteBatch.draw(fogoTexture, fogueiraX, fogueiraY, 2.0f, 2.0f);
            }
            for (int m = 0; m < Math.min(madeiraColetada, 9); m++) {
                spriteBatch.draw(madeiraTexture,
                    0.1f + (m % 3) * 0.45f,
                    0.1f + (m / 3) * 0.3f,
                    0.4f, 0.22f);
            }
        } else if (mapaAtual == 2) {
            spriteBatch.draw(rioTexture, 0, 0, ww, wh);
            spriteBatch.draw(
                baldeCheio ? baldeCheioTexture : baldeVazioTexture,
                rioX - 0.5f, rioY, 0.5f, 0.5f);
        }
        
        Texture spriteAtual;

        if (personagemAtual == personagemAgua) {
            spriteAtual = playerUmTexture;
        } else {
            spriteAtual = playerDoisTexture;
        }

        spriteBatch.draw(spriteAtual, px, py, 1f, 1f);

        if (mensagemTimer > 0) {
            font.setColor(Color.YELLOW);
            font.draw(spriteBatch, mensagem, 0.2f, 4.0f);
        }

        font.setColor(Color.WHITE);
        font.draw(spriteBatch, "Madeira: " + madeiraColetada + "  Agua: " + aguaColetada, 1.2f, 4.5f);
        font.draw(spriteBatch, "[E] Coletar  [F] Fogueira  [W/S] Trocar mapa", 0.2f, 0.35f);
        
        if (mapaAtual == 1){
            font.setColor(Color.CYAN);
            font.draw(spriteBatch, direcaoFogueira(), 0.2f, 4.2f);
        }

        font.setColor(Color.WHITE);

        font.draw(
            spriteBatch,
            "Atual: " + personagemAtual.getNome(),0.2f,3.8f);

        spriteBatch.end();
    }

    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        florestaTexture.dispose();
        rioTexture.dispose();
        arvoreTexture.dispose();
        madeiraTexture.dispose();
        baldeVazioTexture.dispose();
        baldeCheioTexture.dispose();
        playerSheet.dispose();
        fogoTexture.dispose();
    }
}