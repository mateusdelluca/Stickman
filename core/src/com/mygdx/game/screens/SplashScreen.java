package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.entities.Background;
import com.mygdx.game.entities.Crystal;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Grass;
import com.mygdx.game.images.Images;
import com.mygdx.game.principal.Application;

import java.util.ArrayList;

public class SplashScreen implements Screen{

    public final Application app;

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public SpriteBatch spriteBatch;
    public Images images;
    public Player player;
    public OrthographicCamera camera;
    public World world;
    public Grass grass;
    public Background background;
    public ArrayList<Crystal> crystals;
    Box2DDebugRenderer box2DDebugRenderer;
    private Music music;
    ShapeRenderer shapeRenderer;

    public SplashScreen(final Application app){
        this.app = app;
        images = new Images();
        world = new World(new Vector2(0,-10f), false);
        spriteBatch = new SpriteBatch();
        player = new Player(world);
//        camera.setToOrtho(false);
//        camera.viewportHeight = Gdx.graphics.getHeight() * (float) 1/32;
//        camera.viewportWidth = Gdx.graphics.getWidth() * (float) 1/32;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = new OrthographicCamera(1920, 1080);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        app.setScreen(this);
        Gdx.input.setInputProcessor(player);
        crystals = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            crystals.add(new Crystal(world, new Vector2(300 + (i * 100), 380)));
        }
        grass = new Grass(world);
        background = new Background();
        box2DDebugRenderer = new Box2DDebugRenderer(false, false, false, false, false, false);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Guitar solo.mp3"));
        music.play();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        update(delta);
        background.render();
        box2DDebugRenderer.render(world, camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        player.render(shapeRenderer);
        for (Crystal c : crystals)
            c.render(shapeRenderer);
        shapeRenderer.end();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        grass.render(spriteBatch);
        for (int i = 0; i < crystals.size(); i++)
            crystals.get(i).render(spriteBatch);
        player.render(spriteBatch, camera);
        spriteBatch.end();
        camera.translate(player.getBody().getLinearVelocity().x/8, player.getBody().getLinearVelocity().y/16);
        for (int i = 0; i < 5; i++)
            update(delta);
    }

    public void update(float delta){
        player.update(delta);
        world.step(delta, 7,7);
        camera.update();
        world.step(delta, 7,7);
        camera.update();
        for (int index = 0; index < 10; index++){
            crystals.get(index).taked(player.getBodyBounds(), player.getAction());
        }
    }

    @Override
    public void resize(int width, int height) {
        player.resize(spriteBatch, width, height);
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
        spriteBatch.dispose();
        for (Crystal c : crystals)
            c.dispose();
        world.dispose();
        background.dispose();
    }
}
