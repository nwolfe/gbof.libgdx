package org.rnq.bindingoffenrir;

import com.badlogic.gdx.Game;
import org.rnq.bindingoffenrir.screens.ScreenManager;


public class Gleipnir extends Game {
    private ScreenManager screenManager;

	@Override
	public void create () {
	    Assets.load();
	    screenManager = new ScreenManager(this);
	    screenManager.goToTitleScreen();
	}

	@Override
	public void dispose() {
	    screenManager.dispose();
	    Assets.dispose();
	}
}
