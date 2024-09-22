package com.mygdx.game.images;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HP_Bar {

    public static final float WIDTH = Images.hp.getWidth()/2f, HEIGHT = Images.hp.getHeight()/2f;
    private final SpriteBatch s= new SpriteBatch();

    public void render(){
        s.begin();
        s.draw(Images.hp2, 80, 850, WIDTH, HEIGHT);
        s.draw(Images.hp, 80, 850, WIDTH, HEIGHT);
        s.end();

    }

    public void dispose(){
        s.dispose();
        Images.hp.dispose();
    }

}
