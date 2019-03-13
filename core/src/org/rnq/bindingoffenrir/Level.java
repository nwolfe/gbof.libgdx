package org.rnq.bindingoffenrir;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.components.PlayerComponent;
import org.rnq.bindingoffenrir.components.TextureComponent;
import org.rnq.bindingoffenrir.components.TransformComponent;

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
        for (MapLayer layer : map.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                if ("player".equals(object.getName()))
                    buildPlayer(object, engine, world);
            }
        }
    }

    private void buildPlayer(MapObject object, PooledEngine engine, World world) {
        Entity player = engine.createEntity();
        player.add(new PlayerComponent());

        TextureComponent texture = new TextureComponent();
        texture.region = Assets.instance.playerIdleStrip.getFrames()[0];
        player.add(texture);

        // TODO Animation

        TransformComponent transform = new TransformComponent();
        Rectangle r = ((RectangleMapObject) object).getRectangle();
        transform.position.set(r.x, r.y, 1f);
        player.add(transform);

//        PhysicsComponent physics = new PhysicsComponent();
//        float width = texture.region.getRegionWidth();
//        float height = texture.region.getRegionHeight();
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(r.x + width / 2f, r.y + height / 2f);
//        physics.body = world.createBody(bodyDef);
//        PolygonShape box = new PolygonShape();
//        // Assume the player art is twice as wide as
//        // everything else and scale it down accordingly
//        box.setAsBox(width / 4, height / 2);
//        physics.body.createFixture(box, 1f);
//        box.dispose();
//        player.add(physics);
//        System.out.printf("building: %s, %s\n", bodyDef.position.x, bodyDef.position.y);

        engine.addEntity(player);
    }
}
