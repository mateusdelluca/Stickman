package com.mygdx.game.images;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.Level;

public class PowerBar {

    public static final float WIDTH = Images.hp.getWidth()/2f, HEIGHT = Images.hp.getHeight()/2f;
    private final SpriteBatch s= new SpriteBatch();
    private OrthographicCamera camera = new OrthographicCamera(Level.WIDTH, Level.HEIGHT);

    public static float hp, sp;

    public PowerBar(){
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    public void render(){
        camera.update();
        s.setProjectionMatrix(camera.combined);
        s.begin();
        s.draw(Images.hp, 70, 900, WIDTH, HEIGHT);
        s.draw(Images.hp2, 70 + 40, 900, WIDTH + hp, HEIGHT);
        s.draw(Images.sp, 70, 850, WIDTH, HEIGHT);
        s.draw(Images.sp2, 70 + 40, 850, WIDTH + sp, HEIGHT);
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
