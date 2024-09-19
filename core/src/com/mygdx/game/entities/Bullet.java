package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Images;

public class Bullet extends Objeto{

    public static final int WIDTH = 27, HEIGHT = 9;
    private boolean flip;
    public static final float VELOCITY = 300f;

    public Bullet(World world, Vector2 position, boolean flip){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(WIDTH, HEIGHT));
        body.setGravityScale(0.1f);
        this.flip = flip;
        body.setTransform(position, 0);
        body.setLinearVelocity(flip ? -VELOCITY : VELOCITY, 0f);
        getBody().setAwake(true);
//        getBody().setBullet(true);
        visible = true;
        fixtureDef.density = 1f;
    }

    public void update(){
        if (Math.abs(getBody().getLinearVelocity().x) <= 30) {
            visible = false;
            for (Fixture f : getBody().getFixtureList())
                getBody().destroyFixture(f);
        }
    }

    public void render(SpriteBatch spriteBatch){
        Sprite sprite = new Sprite(Images.bullet);
        sprite.flip(flip, false);
        sprite.setOrigin(0,0);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setSize(WIDTH, HEIGHT);
        if (visible)
            sprite.draw(spriteBatch);
        update();
    }

    @Override
    public void render(ShapeRenderer s) {
        s.rect(body.getPosition().x, body.getPosition().y, WIDTH, HEIGHT);
    }
}
