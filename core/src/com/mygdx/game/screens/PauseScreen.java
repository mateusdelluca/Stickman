package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.images.Images;
import com.mygdx.game.principal.Application;

public class PauseScreen implements Screen, InputProcessor {

    public static final int WIDTH = 1920, HEIGHT = 1080;

    public static final int RETURN = 2;
    public static final int LOADGAME = 1;
    public static final int EXIT = 0;

    public static final int NUM_OPTIONS = 3;

    private boolean[] isTouched = new boolean[NUM_OPTIONS];

    private int[] x = new int[NUM_OPTIONS];
    private int[] y = new int[NUM_OPTIONS];

    private String[] options = new String[NUM_OPTIONS];

    private Rectangle[] options_rects = new Rectangle[NUM_OPTIONS];
    private Rectangle mouseRectangle = new Rectangle(0, 0, 3, 9);
    private int optionChoosed = -1;

    private BitmapFont font;
    private SpriteBatch spriteBatch = new SpriteBatch();

    private Camera camera = new OrthographicCamera(WIDTH, HEIGHT);
    private Viewport viewport;
    public static Music music;

    private Application app;
    private Level level;

    public PauseScreen(Application app){
        this.app = app;
        level = app.level;
        for(int index = EXIT; index <= RETURN; ++index) {
            this.x[index] = 850;
            this.y[index] = 720 + 55 * index;
            this.options_rects[index] = new Rectangle(this.x[index], this.y[index] - 20, 360, 30);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Sunglasses2.mp3"));

        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(1f);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        viewport = new ScreenViewport(camera);
    }

    @Override
    public void show() {

    }

    public void update(){

    }

    @Override
    public void render(float delta) {
        update();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(Images.pauseBox, 740, 250);

//        for(int index = EXIT; index <= RETURN; ++index) {
//            font.setColor(Color.BLACK);
//            font.draw(spriteBatch, this.options[index], this.x[index] + 2, this.y[index] + 2);
//            font.setColor(Color.YELLOW);
//            font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
//            if (this.isTouched[index]) {
//                font.setColor(Color.WHITE);
//                font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
//            }
//        }
        spriteBatch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Images.pauseBox.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE){
            app.setScreen(level);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (this.optionChoosed) {
            case RETURN: {
                app.setScreen(level);
                music.stop();
                Gdx.input.setInputProcessor(level);
                break;
            }
            case LOADGAME:{
                break;
            }
            case EXIT: {
                System.exit(0);
                break;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
