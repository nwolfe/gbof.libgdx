package org.rnq.bindingoffenrir.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.Gleipnir;
import org.rnq.bindingoffenrir.systems.RenderingSystem;

public class ECSGameScreen extends ScreenAdapter {
    private final Gleipnir game;
    private final World world;
    private final PooledEngine engine;
    private final SpriteBatch batch;

    public ECSGameScreen(Gleipnir game) {
        this.game = game;
        world = new World(new Vector2(0, -9.8f), true);
        // world.setContactListener();
        batch = new SpriteBatch();
        engine = new PooledEngine();
        RenderingSystem renderingSystem = new RenderingSystem(batch);
        engine.addSystem(renderingSystem);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            game.setScreen(game.pauseScreen);
    }

    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
    }
}
