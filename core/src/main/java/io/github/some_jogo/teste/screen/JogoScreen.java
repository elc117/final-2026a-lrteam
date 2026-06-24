package io.github.some_jogo.teste.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;

import io.github.some_jogo.teste.enums.Habilidade;
import io.github.some_jogo.teste.model.Abrigo;
import io.github.some_jogo.teste.model.Fogueira;
import io.github.some_jogo.teste.model.Personagem;
import io.github.some_jogo.teste.model.Rio;

public class JogoScreen {

    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private BitmapFont gameFont;

    private Texture arvoreTexture, arvoreTroncoTexture;
    private Texture baldeVazioTexture, baldeCheioTexture, madeiraTexture;
    private Texture fogoTexture;
    private Texture machadoTexture;
    private Texture fogueiraTexture;
    private Texture abrigoTexture;
    private Texture anzolTexture;
    private Texture peixeTexture;
    private Texture arbustoTexture;
    private Texture arbustoFrutiferoTexture;
    private Texture cestaTexture;
    private Texture frutoTexture;
    private Texture curaTexture;
    private Texture mesaTexture;
    private Texture copoTexture;
    private Texture aviaoTexture;

    private Texture playerUmTexture;
    private Texture playerDoisTexture;
    private Texture playerTresTexture;
    private Texture playerQuatroTexture;
    private Texture playerCincoTexture;
    private Texture playerSeisTexture;
    private Texture playerSeteTexture;
    private Texture playerOitoTexture;
    private Texture playerNoveTexture;
    private Texture playerDezTexture;

    private TiledMap mapa1;
    private TiledMap mapa2;
    private OrthogonalTiledMapRenderer renderer1;
    private OrthogonalTiledMapRenderer renderer2;

    private ArrayList<Personagem> sobreviventesSelecionados;
    private Personagem personagemAtual;
    private Personagem personagemAgua;
    private Personagem personagemFogo;
    private Personagem personagemPesca;
    private Personagem personagemConstrucao;
    private Personagem personagemCura;

    private Rio rio;
    private Fogueira fogueira;
    private Abrigo abrigo;

    private int mapaAtual = 1;

    private float px = 1f, py = 2f;
    private boolean moving = false;
    private float playerSize = 1f;

    private float arvore1X = 3f, arvore1Y = 12f;
    private float arvore2X = 4f, arvore2Y = 13f;
    private float baldeX = 18f, baldeY = 5f;
    private float distanciaInteracao = 2f;
    private float machadoX = 6f, machadoY = 14f;
    private float fogueiraX = 14.8f, fogueiraY = 4.8f;
    private float abrigoX = 14f, abrigoY = 11f;
    private float mesaX = 13f, mesaY = 11f;
    private float anzolX = 10f, anzolY = 3.5f;
    private float peixe1X = 8f, peixe1Y = 2f;
    private float peixe2X = 12f, peixe2Y = 1f;
    private float peixe3X = 13f, peixe3Y = 3f;
    private float peixe4X = 6f, peixe4Y = 1f;
    private float peixe5X = 10f, peixe5Y = 0f;
    private float arbusto1X = 25.3f, arbusto1Y = 17.3f;
    private float arbusto2X = 23.3f, arbusto2Y = 16.3f;
    private float arbusto3X = 25.3f, arbusto3Y = 15.3f;
    private float arbusto4X = 23.3f, arbusto4Y = 14.3f;
    private float arbusto5X = 25.3f, arbusto5Y = 13.3f;
    private float cestaX = 21.5f, cestaY = 15f;
    private float aviaoX = 19f, aviaoY = 9f;

    final float MAP_WIDTH = 30f;
    final float MAP_HEIGHT = 20f;

    private boolean baldeCheio = false;
    private boolean carregandoAgua = false;
    private boolean aguaEntregue = false;

    private boolean arvore1Cortada = false;
    private boolean arvore2Cortada = false;
    private boolean madeiraFogueiraColetada = false;
    private boolean madeiraAbrigoColetada = false;

    private boolean peixe1Pescado = false;
    private boolean peixe2Pescado = false;
    private boolean peixe3Pescado = false;
    private boolean peixe4Pescado = false;
    private boolean peixe5Pescado = false;
    private int peixesColetados = 0;

