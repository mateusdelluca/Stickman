package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.*;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.sfx.Sounds;


import java.util.ArrayList;

import static com.mygdx.game.sfx.Sounds.PUNCHED;

public class Level implements Screen, InputProcessor {

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public final Application app;

    public SpriteBatch spriteBatch;
//    public Player player;
    public Viewport viewport;
    public OrthographicCamera camera;
    public World world;
    public Background background;
    public ArrayList<Crystal> crystals;
    Box2DDebugRenderer box2DDebugRenderer;
    public static Music songLevel1;
    ShapeRenderer shapeRenderer;
//    private ArrayList<Enemy> enemies;
//    private Tile level1;
    private Tile level2;
    private Portal portal;
    private ArrayList<Rectangle> thorns_rects;
    public MapObjects thorns, staticObjects;
    public ArrayList<RectangleMapObject> thornsRectangleMapObjects;
//    public ArrayList<Rectangle> horizontalRectsThorns;
    public ArrayList<Rectangle> verticalRectsThorns;
    private PowerBar powerBar;

    private Boy boy;
    private Monster1 monster1;

    private Sounds sounds;

    public static final Sound teletransport = Gdx.audio.newSound(Gdx.files.internal("sounds/Eletric Whoosh.wav"));

    public Level(final Application app){
        this.app = app;
        app.pauseScreen = new PauseScreen(app, this);
        world = new World(new Vector2(0,-10f), false);
        spriteBatch = new SpriteBatch();
//        player = new Player(world, this);
//        enemies = new ArrayList<Enemy>();
//        for (int i = 1; i < 5; i++){
//            enemies.add(new Enemy(world, new Vector2(1000 * i, 350)));
//        }
//        camera.setToOrtho(false);
//        camera.viewportHeight = Gdx.graphics.getHeight() * (float) 1/32;
//        camera.viewportWidth = Gdx.graphics.getWidth() * (float) 1/32;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new ScreenViewport(camera);
        camera.update();
        thorns_rects = new ArrayList<>();

//        level1 = new Tile("TiledMap.tmx");
        level2 = new Tile("Level2/Level2.tmx");
        staticObjects = level2.loadMapObjects("Rects");
        level2.createBodies(staticObjects, world, false);
//        staticObjects = level1.loadMapObjects("StaticObjects");
//        level1.createBodies(staticObjects, world, false);

//        thorns = level1.loadMapObjects("Thorns");
//        level1.createBodies(thorns, world, false);

        thorns = level2.loadMapObjects("Thorns");
        level2.createBodies(thorns, world, false);

        thornsRectangleMapObjects = new ArrayList<>();
//        horizontalRectsThorns = new ArrayList<>();
        verticalRectsThorns = new ArrayList<>();

        for (MapObject m : thorns){
            RectangleMapObject t = (RectangleMapObject) m;
            thornsRectangleMapObjects.add(t);
//            if (t.getName().equals("Thorns1")){
//                t.getRectangle().width += 7f;
//                t.getRectangle().height -= 10f;
//                horizontalRectsThorns.add(t.getRectangle());
//
//            } else {
//                if (t.getName().equals("Thorns5")) {
//                    t.getRectangle().x -= 7f;
//                    t.getRectangle().width += 7f;
//                    t.getRectangle().height -= 10f;
//                    horizontalRectsThorns.add(t.getRectangle());
//                } else {
//                    if (t.getName().equals("Thorns6")) {
//                        t.getRectangle().x -= 7f;
//                        t.getRectangle().width += 7f;
//                        t.getRectangle().height -= 10f;
//                        horizontalRectsThorns.add(t.getRectangle());
//                    } else {
                        verticalRectsThorns.add(t.getRectangle());
                        t.getRectangle().height += 7f;
                        t.getRectangle().y -= 7f;
                    }
//                }
//            }
//        }

        crystals = new ArrayList<>();
        portal = new Portal();
        for (int i = 0; i < Crystal.X_POSITIONS.length; i++) {
            crystals.add(new Crystal(world));
        }
        background = new Background();
        box2DDebugRenderer = new Box2DDebugRenderer(true, false, false, false, false, true);
        songLevel1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/Sunrise.mp3"));
        songLevel1.setLooping(true);
        shapeRenderer = new ShapeRenderer();
        powerBar = new PowerBar();


        boy = new Boy(world, new Vector2(100, 800));
        monster1 = new Monster1(world, new Vector2(300, 450));
        sounds = new Sounds();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
//        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        for (int i = 0; i < 5; i++)
            update(delta);
//        player.update(delta);
//        for (Enemy enemy : enemies)
//            enemy.update(delta);
        update(delta);

        background.render();
        powerBar.render();
        box2DDebugRenderer.render(world, camera.combined);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);

