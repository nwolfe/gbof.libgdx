package org.rnq.bindingoffenrir;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen extends ScreenAdapter {
    private final Stage stage;
    private final MapRenderer levelRenderer;

    GameScreen() {
        stage = new Stage();
		stage.addActor(new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(Assets.instance.sampleBgImg, 0, 0);
            }
        });

//        OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
//        camera.setToOrtho(false, 16, 15);
        levelRenderer = new OrthogonalTiledMapRenderer(
                Assets.instance.sampleLevel, 4);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0 ,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        levelRenderer.setView((OrthographicCamera) stage.getCamera());
        levelRenderer.render();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
    }
}
