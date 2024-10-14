package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Animations;

public class Monster1 extends Objeto{

    public static final float WIDTH = 94, HEIGHT = 128;
    public Animations animations = Animations.MONSTER1_WALKING;
    private boolean usingOnlyLastFrame, looping = true, facingRight;

    public Monster1(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(82/2f, 118/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }


    public void render(SpriteBatch spriteBatch){
        Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping, facingRight));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(spriteBatch);
    }

    @Override
    public void render(ShapeRenderer s) {

    }
}
