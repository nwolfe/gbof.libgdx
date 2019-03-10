package org.rnq.bindingoffenrir.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import org.rnq.bindingoffenrir.Gleipnir;

public class ScreenManager {
    private final Screen titleScreen;
    private final Screen gameScreen;
    private final LinkedScreen settingsScreen;
    private final LinkedScreen pauseScreen;
    private final Game game;

    public ScreenManager(Gleipnir game) {
        this.game = game;
        titleScreen = new TitleScreen(this);
//        gameScreen = new ECSGameScreen(this);
        gameScreen = new GameScreen(this);
        settingsScreen = new LinkedScreen(new SettingsScreen(this));
        pauseScreen = new LinkedScreen(new PauseScreen(this));
    }

    private void setScreen(Screen screen) {
        // Store the back link on the screen to build a chain,
        // for cases like: Game -> Pause -> Settings
        if (screen instanceof LinkedScreen)
            ((LinkedScreen) screen).previous = game.getScreen();
        game.setScreen(screen);
    }

    void backToPreviousScreen() {
        if (game.getScreen() instanceof LinkedScreen)
            game.setScreen(((LinkedScreen) game.getScreen()).previous);
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
    private static class LinkedScreen implements Screen {
        private final Screen wrapped;
        private Screen previous;

        private LinkedScreen(Screen decorated) {
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
