package org.rnq.bindingoffenrir.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.rnq.bindingoffenrir.Assets;

public class PauseScreen extends ScreenAdapter {
    private final ScreenManager screenManager;
    private final Stage stage;

    public PauseScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        stage = new Stage(new ScreenViewport());
        pauseText();
        continueButton();
        settingsButton();
        exitButton();
    }

    private void pauseText() {
        Label paused = new Label("P a u s e d", Assets.instance.uiSkin.get());
        paused.setAlignment(Align.center);
        paused.setY((float) Gdx.graphics.getHeight() * 8/10);
        paused.setWidth(Gdx.graphics.getWidth());
        paused.setFontScale(4);
        stage.addActor(paused);
    }

    private void continueButton() {
        TextButton button = new TextButton("Continue", Assets.instance.uiSkin.get());
        button.setWidth((float) Gdx.graphics.getWidth() / 2);
        button.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - button.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.backToPreviousScreen();
            }
        });
        stage.addActor(button);
    }

    private void settingsButton() {
        TextButton settings = new TextButton("Settings", Assets.instance.uiSkin.get());
        settings.setWidth((float) Gdx.graphics.getWidth() / 2);
        settings.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - settings.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 3 - settings.getHeight() / 2);
        settings.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.goToSettingsScreen();
            }
        });
        stage.addActor(settings);
    }

    private void exitButton() {
        TextButton exit = new TextButton("Exit", Assets.instance.uiSkin.get());
        exit.setWidth((float) Gdx.graphics.getWidth() / 2);
        exit.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - exit.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 6 - exit.getHeight() / 2);
        exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0 ,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            screenManager.backToPreviousScreen();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
