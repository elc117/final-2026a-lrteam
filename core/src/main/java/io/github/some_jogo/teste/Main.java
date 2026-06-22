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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;

import io.github.some_jogo.teste.model.Personagem;
import io.github.some_jogo.teste.model.Tarefa;
import io.github.some_jogo.teste.model.Grupo;
import io.github.some_jogo.teste.model.Arvore;
import io.github.some_jogo.teste.model.Rio;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Main implements ApplicationListener {

    Texture arvoreTexture, arvoreTroncoTexture;
    Texture baldeVazioTexture, baldeCheioTexture, madeiraTexture;
    Texture fogoTexture;
    Texture machadoTexture;
    Texture fogueiraTexture;
    Texture abrigoTexture;
    Texture anzolTexture;
    Texture peixeTexture;
    Personagem personagemAgua;
    Personagem personagemFogo;
    Personagem personagemAtual;
    Personagem personagemPesca;

    Texture playerUmTexture;
    Texture playerDoisTexture;
    Texture playerTresTexture;

    Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    TextureRegion idleFrame;

    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    FitViewport viewport;
    BitmapFont font;

    TiledMap mapa1;
    TiledMap mapa2;

    OrthogonalTiledMapRenderer renderer1;
    OrthogonalTiledMapRenderer renderer2;

    OrthographicCamera camera;

    Grupo grupo;
    ArrayList<Personagem> sobreviventesDisponiveis;

    float px = 1f, py = 2f;
    String direcao = "down";
    float stateTime = 0f;
    boolean moving = false;

    float playerSize = 1f;

    Rio rio;
    Arvore arvore;
    int aguaColetada = 0;
    int madeiraColetada = 0;

    int mapaAtual = 1;
    float arvoreX = 3f, arvoreY = 12f;
    float rioX = 6.0f, rioY = 1.0f;
    float baldeX = 18f, baldeY = 5f;
    float distanciaInteracao = 1.5f;
    float machadoX = 5f, machadoY = 14f;
    float fogueiraX = 14.8f, fogueiraY = 4.8f;
    float abrigoX = 14f, abrigoY = 11f;
    float anzolX = 10f, anzolY = 3.5f;
    float peixe1X = 8f, peixe1Y = 2f;
    float peixe2X = 12f, peixe2Y = 1f;
    float peixe3X = 13f, peixe3Y = 3f;

    // Defina aqui as dimensões do seu mapa (ajuste se necessário)
    final float MAP_WIDTH = 30f;
    final float MAP_HEIGHT = 20f;

    boolean fogueiraAcesa = false;
    boolean baldeCheio = false;
    boolean arvoreCortada = false;
    boolean abrigoConstruido = false;
    boolean peixe1Pescado = false;
    boolean peixe2Pescado = false;
    boolean peixe3Pescado = false;

    int peixesColetados = 0;

    String mensagem = "";
    float mensagemTimer = 0f;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(20, 12, camera);
        viewport.apply();
        camera.setToOrtho(false, 20, 12);
        camera.update();

        mapa1 = new TmxMapLoader().load("mapas/mapaFloresta.tmx");
        mapa2 = new TmxMapLoader().load("mapas/mapaRio.tmx");

        renderer1 = new OrthogonalTiledMapRenderer(mapa1, 1f / 16f);
        renderer2 = new OrthogonalTiledMapRenderer(mapa2, 1f / 16f);

        mapaAtual = 1;
        px = 14f;
        py = 11f;

        font = new BitmapFont();
        font.getData().setScale(0.03f);

        arvoreTexture = new Texture("arvore.png");
        arvoreTroncoTexture = new Texture("arvoreTronco.png");
        madeiraTexture = new Texture("madeira.png");
        baldeVazioTexture = new Texture("baldeVazio.png");
        baldeCheioTexture = new Texture("baldeCheio.png");
        fogoTexture = new Texture("fogo.png");
        machadoTexture = new Texture("machado.png");
        fogueiraTexture = new Texture("fogueira.png");
        abrigoTexture = new Texture("abrigo.png");
        anzolTexture = new Texture("anzol.png");
        peixeTexture = new Texture("peixe.png");

        rio = new Rio(50);
        arvore = new Arvore(30);

        grupo = new Grupo();
        sobreviventesDisponiveis = new ArrayList<>();

        Personagem joao = new Personagem("Joao", "Pesca");
        Personagem ana = new Personagem("Ana", "Cura");
        Personagem pedro = new Personagem("Pedro", "Fogo");
        Personagem cintia = new Personagem("Cintia", "Danca");
        Personagem leo = new Personagem("Leo", "Construcao");
        Personagem rita = new Personagem("Rita", "Piloto");
        Personagem felipe = new Personagem("Felipe", "Agua");
        Personagem anita = new Personagem("Anita", "Canto");

        personagemAgua = felipe;
        personagemFogo = pedro;
        personagemAtual = personagemAgua;
        personagemPesca = joao;

        playerUmTexture = new Texture("personagem1.png");
        playerDoisTexture = new Texture("personagem2.png");
        playerTresTexture = new Texture("personagem3.png");

        sobreviventesDisponiveis.add(joao);
        sobreviventesDisponiveis.add(ana);
        sobreviventesDisponiveis.add(pedro);
        sobreviventesDisponiveis.add(cintia);
        sobreviventesDisponiveis.add(leo);
        sobreviventesDisponiveis.add(rita);
        sobreviventesDisponiveis.add(felipe);
        sobreviventesDisponiveis.add(anita);

        grupo.adicionar(joao);
        grupo.adicionar(ana);
        grupo.adicionar(pedro);
        grupo.adicionar(cintia);
        grupo.adicionar(leo);
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0)
            return;
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

        if (Gdx.input.isKeyPressed(Keys.A)) {
            px -= speed * delta;
            direcao = "left";
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            px += speed * delta;
            direcao = "right";
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            py += speed * delta;
            direcao = "up";
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            py -= speed * delta;
            direcao = "down";
            moving = true;
        }

        if (Gdx.input.isKeyJustPressed(Keys.E))
            interagir();
        if (Gdx.input.isKeyJustPressed(Keys.F))
            acenderFogueira();
        if (Gdx.input.isKeyJustPressed(Keys.B))
            montarAbrigo();
        if (Gdx.input.isKeyJustPressed(Keys.P))
            pescar();

        if (mapaAtual == 1 && py < 0) {
            mapaAtual = 2;
            py = MAP_HEIGHT - playerSize - 1; // Coloca o jogador um pouco abaixo do topo
        }

        // Se chegar perto do topo do mapa 2 (ajustado para o limite do clamp)
        if (mapaAtual == 2 && py >= (MAP_HEIGHT - playerSize)) {
            mapaAtual = 1;
            py = 1; // Coloca o jogador na base do mapa 1
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
            personagemAtual = personagemAgua;
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
            personagemAtual = personagemFogo;
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
            personagemAtual = personagemPesca;
        }
    }

    private void logic() {
        if (moving)
            stateTime += Gdx.graphics.getDeltaTime();
        else
            stateTime = 0f;

        px = MathUtils.clamp(px, 0, MAP_WIDTH - playerSize);
        py = MathUtils.clamp(py, 0, MAP_HEIGHT - playerSize);

        if (mensagemTimer > 0)
            mensagemTimer -= Gdx.graphics.getDeltaTime();
    }

    private void interagir() {
        if (mapaAtual == 1 && distancia(px, py, arvoreX, arvoreY) <= distanciaInteracao) {
            if (!arvoreCortada) {
                int madeira = arvore.coletar(3);
                madeiraColetada += madeira;

                arvoreCortada = true;
                mostrarMensagem("Coletou madeira!");
            } else {
                mostrarMensagem("Árvore já foi cortada!");
            }
        }
        if (mapaAtual == 2 && distancia(px, py, baldeX, baldeY) <= distanciaInteracao) {
            if (!personagemAtual.getHabilidade().equals("Agua")) {
                mostrarMensagem("Somente Felipe pode coletar agua!");
                return;
            }
            int agua = rio.coletar(5);
            if (agua > 0) {
                aguaColetada += agua;
                baldeCheio = true;
            }
            mostrarMensagem(agua > 0 ? "Agua coletada!" : "Rio seco!");
        }
    }

    private void acenderFogueira() {
        if (!personagemAtual.getHabilidade().equals("Fogo")) {
            mostrarMensagem("Somente Pedro pode acender a fogueira!");
            return;
        }
        if (mapaAtual == 1 && distancia(px, py, fogueiraX, fogueiraY) <= distanciaInteracao) {
            if (fogueiraAcesa)
                mostrarMensagem("Ja esta acesa!");
            else if (madeiraColetada >= 3) {
                fogueiraAcesa = true;
                madeiraColetada -= 3;
                mostrarMensagem("Fogueira acesa!");
            } else
                mostrarMensagem("Precisa de 3 madeiras!");
        }
    }

    private String direcaoFogueira() {
        float dx = fogueiraX - px;
        float dy = fogueiraY - py;
        float dist = distancia(px, py, fogueiraX, fogueiraY);
        if (dist <= distanciaInteracao)
            return "Pressione F!";
        if (Math.abs(dx) > Math.abs(dy))
            return "Fogueira: " + (dx > 0 ? "DIREITA" : "ESQUERDA");
        else
            return "Fogueira: " + (dy > 0 ? "CIMA" : "BAIXO");
    }

    private float distancia(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2, dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void mostrarMensagem(String msg) {
        mensagem = msg;
        mensagemTimer = 10f;
    }

    private void montarAbrigo() {
        if (!abrigoConstruido) {
            abrigoConstruido = true;
            mostrarMensagem("Abrigo construído!");
        } else {
            mostrarMensagem("Abrigo já está pronto!");
        }
    }

    private void pescar() {

        if (!personagemAtual.getHabilidade().equals("Pesca")) {
            mostrarMensagem("Somente Joao pode pescar!");
            return;
        }

        if (mapaAtual != 2) {
            return;
        }

        if (distancia(px, py, anzolX, anzolY) > distanciaInteracao) {
            mostrarMensagem("Va ate o ponto de pesca!");
            return;
        }

        if (!peixe1Pescado) {
            peixe1Pescado = true;
            peixesColetados++;
            mostrarMensagem("Peixe capturado!");
            return;
        }

        if (!peixe2Pescado) {
            peixe2Pescado = true;
            peixesColetados++;
            mostrarMensagem("Peixe capturado!");
            return;
        }

        if (!peixe3Pescado) {
            peixe3Pescado = true;
            peixesColetados++;
            mostrarMensagem("Peixe capturado!");
            return;
        }

        mostrarMensagem("Nao ha mais peixes neste rio!");
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        // LÓGICA DE TRAVAMENTO DA CÂMERA
        float viewportHalfWidth = camera.viewportWidth / 2f;
        float viewportHalfHeight = camera.viewportHeight / 2f;

        float cameraX = MathUtils.clamp(px, viewportHalfWidth, MAP_WIDTH - viewportHalfWidth);
        float cameraY = MathUtils.clamp(py, viewportHalfHeight, MAP_HEIGHT - viewportHalfHeight);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();
        // --------------------------------------

        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        if (mapaAtual == 1) {
            renderer1.setView(camera);
            renderer1.render();
            spriteBatch.draw(machadoTexture, machadoX, machadoY, 1.2f, 1.2f);
            spriteBatch.draw(fogueiraTexture, fogueiraX, fogueiraY, 1.5f, 1.5f);
            if (!arvoreCortada) {
                spriteBatch.draw(arvoreTexture, arvoreX, arvoreY, 2f, 2f);
            } else {
                spriteBatch.draw(arvoreTroncoTexture, arvoreX + 0.5f, arvoreY, 1f, 1f);
            }
            if (fogueiraAcesa) {
                float fogoX = 15.3f, fogoY = 5.5f;
                spriteBatch.draw(fogoTexture, fogoX, fogoY, 0.6f, 0.6f);
            }
            if (abrigoConstruido) {
                spriteBatch.draw(abrigoTexture, abrigoX, abrigoY, 3f, 3f);
            }
        } else if (mapaAtual == 2) {
            renderer2.setView(camera);
            renderer2.render();
            spriteBatch.draw(anzolTexture, anzolX, anzolY, 0.5f, 0.5f);
            spriteBatch.draw(baldeCheio ? baldeCheioTexture : baldeVazioTexture, baldeX, baldeY, 0.7f, 0.7f);

            if (!peixe1Pescado)
                spriteBatch.draw(peixeTexture, peixe1X, peixe1Y, 0.6f, 0.6f);

            if (!peixe2Pescado)
                spriteBatch.draw(peixeTexture, peixe2X, peixe2Y, 0.6f, 0.6f);

            if (!peixe3Pescado)
                spriteBatch.draw(peixeTexture, peixe3X, peixe3Y, 0.6f, 0.6f);
        }

        Texture texturaAtual;

        if (personagemAtual == personagemAgua) {
            texturaAtual = playerUmTexture;
        } else if (personagemAtual == personagemFogo) {
            texturaAtual = playerDoisTexture;
        } else if (personagemAtual == personagemPesca) {
            texturaAtual = playerTresTexture;
        } else {
            texturaAtual = playerUmTexture;
        }

        spriteBatch.draw(texturaAtual, px, py, playerSize, playerSize);

        if (mensagemTimer > 0)
            font.draw(spriteBatch, mensagem, camera.position.x - 5, camera.position.y + 4);
        font.draw(spriteBatch, "Madeira: " + madeiraColetada, camera.position.x - 9, camera.position.y + 5);
        font.draw(spriteBatch, "Peixes: " + peixesColetados, camera.position.x - 9, camera.position.y + 4);

        spriteBatch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        arvoreTexture.dispose();
        arvoreTroncoTexture.dispose();
        madeiraTexture.dispose();
        machadoTexture.dispose();
        baldeVazioTexture.dispose();
        baldeCheioTexture.dispose();
        anzolTexture.dispose();
        fogueiraTexture.dispose();
        fogoTexture.dispose();
        abrigoTexture.dispose();
        playerUmTexture.dispose();
        playerDoisTexture.dispose();
        playerTresTexture.dispose();
        peixeTexture.dispose();
    }
}