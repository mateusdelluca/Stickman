package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public Stickman(World world){
        this.world = world;
        createBody();
    }

    public void createBody(){
        box = new BoxBounds(world);
//        // Definição de corpo
//        bDef.type = BodyDef.BodyType.DynamicBody; // Corpo dinâmico (afetado pela gravidade)
//        bDef.active = true;
//        bDef.position.set(position); // Posição inicial
//
//        // Adicione formas (fixtures) ao corpo para representar sua geometria
//        shape = new PolygonShape();
//        shape.setAsBox(WIDTH/2f, HEIGHT/2f, bDef.position,0);
//        fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//
//        // Criando o corpo
//        body = world.createBody(bDef);
//        body.createFixture(fixtureDef);
//        body.setActive(true);
    }

    public abstract void render(SpriteBatch s, Camera camera);

    public void dispose(){
    }

    public Body getBody(){
        return box.getBody();
    }
}