    private boolean arbusto1Coletado = false;
    private boolean arbusto2Coletado = false;
    private boolean arbusto3Coletado = false;
    private boolean arbusto4Coletado = false;
    private boolean arbusto5Coletado = false;
    private boolean curaCriada = false;
    private int frutosColetados = 0;

    private String mensagem = "";
    private float mensagemTimer = 0f;

    private boolean jogoFinalizado = false;

    public JogoScreen(SpriteBatch spriteBatch, FitViewport viewport, OrthographicCamera camera, BitmapFont gameFont) {
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        this.camera = camera;
        this.gameFont = gameFont;

        mapa1 = new TmxMapLoader().load("mapas/mapaFloresta.tmx");
        mapa2 = new TmxMapLoader().load("mapas/mapaRio.tmx");

        renderer1 = new OrthogonalTiledMapRenderer(mapa1, 1f / 16f);
        renderer2 = new OrthogonalTiledMapRenderer(mapa2, 1f / 16f);

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
        mesaTexture = new Texture("mesa.png");
        copoTexture = new Texture("copo.png");
        aviaoTexture = new Texture("aviao.png");

        playerUmTexture = new Texture("personagem1.png");
        playerDoisTexture = new Texture("personagem2.png");
        playerTresTexture = new Texture("personagem3.png");
        playerQuatroTexture = new Texture("personagem4.png");
        playerCincoTexture = new Texture("personagem5.png");
        playerSeisTexture = new Texture("personagem6.png");
        playerSeteTexture = new Texture("personagem7.png");
        playerOitoTexture = new Texture("personagem8.png");
        playerNoveTexture = new Texture("personagem9.png");
        playerDezTexture = new Texture("personagem10.png");

        fogueira = new Fogueira();
        abrigo = new Abrigo();

        rio = new Rio(50);
    }

    public void inicializar(ArrayList<Personagem> sobreviventesSelecionados, 
            Personagem personagemAgua, Personagem personagemFogo,
            Personagem personagemPesca, Personagem personagemConstrucao,
            Personagem personagemCura) {

        this.sobreviventesSelecionados = sobreviventesSelecionados;
        this.personagemAgua = personagemAgua;
        this.personagemFogo = personagemFogo;
        this.personagemPesca = personagemPesca;
        this.personagemConstrucao = personagemConstrucao;
        this.personagemCura = personagemCura;

        personagemAtual = sobreviventesSelecionados.get(0);

        px = 20.5f;
        py = 10.5f;
        mapaAtual = 1;
    }

    public void render() {
        input();
        logic();
        draw();
    }

    public boolean isJogoFinalizado() {
        return jogoFinalizado;
    }

