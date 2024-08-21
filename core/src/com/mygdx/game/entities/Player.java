package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Animations;

import java.util.Random;

public class Player extends Stickman implements InputProcessor {

    float stateTime; // A variable for tracking elapsed time for the animation
    public Animations animations;
    private boolean lastFrame;
//    private Image image = new Image(new Texture(Gdx.files.internal("Saber.png")));
    private Sound JUMP = Gdx.audio.newSound(Gdx.files.internal("sounds/Jump.wav"));
    private Sound HIYAH = Gdx.audio.newSound(Gdx.files.internal("sounds/HoYah.wav"));
    private boolean facingRight;
    private Rectangle action;

    public Player(World world){
        super(world);
        animations = Animations.IDLE;
        stateTime = 0f;
        action = new Rectangle(0, 0, 0, 0);
    }

    public void render(SpriteBatch spriteBatch, Camera camera){
        Sprite s = new Sprite(animations.getAnimator().currentSpriteFrame(lastFrame, loopTrueOrFalse(animations.name()), flip));
        s.setOrigin(0,0);
        s.setCenter(WIDTH/2f, HEIGHT/2f);
        float rotation = (float) Math.toDegrees(getBody().getTransform().getRotation());
        s.setRotation(rotation);
        s.setPosition(getBody().getPosition().x, getBody().getPosition().y);
        s.draw(spriteBatch);
        facingRight = !s.isFlipX();
    }

    private boolean loopTrueOrFalse(String name){
        return (name.equals("WALKING") && getBody().getLinearVelocity().x != 0) | (name.equals("WALKING") && getBody().getLinearVelocity().y != 0)
                | (name.equals("IDLE") && getBody().getLinearVelocity().x == 0);
    }

    public void update(float delta) {
        animation();
    }

    private void animation(){
//        animations.animator.updateFramePosition();
        String name = animations.name();
        if (name.equals("WALKING")){
            getBody().setFixedRotation(true);
            getBody().setTransform(getBody().getPosition().x,getBody().getPosition().y,0);
            if (getBody().getLinearVelocity().x == 0)
                animations = Animations.IDLE;
        } else{
            if (name.equals("PUNCH")){

                if (animations.animator.ani_finished()) {
                    animations.animator.resetStateTime();
                    animations = Animations.IDLE;
                }
            } else{
                if (name.equals("JUMPING")){
                    if (animations.animator.ani_finished() && Math.abs(getBody().getLinearVelocity().y) < 5){
                        getBody().setLinearVelocity(getBody().getLinearVelocity().x, 70);
                        JUMP.play();
                        animations.animator.resetStateTime();
                        if (getBody().getLinearVelocity().x == 0)
                            animations = Animations.IDLE;
                        else
                            animations = Animations.WALKING;
                    }
                } else{
                    if (name.equals("HIYAH")) {
                        lastFrame = true;
                        if (getBody().getLinearVelocity().x == 0){
                            getBody().setLinearVelocity(new Vector2((!flip ? 10 : -10), getBody().getLinearVelocity().y));
                        }
                        if (getBody().getLinearVelocity().x != 0){
                            getBody().setLinearVelocity(new Vector2((!flip ? 30 : -30), getBody().getLinearVelocity().y));
                        }
                        if (getBody().getLinearVelocity().y == 0) {
                            if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT))
                                animations = Animations.IDLE;
                            else
                                animations = Animations.WALKING;
                            lastFrame = false;
                        }
                    } else{
                        getBody().setFixedRotation(false);
                        lastFrame = false;
                        animations = Animations.IDLE;
                        getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
                    }
                }
            }
        }
    }

    public void dispose(){
        super.dispose();
        animations.getAnimator().dispose();
        world.dispose();
    }

    public void resize(SpriteBatch spriteBatch, int width, int height){
        spriteBatch.getProjectionMatrix().setToOrtho2D(getBody().getPosition().x, getBody().getPosition().y, width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D){
            animations = Animations.PUNCH;
            JUMP.play();
        }
        if (keycode == Input.Keys.RIGHT){
            animations = Animations.WALKING;
            flip = false;
            getBody().setLinearVelocity(15, getBody().getLinearVelocity().y);
        }
        if (keycode == Input.Keys.LEFT){
            animations = Animations.WALKING;
            flip = true;
            getBody().setLinearVelocity(-15, getBody().getLinearVelocity().y);
        }
        if (keycode == Input.Keys.SPACE){
            if (Math.abs(getBody().getLinearVelocity().y) > 5){
                animations = Animations.HIYAH;
                HIYAH.play();
            } else{
                animations = Animations.JUMPING;
            }animations.getAnimator().resetStateTime();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT)
            getBody().setLinearVelocity(new Vector2(0,getBody().getLinearVelocity().y));
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       BodyDef bodyDef = new BodyDef();
       int value = new Random().nextInt(2);
       switch(value){
           case 0:{
               bodyDef.type = BodyDef.BodyType.StaticBody;
               break;
           }
           case 1:{
               bodyDef.type = BodyDef.BodyType.DynamicBody;
               break;
           }
       }
       bodyDef.active = true;
       bodyDef.position.set(getBody().getPosition().x, new Random().nextFloat(768f)); // Posição inicial
       CircleShape shape = new CircleShape();
       shape.setRadius(new Random().nextFloat(100f));
       FixtureDef fixtureDef = new FixtureDef();
       fixtureDef.shape = shape;
       fixtureDef.density = 1.0f;
       fixtureDef.friction = 0.1f;
       Body body = world.createBody(bodyDef);
       body.createFixture(fixtureDef);
       body.setActive(true);
       return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public Rectangle getBodyBounds(){
        return new Rectangle(getBody().getPosition().x + WIDTH/2f - 25, getBody().getPosition().y + HEIGHT/2f, 50, 100);
    }


    public void render(ShapeRenderer s) {
        if (animations.name().equals("PUNCH")) {
            action = new Rectangle(facingRight ? getBody().getPosition().x + WIDTH/2f : getBody().getPosition().x + WIDTH/2f -80, getBody().getPosition().y + HEIGHT/2f + 40, 80, 20);
//          s.rect(getBody().getPosition().x + WIDTH/2f, getBody().getPosition().y + HEIGHT/2f + 40, 80, 20);
//          s.rect(getBody().getPosition().x + WIDTH / 2f - 80, getBody().getPosition().y + HEIGHT / 2f + 40, 80, 20);
        } else{
            if (animations.name().equals("HIYAH")){
                action = new Rectangle(facingRight ? getBody().getPosition().x + WIDTH/2f + 50 : getBody().getPosition().x + WIDTH/2f - 100, getBody().getPosition().y + HEIGHT/2f - 40, 50, 50);
            } else{
                action = new Rectangle(0, 0, 0, 0);
            }
        }
//        action = getBodyBounds();
        s.rect(action.x, action.y, action.width, action.height);
    }

    public Rectangle getAction() {
        return action;
    }
}
