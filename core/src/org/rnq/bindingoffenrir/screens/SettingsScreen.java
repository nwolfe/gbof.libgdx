package org.rnq.bindingoffenrir.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.rnq.bindingoffenrir.Assets;

public class SettingsScreen extends ScreenAdapter {
    private final ScreenManager screenManager;
    private final Stage stage;

    public SettingsScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        stage = new Stage(new ScreenViewport());
        backButton();
    }

    private void backButton() {
        TextButton back = new TextButton("Back", Assets.instance.uiSkin.get());
        back.setWidth((float) Gdx.graphics.getWidth() / 2);
        back.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - back.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - back.getHeight() / 2);
        back.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.backToPreviousScreen();
            }
        });
        stage.addActor(back);
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
