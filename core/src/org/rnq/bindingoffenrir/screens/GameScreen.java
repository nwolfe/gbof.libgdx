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

public class GameScreen extends ScreenAdapter {
    private final Gleipnir game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final MapRenderer levelRenderer;

    public GameScreen(Gleipnir game) {
        this.game = game;
        camera = new OrthographicCamera(
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(new ScreenViewport(camera));
//		stage.addActor(new Actor() {
//            @Override
//            public void draw(Batch batch, float parentAlpha) {
//                batch.draw(Assets.instance.sampleBgImg.get(), 0, 0);
//            }
//        });

//        OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
//        camera.setToOrtho(false, 16, 15);
        levelRenderer = new OrthogonalTiledMapRenderer(
                Assets.instance.sampleLevel.get(), 4);
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
