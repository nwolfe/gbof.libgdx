package org.rnq.bindingoffenrir;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen extends ScreenAdapter {
    private final Stage stage;

    private final Texture img;

    public GameScreen() {
        stage = new Stage();
		img = new Texture("assets/badlogic.jpg");
		stage.addActor(new Actor() {
            @Override
            public void draw(Batch batch, float parentAlpha) {
                batch.draw(img, 0, 0);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0 ,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
}
