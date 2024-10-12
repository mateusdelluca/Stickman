package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.images.Images;

public class Portal extends Objeto{

    public static final float WIDTH = 857/3f, HEIGHT = 873/3f;

    private float x = 5350, y = 380;

    public void render(SpriteBatch s){
        s.draw(Images.portal.currentSpriteFrame(false, true, false), x,y, WIDTH, HEIGHT);
    }


    @Override
    public void render(ShapeRenderer s) {
        s.rect(x, y, WIDTH, HEIGHT);
    }

    public Rectangle getRectangle(){
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
