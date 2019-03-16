package org.rnq.bindingoffenrir.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import org.rnq.bindingoffenrir.Gleipnir;

public class ScreenManager {
    private final Screen titleScreen;
    private final Screen gameScreen;
    private final PreviousScreen settingsScreen;
    private final PreviousScreen pauseScreen;
    private final Game game;

    public ScreenManager(Gleipnir game) {
        this.game = game;
        titleScreen = new TitleScreen(this);
        gameScreen = new GameScreen(this);
        settingsScreen = new PreviousScreen(new SettingsScreen(this));
        pauseScreen = new PreviousScreen(new PauseScreen(this));
    }

    private void setScreen(Screen screen) {
        // Store the back link on the screen to build a chain,
        // for cases like: Game -> Pause -> Settings
        if (screen instanceof PreviousScreen)
            ((PreviousScreen) screen).previous = game.getScreen();
        game.setScreen(screen);
    }

    void backToPreviousScreen() {
        Screen current = game.getScreen();
        if (current instanceof PreviousScreen)
            game.setScreen(((PreviousScreen) current).previous);
    }

    public void dispose() {
        titleScreen.dispose();
        gameScreen.dispose();
        settingsScreen.dispose();
        pauseScreen.dispose();
    }

    public void goToTitleScreen() {
        setScreen(titleScreen);
    }

    public void goToGameScreen() {
        setScreen(gameScreen);
    }

    public void goToSettingsScreen() {
        setScreen(settingsScreen);
    }

    public void goToPauseScreen() {
        setScreen(pauseScreen);
    }

    // Decorator Pattern
    private static class PreviousScreen implements Screen {
        private final Screen decorated;
        private Screen previous;

        private PreviousScreen(Screen decorated) {
            this.decorated = decorated;
        }

        @Override
        public void show() {
            decorated.show();
        }

        @Override
        public void render(float delta) {
            decorated.render(delta);
        }

        @Override
        public void resize(int width, int height) {
            decorated.resize(width, height);
        }

        @Override
        public void pause() {
            decorated.pause();
        }

        @Override
        public void resume() {
            decorated.resume();
        }

        @Override
        public void hide() {
            decorated.hide();
        }

        @Override
        public void dispose() {
            decorated.dispose();
        }
    }
}
