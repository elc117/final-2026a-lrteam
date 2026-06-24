package io.github.some_jogo.teste;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.some_jogo.teste.screen.FinalScreen;
import io.github.some_jogo.teste.screen.IntroducaoScreen;
import io.github.some_jogo.teste.screen.JogoScreen;
import io.github.some_jogo.teste.screen.MenuScreen;
import io.github.some_jogo.teste.screen.SelecaoScreen;
import io.github.some_jogo.teste.model.Personagem;

import java.util.ArrayList;

public class Main implements ApplicationListener {

    private enum EstadoJogo {
        INTRODUCAO,
        MENU_INICIAR,
        SELECAO,
        JOGANDO,
        FINAL
    }

    private EstadoJogo estadoAtual = EstadoJogo.MENU_INICIAR;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private BitmapFont gameFont;
    private BitmapFont menuFont;

    private MenuScreen menuScreen;
    private SelecaoScreen selecaoScreen;
    private IntroducaoScreen introducaoScreen;
    private JogoScreen jogoScreen;
    private FinalScreen finalScreen;

    private ArrayList<Personagem> sobreviventesDisponiveis;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(20, 12, camera);
        viewport.apply();
        camera.setToOrtho(false, 20, 12);
        camera.update();

        gameFont = new BitmapFont();
        gameFont.getData().setScale(0.02f);

        menuFont = new BitmapFont();
        menuFont.getData().setScale(2f);

        sobreviventesDisponiveis = new ArrayList<>();
        sobreviventesDisponiveis.add(new Personagem("Joao",    io.github.some_jogo.teste.enums.Habilidade.PESCA));
        sobreviventesDisponiveis.add(new Personagem("Ana",     io.github.some_jogo.teste.enums.Habilidade.CURA));
        sobreviventesDisponiveis.add(new Personagem("Pedro",   io.github.some_jogo.teste.enums.Habilidade.FOGO));
        sobreviventesDisponiveis.add(new Personagem("Leo",     io.github.some_jogo.teste.enums.Habilidade.CONSTRUCAO));
        sobreviventesDisponiveis.add(new Personagem("Felipe",  io.github.some_jogo.teste.enums.Habilidade.AGUA));
        sobreviventesDisponiveis.add(new Personagem("Cintia",  io.github.some_jogo.teste.enums.Habilidade.LIDERANCA));
        sobreviventesDisponiveis.add(new Personagem("Rita",    io.github.some_jogo.teste.enums.Habilidade.PILOTO));
        sobreviventesDisponiveis.add(new Personagem("Anita",   io.github.some_jogo.teste.enums.Habilidade.COMUNICACAO));
        sobreviventesDisponiveis.add(new Personagem("Bruno",   io.github.some_jogo.teste.enums.Habilidade.CACADOR));
        sobreviventesDisponiveis.add(new Personagem("Marina",  io.github.some_jogo.teste.enums.Habilidade.BOTANICA));

        menuScreen      = new MenuScreen(spriteBatch, viewport, camera);
        selecaoScreen   = new SelecaoScreen(spriteBatch, viewport, camera, menuFont, sobreviventesDisponiveis);
        introducaoScreen = new IntroducaoScreen(spriteBatch, viewport, camera, gameFont);
        jogoScreen      = new JogoScreen(spriteBatch, viewport, camera, gameFont);
        finalScreen     = new FinalScreen(spriteBatch, viewport, camera);
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        switch (estadoAtual) {

            case MENU_INICIAR:
                menuScreen.render();
                if (menuScreen.deveIrParaSelecao()) {
                    estadoAtual = EstadoJogo.SELECAO;
                }
                break;

            case SELECAO:
                selecaoScreen.render();
                if (selecaoScreen.deveIrParaIntroducao()) {
                    jogoScreen.inicializar(
                            selecaoScreen.getSobreviventesSelecionados(),
                            selecaoScreen.getPersonagemAgua(),
                            selecaoScreen.getPersonagemFogo(),
                            selecaoScreen.getPersonagemPesca(),
                            selecaoScreen.getPersonagemConstrucao(),
                            selecaoScreen.getPersonagemCura());
                    introducaoScreen.resetar();
                    estadoAtual = EstadoJogo.INTRODUCAO;
                }
                break;

            case INTRODUCAO:
                introducaoScreen.render();
                if (introducaoScreen.deveIrParaJogo()) {
                    estadoAtual = EstadoJogo.JOGANDO;
                }
                break;

            case JOGANDO:
                jogoScreen.render();
                if (jogoScreen.isJogoFinalizado()) {
                    estadoAtual = EstadoJogo.FINAL;
                }
                break;

            case FINAL:
                finalScreen.render();
                break;
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        gameFont.dispose();
        menuFont.dispose();
        menuScreen.dispose();
        selecaoScreen.dispose();
        introducaoScreen.dispose();
        jogoScreen.dispose();
        finalScreen.dispose();
    }
}