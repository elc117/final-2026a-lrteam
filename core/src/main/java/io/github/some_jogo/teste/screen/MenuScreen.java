package io.github.some_jogo.teste.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen {

    private Texture cardMenuIniciarTexture;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private OrthographicCamera camera;

    private boolean irParaSelecao = false;

    public MenuScreen(SpriteBatch spriteBatch, FitViewport viewport, OrthographicCamera camera) {
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        this.camera = camera;

        cardMenuIniciarTexture = new Texture("telaInicio.png");
    }

    public void render() {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();

        camera.setToOrtho(
                false,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());

        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();

        spriteBatch.draw(
                cardMenuIniciarTexture,
                0,
                0,
                screenW,
                screenH);

        spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            irParaSelecao = true;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            float mx = Gdx.input.getX();
            float my = screenH - Gdx.input.getY();

            float botaoX = screenW * 0.38f;
            float botaoY = screenH * 0.28f;
            float botaoW = screenW * 0.24f;
            float botaoH = screenH * 0.08f;

            if (mx >= botaoX &&
                    mx <= botaoX + botaoW &&
                    my >= botaoY &&
                    my <= botaoY + botaoH) {

                irParaSelecao = true;
            }
        }
    }

    public boolean deveIrParaSelecao() {
        return irParaSelecao;
    }

    public void dispose() {
        cardMenuIniciarTexture.dispose();
    }
}