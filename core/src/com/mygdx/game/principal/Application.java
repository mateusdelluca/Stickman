package com.mygdx.game.principal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.Level;
import com.mygdx.game.screens.SplashScreen;

public class Application extends Game {

    public static boolean pause;
    public Images images;
    private SplashScreen splashScreen;

    @Override
    public void create() {
        images = new Images();
        splashScreen = new SplashScreen(this);
        this.setScreen(splashScreen);
        Gdx.input.setInputProcessor(splashScreen);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        if (!pause) {
            super.render();
        } else{
            pause();
        }
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
//        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
    }


    @Override
    public void pause() {
        //TODO
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() { // SpriteBatches and Textures must always be disposed
    }

}
