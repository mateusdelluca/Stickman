package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Animations;

public class Enemy extends Stickman{

    private Animations animations = Animations.E_IDLE;
    private float timer;

    public Enemy(World world, Vector2 position) {
        super(world, position);
        flip = true;
    }

    @Override
    public void render(SpriteBatch s) {
        Sprite s1 = new Sprite(animations.animator.currentSpriteFrame(false, loopTrueOrFalse(animations.name()), flip));
        s1.setOrigin(0,0);
        s1.setCenter(getBody().localPoint2.x, getBody().localPoint2.y);
        s1.setPosition(getBody().getPosition().x, getBody().getPosition().y);
//        s1.translate(getBody().getLinearVelocity().x, getBody().getLinearVelocity().y);
        float rotation = (float) Math.toDegrees(getBody().getTransform().getRotation());
        s1.setRotation(rotation);
        s1.draw(s);
    }

    @Override
    public void update(float delta) {
        animation();
    }

    private void animation(){
        if (animations.animator.ani_finished() && animations.name().equals("E_PUNCHED")){
            animations.animator.resetStateTime();
            animations = Animations.E_IDLE;
            getBody().setLinearVelocity(0,0);
        } else{
            if (animations.name().equals("E_IDLE")){
                getBody().setLinearVelocity(0,0);
                timer += Gdx.graphics.getDeltaTime();
                if (timer > 30f){
                    getBody().setTransform(getBody().getPosition().x,getBody().getPosition().y,0);
                    timer = 0f;
                }
            }
        }
    }

    @Override
    public void render(ShapeRenderer s) {
        
    }

//    public void resize(SpriteBatch spriteBatch, int width, int height){
//        spriteBatch.getProjectionMatrix().setToOrtho2D(getBody().getPosition().x, getBody().getPosition().y, width, height);
//    }


    public void setAnimation(String name){
        animations = Animations.valueOf(name);
    }


}
