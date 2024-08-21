//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mygdx.game.stages;

//import graphics.Graphics;
//import manager.Level_Manager;
//import manager.Stage_Manager;
//import sfx.Sounds;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Menu extends StageScreen {
    private Font kristenITC = new Font("Kristen ITC", 0, 28);
    private Font calibri = new Font("Calibri", 0, 28);
    private Font copperplate = new Font("Copperplate Gothic Bold", Font.PLAIN, 72);
    private Color yellow = new Color(255, 220, 0);
    public static final int numberOfOptions = 3;
    private boolean[] isTouched = new boolean[3];
    private int[] x = new int[3];
    private int[] y = new int[3];
    private String[] options = new String[3];
    private Rectangle mouseRectangle = new Rectangle(0, 0, 3, 9);
    private Rectangle[] options_rects = new Rectangle[3];
    private int optionChoosed = -1;
    public static final int NEWGAME = 0;
    public static final int LOADGAME = 1;
    public static final int EXIT = 2;

    public static boolean running;

    public Menu() {
        for(int index = 0; index < 3; ++index) {
            this.x[index] = 850;
            this.y[index] = 520 + 35 * index;
            this.options_rects[index] = new Rectangle(this.x[index], this.y[index] - 27, 160, 30);
        }

        this.options[0] = "NEW GAME";
        this.options[1] = "LOAD GAME";
        this.options[2] = "EXIT";
//        Sounds.MENU.play();
    }

    public void update() {
        int counter = 0;

        for(int index = 0; index < 3; ++index) {
            if (this.mouseRectangle.intersects(this.options_rects[index])) {
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

    public void render(Graphics2D g2D) {
//        g2D.drawImage(Graphics.menu, 0, 0, 1080, 720, (ImageObserver)null);


        g2D.setColor(Color.BLACK);
        g2D.setFont(copperplate);
        g2D.drawString("STICKMEN RAGE", 200, 200);
        g2D.setColor(Color.WHITE);
        g2D.drawString("STICKMEN RAGE", 202, 202);

        g2D.setFont(this.calibri);
        for(int index = 0; index < 3; ++index) {
            g2D.setColor(Color.BLACK);
            g2D.drawString(this.options[index], this.x[index] + 2, this.y[index] + 2);
            g2D.setColor(this.yellow);
            g2D.drawString(this.options[index], this.x[index], this.y[index]);
            if (this.isTouched[index]) {
                g2D.setColor(Color.WHITE);
                g2D.drawString(this.options[index], this.x[index], this.y[index]);
            }
        }

    }


    public void mouseMoved(MouseEvent e) {
        this.mouseRectangle.setLocation(e.getX(), e.getY());
    }

    public void mouseClicked(MouseEvent e) {
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

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    public Rectangle getMouseRectangle() {
        return this.mouseRectangle;
    }

    public void setMouseRectangle(Rectangle mouseRectangle) {
        this.mouseRectangle = mouseRectangle;
    }
}
