package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Images;

import java.util.Random;

public class Crystal extends Objeto{

    public static final int WIDTH = 282/4, HEIGHT = 421/4;
    private Random r = new Random();
    private boolean visible2;
    private Rectangle box1, box2;
    private Vector2 position;

    public Crystal(World world, Vector2 position) {
        super(world, WIDTH, HEIGHT);

        this.position = position;
        body = createBoxBody();
        box1 = new Rectangle(position.x, position.y, 100, 100);
        box2 = new Rectangle(position.x, position.y + 200, 100, 100);
        visible = true;
        visible2 = true;
    }

    protected Body createBoxBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = false;
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(30, 30, new Vector2(WIDTH/2f, HEIGHT/2f), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = ps;
        fixtureDef.isSensor = true;
        fixtureDef.friction = 0;
        fixtureDef.density = 1.0f;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setActive(true);

        return body;
    }

    public void render(SpriteBatch s){
        Sprite s1 = new Sprite(Images.crystal);
        s1.setOrigin(0,0);
        s1.setCenter(WIDTH/2f, HEIGHT/2f);
        s1.setPosition(position.x, position.y);
        s1.setSize(WIDTH, HEIGHT);
        if (visible) {
            s1.draw(s);
        }

        Sprite s2 = new Sprite(Images.crystal3);
        s2.setOrigin(0,0);
        s2.setCenter(WIDTH/2f, HEIGHT/2f);
        s2.setPosition(position.x, 580);
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
        if (Intersector.overlaps(box1, playerRect[0]) || Intersector.overlaps(box1, playerRect[1])){
            visible = false;
        }
        if (Intersector.overlaps(box2, playerRect[0]) || Intersector.overlaps(box2, playerRect[1])){
            visible2 = false;
        }
    }

    @Override
    public void render(ShapeRenderer s) {
        s.rect(box1.x, box1.y, box1.width, box1.height);
        s.rect(box2.x, box2.y, box2.width, box2.height);
    }


}
