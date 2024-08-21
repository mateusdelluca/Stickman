package com.mygdx.game.principal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Player;
import com.mygdx.game.screens.SplashScreen;

public class Application extends Game {

    @Override
    public void create() {
        this.setScreen(new SplashScreen(this));
    }

    @Override
    public void resize(int width, int height) {
//        player.rezise(spriteBatch, width, height);
    }

    @Override
    public void render() {
        super.render();
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
//        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
    }


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() { // SpriteBatches and Textures must always be disposed
    }

}
