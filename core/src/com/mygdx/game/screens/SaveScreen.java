package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.images.Images;


public class SaveScreen implements Screen, InputProcessor {

    public static final int WIDTH = 1920, HEIGHT = 1080;

    public static final int WIDTH2 = 1920, HEIGHT2 = 5690;

    SpriteBatch spriteBatch = new SpriteBatch();
    private OrthographicCamera camera = new OrthographicCamera(WIDTH, HEIGHT);
    private ScreenViewport viewport = new ScreenViewport(camera);
    private Application app;

    public SaveScreen(Application app){
        this.app = app;
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
    }

    @Override
    public void show() {
    }

    public void update(){
        camera.update();
        if (camera.position.y < (5690 - 1080))
            camera.translate(0,0.2f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        update();
        spriteBatch.setProjectionMatrix(camera.combined);
        Sprite s = new Sprite(Images.save_page);
        spriteBatch.begin();
        s.draw(spriteBatch);
        s.setSize(WIDTH2, HEIGHT2);
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

    }

    @Override
    public boolean keyDown(int keycode) {
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
