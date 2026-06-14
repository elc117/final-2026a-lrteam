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
import java.util.ArrayList;

import io.github.some_jogo.teste.model.Personagem;
import io.github.some_jogo.teste.model.Tarefa;
import io.github.some_jogo.teste.model.Grupo;
import io.github.some_jogo.teste.model.Animal;
import io.github.some_jogo.teste.model.Recurso;
import io.github.some_jogo.teste.model.Arvore;
import io.github.some_jogo.teste.model.Rio;
import io.github.some_jogo.teste.model.Fogueira;
import io.github.some_jogo.teste.model.Estrutura;



/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Texture florestaTexture;
    Texture rioTexture;
    Texture personagemTexture;
    Animation<TextureRegion> walkAnimation;
    TextureRegion idleFrame;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Grupo grupo;
    ArrayList<Personagem> sobreviventesDisponiveis;

    Rio rio;
    Arvore arvore;

    int aguaColetada = 0;
    int madeiraColetada = 0;

    // posição e estado do personagem
    float x = 4f, y = 2f; // não começa mais na borda por causa da mudança de tela
    float stateTime = 0f;
    boolean moving = false;
    boolean facingLeft = false;

    int mapaAtual = 1;

    float arvoreX = 2f;
    float arvoreY = 3f;

    float rioX = 4f;
    float rioY = 2f;

    float distanciaInteracao = 1.5f; //coleta itens se estiver a 1.5 de distancia

    @Override
    public void create() {
        florestaTexture = new Texture("grama.png");
        rioTexture = new Texture("rio.png");
        personagemTexture = new Texture("personagem.png");
        // Prepare your application here.

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        

        idleFrame = new TextureRegion(personagemTexture);

        grupo = new Grupo();

        rio = new Rio(50);
        arvore = new Arvore(30);

        sobreviventesDisponiveis = new ArrayList<>();

        Personagem joao = new Personagem("Joao", "Pesca");

        Personagem ana = new Personagem("Ana", "Cura");

        Personagem pedro = new Personagem("Pedro", "Fogo");

        Personagem cintia = new Personagem("Cintia", "Danca");

        Personagem leo = new Personagem("Leo", "Construcao");

        Personagem rita = new Personagem("Rita", "Piloto");

        Personagem felipe = new Personagem("Felipe", "Agua");

        Personagem anita = new Personagem("Anita", "Canto");

        sobreviventesDisponiveis.add(joao);
        sobreviventesDisponiveis.add(ana);
        sobreviventesDisponiveis.add(pedro);
        sobreviventesDisponiveis.add(cintia);
        sobreviventesDisponiveis.add(leo);
        sobreviventesDisponiveis.add(rita);
        sobreviventesDisponiveis.add(felipe);
        sobreviventesDisponiveis.add(anita);

        //testando funcionalidade (deu certo)
        grupo.adicionar(joao);
        grupo.adicionar(ana);
        grupo.adicionar(pedro);
        grupo.adicionar(cintia);
        grupo.adicionar(leo);

        System.out.println("\nSobreviventes disponiveis: " + sobreviventesDisponiveis.size());

        System.out.println("\nMembros do grupo: " + grupo.getQuantidadeMembros());

        System.out.println("\nGrupo escolhido:");

        for (Personagem p : grupo.getMembros()) {
            System.out.println(p.getNome() + " - " + p.getHabilidade());
        }
        //fim teste



        Tarefa pescar = new Tarefa("Pescar", "Pesca");

        System.out.println("\nJoao pode pescar? " + pescar.podeSerRealizadaPor(joao));

        System.out.println("Ana pode pescar? " + pescar.podeSerRealizadaPor(ana));
        
        System.out.println("Pedro pode pescar? " + pescar.podeSerRealizadaPor(pedro));
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

        if (Gdx.input.isKeyJustPressed(Keys.E)) {
            interagir();
        }
    }

    private void logic() {
        if (moving) {
        stateTime += Gdx.graphics.getDeltaTime();
        } else {
        stateTime = 0f; // reseta quando parado
        }

        if (mapaAtual == 1 && y < 0) {
            mapaAtual = 2;
            y = viewport.getWorldHeight() - 1;
        }

        if (mapaAtual == 2 && y > viewport.getWorldHeight()) {

            mapaAtual = 1;
            y = 0;
        }

        if (x < 0) { //impede de sair pela borda da esquerda
            x = 0;
        }

        if (x > viewport.getWorldWidth() - 1) { //impede de sair pela borda da direita
            x = viewport.getWorldWidth() - 1;
        }
    }

    private float distancia(float x1, float y1, float x2, float y2) { //calcula a dist entre dois pontos
            float dx = x1 - x2;
            float dy = y1 - y2;

        return (float)Math.sqrt(dx * dx + dy * dy);
    }

    private void interagir() { //interagir com itens das tarefas (árvore, rio...)
        
        if (mapaAtual == 1) {
            if (distancia(x, y, arvoreX, arvoreY) <= distanciaInteracao) {
                int madeira = arvore.coletar(3); //coleta 3 madeiras

                madeiraColetada += madeira;

                System.out.println("Coletou " + madeira + " de madeira. Total: " + madeiraColetada);
            }
        }

        if (mapaAtual == 2) {
            if (distancia(x, y, rioX, rioY) <= distanciaInteracao) {
                int agua = rio.coletar(5); //coleta 5 águas

                aguaColetada += agua;

                System.out.println("Coletou " + agua + " de agua. Total: " + aguaColetada);
            }
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

        if (mapaAtual == 1) {

            spriteBatch.draw(florestaTexture, 0, 0, worldWidth, worldHeight);
        
        } else if (mapaAtual == 2) {

            spriteBatch.draw(rioTexture, 0, 0, worldWidth, worldHeight);

        }

        
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
        florestaTexture.dispose();
        rioTexture.dispose();
        personagemTexture.dispose();
    }
}