package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.mygdx.game.images.Images;
import com.mygdx.game.images.PowerBar;


import java.util.ArrayList;

public class Level implements Screen, InputProcessor {

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public final Application app;

    public SpriteBatch spriteBatch;
    public Images images;
    public Player player;
    public Viewport viewport;
    public OrthographicCamera camera;
    public World world;
    public Background background;
    public ArrayList<Crystal> crystals;
    Box2DDebugRenderer box2DDebugRenderer;
    public Music music;
    ShapeRenderer shapeRenderer;
    private ArrayList<Enemy> enemies;
    private Tile level1;
    private Portal portal;
    private ArrayList<Rectangle> thorns_rects;
    public MapObjects thorns, staticObjects;
    public ArrayList<RectangleMapObject> thornsRectangleMapObjects;
    public ArrayList<Rectangle> horizontalRectsThorns;
    public ArrayList<Rectangle> verticalRectsThorns;
    private PowerBar powerBar;

    public static float level_musicPosition;

    public Level(final Application app){
        this.app = app;
        images = new Images();
        app.pauseScreen = new PauseScreen(app, this);
        world = new World(new Vector2(0,-10f), false);
        spriteBatch = new SpriteBatch();
        player = new Player(world);
        enemies = new ArrayList<Enemy>();
        for (int i = 1; i < 5; i++){
            enemies.add(new Enemy(world, new Vector2(1000 * i, 350)));
        }
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

        level1 = new Tile("TiledMap.tmx");
        staticObjects = level1.loadMapObjects("StaticObjects");
        level1.createBodies(staticObjects, world, false);

        thorns = level1.loadMapObjects("Thorns");
        level1.createBodies(thorns, world, false);


        thornsRectangleMapObjects = new ArrayList<>();
        horizontalRectsThorns = new ArrayList<>();
        verticalRectsThorns = new ArrayList<>();

        for (MapObject m : thorns){
            RectangleMapObject t = (RectangleMapObject) m;
            thornsRectangleMapObjects.add(t);
            if (t.getName().equals("Thorns1")){
                t.getRectangle().width += 7f;
                t.getRectangle().height -= 10f;
                horizontalRectsThorns.add(t.getRectangle());

            } else {
                if (t.getName().equals("Thorns5")) {
                    t.getRectangle().x -= 7f;
                    t.getRectangle().width += 7f;
                    t.getRectangle().height -= 10f;
                    horizontalRectsThorns.add(t.getRectangle());
                } else {
                    verticalRectsThorns.add(t.getRectangle());
                    t.getRectangle().height += 7f;
                    t.getRectangle().y -= 7f;
                }
            }
        }

        crystals = new ArrayList<>();
        portal = new Portal();
        for (int i = 0; i < Crystal.X_POSITIONS.length; i++) {
            crystals.add(new Crystal(world));
        }
        background = new Background();
        box2DDebugRenderer = new Box2DDebugRenderer(true, false, false, false, false, true);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Sunglasses.mp3"));

        shapeRenderer = new ShapeRenderer();
        powerBar = new PowerBar();
    }

    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
        for (int i = 0; i < 5; i++)
            update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        update(delta);
        background.render();
        powerBar.render();
        box2DDebugRenderer.render(world, camera.combined);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);

        shapeRenderer.begin();
//        for (Enemy enemy : enemies)
//            enemy.render(shapeRenderer);

        player.render(shapeRenderer);
        for (Crystal c : crystals)
            c.render(shapeRenderer);
        shapeRenderer.end();

        spriteBatch.setProjectionMatrix(camera.combined);
        camera.position.set((player.getBody().getPosition().x) + WIDTH/2f, (player.getBody().getPosition().y - Stickman.HEIGHT/2f + HEIGHT)/2f, 0);

        renderObjects();
    }

    public void renderObjects(){
        spriteBatch.begin();
        level1.render(camera);
        portal.render(spriteBatch);
        for (Crystal crystal : crystals)
            crystal.render(spriteBatch);
        for (Enemy enemy : enemies)
            enemy.render(spriteBatch);
        player.render(spriteBatch);
        spriteBatch.end();
    }

    public void update(float delta){
        for (Rectangle rect : horizontalRectsThorns) {
            if (rect.overlaps(player.getBodyBounds())){
                if (rect.equals(horizontalRectsThorns.get(1))){
                    player.getBody().setTransform(player.getBody().getPosition().x - 7f, player.getBody().getPosition().y, 0);
                } if(rect.equals(horizontalRectsThorns.get(0))){
                    player.getBody().setTransform(player.getBody().getPosition().x + 7f, player.getBody().getPosition().y, 0);
                }
                System.out.println("horizontalRectsThorns");
                if (!player.animations.name().equals("IDLE_FLASH")) {
                    PowerBar.hp -= 20;
                    player.animations = Animations.IDLE_FLASH;
                }
            }
        }
        for (Rectangle rect : verticalRectsThorns) {
            if (rect.overlaps(player.getBodyBounds())){
                System.out.println("verticalRectsThorns");
                player.getBody().setTransform(player.getBody().getPosition().x, player.getBodyBounds().y - 90f, 0);
                if (!player.animations.name().equals("IDLE_FLASH")) {
                    Timer timer = new Timer();
                    timer.scheduleTask(new Timer.Task() {
                        @Override
                        public void run() {
                            PowerBar.hp -= 20;
                        }
                    }, 0f, 1000);
                    player.animations = Animations.IDLE_FLASH;
                    player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 40f);
                }
            }
        }

        player.update(delta);
        for (Enemy enemy : enemies)
            enemy.update(delta);
        world.step(delta, 7,7);
        camera.update();
        world.step(delta, 7,7);
        camera.update();
        for (Enemy enemy : enemies) {
            if (Intersector.overlaps(player.getAction(), enemy.getBodyBounds()) && player.animations.name().equals("PUNCH")) {
                enemy.setAnimation("E_PUNCHED");
            }
            if (Intersector.overlaps(player.getAction(), enemy.getBodyBounds()) && player.animations.name().equals("SABER")) {
                for (Fixture f : enemy.getBody().getFixtureList()){
                    f.setSensor(true);
                }
                enemy.setAnimation("E_SPLIT");
                enemy.getBody().setGravityScale(0f);
                Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task(){
                    @Override
                    public void run() {
                        enemy.setVisible(false);
                    }
                }, 3);
            }
        }
        for (int index = 0; index < Crystal.X_POSITIONS.length; index++){
            crystals.get(index).taked(player.getBodyBounds(), player.getAction());
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
//        player.resize(spriteBatch, width, height);
//        enemy.resize(spriteBatch, width, height);
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
        player.dispose();
        for (Enemy enemy : enemies)
            enemy.dispose();
        spriteBatch.dispose();
        for (Crystal c : crystals)
            c.dispose();
        world.dispose();
        background.dispose();
        powerBar.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        player.keyDown(keycode);

        if (keycode == Input.Keys.ESCAPE){
            Gdx.input.setInputProcessor(app.pauseScreen);
            app.setScreen(app.pauseScreen);
            level_musicPosition = music.getPosition();
            music.stop();
            app.pauseScreen.song.play();
            app.pauseScreen.song.setPosition(PauseScreen.pause_musicPosition);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        player.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.touchDown(screenX, screenY,pointer,button);
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
        player.mouseMoved(screenX, screenY);
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
