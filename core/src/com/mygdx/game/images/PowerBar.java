package com.mygdx.game.images;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PowerBar {

    public static final float WIDTH = Images.hp.getWidth()/2f, HEIGHT = Images.hp.getHeight()/2f;
    private final SpriteBatch s= new SpriteBatch();

    public static float hp, sp;

    public void render(){
        s.begin();
        s.draw(Images.hp, 70, 900, WIDTH, HEIGHT);
        s.draw(Images.hp2, 70, 900, WIDTH - hp, HEIGHT);
        s.draw(Images.sp, 70, 850, WIDTH, HEIGHT);
        s.draw(Images.sp2, 70, 850, WIDTH - sp, HEIGHT);
        s.end();

    }

    public void dispose(){
        s.dispose();
        Images.hp.dispose();
        Images.hp2.dispose();
        Images.sp.dispose();
        Images.sp2.dispose();
    }

}
