package com.mygdx.game.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Animations;

import static com.mygdx.game.sfx.Sounds.JUMP;

public class Boy extends Objeto{

    public static final float WIDTH = 128f, HEIGHT = 128f;
    public static final float VELOCITY_X = 5f;
    public Animations animations = Animations.BOY_IDLE;
    private boolean flip, usingOnlyLastFrame;

    public Boy(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(65f/2f, 95f/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch s){
        update();
        Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, true, flip));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(s);
    }

    public void update(){
        animations();
    }

    private void animations() {
        String name = animations.name();
//        if (name.equals("BOY_JUMPING")) {
//            if (body.getLinearVelocity().y < -29f) {
//                animations = Animations.BOY_IDLE;
//                body.
//            }
//        } else{
            if (name.equals("BOY_WALKING")) {

            } else {
                if (body.getLinearVelocity().y == 0) {
                    animations = Animations.BOY_IDLE;
//                    usingOnlyLastFrame = true;
                } else{
                    animations = Animations.BOY_JUMPING;
//                    usingOnlyLastFrame = false;
                }
            }
//        }
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
//        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    @Override
    public void render(ShapeRenderer s) {
//        s.rect(body.getPosition().x, body.getPosition().y, width, height);
    }

    public void keyDown(int keycode){
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT){
            body.setLinearVelocity(keycode == Input.Keys.RIGHT ? VELOCITY_X : -VELOCITY_X, body.getLinearVelocity().y);
            flip = keycode != Input.Keys.RIGHT;
            animations = Animations.BOY_WALKING;
            usingOnlyLastFrame = false;
        }
        if (keycode == Input.Keys.SPACE){
            animations = Animations.BOY_JUMPING;
            getBody().setLinearVelocity(getBody().getLinearVelocity().x, 100);
            JUMP.play();
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT){
            body.setLinearVelocity(0f, body.getLinearVelocity().y);
            animations = Animations.BOY_IDLE;
        }
    }
}
