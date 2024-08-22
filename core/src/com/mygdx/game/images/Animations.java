package com.mygdx.game.images;

import com.badlogic.gdx.graphics.Color;

public enum Animations {

    //for player
    WALKING(new Animator(30, 10, 40, 400, 300, "stickman/Stickman_walking.png")),
    IDLE(new Animator( 26, 10, 20, 400, 300, "stickman/Idle.png")),
    PUNCH(new Animator(6, 6, 10, 400, 300, "stickman/Stickman_punching.png")),
    JUMPING(new Animator(10, 10, 5, 400, 300, "stickman/Stickman_Jumping.png")),
    HIYAH(new Animator(14, 10, 30, 400, 300, "stickman/Stickman_Jumping.png")),
    PUNCHED(new Animator(5, 5, 10, 400, 300, "stickman/Stickman_punched.png")),
    SHOT(new Animator(6, 6, 5, 400, 300, "stickman/Shot.png")),
    //for enemies
    E_WALKING(new Animator(Color.RED,30, 10, 40, 400, 300, "stickman/Stickman_walking.png")),
    E_IDLE(new Animator(Color.RED, 26, 10, 5, 400, 300, "stickman/Idle.png")),
    E_PUNCH(new Animator(Color.RED,6, 6, 10, 400, 300, "stickman/Stickman_punching.png")),
    E_JUMPING(new Animator(Color.RED,10, 10, 5, 400, 300, "stickman/Stickman_Jumping.png")),
    E_HIYAH(new Animator(Color.RED,14, 10, 30, 400, 300, "stickman/Stickman_Jumping.png")),
    E_PUNCHED(new Animator(Color.RED, 5, 5, 5, 400, 300, "stickman/Stickman_punched.png")),;

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