        shapeRenderer.begin();
//        for (Enemy enemy : enemies)
//            enemy.render(shapeRenderer);
//        player.render(shapeRenderer);
        for (Crystal c : crystals)
            c.render(shapeRenderer);
        portal.render(shapeRenderer);
        boy.render(shapeRenderer);
        shapeRenderer.end();

        System.out.println(boy.getBody().getPosition().toString());

        spriteBatch.setProjectionMatrix(camera.combined);
        if (boy.getBody().getPosition().x > WIDTH/2f && boy.getBody().getPosition().x < (6000 - WIDTH))
            camera.position.set((boy.getBody().getPosition().x) + WIDTH/4f, (boy.getBody().getPosition().y - Boy.HEIGHT/2f) + HEIGHT/4f, 0);
        if (boy.getBody().getPosition().x >= (6000 - WIDTH))
            camera.position.set(6000 - WIDTH/2f, HEIGHT / 2f, 0);
        if (boy.getBody().getPosition().x < WIDTH/2f)
            camera.position.set(0f + WIDTH / 2f, (boy.getBody().getPosition().y - Boy.HEIGHT / 2f) + HEIGHT / 4f, 0);

        renderObjects();
    }

    public void renderObjects(){
        spriteBatch.begin();
//        level1.render(camera);
        level2.render(camera);
        portal.render(spriteBatch);
        for (Crystal crystal : crystals)
            crystal.render(spriteBatch);
//        for (Enemy enemy : enemies)
//            enemy.render(spriteBatch);
//        player.render(spriteBatch);
        boy.render(spriteBatch);
        monster1.render(spriteBatch);
        spriteBatch.end();
    }

    public void update(float delta){
        world.step(delta, 7,7);
        camera.update();
        world.step(delta, 7,7);
        camera.update();

//        for (Rectangle rect : horizontalRectsThorns) {
//            if (rect.overlaps(player.getBodyBounds())){
//                if (rect.equals(horizontalRectsThorns.get(1))){
//                    player.getBody().setTransform(player.getBody().getPosition().x - 7f, player.getBody().getPosition().y, 0);
//                } if(rect.equals(horizontalRectsThorns.get(0))){
//                    player.getBody().setTransform(player.getBody().getPosition().x + 7f, player.getBody().getPosition().y, 0);
//                }
//                System.out.println("horizontalRectsThorns");
//                if (!player.animations.name().equals("IDLE_FLASH")) {
//                    PowerBar.hp -= 20;
//                    player.animations = Animations.IDLE_FLASH;
//                }
//            }
//        }
        for (Rectangle rect : verticalRectsThorns) {
//            if (rect.overlaps(player.getBodyBounds())){
//                System.out.println("verticalRectsThorns");
//                player.getBody().setTransform(player.getBody().getPosition().x, player.getBodyBounds().y - 90f, 0);
//                if (!player.animations.name().equals("IDLE_FLASH") && !player.animations.name().equals("JUMPING") && !player.isHited()) {
//                    player.setHited(true);
//                    Timer timer = new Timer();
//                    timer.scheduleTask(new Timer.Task() {
//                        @Override
//                        public void run() {
//                            PowerBar.hp -= 20; player.setHited(false);
//                        }
//                    }, 0f, 1000);
//                    player.animations = Animations.IDLE_FLASH;
//                    player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y + 10f);
//                }
//            }
        }

//        for (Enemy enemy : enemies) {
//            if (!enemy.isSplit()){
//                if (Math.abs(enemy.getBodyBounds().x - player.getBodyBounds().x) < 300 && enemy.getBody().getAngle() < Math.toRadians(30f)) {
//                   if (!enemy.isHited()) {
//                       enemy.setAnimation("E_WALKING");
//                       enemy.getBody().setLinearVelocity(player.getBodyBounds().x < enemy.getBodyBounds().x ? -5 : 5,
//                               enemy.getBody().getLinearVelocity().y);
//                       enemy.setFlip(player.getBodyBounds().x < enemy.getBodyBounds().x);
//                   } else {
//                       if (enemy.getBody().getLinearVelocity().x > 0)
//                           enemy.setFlip(false);
//                       else
//                           enemy.setFlip(true);
//                       if (Math.abs(enemy.getBodyBounds().x - player.getBodyBounds().x) < 100) {
//                           if (!enemy.animations.name().equals("E_PUNCHED") &&
//                                   !Intersector.overlaps(player.getAction(), enemy.getBodyBounds())) {
//                               enemy.setAnimation("E_PUNCH");
//                           }
//                       }
//                   }
//                   if (player.getAction().overlaps(enemy.getBodyBounds())) {
//                        enemy.setAnimation("E_PUNCHED");
//                        if (!enemy.isHited()) {
//                            enemy.setHited(true);
//                            PUNCHED.play();
//                        }
//                        enemy.setFrameCounter(0);
//                   }
//                } else {
//                    enemy.getBody().setLinearVelocity(0, enemy.getBody().getLinearVelocity().y);
//                    enemy.setAnimation(("E_IDLE"));
//                }
//
//                if (Intersector.overlaps(enemy.getAction(), player.getBodyBounds())) {
//                    if (!player.isHited()) {
//                        player.animations = Animations.PUNCHED;
//                        player.setHited(true);
//                        player.setFrameCounter(0);
//                    }
//                }
//            }
//            if (Intersector.overlaps(player.getAction(), enemy.getBodyBounds()) && player.animations.name().equals("SABER")) {
//                enemy.setAnimation("E_SPLIT");
//                enemy.setSplit(true);
//                enemy.getBody().setLinearVelocity(0,0);
//                enemy.getBody().setFixedRotation(true);
//            }
//        }
        for (int index = 0; index < Crystal.X_POSITIONS.length; index++){
            crystals.get(index).taked(boy.getBodyBounds());
        }
//        if (portal.getRectangle().contains(player.getBodyBounds())){
//            teletransport.play();
//            player.getBody().setTransform(350, 400, 0);
//        }
        if (portal.getRectangle().contains(boy.getBodyBounds())){
            teletransport.play();
            boy.getBody().setTransform(100, 800, 0);
        }
        if (boy.getBodyBounds().overlaps(monster1.getBodyBounds())){
            monster1.animations = Animations.MONSTER1_FLICKERING;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
//        player.resize(spriteBatch, width, height);
//        for (Enemy enemy : enemies)
//            enemy.resize(spriteBatch, width, height);
        boy.resize(spriteBatch, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
//        player.dispose();
//        for (Enemy enemy : enemies)
//            enemy.dispose();
        for (Crystal c : crystals)
            c.dispose();
        spriteBatch.dispose();
        world.dispose();
        background.dispose();
        powerBar.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        boy.keyDown(keycode);
//        if (player.isStand()) {
//            player.keyDown(keycode);
            if (keycode == Input.Keys.ESCAPE) {
                Gdx.input.setInputProcessor(app.pauseScreen);
                app.setScreen(app.pauseScreen);
//            level_musicPosition = songLevel1.getPosition();
                songLevel1.pause();
                app.pauseScreen.song.play();
//                app.pauseScreen.song.setPosition(PauseScreen.pause_musicPosition);
            }
//        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        boy.keyUp(keycode);
//        player.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        player.touchDown(screenX, screenY,pointer,button);
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
//        player.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

//    public static TimerTask timerTask(Runnable... r){
//        return new TimerTask() {
//            @Override
//            public void run() {
//                for (Runnable rr : r)
//                    rr.run();
//            }
//        };
//    }
//
//    private void timer(long timer1, Runnable... r){
//        java.util.Timer timer = new java.util.Timer();
//        timer.schedule(timerTask(r), timer1);
//    }
}
