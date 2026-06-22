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
    Texture arbustoTexture;
    Texture arbustoFrutiferoTexture;
    Texture cestaTexture;
    Texture frutoTexture;
    Texture curaTexture;
    Personagem personagemAtual;
    Personagem personagemAgua;
    Personagem personagemFogo;
    Personagem personagemPesca;
    Personagem personagemConstrucao;
    Personagem personagemCura;

    Texture playerUmTexture;
    Texture playerDoisTexture;
    Texture playerTresTexture;
    Texture playerQuatroTexture;
    Texture playerCincoTexture;

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

    int mapaAtual = 1;
    float arvore1X = 3f, arvore1Y = 12f;
    float arvore2X = 4f, arvore2Y = 13f;
    float rioX = 6.0f, rioY = 1.0f;
    float baldeX = 18f, baldeY = 5f;
    float distanciaInteracao = 2f;
    float machadoX = 6f, machadoY = 14f;
    float fogueiraX = 14.8f, fogueiraY = 4.8f;
    float abrigoX = 14f, abrigoY = 11f;
    float anzolX = 10f, anzolY = 3.5f;
    float peixe1X = 8f, peixe1Y = 2f;
    float peixe2X = 12f, peixe2Y = 1f;
    float peixe3X = 13f, peixe3Y = 3f;
    float peixe4X = 6f, peixe4Y = 1f;
    float peixe5X = 10f, peixe5Y = 0f;
    float arbusto1X = 25.3f, arbusto1Y = 17.3f;
    float arbusto2X = 23.3f, arbusto2Y = 16.3f;
    float arbusto3X = 25.3f, arbusto3Y = 15.3f;
    float arbusto4X = 23.3f, arbusto4Y = 14.3f;
    float arbusto5X = 25.3f, arbusto5Y = 13.3f;
    float cestaX = 21.5f, cestaY = 16f;

    final float MAP_WIDTH = 30f;
    final float MAP_HEIGHT = 20f;

    boolean baldeCheio = false;
    boolean arvore1Cortada = false;
    boolean arvore2Cortada = false;

    boolean madeiraFogueiraColetada = false;
    boolean madeiraAbrigoColetada = false;
    boolean fogueiraMontada = false;
    boolean fogueiraAcesa = false;

    boolean abrigoConstruido = false;

    boolean peixe1Pescado = false;
    boolean peixe2Pescado = false;
    boolean peixe3Pescado = false;
    boolean peixe4Pescado = false;
    boolean peixe5Pescado = false;

    int peixesColetados = 0;

    boolean arbusto1Coletado = false;
    boolean arbusto2Coletado = false;
    boolean arbusto3Coletado = false;
    boolean arbusto4Coletado = false;
    boolean arbusto5Coletado = false;

    int frutosColetados = 0;

    boolean curaCriada = false;

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
        arbustoTexture = new Texture("arbusto.png");
        arbustoFrutiferoTexture = new Texture("arbustoFrutifero.png");
        cestaTexture = new Texture("cesta.png");
        frutoTexture = new Texture("fruto.png");
        curaTexture = new Texture("cura.png");

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
        personagemAtual = personagemAgua;
        personagemFogo = pedro;
        personagemPesca = joao;
        personagemConstrucao = leo;
        personagemCura = ana;

        playerUmTexture = new Texture("personagem1.png");
        playerDoisTexture = new Texture("personagem2.png");
        playerTresTexture = new Texture("personagem3.png");
        playerQuatroTexture = new Texture("personagem4.png");
        playerCincoTexture = new Texture("personagem5.png");

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
        if (Gdx.input.isKeyJustPressed(Keys.C)) {

            if (personagemAtual.getHabilidade().equals("Fogo")) {
                montarFogueira();
            }

            if (personagemAtual.getHabilidade().equals("Cura")) {
                criarCura();
            }
        }

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
        if (Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
            personagemAtual = personagemConstrucao;
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_5)) {
            personagemAtual = personagemCura;
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
        if (mapaAtual == 1) {

            // arvore pedro
            if (distancia(px, py, arvore1X, arvore1Y) <= distanciaInteracao) {
                if (!personagemAtual.getHabilidade().equals("Fogo")) {
                    mostrarMensagem("Somente Pedro pode cortar esta arvore!");
                    return;
                }
                if (madeiraFogueiraColetada) {
                    mostrarMensagem("Madeira para fogueira coletada!");
                    return;
                }
                arvore1Cortada = true;
                madeiraFogueiraColetada = true;

                mostrarMensagem("Madeira para a fogueira coletada!");
                return;
            }

            // arvore leo
            if (distancia(px, py, arvore2X, arvore2Y) <= distanciaInteracao) {
                if (!personagemAtual.getHabilidade().equals("Construcao")) {
                    mostrarMensagem("Somente Leo pode cortar esta arvore!");
                    return;
                }
                if (madeiraAbrigoColetada) {
                    mostrarMensagem("Madeira para abrigo coletada!");
                    return;
                }
                arvore2Cortada = true;
                madeiraAbrigoColetada = true;

                mostrarMensagem("Madeira para o abrigo coletada!");
                return;
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

        if (mapaAtual == 2 && personagemAtual.getHabilidade().equals("Cura")) {
            coletarFrutos();
        }
    }

    private void montarFogueira() {
        if (!personagemAtual.getHabilidade().equals("Fogo")) {
            return;
        }

        if (!madeiraFogueiraColetada) {
            mostrarMensagem("Colete madeira primeiro!");
            return;
        }

        if (fogueiraMontada) {
            mostrarMensagem("Fogueira ja montada!");
            return;
        }

        if (distancia(px, py, fogueiraX, fogueiraY) > distanciaInteracao) {
            mostrarMensagem("Va ate o local da fogueira!");
            return;
        }

        fogueiraMontada = true;

        mostrarMensagem("Fogueira montada!");
    }

    private void acenderFogueira() {
        if (!personagemAtual.getHabilidade().equals("Fogo")) {
            mostrarMensagem("Somente Pedro pode acender a fogueira!");
            return;
        }
        if (distancia(px, py, fogueiraX, fogueiraY) > distanciaInteracao) {
            return;
        }
        if (!fogueiraMontada) {
            mostrarMensagem("Monte a fogueira primeiro!");
            return;
        }
        if (fogueiraAcesa) {
            mostrarMensagem("Ja esta acesa!");
            return;
        }
        fogueiraAcesa = true;
        mostrarMensagem("Fogueira acesa!");
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
        if (!personagemAtual.getHabilidade().equals("Construcao")) {
            mostrarMensagem("Somente Leo pode construir o abrigo!");
            return;
        }

        if (abrigoConstruido) {
            mostrarMensagem("Abrigo ja esta pronto!");
            return;
        }

        if (!madeiraAbrigoColetada) {
            mostrarMensagem("Colete a madeira do abrigo primeiro!");
            return;
        }

        madeiraAbrigoColetada = false;
        abrigoConstruido = true;

        mostrarMensagem("Abrigo construido!");
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

        if (!peixe4Pescado) {
            peixe4Pescado = true;
            peixesColetados++;
            mostrarMensagem("Peixe capturado!");
            return;
        }

        if (!peixe5Pescado) {
            peixe5Pescado = true;
            peixesColetados++;
            mostrarMensagem("Peixe capturado!");
            return;
        }

        mostrarMensagem("Nao ha mais peixes neste rio!");
    }

    private void coletarFrutos() {
        if (!personagemAtual.getHabilidade().equals("Cura")) {
            mostrarMensagem("Somente Ana pode coletar frutos!");
            return;
        }

        if (mapaAtual != 2) {
            return;
        }

        if (!arbusto1Coletado && distancia(px, py, arbusto1X, arbusto1Y) <= distanciaInteracao) {
            arbusto1Coletado = true;
            frutosColetados++;
            mostrarMensagem("Fruto coletado!");
            return;
        }

        if (!arbusto2Coletado && distancia(px, py, arbusto2X, arbusto2Y) <= distanciaInteracao) {
            arbusto2Coletado = true;
            frutosColetados++;
            mostrarMensagem("Fruto coletado!");
            return;
        }

        if (!arbusto3Coletado && distancia(px, py, arbusto3X, arbusto3Y) <= distanciaInteracao) {
            arbusto3Coletado = true;
            frutosColetados++;
            mostrarMensagem("Fruto coletado!");
            return;
        }

        if (!arbusto4Coletado && distancia(px, py, arbusto4X, arbusto4Y) <= distanciaInteracao) {
            arbusto4Coletado = true;
            frutosColetados++;
            mostrarMensagem("Fruto coletado!");
            return;
        }

        if (!arbusto5Coletado && distancia(px, py, arbusto5X, arbusto5Y) <= distanciaInteracao) {
            arbusto5Coletado = true;
            frutosColetados++;
            mostrarMensagem("Fruto coletado!");
            return;
        }

        mostrarMensagem("Nenhum arbusto por perto!");
    }

    private void criarCura() {
        if (!personagemAtual.getHabilidade().equals("Cura")) {
            mostrarMensagem("Somente Ana pode produzir a cura!");
            return;
        }

        if (curaCriada) {
            mostrarMensagem("A cura ja foi produzida!");
            return;
        }

        if (frutosColetados < 5) {
            mostrarMensagem("Colete os 5 frutos primeiro!");
            return;
        }

        frutosColetados = 0;
        curaCriada = true;
        mostrarMensagem("Cura produzida!");
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
            if (fogueiraMontada) {
                spriteBatch.draw(fogueiraTexture, fogueiraX, fogueiraY, 1.5f, 1.5f);
            }

            // arvore leo
            if (!arvore2Cortada) {
                spriteBatch.draw(arvoreTexture, arvore2X, arvore2Y, 2f, 2f);
            } else {
                spriteBatch.draw(arvoreTroncoTexture, arvore2X + 0.5f, arvore2Y, 1f, 1f);
            }
            // arvore pedro
            if (!arvore1Cortada) {
                spriteBatch.draw(arvoreTexture, arvore1X, arvore1Y, 2f, 2f);
            } else {
                spriteBatch.draw(arvoreTroncoTexture, arvore1X + 0.5f, arvore1Y, 1f, 1f);
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

            spriteBatch.draw(cestaTexture, cestaX, cestaY, 0.8f, 0.8f);

            if (frutosColetados >= 1)
                spriteBatch.draw(frutoTexture, cestaX - 0.15f, cestaY + 0.05f, 0.8f, 0.8f);

            if (frutosColetados >= 2)
                spriteBatch.draw(frutoTexture, cestaX + 0.05f, cestaY + 0.05f, 0.8f, 0.8f);

            if (frutosColetados >= 3)
                spriteBatch.draw(frutoTexture, cestaX + 0.25f, cestaY + 0.05f, 0.8f, 0.8f);

            if (frutosColetados >= 4)
                spriteBatch.draw(frutoTexture, cestaX - 0.05f, cestaY + 0.20f, 0.8f, 0.8f);

            if (frutosColetados >= 5)
                spriteBatch.draw(frutoTexture, cestaX + 0.15f, cestaY + 0.20f, 0.8f, 0.8f);

            spriteBatch.draw(arbusto1Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto1X, arbusto1Y, 1.5f,
                    1.5f);
            spriteBatch.draw(arbusto2Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto2X, arbusto2Y, 1.5f,
                    1.5f);
            spriteBatch.draw(arbusto3Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto3X, arbusto3Y, 1.5f,
                    1.5f);
            spriteBatch.draw(arbusto4Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto4X, arbusto4Y, 1.6f,
                    1.6f);
            spriteBatch.draw(arbusto5Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto5X, arbusto5Y, 1.6f,
                    1.6f);

            if (!peixe1Pescado)
                spriteBatch.draw(peixeTexture, peixe1X, peixe1Y, 0.6f, 0.6f);

            if (!peixe2Pescado)
                spriteBatch.draw(peixeTexture, peixe2X, peixe2Y, 0.6f, 0.6f);

            if (!peixe3Pescado)
                spriteBatch.draw(peixeTexture, peixe3X, peixe3Y, 0.6f, 0.6f);

            if (!peixe4Pescado)
                spriteBatch.draw(peixeTexture, peixe4X, peixe4Y, 0.6f, 0.6f);

            if (!peixe5Pescado)
                spriteBatch.draw(peixeTexture, peixe5X, peixe5Y, 0.6f, 0.6f);
        }

        Texture texturaAtual;

        if (personagemAtual == personagemAgua) {
            texturaAtual = playerUmTexture;
        } else if (personagemAtual == personagemFogo) {
            texturaAtual = playerDoisTexture;
        } else if (personagemAtual == personagemPesca) {
            texturaAtual = playerTresTexture;
        } else if (personagemAtual == personagemConstrucao) {
            texturaAtual = playerQuatroTexture;
        } else if (personagemAtual == personagemCura) {
            texturaAtual = playerCincoTexture;
        } else {
            texturaAtual = playerUmTexture;
        }

        spriteBatch.draw(texturaAtual, px, py, playerSize, playerSize);
        if (curaCriada && personagemAtual == personagemCura) {
            float curaX = px + 0.55f, curaY = py + 0.15f;

            spriteBatch.draw(curaTexture, curaX, curaY, 0.45f, 0.45f);
        }

        if (mensagemTimer > 0)
            font.draw(spriteBatch, mensagem, camera.position.x - 5, camera.position.y + 4);
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
        playerQuatroTexture.dispose();
        arbustoTexture.dispose();
        arbustoFrutiferoTexture.dispose();
        playerCincoTexture.dispose();
        cestaTexture.dispose();
        frutoTexture.dispose();
        curaTexture.dispose();
    }
}