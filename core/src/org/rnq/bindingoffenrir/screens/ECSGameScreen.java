package org.rnq.bindingoffenrir.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.LevelManager;
import org.rnq.bindingoffenrir.systems.RenderingSystem;

public class ECSGameScreen extends ScreenAdapter {
    private final ScreenManager screenManager;
    private final LevelManager levelManager;
    private final World world;
    private final PooledEngine engine;
    private final SpriteBatch batch;
    private final Viewport viewport;

    public ECSGameScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        world = new World(new Vector2(0, Constants.GRAVITY), true);
        // world.setContactListener();
        batch = new SpriteBatch();
        levelManager = new LevelManager();
        engine = new PooledEngine();
        RenderingSystem renderingSystem = new RenderingSystem(batch, levelManager);
        viewport = new FitViewport(Constants.GAME_WIDTH_IN_TILES,
                Constants.GAME_HEIGHT_IN_TILES, renderingSystem.camera);

        engine.addSystem(renderingSystem);

        levelManager.currentLevel().build(engine, world);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            screenManager.goToPauseScreen();
    }

    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        levelManager.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(null);
    }
}
