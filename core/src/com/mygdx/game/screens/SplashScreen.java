//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.images.Images;

public class SplashScreen implements Screen, InputProcessor {

    public static final int WIDTH = 1920, HEIGHT = 1080;

    public static final int NEWGAME = 2;
    public static final int LOADGAME = 1;
    public static final int EXIT = 0;

    public Application app;

    public static final int NUM_OPTIONS = 3;

    private boolean[] isTouched = new boolean[NUM_OPTIONS];

    private int[] x = new int[NUM_OPTIONS];
    private int[] y = new int[NUM_OPTIONS];

    private String[] options = new String[NUM_OPTIONS];

    private Rectangle[] options_rects = new Rectangle[NUM_OPTIONS];
    private Rectangle mouseRectangle = new Rectangle(0, 0, 3, 9);
    private int optionChoosed = -1;

    private BitmapFont font;
    private SpriteBatch spriteBatch = new SpriteBatch();

    private Camera camera = new OrthographicCamera(WIDTH, HEIGHT);
    private Viewport viewport = new ScreenViewport(camera);
    public static Music music;
    public static boolean runningMusic = true;
    private Sound shot;

    public SplashScreen(Application app){
        this.app = app;
        for(int index = EXIT; index <= NEWGAME; ++index) {
            this.x[index] = (1920/2) - 200;
            this.y[index] = 120 + 55 * index;
            this.options_rects[index] = new Rectangle(this.x[index], this.y[index] - 20, 360, 30);
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/Sunglasses2.mp3"));
        shot = Gdx.audio.newSound(Gdx.files.internal("sounds/gun shot.wav"));
        this.options[NEWGAME] = "NEW  GAME";
        this.options[LOADGAME] = "LOAD GAME";
        this.options[EXIT] = "     EXIT";

        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.5f);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        Level level = new Level(app);
        app.level = level;
        music.play();
        music.setLooping(true);

    }

    public void update() {
        Application.currentScreen = getClass().toString();

        camera.update();

        if (!runningMusic) {
            music.play();
            runningMusic = true;
        }
        int counter = 0;
        for(int index = EXIT; index <= NEWGAME; ++index) {
            if (this.mouseRectangle.overlaps(this.options_rects[index])) {
                this.isTouched[index] = true;
                this.optionChoosed = index;
            } else {
                this.isTouched[index] = false;
               ++counter;
            }
        }
        if (counter == 3) {
            counter = 0;
            this.optionChoosed = -1;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        Sprite sprite = new Sprite(Images.splashScreen);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.draw(spriteBatch);

//        font.setColor(Color.BLACK);
//        font.draw(spriteBatch, "BLUE DOME", (1920/2f - 200) + 2, (1080/2f) + 2);
//        font.setColor(Color.WHITE);
//        font.draw(spriteBatch, "blue dome", 1920/2f - 200, 1080/2f);
        for(int index = EXIT; index <= NEWGAME; ++index) {
//            font.setColor(Color.BLACK);
//            font.draw(spriteBatch, this.options[index], this.x[index] + 2, this.y[index] + 2);
            font.setColor(Color.WHITE);
            font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
            if (this.isTouched[index]) {
                font.setColor(Color.YELLOW);
                font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
            }
        }
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (this.optionChoosed) {
            case NEWGAME: {
                Level level = new Level(app);
                app.level = level;
                app.setScreen(level);
                music.pause();
                Gdx.input.setInputProcessor(level);
                runningMusic = false;
                break;
            }
            case LOADGAME:{
                app.setScreen(app.loadScreen);
                Gdx.input.setInputProcessor(app.loadScreen);
                break;
            }
            case EXIT: {
                System.exit(0);
                break;
            }
        }
        shot.play();
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
        // Suponha que você tenha uma câmera (por exemplo, OrthographicCamera) configurada
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        viewport.unproject(worldCoordinates);

        // Agora 'worldCoordinates' contém as coordenadas do mundo
        float worldX = worldCoordinates.x;
        float worldY = worldCoordinates.y;

        this.mouseRectangle.setPosition(worldX, worldY);
//        System.out.println(worldX + " " + worldY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }



}
