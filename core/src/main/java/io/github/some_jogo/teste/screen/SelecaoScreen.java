package io.github.some_jogo.teste.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;

import io.github.some_jogo.teste.enums.Habilidade;
import io.github.some_jogo.teste.model.Grupo;
import io.github.some_jogo.teste.model.Personagem;

public class SelecaoScreen {

    private Texture cardSelecaoTexture;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private BitmapFont menuFont;

    private ArrayList<Personagem> sobreviventesDisponiveis;
    private ArrayList<Personagem> sobreviventesSelecionados;
    private ArrayList<Integer> numerosSelecionados;
    private String numeroDigitado = "";

    private Grupo grupo;
    private Personagem personagemAgua;
    private Personagem personagemFogo;
    private Personagem personagemPesca;
    private Personagem personagemConstrucao;
    private Personagem personagemCura;

    private boolean irParaIntroducao = false;

    public SelecaoScreen(SpriteBatch spriteBatch, FitViewport viewport, OrthographicCamera camera, BitmapFont menuFont,
            ArrayList<Personagem> sobreviventesDisponiveis) {
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        this.camera = camera;
        this.menuFont = menuFont;
        this.sobreviventesDisponiveis = sobreviventesDisponiveis;

        sobreviventesSelecionados = new ArrayList<>();
        numerosSelecionados = new ArrayList<>();
        grupo = new Grupo();

        cardSelecaoTexture = new Texture("telaSelecao.png");
    }

    public void render() {
        atualizarSelecaoPorNumero();

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();

        camera.setToOrtho(
                false,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());

        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);

        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        spriteBatch.begin();

        spriteBatch.draw(
                cardSelecaoTexture,
                0,
                0,
                screenW,
                screenH);

        menuFont.setColor(Color.GOLD);

        float slotX = screenW * 0.83f;
        float slotY = screenH * 0.63f;
        float espacamento = screenH * 0.08f;

        for (int i = 0; i < numerosSelecionados.size(); i++) {
            menuFont.draw(
                    spriteBatch,
                    String.valueOf(numerosSelecionados.get(i)),
                    slotX,
                    slotY - (i * espacamento));
        }

        spriteBatch.end();
    }

    private void atualizarSelecaoPorNumero() {
        if (Gdx.input.isKeyJustPressed(Keys.NUM_0))
            numeroDigitado += "0";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_1))
            numeroDigitado += "1";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_2))
            numeroDigitado += "2";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_3))
            numeroDigitado += "3";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_4))
            numeroDigitado += "4";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_5))
            numeroDigitado += "5";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_6))
            numeroDigitado += "6";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_7))
            numeroDigitado += "7";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_8))
            numeroDigitado += "8";
        if (Gdx.input.isKeyJustPressed(Keys.NUM_9))
            numeroDigitado += "9";

        if (Gdx.input.isKeyJustPressed(Keys.DEL) && numeroDigitado.length() > 0) {
            numeroDigitado = numeroDigitado.substring(0, numeroDigitado.length() - 1);
        }

        if (Gdx.input.isKeyJustPressed(Keys.ENTER) && !numeroDigitado.isEmpty()) {
            int numero = Integer.parseInt(numeroDigitado);

            if (numero >= 1
                    && numero <= 10
                    && numerosSelecionados.size() < 5
                    && !numerosSelecionados.contains(numero)) {

                numerosSelecionados.add(numero);

                Personagem p = sobreviventesDisponiveis.get(numero - 1);
                sobreviventesSelecionados.add(p);

                if (sobreviventesSelecionados.size() == 5) {
                    finalizarSelecao();
                }
            }

            numeroDigitado = "";
        }
    }

    private void finalizarSelecao() {
        grupo = new Grupo();

        for (Personagem p : sobreviventesSelecionados) {
            grupo.adicionar(p);

            switch (p.getHabilidade()) {
                case AGUA:
                    personagemAgua = p;
                    break;
                case FOGO:
                    personagemFogo = p;
                    break;
                case PESCA:
                    personagemPesca = p;
                    break;
                case CONSTRUCAO:
                    personagemConstrucao = p;
                    break;
                case CURA:
                    personagemCura = p;
                    break;
                default:
                    break;
            }
        }

        irParaIntroducao = true;
    }

    public boolean deveIrParaIntroducao() {
        return irParaIntroducao;
    }

    public ArrayList<Personagem> getSobreviventesSelecionados() {
        return sobreviventesSelecionados;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Personagem getPersonagemAgua() {
        return personagemAgua;
    }

    public Personagem getPersonagemFogo() {
        return personagemFogo;
    }

    public Personagem getPersonagemPesca() {
        return personagemPesca;
    }

    public Personagem getPersonagemConstrucao() {
        return personagemConstrucao;
    }

    public Personagem getPersonagemCura() {
        return personagemCura;
    }

    public void dispose() {
        cardSelecaoTexture.dispose();
    }
}