package com.mygdx.game.images;

import com.badlogic.gdx.graphics.Color;

public enum Animations {

    //player
    FIRST(new Animator(10,10, 25, 400, 300,"stickman/First.png")),
    WALKING(new Animator(30, 10, 20, 400, 300, "stickman/Stickman_walking.png")),
    IDLE(new Animator( 26, 10, 10, 400, 300, "stickman/Idle.png")),
    PUNCH(new Animator(6, 6, 10, 400, 300, "stickman/Stickman_punching.png")),
    JUMPING(new Animator(10, 10, 20, 400, 300, "stickman/Stickman_Jumping.png")),
    HIYAH(new Animator(14, 10, 30, 400, 300, "stickman/Stickman_Jumping.png")),
    PUNCHED(new Animator(5, 5, 10, 400, 300, "stickman/Stickman_punched.png")),
    SHOT(new Animator(6, 6, 5, 400, 300, "stickman/Shot.png")),
    HITED(new Animator(1, 1, 1, 400, 300, "stickman/Stickman_baseball_hited.png")),
    IDLE_FLASH(new Animator(0.1f, 26, 10, 5, 400, 300, "stickman/Idle3.png")),
    SABER(new Animator(6,6,12,400,300, "stickman/SabreHit.png")),

    //enemy
    E_WALKING(new Animator(Color.RED,30, 10, 40, 400, 300, "stickman/Stickman_walking.png")),
    E_IDLE(new Animator(Color.RED, 26, 10, 1, 400, 300, "stickman/Idle.png")),
    E_SPLIT(new Animator(Color.RED, 4, 4, 3, 400, 300, "stickman/Split.png")),
    E_PUNCH(new Animator(Color.RED,6, 6, 10, 400, 300, "stickman/Stickman_punching.png")),
    E_JUMPING(new Animator(Color.RED,10, 10, 5, 400, 300, "stickman/Stickman_Jumping.png")),
    E_HIYAH(new Animator(Color.RED,14, 10, 30, 400, 300, "stickman/Stickman_Jumping.png")),
    E_PUNCHED(new Animator(Color.RED, 5, 5, 5, 400, 300, "stickman/Stickman_punched.png")),
    E_HITED(new Animator(Color.RED,1, 1, 1, 400, 300, "stickman/Stickman_baseball_hited.png"));

    public Animator animator;

    Animations(Animator animator){
        this.animator = animator;
    }

    public Animator getAnimator() {
        return animator;
    }

    public Animations getAnimation(String name){
        return valueOf(name);
    }
}
