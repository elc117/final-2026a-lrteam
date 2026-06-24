package io.github.some_jogo.teste.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class FinalScreen {

    private Texture telaFinalTexture;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private OrthographicCamera camera;

    public FinalScreen(SpriteBatch spriteBatch, FitViewport viewport, OrthographicCamera camera) {
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        this.camera = camera;

        telaFinalTexture = new Texture("telaFinal.png");
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

        spriteBatch.draw(
                telaFinalTexture,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());

        spriteBatch.end();
    }

    public void dispose() {
        telaFinalTexture.dispose();
    }
}