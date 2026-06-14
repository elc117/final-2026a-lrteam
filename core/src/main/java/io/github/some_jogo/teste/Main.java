package io.github.some_jogo.teste;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_jogo.teste.model.Personagem;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Texture backgroundTexture;
    Texture personagemTexture;
    Animation<TextureRegion> walkAnimation;
    TextureRegion idleFrame;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    // posição e estado do personagem
    float x = 0f, y = 0f;
    float stateTime = 0f;
    boolean moving = false;
    boolean facingLeft = false;

    @Override
    public void create() {
        backgroundTexture = new Texture("grama.png");
        personagemTexture = new Texture("personagem.png");
        // Prepare your application here.

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        idleFrame = new TextureRegion(personagemTexture);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void render() {
        // Draw your application here.

        // organize code into three methods
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 3f;
        float delta = Gdx.graphics.getDeltaTime();
        moving = false;

        if (Gdx.input.isKeyPressed(Keys.A)) { x -= speed * delta; facingLeft = true;  moving = true; }
        if (Gdx.input.isKeyPressed(Keys.D)) { x += speed * delta; facingLeft = false; moving = true; }
        if (Gdx.input.isKeyPressed(Keys.W)) { y += speed * delta; moving = true; }
        if (Gdx.input.isKeyPressed(Keys.S)) { y -= speed * delta; moving = true; }
    }

    private void logic() {
        if (moving) {
        stateTime += Gdx.graphics.getDeltaTime();
        } else {
        stateTime = 0f; // reseta quando parado
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        // add lines to draw stuff here

        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        TextureRegion frame = idleFrame;

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight); // draw the background
        spriteBatch.draw(frame, x, y, 1f, 1f);
        

        spriteBatch.end();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
        spriteBatch.dispose();
        backgroundTexture.dispose();
        personagemTexture.dispose();
    }
}