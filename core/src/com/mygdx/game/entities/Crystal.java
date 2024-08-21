package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Images;

import java.util.Arrays;
import java.util.Random;

public class Crystal extends Objeto{

    public static final int WIDTH = 282/4, HEIGHT = 421/4;
    private Random r = new Random();
    public static final int NUM_CRYSTALS = 10;
    private boolean[] visible2 = new boolean[NUM_CRYSTALS];
    private boolean[] fadeOut = new boolean[NUM_CRYSTALS];


    public Crystal(World world, Vector2 position) {
        super(world, WIDTH, HEIGHT);
        dimensions = new Vector2(position.x,380f);
        body = createBoxBody();
        body.setTransform(dimensions, 0);
        visible = true;
        Arrays.fill(visible2, true);

    }

    protected Body createBoxBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = false;
        CircleShape cs = new CircleShape();
        cs.setRadius(30f);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = cs;
        fixtureDef.isSensor = true;
        fixtureDef.friction = 0;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setActive(true);
        fixtureDef.density = 1.0f;
        return body;
    }

    public void render(SpriteBatch sp, int index){
//        Sprite sprite = new Sprite(Images.crystals[i].currentSpriteFrame(false, true, true));
//        sp.draw(sprite, dimensions.x, 350, WIDTH, HEIGHT);
////        if (i == new Random().nextInt(10)) {
////            Images.lights[i].stateTime = 0;
////            Images.lights[i].setFrameDuration(1/new Random().nextFloat(6));
////        }
//        Sprite sprite2 = new Sprite(Images.lights[i].currentSpriteFrame(false, true, false));
//        sp.draw(sprite2, dimensions.x, 330, WIDTH, HEIGHT);
//        Sprite cr = new Sprite(Images.crystal);
//        cr.flip(false, new Random().nextInt(4) == 1);
       if (visible) {
           sp.draw(Images.crystal, dimensions.x - 30, 330, WIDTH, HEIGHT);
       }
       if (visible2[index]){
           sp.draw(Images.crystal3, dimensions.x - 30, 530, WIDTH, HEIGHT);
       }
       if (fadeOut[index]){
           sp.draw(Images.crystals3fadeout[index].currentSpriteFrame(false, false, false),dimensions.x - 30, 530, WIDTH, HEIGHT);
       }
    }

    public void dispose(){
        Images.crystal.dispose();
        Images.crystal3.dispose();
    }

    public void taked(Rectangle playerRect){
        if (super.intersectsRectangle(new Vector2(WIDTH, HEIGHT), playerRect)){
            visible = false;
        }
    }

    public void taked2(Rectangle playerRect, int index){
        if (Intersector.overlaps(getRectangle2(WIDTH, HEIGHT), playerRect)){
            fadeOut[index] = true;
            visible2[index] = false;
        }
    }

    public Rectangle getRectangle2(float width, float height){
        return new Rectangle(getBody().getPosition().x, getBody().getPosition().y + 200, width, height);
    }
}
