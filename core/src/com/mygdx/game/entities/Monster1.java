package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Animations;

public class Monster1 extends Objeto{

    public static final float WIDTH = 94, HEIGHT = 128;
    public Animations animations = Animations.MONSTER1_WALKING;
    private boolean usingOnlyLastFrame, looping = true, facingRight;
    private Vector2 dimensions = new Vector2(82f, 118f);
    private float flickering_time;

    public Monster1(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(dimensions.x/2f, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }


    public void render(SpriteBatch spriteBatch){
        update();
        Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping, facingRight));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(spriteBatch);
    }

    private void update(){
        String name = animations.name();
        if (name.equals("MONSTER1_FLICKERING")){
            flickering_time += Gdx.graphics.getDeltaTime();
            if (flickering_time >= 3f){
                flickering_time = 0f;
                animations = Animations.MONSTER1_WALKING;
            }
        }
    }

    @Override
    public void render(ShapeRenderer s) {
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);
    }

    public Rectangle getBodyBounds() {
        return new Rectangle(body.getPosition().x + 2.5f, body.getPosition().y, dimensions.x + 10f, dimensions.y);
    }
}
