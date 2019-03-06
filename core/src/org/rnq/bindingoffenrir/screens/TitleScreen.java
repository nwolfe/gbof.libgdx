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
import org.rnq.bindingoffenrir.Gleipnir;

public class TitleScreen extends ScreenAdapter {
    private final Gleipnir game;
    private final Stage stage;

    public TitleScreen(Gleipnir game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        title();
        playButton();
        settingsButton();
        exitButton();
    }

    private void title() {
        Label title = new Label("G L E I P N I R", Assets.instance.uiSkin.get());
        title.setAlignment(Align.center);
        title.setY((float) Gdx.graphics.getHeight() * 8/10);
        title.setWidth(Gdx.graphics.getWidth());
        title.setFontScale(4);
        stage.addActor(title);

        Label subtitle = new Label("Binding of Fenrir", Assets.instance.uiSkin.get());
        subtitle.setAlignment(Align.center);
        subtitle.setY((float) Gdx.graphics.getHeight() * 7/10);
        subtitle.setWidth(Gdx.graphics.getWidth());
        subtitle.setFontScale(2);
        stage.addActor(subtitle);
    }

    private void playButton() {
        TextButton play = new TextButton("Play", Assets.instance.uiSkin.get());
        play.setWidth((float) Gdx.graphics.getWidth() / 2);
        play.setPosition(
                (float) Gdx.graphics.getWidth() / 2 - play.getWidth() / 2,
                (float) Gdx.graphics.getHeight() / 2 - play.getHeight() / 2);
        play.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.gameScreen);
            }
        });
        stage.addActor(play);
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
                game.setScreen(game.settingsScreen);
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
            Gdx.app.exit();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
