package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Stickman extends Objeto{

//    private FixtureDef fixtureDef;
//    protected BodyDef bDef = new BodyDef();
    protected World world;
//    protected PolygonShape shape;
//    private Body body;
    protected boolean flip;
    public static final int WIDTH = 400, HEIGHT = 300;
    public BoxBounds box;
    public float rotation;
    protected Vector2 position;

    public Stickman(World world){
        this.world = world;
        createBody();
    }

    public Stickman(World world, Vector2 position){
        this.world = world;
        this.position = position;
        createBody();
    }

    public void createBody(){
        box = new BoxBounds(world);
    }

    public void createBody2(){
        // Definição de corpo
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody; // Corpo dinâmico (afetado pela gravidade)
        bDef.active = true;
        bDef.position.set(1000, 0);

        // Adicione formas (fixtures) ao corpo para representar sua geometria
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WIDTH/4f - 70, HEIGHT/4f, new Vector2(position),0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Criando o corpo
        body = world.createBody(bDef);
        body.setActive(true);
        body.createFixture(fixtureDef);
        body.getPosition().set(position);
        body.setFixedRotation(false);
    }

    protected boolean loopTrueOrFalse(String name){
        return (name.equals("WALKING") && getBody().getLinearVelocity().x != 0) | (name.equals("WALKING") && getBody().getLinearVelocity().y != 0)
                | (name.equals("IDLE") && Math.abs(getBody().getLinearVelocity().x) <= 1f) | (name.equals("E_WALKING") && getBody().getLinearVelocity().x != 0) |
                (name.equals("E_WALKING") && getBody().getLinearVelocity().y != 0) | (name.equals("E_IDLE") && Math.abs(getBody().getLinearVelocity().x) <= 0.5f);
    }

    public abstract void render(SpriteBatch s);

    public abstract void update(float delta);

    public void dispose(){
        world.dispose();
    }

    public Body getBody(){
        return box.getBody();
    }
}
