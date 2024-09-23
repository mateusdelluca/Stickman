package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.images.Images;

public class Portal extends Objeto{

    public static final float WIDTH = 857/3f, HEIGHT = 873/3f;


    public void render(SpriteBatch s){
        s.draw(Images.portal.currentSpriteFrame(false, true, false), 5700,420, WIDTH, HEIGHT);
    }


    @Override
    public void render(ShapeRenderer s) {

    }
}
