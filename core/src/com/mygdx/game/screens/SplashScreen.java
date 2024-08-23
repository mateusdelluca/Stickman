//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.images.Images;
import com.mygdx.game.principal.Application;

public class SplashScreen implements Screen, InputProcessor {

    public static final int WIDTH = 1920, HEIGHT = 1080;

    public static final int NEWGAME = 0;
    public static final int LOADGAME = 1;
    public static final int EXIT = 2;

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

    public SplashScreen(Application app){
        this.app = app;
        for(int index = 0; index < 3; ++index) {
            this.x[index] = 850;
            this.y[index] = 20 + 35 * index;
            this.options_rects[index] = new Rectangle(this.x[index], this.y[index] - 27, 160, 30);
        }

        this.options[0] = "NEW GAME";
        this.options[1] = "LOAD GAME";
        this.options[2] = "EXIT";

        Texture t = new Texture(Gdx.files.internal("Font.png"));
        font = new BitmapFont(Gdx.files.internal("Font.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(1f);
    }

    public void update() {
        int counter = 0;
        for(int index = 0; index < 3; ++index) {
            if (Intersector.overlaps(this.mouseRectangle, this.options_rects[index])) {
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
        spriteBatch.begin();
        Sprite sprite = new Sprite(Images.splashScreen);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.draw(spriteBatch);

        for(int index = 0; index < 3; ++index) {
            font.draw(spriteBatch, this.options[index], this.x[index] + 2, this.y[index] + 2);
            font.setColor(Color.YELLOW);
            font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
            if (this.isTouched[index]) {
                font.setColor(Color.RED);
                font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
            }
        }
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

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
//        app.setScreen(new Level(app));
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


//    public void mouseMoved(MouseEvent e) {
//        this.mouseRectangle.setLocation(e.getX(), e.getY());
//    }
//
//    public void mouseClicked(MouseEvent e) {
//        Sounds.GUN_SHOT.play2();
//        switch (this.optionChoosed) {
//            case NEWGAME:
//                running = true;
//                Sounds.MENU.getClip().stop();
//                Sounds.ELETRIC_WHOOSH.play2();
//                Level_Manager.running = true;
//                Stage_Manager.currentStage = StageEnum.valueOf("LEVEL_MANAGER");
//                if (Stage_Manager.oldStage.equals("LEVEL_MANAGER")) {
//                    Stage_Manager.oldStage = "MENU";
//                }
//                break;
//            case LOADGAME:
//
//                Level_Manager.running = true;
//                Sounds.PUNCH.play2();
//                Stage_Manager.currentStage = StageEnum.valueOf("LOAD_PAGE");
//                break;
//            case EXIT:
//                System.exit(0);
//        }
}
