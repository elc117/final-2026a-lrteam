package io.github.some_jogo.teste.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class IntroducaoScreen {

    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private OrthographicCamera camera;
    private BitmapFont gameFont;

    private Texture[] framesAviao;
    private Animation<TextureRegion> animacaoAviao;
    private float tempoIntroducao = 0f;

    private boolean irParaJogo = false;

    public IntroducaoScreen(SpriteBatch spriteBatch, FitViewport viewport, OrthographicCamera camera, BitmapFont gameFont) {
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        this.camera = camera;
        this.gameFont = gameFont;

        framesAviao = new Texture[100];
        TextureRegion[] regioes = new TextureRegion[100];

        for (int i = 1; i <= 100; i++) {
            String numeroFormatado;
            if (i < 10) {
                numeroFormatado = "00" + i;
            } else if (i < 100) {
                numeroFormatado = "0" + i;
            } else {
                numeroFormatado = String.valueOf(i);
            }

            String arquivo = "frames-aviao/frame_" + numeroFormatado + ".png";
            framesAviao[i - 1] = new Texture(arquivo);
            regioes[i - 1] = new TextureRegion(framesAviao[i - 1]);
        }

        animacaoAviao = new Animation<>(0.06f, regioes);
    }

    public void resetar() {
        tempoIntroducao = 0f;
        irParaJogo = false;
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

        tempoIntroducao += Gdx.graphics.getDeltaTime();

        TextureRegion frameAtual = animacaoAviao.getKeyFrame(tempoIntroducao);

        spriteBatch.begin();

        float largura = Gdx.graphics.getWidth();
        float altura = Gdx.graphics.getHeight();

        float w = frameAtual.getRegionWidth();
        float h = frameAtual.getRegionHeight();

        spriteBatch.draw(
                frameAtual,
                (largura - w) / 2f,
                (altura - h) / 2f);

        gameFont.draw(spriteBatch,
                "ESPACO para pular",
                20,
                40);

        spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            irParaJogo = true;
        }

        if (animacaoAviao.isAnimationFinished(tempoIntroducao)) {
            irParaJogo = true;
        }
    }

    public boolean deveIrParaJogo() {
        return irParaJogo;
    }

    public void dispose() {
        for (Texture t : framesAviao) {
            if (t != null)
                t.dispose();
        }
    }
}