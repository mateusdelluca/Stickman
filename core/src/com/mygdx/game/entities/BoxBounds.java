package com.mygdx.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BoxBounds {

    public Body body;
    public FixtureDef fixtureDef, fixtureDef2, fixtureDef3;
    public PolygonShape polygonShape = new PolygonShape();
    public static final int WIDTH = 400, HEIGHT = 300;
    public BodyDef bodyDef = new BodyDef();
    Vector2 dimensions4, dimensions5;
    public BoxBounds(World world){
        body = createBody(world);
    }

    private Body createBody(World world){
//        Vector2 dimensions = new Vector2(WIDTH/2f, (HEIGHT/2f) - 40f);
        Vector2 dimensions2 = new Vector2(WIDTH/2f, (HEIGHT/2f) + 15f);
        Vector2 dimensions3 = new Vector2(WIDTH/2f, (HEIGHT/2f) + 55f);
        dimensions4 = new Vector2(WIDTH/4f, (HEIGHT/4f));
        dimensions5 = new Vector2(WIDTH/2f, (HEIGHT/2f));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = false;
        // Adicione formas (fixtures) ao corpo para representar sua geometria
//        polygonShape.setAsBox(WIDTH/2f, HEIGHT/2f,dimensions,0);
//        polygonShape.setRadius(50f);
//        CircleShape cs = new CircleShape();
//        cs.setRadius(30f);
//        cs.setPosition(dimensions);
        CircleShape cs2 = new CircleShape();
        cs2.setRadius(30f);
        cs2.setPosition(dimensions2);
        CircleShape cs3 = new CircleShape();
        cs3.setRadius(30f);
        cs3.setPosition(dimensions3);
        polygonShape.setAsBox(dimensions4.x - 75, dimensions4.y, new Vector2(dimensions5.x, dimensions5.y + 20), 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = cs2;
        fixtureDef3 = new FixtureDef();
        fixtureDef3.shape = cs3;
//        fixtureDef.friction = 0f;
        Body body = world.createBody(bodyDef);
//        world.createBody(bodyDef);
//        world.createBody(bodyDef);
        body.setActive(true);
        MassData massData = new MassData();
        massData.mass = 2.0f;
        body.setMassData(massData);
        body.setAwake(true);

        fixtureDef.density = 1.0f;
//        fixtureDef.friction = 0.1f;
//        body.setGravityScale(0.1f);
        body.createFixture(fixtureDef);
        body.createFixture(fixtureDef2);
        body.createFixture(fixtureDef3);
        return body;
    }

    public Body getBody() {
        return body;
    }

    public void setBodyAngle(float angle) {
        body.getPosition().setAngleDeg(angle);
    }
}
