package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.*;
import com.mygdx.game.principal.Application;


import java.util.ArrayList;

public class Level implements Screen, InputProcessor {

    public final Application app;

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public SpriteBatch spriteBatch;
//    public Images images;
    public Player player;
    public Viewport viewport;
    public OrthographicCamera camera;
    public World world;
    public Grass grass;
    public Background background;
    public ArrayList<Crystal> crystals;
    Box2DDebugRenderer box2DDebugRenderer;
    private Music music;
    ShapeRenderer shapeRenderer;
    private ArrayList<Enemy> enemies;
    private Tile level1;
    private Portal portal;

    public Level(final Application app){
        this.app = app;

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


        level1 = new Tile("TiledMap.tmx");
        level1.createBodies(level1.loadMapObjects("StaticObjects"), world);

        crystals = new ArrayList<>();
        portal = new Portal();
        for (int i = 0; i < 10; i++) {
            crystals.add(new Crystal(world, new Vector2(300 + (i * 100), 450)));
        }
//        grass = new Grass(world);
//        tile480x320 = new Tile480x320(world, camera, new Vector2(0,0), new Vector2(205,0), new Vector2(479,135),
//                new Vector2(479, 319), new Vector2(325, 329), new Vector2(0,0));
        background = new Background();
        box2DDebugRenderer = new Box2DDebugRenderer(true, false, false, false, false, false);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Sunglasses.mp3"));

        shapeRenderer = new ShapeRenderer();

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



        box2DDebugRenderer.render(world, camera.combined);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);

        shapeRenderer.begin();
        for (Enemy enemy : enemies)
            enemy.render(shapeRenderer);

        player.render(shapeRenderer);
        for (Crystal c : crystals)
            c.render(shapeRenderer);
        shapeRenderer.end();

        spriteBatch.setProjectionMatrix(camera.combined);
        camera.position.set((player.getBody().getPosition().x) + WIDTH/2f, (player.getBody().getPosition().y - Stickman.HEIGHT/2f + HEIGHT)/2f, 0);

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
        player.update(delta);
//        for (Enemy enemy : enemies)
//            enemy.update(delta);
        world.step(delta, 7,7);
        camera.update();
        world.step(delta, 7,7);
        camera.update();
        for (Enemy enemy : enemies) {
            if (Intersector.overlaps(player.getAction(), enemy.getBodyBounds()) && player.animations.name().equals("PUNCH")) {
                enemy.setAnimation("E_PUNCHED");
            }
        }
        for (int index = 0; index < 10; index++){
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
    }

    @Override
    public boolean keyDown(int keycode) {
        player.keyDown(keycode);
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
}
