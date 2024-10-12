package com.mygdx.game.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Animations;

public class Boy extends Objeto{

    public static final float WIDTH = 128f, HEIGHT = 128f;

    public Animations animations = Animations.BOY_WALKING;
    private Body body;


    public Boy(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(position, BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }


    public void render(SpriteBatch s){
        Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(false, true, false));
        sprite.setOrigin(0,0);
        sprite.setCenter(WIDTH/2f, HEIGHT/2f);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.draw(s);
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    @Override
    public void render(ShapeRenderer s) {
        s.rect(body.getPosition().x, body.getPosition().y, width, height);
    }

    public void keyDown(int keycode){
        if (keycode == Input.Keys.RIGHT){
            body.setLinearVelocity(2f, 0f);
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.RIGHT){
            body.setLinearVelocity(0f, 0f);
        }
    }
}
