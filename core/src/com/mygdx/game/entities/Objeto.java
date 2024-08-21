package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Objeto {

    protected Body body;
    protected PolygonShape polygonShape;
    protected FixtureDef fixtureDef;
    protected World world;
    protected float width, height;
    protected Vector2 dimensions;
    protected boolean visible;

    public Objeto(World world, float width, float height){
        this.width = width;
        this.height = height;
        this.world = world;
        visible = true;
    }

    public Objeto(){

    }

    protected Body createBoxBody(Vector2 dimensions){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = true;
        polygonShape = new PolygonShape();
//         Adicione formas (fixtures) ao corpo para representar sua geometria
        polygonShape.setAsBox(width/2f, height/2f,dimensions,0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.friction = 0;
        Body body = world.createBody(bodyDef);
//        body.createFixture(fixtureDef).setUserData(this);
        body.createFixture(fixtureDef);
        body.setActive(true);
        fixtureDef.density = 1.0f;
        return body;
    }

    public abstract void render(ShapeRenderer s);


    public boolean intersectsRectangle(Rectangle first, Rectangle another){
        return Intersector.overlaps(first, another);
    }

    public Body getBody() {
        return body;
    }
}
