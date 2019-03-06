package org.rnq.bindingoffenrir.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.rnq.bindingoffenrir.Assets;
import org.rnq.bindingoffenrir.Gleipnir;
import org.rnq.bindingoffenrir.Player;

public class GameScreen extends ScreenAdapter {
    private final Gleipnir game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final MapRenderer levelRenderer;

    public GameScreen(Gleipnir game) {
        this.game = game;
        camera = new OrthographicCamera(
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Don't scale any art in code; make them all the same
        // in the art editor. Then, configure the viewport to
        // apply a uniform scaling when rendering them.
        ScreenViewport viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(1 / 4f);

        stage = new Stage(viewport);
        stage.addActor(new Player());

//        camera.setToOrtho(false, 16, 15);
        levelRenderer = new OrthogonalTiledMapRenderer(
                Assets.instance.sampleLevel.get());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0 ,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        camera.update();
        levelRenderer.setView(camera);
        levelRenderer.render();
        stage.draw();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            game.setScreen(game.pauseScreen);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
}
