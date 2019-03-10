package org.rnq.bindingoffenrir;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Level {
    private final TiledMap map;
    private final MapRenderer renderer;

    public Level(TiledMap map) {
        this.map = map;
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        map.dispose();
    }
}
