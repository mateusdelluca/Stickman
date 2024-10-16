package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Animations;
import com.mygdx.game.sfx.Sounds;

import static com.mygdx.game.screens.Level.songLevel1;
import static com.mygdx.game.sfx.Sounds.HIYAH;
import static com.mygdx.game.sfx.Sounds.JUMP;

import com.badlogic.gdx.math.Rectangle;

public class Boy extends Objeto{

    public static final float WIDTH = 128f, HEIGHT = 128f;
    public static final float VELOCITY_X = 20f, JUMP_VELOCITY = 100f;
    public Animations animations = Animations.BOY_IDLE;
    private boolean flip, usingOnlyLastFrame, looping = true, init;
    private float punchingAnimationTimer;
    private Vector2 dimensions = new Vector2(65f, 95f);
    private Rectangle actionRect = new Rectangle();

    public Boy(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(dimensions.x/2f, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch s){
        update();
        Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping, flip));
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(s);
    }

    public void update(){
        animations();
        if (body.getPosition().x < 32){
            body.setTransform(32, body.getPosition().y, 0);
        }
        if (body.getPosition().y <= 400 && !init) {
            songLevel1.play();
            init = true;
        }
        actionRect = actionRect();
    }

    private Rectangle actionRect(){
        if (animations.name().equals("BOY_PUNCHING")) {
            if (frameCounter() >= 2)
                return new Rectangle(!flip ? getBody().getPosition().x + (WIDTH/2f) + 10 : getBody().getPosition().x + (WIDTH / 2f) - 55,
                        getBody().getPosition().y + HEIGHT / 2f - 25, 45, 45);
        }
        return new Rectangle();
    }

    private void animations() {
        String name = animations.name();
//        if (name.equals("BOY_JUMPING")) {
//            if (body.getLinearVelocity().y < -29f) {
//                animations = Animations.BOY_IDLE;
//            }
//        } else{
        if (name.equals("BOY_PUNCHING")) {
            punchingAnimationTimer += Gdx.graphics.getDeltaTime();
            if (punchingAnimationTimer > 2f) {
                animations = Animations.BOY_IDLE;
                punchingAnimationTimer = 0f;
            }
        } else{
            if (name.equals("BOY_WALKING")) {

            } else {
                if (onGround()) {
                    if (walking())
                        animations = Animations.BOY_WALKING;
                    else
                        animations = Animations.BOY_IDLE;
    //                   usingOnlyLastFrame = true;
                } else{
                    animations = Animations.BOY_JUMPING;
    //                    usingOnlyLastFrame = false;
                }
            }
        }
    }

    private boolean onGround(){
        return Math.abs(body.getLinearVelocity().y) <= 0.01f;
    }

    private boolean walking(){
        return Math.abs(body.getLinearVelocity().x) > 0;
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
//        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    @Override
    public void render(ShapeRenderer s) {
//        s.rect(body.getPosition().x, body.getPosition().y, width, height);
        s.rect(actionRect.x, actionRect.y, actionRect.width, actionRect.height);
    }

    public void keyDown(int keycode){
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT){
            body.setLinearVelocity(keycode == Input.Keys.RIGHT ? VELOCITY_X : -VELOCITY_X, body.getLinearVelocity().y);
            flip = keycode != Input.Keys.RIGHT;
            animations = Animations.BOY_WALKING;
            usingOnlyLastFrame = false;
            looping = true;
        }
        if (keycode == Input.Keys.SPACE){
            animations = Animations.BOY_JUMPING;
            getBody().setLinearVelocity(getBody().getLinearVelocity().x, JUMP_VELOCITY);
            JUMP.play();
        }
        if (keycode == Input.Keys.D){
            punchingAnimationTimer = 0f;
            animations = Animations.BOY_PUNCHING;
            JUMP.play();
            HIYAH.play();
            usingOnlyLastFrame = false;
            looping = false;
            animations.animator.resetStateTime();
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT){
            body.setLinearVelocity(0f, body.getLinearVelocity().y);
            animations = Animations.BOY_IDLE;
        }
    }

    public void setFrameCounter(int frame){
        setStateTime(animations.animator.timeToFrame(frame));
    }

    public void setStateTime(float time){
        animations.animator.stateTime = time;
    }

    public float frameCounter(){
        return animations.animator.getAnimation().getKeyFrame(animations.animator.stateTime).getU2() * animations.animator.getNumFrames();
    }

    public Rectangle getBodyBounds() {
        return new Rectangle(body.getPosition().x, body.getPosition().y, dimensions.x, dimensions.y);
    }


}
