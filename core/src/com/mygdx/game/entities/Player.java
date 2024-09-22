package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Animations;
import com.mygdx.game.principal.Application;

import java.util.ArrayList;
import java.util.Random;

public class Player extends Stickman {

    float stateTime; // A variable for tracking elapsed time for the animation
    public Animations animations;
    private boolean lastFrame;
//    private Image image = new Image(new Texture(Gdx.files.internal("Saber.png")));
    private Sound JUMP = Gdx.audio.newSound(Gdx.files.internal("sounds/Jump.wav"));
    private Sound HIYAH = Gdx.audio.newSound(Gdx.files.internal("sounds/HoYah.wav"));
    private Sound GUNSHOT = Gdx.audio.newSound(Gdx.files.internal("sounds/gun shot.wav"));
    private boolean facingRight;
    private Rectangle action;
    private ArrayList<Bullet> bullets = new ArrayList<>();



    public Player(World world){
        super(world);
        animations = Animations.IDLE;
        box.getBody().setTransform(130, 350, 0);
        action = new Rectangle(0, 0, 0, 0);

    }

    public void render(SpriteBatch spriteBatch){

        Sprite s = new Sprite(animations.getAnimator().currentSpriteFrame(lastFrame, loopTrueOrFalse(animations.name()), flip));
        s.setOrigin(0,0);
        s.setCenter(WIDTH/2f, HEIGHT/2f);
        float rotation = (float) Math.toDegrees(getBody().getTransform().getRotation());
        s.setRotation(rotation);
        s.setPosition(getBody().getPosition().x, getBody().getPosition().y);
        s.draw(spriteBatch);
        facingRight = !s.isFlipX();
        for (Bullet b : bullets)
            b.render(spriteBatch);
    }

    private void resetPosition(){
        if (getBody().getPosition().y <= -300){
            getBody().setTransform(150,300, 0);
            animations = Animations.IDLE_FLASH;
            animations.animator.stateTime = 0f;
        }
    }

    public void update(float delta) {
        resetPosition();
        animation();
    }

    private void animation(){
        String name = animations.name();
        if (name.equals("WALKING")){
            lastFrame = false;
            getBody().setFixedRotation(true);
            getBody().setTransform(getBody().getPosition().x,getBody().getPosition().y,0);
            if (Math.abs(getBody().getLinearVelocity().x) <= 0.2f)
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
                        getBody().setFixedRotation(true);
                        lastFrame = true;
                        if (getBody().getLinearVelocity().x == 0){
                            getBody().setLinearVelocity(new Vector2((!flip ? 5 : -5), getBody().getLinearVelocity().y));
                        }
                        if (getBody().getLinearVelocity().x != 0){
                            getBody().setLinearVelocity(new Vector2((!flip ? 60 : -60), getBody().getLinearVelocity().y));
                        }
                        if (getBody().getLinearVelocity().y == 0) {
                            if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT))
                                animations = Animations.IDLE;
                            else
                                animations = Animations.WALKING;
                            lastFrame = false;
                        }
                    } else{
                        if (name.equals("SHOT")){
                            if (animations.animator.ani_finished()){
                                bullets.add(new Bullet(world, new Vector2(facingRight ? getBody().getPosition().x +
                                        WIDTH/2f + 60: getBody().getPosition().x + WIDTH/2f -80,
                                        getBody().getPosition().y + HEIGHT/2f + 40), flip));
                                animations.animator.resetStateTime();
                                animations = Animations.IDLE;
                            }
                        } else {
                            if (name.equals("IDLE_FLASH")){
                                lastFrame = false;
                                getBody().setFixedRotation(true);
//                                box.getBody().setTransform(150f, 300f, 0f);
                                if (animations.animator.ani_finished()) {
                                    animations.getAnimation("IDLE_FLASH").animator.resetStateTime();
                                    animations = Animations.IDLE;
                                }
                            } else {
                                getBody().setFixedRotation(false);
                                lastFrame = false;
                                animations = Animations.IDLE;
                                getBody().setLinearVelocity(0, getBody().getLinearVelocity().y);
                            }
                        }
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

    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.S){
            animations = Animations.SHOT;
            GUNSHOT.play();
        }
        if (keycode == Input.Keys.D){
           animations = Animations.PUNCH;
           JUMP.play();
           PolygonShape ps = new PolygonShape();
           ps.setAsBox(30,10, new Vector2(facingRight ? WIDTH/2f + 50 : WIDTH/2f - 50, HEIGHT/2f + 50), 0);
           box.fixtureDef3.shape = ps;
           getBody().createFixture(box.fixtureDef3);
        }
        if (keycode == Input.Keys.RIGHT){
            animations = Animations.WALKING;
            flip = false;
            getBody().setLinearVelocity(getBody().getLinearVelocity().x + 20, getBody().getLinearVelocity().y);
        }
        if (keycode == Input.Keys.LEFT){
            animations = Animations.WALKING;
            flip = true;
            getBody().setLinearVelocity(getBody().getLinearVelocity().x + -20, getBody().getLinearVelocity().y);
        }
        if (keycode == Input.Keys.SPACE){
            if (Math.abs(getBody().getLinearVelocity().y) > 5){
                animations = Animations.HIYAH;
                HIYAH.play();
            } else{
                animations = Animations.JUMPING;
            }animations.getAnimator().resetStateTime();
        }
        if (keycode == Input.Keys.ESCAPE){
            Application.pause = !Application.pause;
        }
        return false;
    }


    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.D){
            getBody().destroyFixture(box.getBody().getFixtureList().get(3));
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT)
            getBody().setLinearVelocity(new Vector2(0,getBody().getLinearVelocity().y));
        return false;
    }


    public boolean keyTyped(char character) {
        return false;
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       BodyDef bodyDef = new BodyDef();
       bodyDef.type = BodyDef.BodyType.DynamicBody;
       bodyDef.active = true;
       bodyDef.position.set(getBody().getPosition().x + 300, new Random().nextFloat(768f)); // Posição inicial
       CircleShape shape = new CircleShape();
       shape.setRadius(new Random().nextFloat(200f));
       FixtureDef fixtureDef = new FixtureDef();
       fixtureDef.shape = shape;
       fixtureDef.density = 1.0f;
       fixtureDef.friction = 0.1f;
       MassData m = new MassData();

       Body body = world.createBody(bodyDef);
       body.createFixture(fixtureDef);
       body.setActive(true);
       m.mass = 1_000_000f;
       body.setMassData(m);
       return false;
    }


    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }


    public boolean scrolled(float amountX, float amountY) {
        return false;
    }




    public void render(ShapeRenderer s) {
        for (Bullet b : bullets)
            b.render(s);
        if (animations.name().equals("PUNCH")) {
            action = new Rectangle(facingRight ? getBody().getPosition().x + WIDTH/2f : getBody().getPosition().x + WIDTH/2f -80,
                    getBody().getPosition().y + HEIGHT/2f + 40, 80, 20);
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
        s.rect(getBodyBounds().x, getBodyBounds().y, getBodyBounds().width, getBodyBounds().height);
    }

    public Rectangle getAction() {
        return action;
    }
}
