package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.Images;
import com.mygdx.game.sfx.Sounds;

import static com.mygdx.game.screens.Level.songLevel1;
import static com.mygdx.game.sfx.Sounds.*;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Boy extends Objeto{

    public static final float WIDTH = 128f, HEIGHT = 128f;
    public static final float VELOCITY_X = 20f, JUMP_VELOCITY = 100f;
    public Animations animations = Animations.BOY_IDLE;
    private boolean flip, usingOnlyLastFrame, looping = true, init;
    private float punchingAnimationTimer;
    private Vector2 dimensions = new Vector2(65f, 95f);
    private Rectangle actionRect = new Rectangle();
    private float flickering_time;
    private boolean stricken;
    private boolean shooting;
    private float imgX, imgY, degrees, radians, dx, dy;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public Boy(World world, Vector2 position){
        super(world, WIDTH, HEIGHT);
        body = createBoxBody(new Vector2(dimensions.x/2f, dimensions.y/2f), BodyDef.BodyType.DynamicBody, false);
        body.setTransform(position, 0);
    }

    public void render(SpriteBatch s){
        update();

        Sprite sprite = new Sprite(animations.animator.currentSpriteFrame(usingOnlyLastFrame, looping, flip));
        if (!shooting) {
            sprite.setPosition(body.getPosition().x, body.getPosition().y);
            sprite.draw(s);
        }
        if (shooting) {
            Sprite sprite2 = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.getFrame(0));
            if (isMoving())
                sprite2 = new Sprite(Animations.BOY_SHOOTING_AND_WALKING.animator.currentSpriteFrame(usingOnlyLastFrame, looping, flip));
            sprite2.setPosition(body.getPosition().x, body.getPosition().y);

            sprite = new Sprite(Images.shooting1);
            sprite.setPosition(body.getPosition().x , body.getPosition().y);
            sprite.setRotation(degrees);
            if (Math.abs(degrees) > 90f) {
                sprite.setRotation(-Math.abs(180f - degrees));
                sprite.flip(true, false);
                sprite2.flip(true, false);
            }
            else{
                sprite.flip(false, false);
                sprite2.flip(false, false);
            }
            sprite.draw(s);
            sprite2.draw(s);
//            Sprite sprite3 = new Sprite(Images.shoot);
//            sprite3.setPosition(dx, dy);
//            sprite3.draw(s);

        }for (Bullet b : bullets)
            b.render(s);
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

        if (flickering_time >= 3.2f) {
//            animations = Animations.BOY_IDLE;
            flickering_time = 0f;
            stricken = false;
            Sounds.HURT.stop();
        }

        aim();
    }

    private void aim(){
        if (shooting) {
            dx = Gdx.input.getX() - body.getPosition().x;
            dy = (Gdx.graphics.getHeight() - Gdx.input.getY()) - body.getPosition().y; // Invert Y-axis
            degrees = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
            System.out.println(degrees);
            radians = (float) Math.atan2(dy, dx);
        }
    }

    public Rectangle actionRect(){
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
        if (name.equals("BOY_STRICKEN")){
            flickering_time += Gdx.graphics.getDeltaTime();
//            System.out.println(flickering_time);
            if (flickering_time >= 3.2f) {
                animations = Animations.BOY_IDLE;
                flickering_time = 0f;
                stricken = false;
                Sounds.HURT.stop();
            }
        } else {
            if (!stricken) {
                if (name.equals("BOY_PUNCHING")) {
                    punchingAnimationTimer += Gdx.graphics.getDeltaTime();
                    if (punchingAnimationTimer > 2f) {
                        animations = Animations.BOY_IDLE;
                        punchingAnimationTimer = 0f;
                    }
                } else {
                    if (name.equals("BOY_WALKING")) {

                    } else {
                        if (onGround()) {
                            if (isMoving())
                                animations = Animations.BOY_WALKING;
                            else
                                animations = Animations.BOY_IDLE;
                            //                   usingOnlyLastFrame = true;
                        } else {
                            animations = Animations.BOY_JUMPING;
                            //                    usingOnlyLastFrame = false;
                        }
                    }
                }
            }
        }
    }

    private boolean onGround(){
        return Math.abs(body.getLinearVelocity().y) <= 0.01f;
    }

    private boolean isMoving(){
        return Math.abs(body.getLinearVelocity().x) > 0;
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
//        spriteBatch.getProjectionMatrix().setToOrtho2D(body.getPosition().x, body.getPosition().y, width, height);
    }

    @Override
    public void render(ShapeRenderer s) {
//        s.rect(body.getPosition().x, body.getPosition().y, width, height);
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);
        s.rect(actionRect.x, actionRect.y, actionRect.width, actionRect.height);
    }

    public void keyDown(int keycode){
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT){
            body.setLinearVelocity(keycode == Input.Keys.RIGHT ? VELOCITY_X : -VELOCITY_X, body.getLinearVelocity().y);
            if (!shooting)
                flip = keycode == Input.Keys.LEFT;
            if (!stricken) {
                animations = Animations.BOY_WALKING;
            }
            usingOnlyLastFrame = false;
            looping = true;
        }
        if (!stricken) {
            if (keycode == Input.Keys.SPACE) {
                animations = Animations.BOY_JUMPING;
                getBody().setLinearVelocity(getBody().getLinearVelocity().x, JUMP_VELOCITY);
                JUMP.play();
            }
            if (keycode == Input.Keys.D) {
                punchingAnimationTimer = 0f;
                animations = Animations.BOY_PUNCHING;
                JUMP.play();
                HIYAH.play();
                usingOnlyLastFrame = false;
                looping = false;
                animations.animator.resetStateTime();
            }
            if (keycode == Input.Keys.A) {
                shooting = !shooting;
            }
        }
    }

    public void keyUp(int keycode){
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT){
            body.setLinearVelocity(0f, body.getLinearVelocity().y);
            if (!stricken)
                animations = Animations.BOY_IDLE;
        }
    }

    public void mouseMoved(int screenX, int screenY){
//        if (shooting) {
//            dx = screenX - body.getPosition().x;
//            dy = (Gdx.graphics.getHeight() - screenY) - body.getPosition().y; // Invert Y-axis
//            angle = (float) Math.atan2(dy, dx) * (180f / (float) Math.PI);
//            System.out.println(angle);
//            angle2 = (float) Math.atan2(dy, dx);
//        }
    }

    public void touchDown(int screenX, int screenY, int pointer, int button){
        if (shooting){
            System.out.println(true);
            bullets.add(new Bullet(world, new Vector2(!flip ? getBody().getPosition().x +
                    WIDTH / 2f: getBody().getPosition().x - WIDTH / 2f,
                    getBody().getPosition().y + HEIGHT / 2f), flip, radians));
            GUNSHOT.play();
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
        return new Rectangle(body.getPosition().x + width/2f - 30f, body.getPosition().y + height/2f - 50f, dimensions.x, dimensions.y);
    }

    public boolean isStricken() {
        return stricken;
    }

    public void setStricken(boolean stricken) {
        this.stricken = stricken;
    }
}
