package org.rnq.bindingoffenrir;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import org.rnq.bindingoffenrir.screens.*;


public class Gleipnir extends Game {
	public Screen titleScreen;
    public Screen gameScreen;
    public _LinkedScreen settingsScreen;
    public _LinkedScreen pauseScreen;
    private Screen previous;

	@Override
	public void create () {
	    Assets.load();
		gameScreen = new GameScreen(this);
		titleScreen = new TitleScreen(this);
		settingsScreen = new _LinkedScreen(new SettingsScreen(this));
		pauseScreen = new _LinkedScreen(new PauseScreen(this));
		setScreen(titleScreen);
	}

	@Override
	public void setScreen(Screen screen) {
	    // Store the back link on the screen to build a chain,
		// for cases like: Game -> Pause -> Settings
		if (screen instanceof _LinkedScreen)
			((_LinkedScreen) screen).previous = getScreen();
        super.setScreen(screen);
	}

	public void backToPreviousScreen() {
		if (screen instanceof _LinkedScreen)
			super.setScreen(((_LinkedScreen) screen).previous);
	}

	@Override
	public void dispose() {
	    gameScreen.dispose();
	    titleScreen.dispose();
	    settingsScreen.dispose();
	    pauseScreen.dispose();
	    Assets.dispose();
	}

	// Decorator Pattern
	private static class _LinkedScreen implements Screen {
	    private final Screen wrapped;
	    private Screen previous;

	    private _LinkedScreen(Screen decorated) {
	    	wrapped = decorated;
		}

		@Override
		public void show() {
	        wrapped.show();
		}

		@Override
		public void render(float delta) {
	    	wrapped.render(delta);
		}

		@Override
		public void resize(int width, int height) {
	        wrapped.resize(width, height);
		}

		@Override
		public void pause() {
	        wrapped.pause();
		}

		@Override
		public void resume() {
	        wrapped.resume();
		}

		@Override
		public void hide() {
	        wrapped.hide();
		}

		@Override
		public void dispose() {
	        wrapped.dispose();
		}
	}
}
