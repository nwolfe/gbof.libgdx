package org.rnq.bindingoffenrir.map;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.map.objects.ObjectFactory;

public class Level {
    private final TiledMap map;
    private final MapRenderer renderer;

    public Level(TiledMap map) {
        this.map = map;
        renderer = new OrthogonalTiledMapRenderer(map, Constants.PIXELS_TO_METERS);
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public void dispose() {
        map.dispose();
    }

    public void build(PooledEngine engine, World world) {
        for (MapLayer layer : map.getLayers())
            for (MapObject object : layer.getObjects())
                buildObject(object, engine, world);
    }
    
    private void buildObject(MapObject object, PooledEngine engine, World world) {
        for (ObjectFactory factory : ObjectFactory.all) {
            if (factory.canBuild(object)) {
                factory.build(object, engine, world);
                return;
            }
        }
        Gdx.app.log("TODO", "Map object not being built: " + object.getName());
    }
}
