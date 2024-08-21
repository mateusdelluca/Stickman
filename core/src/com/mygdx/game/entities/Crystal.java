package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.images.Images;

import java.util.Random;

public class Crystal extends Objeto{

    public static final int WIDTH = 282/4, HEIGHT = 421/4;
    private Random r = new Random();
    private boolean visible2;
    private Rectangle box1, box2;

    public Crystal(World world, Vector2 position) {
        super(world, WIDTH, HEIGHT);
        dimensions = new Vector2(position.x,380f);
        body = createBoxBody();
        body.setTransform(dimensions, 0);
        visible = true;
        visible2 = true;
    }

    protected Body createBoxBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.active = true;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation = false;
        CircleShape cs = new CircleShape();
        cs.setRadius(30f);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = cs;
        fixtureDef.isSensor = true;
        fixtureDef.friction = 0;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setActive(true);
        fixtureDef.density = 1.0f;
        return body;
    }

    public void render(SpriteBatch sp, int index){
//        Sprite sprite = new Sprite(Images.crystals[i].currentSpriteFrame(false, true, true));
//        sp.draw(sprite, dimensions.x, 350, WIDTH, HEIGHT);
////        if (i == new Random().nextInt(10)) {
////            Images.lights[i].stateTime = 0;
////            Images.lights[i].setFrameDuration(1/new Random().nextFloat(6));
////        }
//        Sprite sprite2 = new Sprite(Images.lights[i].currentSpriteFrame(false, true, false));
//        sp.draw(sprite2, dimensions.x, 330, WIDTH, HEIGHT);
//        Sprite cr = new Sprite(Images.crystal);
//        cr.flip(false, new Random().nextInt(4) == 1);
       if (visible) {
           sp.draw(Images.crystal, dimensions.x - 30, 330, WIDTH, HEIGHT);
       }
       if (visible2){
           sp.draw(Images.crystal3, dimensions.x - 30, 530, WIDTH, HEIGHT);
       }
//       if (fadeOut){
//           sp.draw(Images.crystals3fadeout[index].currentSpriteFrame(false, false, false),dimensions.x - 30, 530, WIDTH, HEIGHT);
//       }
    }

    public void dispose(){
        Images.crystal.dispose();
        Images.crystal3.dispose();
    }

    public void taked(Rectangle... playerRect){
        if (Intersector.overlaps(getBox1(), playerRect[0]) || Intersector.overlaps(getBox1(), playerRect[1])){
            visible = false;
        }
        if (Intersector.overlaps(getBox2(), playerRect[0]) || Intersector.overlaps(getBox2(), playerRect[1])){
            visible2 = false;
        }
    }

    public Rectangle getBox2(){
        return new Rectangle(getBody().getPosition().x, getBody().getPosition().y + 200, 100, 100);
    }

    public Rectangle getBox1() {
        return new Rectangle(getBody().getPosition().x, getBody().getPosition().y, 100, 100);
    }
    @Override
    public void render(ShapeRenderer s) {
        box1 = getBox1();
        box2 = getBox2();
        s.rect(box1.x, box1.y, box1.width, box1.height);
        s.rect(box2.x, box2.y, box2.width, box2.height);
    }


}
