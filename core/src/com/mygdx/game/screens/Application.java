package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.images.Images;

public class Application extends Game implements InputProcessor {

    public static boolean pause, pause2;
    public Images images;
    public SplashScreen splashScreen;
    public Level level;
    public LoadScreen loadScreen;
    public SaveScreen saveScreen;
    public PauseScreen pauseScreen;
    public static String currentScreen = "SplashScreen";


    @Override
    public void create() {
        images = new Images();
        splashScreen = new SplashScreen(this);
        this.setScreen(splashScreen);
        Gdx.input.setInputProcessor(splashScreen);
        loadScreen = new LoadScreen(this);
        saveScreen = new SaveScreen(this);
    }

    @Override
    public void resize(int width, int height) {
    }

    public void update(){
    }

    @Override
    public void render() {
        update();
        super.render();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() { // SpriteBatches and Textures must always be disposed
        splashScreen.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
//        splashScreen.keyDown(keycode);
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
//        splashScreen.touchDown(screenX, screenY, pointer, button);
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
//        splashScreen.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
