package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Images;
import com.mygdx.game.images.PowerBar;

import java.util.Random;

public class Crystal extends Objeto{

    public static final float WIDTH = 282/6f, HEIGHT = 421/6f;
    private Random r = new Random();
    private boolean visible2;
    private Rectangle box1, box2;
    private Vector2 position;
    private Sound clink, clink2;
    public static final float[] X_POSITIONS = {300, 600, 1000, 1600, 2000, 2200, 2800, 3100, 3700, 3950, 4200, 4500, 4750, 5100, 5250, 5400};
    public static final float[] Y_POSITIONS = {450, 450,  450,  650,  750,  850,  550,  450,  450,  450,  550,  650,  650,  450,  350,  350};
    public static int index;

    public Crystal(World world) {
        super(world, WIDTH, HEIGHT);
        if (index >= X_POSITIONS.length)
            index = 0;
        this.position = new Vector2(X_POSITIONS[index], Y_POSITIONS[index++]);
//        body = createBoxBody(position, BodyDef.BodyType.StaticBody, true);
        box1 = new Rectangle(position.x, position.y, 100, 100);
        box2 = new Rectangle(position.x, position.y + 150, 100, 100);
        visible = true;
        visible2 = true;

        clink = Gdx.audio.newSound(Gdx.files.internal("sounds/clink.mp3"));
        clink2 = Gdx.audio.newSound(Gdx.files.internal("sounds/clink2.mp3"));
    }

//    protected Body createBoxBody(){
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.active = true;
//        bodyDef.position.set(position);
//        bodyDef.fixedRotation = false;
//        PolygonShape ps = new PolygonShape();
//        ps.setAsBox(30, 30, new Vector2(WIDTH/2f, HEIGHT/2f), 0);
//        fixtureDef = new FixtureDef();
//        fixtureDef.shape = ps;
//        fixtureDef.isSensor = true;
//        fixtureDef.friction = 0;
//        fixtureDef.density = 1.0f;
//        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef);
//        body.setActive(true);
//
//        return body;
//    }

    public void render(SpriteBatch s){
        Sprite s1 = new Sprite(Images.crystal);
        s1.setOrigin(0,0);
//        s1.setCenter(WIDTH/2f, HEIGHT/2f);
        s1.setPosition(box1.x, box1.y);
        s1.setSize(WIDTH, HEIGHT);
        if (visible) {
            s1.draw(s);
        }

        Sprite s2 = new Sprite(Images.crystal3);
        s2.setOrigin(0,0);
//        s2.setCenter(WIDTH/2f, HEIGHT/2f);
        s2.setPosition(box2.x, box2.y);
        s2.setSize(WIDTH, HEIGHT);
        if (visible2) {
            s2.draw(s);
        }
    }

    public void dispose(){
        Images.crystal.dispose();
        Images.crystal3.dispose();
    }

    public void taked(Rectangle... playerRect){
        if ((Intersector.overlaps(box1, playerRect[0])) && visible){
            visible = false;
            clink.play();
            PowerBar.sp += 20;
        }
        if ((Intersector.overlaps(box2, playerRect[0])) && visible2){
            visible2 = false;
            clink2.play();
            PowerBar.hp += 20;
        }
    }

    @Override
    public void render(ShapeRenderer s) {
//        s.rect(box1.x, box1.y, box1.width, box1.height);
//        s.rect(box2.x, box2.y, box2.width, box2.height);
    }


}