    private void input() {
        float speed = 3f;
        float delta = Gdx.graphics.getDeltaTime();
        moving = false;

        if (Gdx.input.isKeyPressed(Keys.A)) {
            px -= speed * delta;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            px += speed * delta;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            py += speed * delta;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            py -= speed * delta;
            moving = true;
        }

        if (Gdx.input.isKeyJustPressed(Keys.E)) {
            interagir();
            pescar();
        }

        if (Gdx.input.isKeyJustPressed(Keys.F))
            acenderFogueira();

        if (Gdx.input.isKeyJustPressed(Keys.C)) {
            entregarAgua();
            montarFogueira();
            montarAbrigo();
            criarCura();
        }

        if (mapaAtual == 1 && py < 0) {
            mapaAtual = 2;
            py = MAP_HEIGHT - playerSize - 1;
        }

        if (mapaAtual == 2 && py >= (MAP_HEIGHT - playerSize)) {
            mapaAtual = 1;
            py = 1;
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) trocarPersonagem(0);
        if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) trocarPersonagem(1);
        if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) trocarPersonagem(2);
        if (Gdx.input.isKeyJustPressed(Keys.NUM_4)) trocarPersonagem(3);
        if (Gdx.input.isKeyJustPressed(Keys.NUM_5)) trocarPersonagem(4);
    }

    private void trocarPersonagem(int index) {
        if (index < sobreviventesSelecionados.size()) {
            personagemAtual = sobreviventesSelecionados.get(index);
        }
    }

    private void logic() {

        px = MathUtils.clamp(px, 0, MAP_WIDTH - playerSize);
        py = MathUtils.clamp(py, 0, MAP_HEIGHT - playerSize);

        if (mensagemTimer > 0)
            mensagemTimer -= Gdx.graphics.getDeltaTime();

        if (aguaEntregue
                && fogueira.isAcesa()
                && abrigo.isConstruido()
                && curaCriada
                && peixesColetados >= 5) {

            jogoFinalizado = true;
        }
    }

    private void interagir() {
        if (!personagemValido(personagemAtual)) {
            mostrarMensagem("Esse personagem nao sabe realizar tarefas!");
            return;
        }

        if (mapaAtual == 1) {
            if (distancia(px, py, arvore1X, arvore1Y) <= distanciaInteracao) {
                if (!personagemAtual.getHabilidade().equals(Habilidade.FOGO)) {
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

            if (distancia(px, py, arvore2X, arvore2Y) <= distanciaInteracao) {
                if (!personagemAtual.getHabilidade().equals(Habilidade.CONSTRUCAO)) {
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
            if (!personagemAtual.getHabilidade().equals(Habilidade.AGUA)) {
                mostrarMensagem("Somente Felipe pode coletar agua!");
                return;
            }
            int agua = rio.coletar(5);
            if (agua > 0) {
                baldeCheio = true;
                carregandoAgua = true;
            }
            mostrarMensagem(agua > 0 ? "Agua coletada!" : "Rio seco!");
        }

        if (mapaAtual == 2 && personagemAtual.getHabilidade() == Habilidade.CURA) {
            coletarFrutos();
        }
    }

    private void montarFogueira() {
        if (!personagemValido(personagemAtual)) {
            mostrarMensagem("Esse personagem nao sabe realizar tarefas!");
            return;
        }
        if (personagemFogo == null) {
            mostrarMensagem("Seu grupo nao possui especialista em Fogo!");
            return;
        }
        if (!personagemAtual.getHabilidade().equals(Habilidade.FOGO)) return;

        if (!madeiraFogueiraColetada) {
            mostrarMensagem("Colete madeira primeiro!");
            return;
        }
        if (fogueira.isAcesa()) {
            mostrarMensagem("Fogueira ja montada!");
            return;
        }
        if (distancia(px, py, fogueiraX, fogueiraY) > distanciaInteracao) {
            mostrarMensagem("Va ate o local da fogueira!");
            return;
        }

        mostrarMensagem("Fogueira montada!");
    }

    private void acenderFogueira() {
        if (!personagemValido(personagemAtual)) {
            mostrarMensagem("Esse personagem nao sabe realizar tarefas!");
            return;
        }
        if (personagemFogo == null) {
            mostrarMensagem("Seu grupo nao possui especialista em Fogo!");
            return;
        }
        if (!personagemAtual.getHabilidade().equals(Habilidade.FOGO)) {
            mostrarMensagem("Somente Pedro pode acender a fogueira!");
            return;
        }
        if (distancia(px, py, fogueiraX, fogueiraY) > distanciaInteracao) return;

        if (!madeiraFogueiraColetada) {
            mostrarMensagem("Monte a fogueira primeiro!");
            return;
        }
        if (fogueira.isAcesa()) {
            mostrarMensagem("Ja esta acesa!");
            return;
        }

        fogueira.acender();
        mostrarMensagem("Fogueira acesa!");
    }

    private void montarAbrigo() {
        if (!personagemValido(personagemAtual)) {
            mostrarMensagem("Esse personagem nao sabe realizar tarefas!");
            return;
        }
        if (personagemConstrucao == null) {
            mostrarMensagem("Seu grupo nao possui especialista em Construcao!");
            return;
        }
        if (!personagemAtual.getHabilidade().equals(Habilidade.CONSTRUCAO)) {
            mostrarMensagem("Somente Leo pode construir o abrigo!");
            return;
        }
        if (abrigo.isConstruido()) {
            mostrarMensagem("Abrigo ja esta pronto!");
            return;
        }
        if (!madeiraAbrigoColetada) {
            mostrarMensagem("Colete a madeira do abrigo primeiro!");
            return;
        }

        madeiraAbrigoColetada = false;
        abrigo.construir();
        mostrarMensagem("Abrigo construido!");
    }

    private void pescar() {
        if (!personagemValido(personagemAtual)) {
            mostrarMensagem("Esse personagem nao sabe realizar tarefas!");
            return;
        }
        if (personagemPesca == null) {
            mostrarMensagem("Seu grupo nao possui especialista em Pesca!");
            return;
        }
        if (!personagemAtual.getHabilidade().equals(Habilidade.PESCA)) {
            mostrarMensagem("Somente Joao pode pescar!");
            return;
        }
        if (mapaAtual != 2) return;

        if (distancia(px, py, anzolX, anzolY) > distanciaInteracao) {
            mostrarMensagem("Va ate o ponto de pesca!");
            return;
        }

        if (!peixe1Pescado) { peixe1Pescado = true; peixesColetados++; mostrarMensagem("Peixe capturado!"); return; }
        if (!peixe2Pescado) { peixe2Pescado = true; peixesColetados++; mostrarMensagem("Peixe capturado!"); return; }
        if (!peixe3Pescado) { peixe3Pescado = true; peixesColetados++; mostrarMensagem("Peixe capturado!"); return; }
        if (!peixe4Pescado) { peixe4Pescado = true; peixesColetados++; mostrarMensagem("Peixe capturado!"); return; }
        if (!peixe5Pescado) { peixe5Pescado = true; peixesColetados++; mostrarMensagem("Peixe capturado!"); return; }

        mostrarMensagem("Nao ha mais peixes neste rio!");
    }

    private void coletarFrutos() {
        if (!personagemValido(personagemAtual)) {
            mostrarMensagem("Esse personagem nao sabe realizar tarefas!");
            return;
        }
        if (!personagemAtual.getHabilidade().equals(Habilidade.CURA)) {
            mostrarMensagem("Somente Ana pode coletar frutos!");
            return;
        }
        if (mapaAtual != 2) return;

        if (!arbusto1Coletado && distancia(px, py, arbusto1X, arbusto1Y) <= distanciaInteracao) { arbusto1Coletado = true; frutosColetados++; mostrarMensagem("Fruto coletado!"); return; }
        if (!arbusto2Coletado && distancia(px, py, arbusto2X, arbusto2Y) <= distanciaInteracao) { arbusto2Coletado = true; frutosColetados++; mostrarMensagem("Fruto coletado!"); return; }
        if (!arbusto3Coletado && distancia(px, py, arbusto3X, arbusto3Y) <= distanciaInteracao) { arbusto3Coletado = true; frutosColetados++; mostrarMensagem("Fruto coletado!"); return; }
        if (!arbusto4Coletado && distancia(px, py, arbusto4X, arbusto4Y) <= distanciaInteracao) { arbusto4Coletado = true; frutosColetados++; mostrarMensagem("Fruto coletado!"); return; }
        if (!arbusto5Coletado && distancia(px, py, arbusto5X, arbusto5Y) <= distanciaInteracao) { arbusto5Coletado = true; frutosColetados++; mostrarMensagem("Fruto coletado!"); return; }

        mostrarMensagem("Nenhum arbusto por perto!");
    }

    private void criarCura() {
        if (!personagemValido(personagemAtual)) {
            mostrarMensagem("Esse personagem nao sabe realizar tarefas!");
            return;
        }
        if (personagemCura == null) {
            mostrarMensagem("Seu grupo nao possui especialista em Cura!");
            return;
        }
        if (!personagemAtual.getHabilidade().equals(Habilidade.CURA)) {
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
        if (distancia(px, py, cestaX, cestaY) > distanciaInteracao) {
            mostrarMensagem("Va ate a cesta para preparar a cura!");
            return;
        }

        frutosColetados = 0;
        curaCriada = true;
        mostrarMensagem("Cura produzida!");
    }

    private void entregarAgua() {
        if (!personagemValido(personagemAtual)) {
            mostrarMensagem("Esse personagem nao sabe realizar tarefas!");
            return;
        }
        if (personagemAgua == null) {
            mostrarMensagem("Seu grupo nao possui especialista em Agua!");
            return;
        }
        if (!personagemAtual.getHabilidade().equals(Habilidade.AGUA)) return;

        if (!carregandoAgua) {
            mostrarMensagem("Colete agua primeiro!");
            return;
        }
        if (!abrigo.isConstruido()) {
            mostrarMensagem("Construa o abrigo primeiro!");
            return;
        }
        if (distancia(px, py, mesaX, mesaY) > distanciaInteracao) {
            mostrarMensagem("Va ate a mesa do abrigo!");
            return;
        }

        carregandoAgua = false;
        aguaEntregue = true;
        baldeCheio = false;
        mostrarMensagem("Agua entregue!");
    }

    private Texture getTextureDoPersonagem(Personagem p) {
        if (p == null) return playerUmTexture;

        String nome = p.getNome();

        if (nome.equalsIgnoreCase("Joao")) return playerUmTexture;
        if (nome.equalsIgnoreCase("Ana")) return playerDoisTexture;
        if (nome.equalsIgnoreCase("Pedro")) return playerTresTexture;
        if (nome.equalsIgnoreCase("Leo")) return playerQuatroTexture;
        if (nome.equalsIgnoreCase("Felipe")) return playerCincoTexture;
        if (nome.equalsIgnoreCase("Cintia")) return playerSeisTexture;
        if (nome.equalsIgnoreCase("Rita")) return playerSeteTexture;
        if (nome.equalsIgnoreCase("Anita")) return playerOitoTexture;
        if (nome.equalsIgnoreCase("Bruno")) return playerNoveTexture;
        if (nome.equalsIgnoreCase("Marina")) return playerDezTexture;

        return playerUmTexture;
    }

    private boolean personagemValido(Personagem p) {
        String nome = p.getNome();
        return nome.equals("Joao") ||
                nome.equals("Ana") ||
                nome.equals("Pedro") ||
                nome.equals("Leo") ||
                nome.equals("Felipe");
    }

    private float distancia(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2, dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void mostrarMensagem(String msg) {
        mensagem = msg;
        mensagemTimer = 10f;
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        gameFont.getData().setScale(0.02f);

        float viewportHalfWidth = camera.viewportWidth / 2f;
        float viewportHalfHeight = camera.viewportHeight / 2f;

        float cameraX = MathUtils.clamp(px, viewportHalfWidth, MAP_WIDTH - viewportHalfWidth);
        float cameraY = MathUtils.clamp(py, viewportHalfHeight, MAP_HEIGHT - viewportHalfHeight);

        camera.position.set(cameraX, cameraY, 0);
        camera.update();

        viewport.apply();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        if (mapaAtual == 1) {
            renderer1.setView(camera);
            renderer1.render();
            spriteBatch.draw(aviaoTexture, aviaoX, aviaoY, 9.5f, 9.5f);
            spriteBatch.draw(machadoTexture, machadoX, machadoY, 1.2f, 1.2f);

            if (fogueira.isAcesa()) {
                spriteBatch.draw(fogueiraTexture, fogueiraX, fogueiraY, 1.5f, 1.5f);
            }

            if (!arvore2Cortada) {
                spriteBatch.draw(arvoreTexture, arvore2X, arvore2Y, 2f, 2f);
            } else {
                spriteBatch.draw(arvoreTroncoTexture, arvore2X + 0.5f, arvore2Y, 1f, 1f);
            }

            if (!arvore1Cortada) {
                spriteBatch.draw(arvoreTexture, arvore1X, arvore1Y, 2f, 2f);
            } else {
                spriteBatch.draw(arvoreTroncoTexture, arvore1X + 0.5f, arvore1Y, 1f, 1f);
            }

            if (fogueira.isAcesa()) {
                float fogoX = 15.3f, fogoY = 5.5f;
                spriteBatch.draw(fogoTexture, fogoX, fogoY, 0.6f, 0.6f);
            }

            if (abrigo.isConstruido()) {
                spriteBatch.draw(abrigoTexture, abrigoX, abrigoY, 3f, 3f);
                spriteBatch.draw(mesaTexture, mesaX, mesaY, 1.5f, 1.5f);

                if (aguaEntregue) {
                    spriteBatch.draw(copoTexture, mesaX + 0.2f, mesaY + 0.4f, 0.6f, 0.6f);
                    spriteBatch.draw(copoTexture, mesaX + 0.6f, mesaY + 0.4f, 0.6f, 0.6f);
                }
            }

        } else if (mapaAtual == 2) {
            renderer2.setView(camera);
            renderer2.render();
            spriteBatch.draw(anzolTexture, anzolX, anzolY, 0.5f, 0.5f);

            if (!carregandoAgua) {
                spriteBatch.draw(baldeCheio ? baldeCheioTexture : baldeVazioTexture, baldeX, baldeY, 0.7f, 0.7f);
            }

            spriteBatch.draw(cestaTexture, cestaX, cestaY, 0.8f, 0.8f);

            if (frutosColetados >= 1) spriteBatch.draw(frutoTexture, cestaX - 0.15f, cestaY + 0.05f, 0.8f, 0.8f);
            if (frutosColetados >= 2) spriteBatch.draw(frutoTexture, cestaX + 0.05f, cestaY + 0.05f, 0.8f, 0.8f);
            if (frutosColetados >= 3) spriteBatch.draw(frutoTexture, cestaX + 0.25f, cestaY + 0.05f, 0.8f, 0.8f);
            if (frutosColetados >= 4) spriteBatch.draw(frutoTexture, cestaX - 0.05f, cestaY + 0.20f, 0.8f, 0.8f);
            if (frutosColetados >= 5) spriteBatch.draw(frutoTexture, cestaX + 0.15f, cestaY + 0.20f, 0.8f, 0.8f);

            spriteBatch.draw(arbusto1Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto1X, arbusto1Y, 1.5f, 1.5f);
            spriteBatch.draw(arbusto2Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto2X, arbusto2Y, 1.5f, 1.5f);
            spriteBatch.draw(arbusto3Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto3X, arbusto3Y, 1.5f, 1.5f);
            spriteBatch.draw(arbusto4Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto4X, arbusto4Y, 1.6f, 1.6f);
            spriteBatch.draw(arbusto5Coletado ? arbustoTexture : arbustoFrutiferoTexture, arbusto5X, arbusto5Y, 1.6f, 1.6f);

            if (!peixe1Pescado) spriteBatch.draw(peixeTexture, peixe1X, peixe1Y, 0.6f, 0.6f);
            if (!peixe2Pescado) spriteBatch.draw(peixeTexture, peixe2X, peixe2Y, 0.6f, 0.6f);
            if (!peixe3Pescado) spriteBatch.draw(peixeTexture, peixe3X, peixe3Y, 0.6f, 0.6f);
            if (!peixe4Pescado) spriteBatch.draw(peixeTexture, peixe4X, peixe4Y, 0.6f, 0.6f);
            if (!peixe5Pescado) spriteBatch.draw(peixeTexture, peixe5X, peixe5Y, 0.6f, 0.6f);
        }

        Texture texturaAtual = getTextureDoPersonagem(personagemAtual);
        spriteBatch.draw(texturaAtual, px, py, playerSize, playerSize);

        if (personagemAtual != null) {
            String texto = personagemAtual.getHabilidade().toString();
            float textoX = px - 0.08f;
            float textoY = py + playerSize + 0.5f;
            gameFont.draw(spriteBatch, texto, textoX, textoY);
        }

        if (carregandoAgua) {
            spriteBatch.draw(baldeCheioTexture, px + 0.6f, py + 0.1f, 0.7f, 0.7f);
        }

        if (curaCriada && personagemAtual == personagemCura) {
            float curaX = px + 0.55f, curaY = py + 0.15f;
            spriteBatch.draw(curaTexture, curaX, curaY, 0.45f, 0.45f);
        }

        if (mensagemTimer > 0)
            gameFont.draw(spriteBatch, mensagem, camera.position.x - 5, camera.position.y + 4);

        gameFont.draw(spriteBatch, "Peixes: " + peixesColetados, camera.position.x - 9, camera.position.y + 5.2f);

        spriteBatch.end();
    }

    public void dispose() {
        mapa1.dispose();
        mapa2.dispose();
        renderer1.dispose();
        renderer2.dispose();
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
        peixeTexture.dispose();
        arbustoTexture.dispose();
        arbustoFrutiferoTexture.dispose();
        cestaTexture.dispose();
        frutoTexture.dispose();
        curaTexture.dispose();
        mesaTexture.dispose();
        copoTexture.dispose();
        aviaoTexture.dispose();
        playerUmTexture.dispose();
        playerDoisTexture.dispose();
        playerTresTexture.dispose();
        playerQuatroTexture.dispose();
        playerCincoTexture.dispose();
        playerSeisTexture.dispose();
        playerSeteTexture.dispose();
        playerOitoTexture.dispose();
        playerNoveTexture.dispose();
        playerDezTexture.dispose();
    }
}