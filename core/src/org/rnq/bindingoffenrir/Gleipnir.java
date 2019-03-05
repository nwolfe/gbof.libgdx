package org.rnq.bindingoffenrir;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;


public class Gleipnir extends Game {
    private Screen gameScreen;

	@Override
	public void create () {
	    Assets.load();
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	@Override
	public void dispose() {
	    gameScreen.dispose();
	    Assets.dispose();
	}
}
