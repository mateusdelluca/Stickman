package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.principal.Application;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {

	public static final int WIDTH = 1920, HEIGHT = 1080;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Stickman");
		config.setWindowedMode(WIDTH, HEIGHT);
		config.setResizable(false);
//		config.setMaximized(true);
		new Lwjgl3Application(new Application(), config);
	}
}
