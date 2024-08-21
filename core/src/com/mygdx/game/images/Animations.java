package com.mygdx.game.images;

import com.badlogic.gdx.graphics.Color;

public enum Animations {

    WALKING(new Animator(30, 10, 40, 400, 300, "stickman/Stickman_walking.png")),
    IDLE(new Animator( 26, 10, 20, 400, 300, "stickman/Idle.png")),
    PUNCH(new Animator(6, 6, 10, 400, 300, "stickman/Stickman_punching.png")),
    JUMPING(new Animator(10, 10, 5, 400, 300, "stickman/Stickman_Jumping.png")),
    HIYAH(new Animator(14, 10, 30, 400, 300, "stickman/Stickman_Jumping.png"));

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
