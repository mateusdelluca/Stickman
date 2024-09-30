package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.images.Images;

public class Portal extends Objeto{

    public static final float WIDTH = 857/3f, HEIGHT = 873/3f;


    public void render(SpriteBatch s){
        s.draw(Images.portal.currentSpriteFrame(false, true, false), 5700,380, WIDTH, HEIGHT);
    }


    @Override
    public void render(ShapeRenderer s) {
        s.rect(5700, 380, WIDTH, HEIGHT);
    }

    public Rectangle getRectangle(){
        return new Rectangle(5700, 380, WIDTH, HEIGHT);
    }
}
