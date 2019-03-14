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
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.components.PhysicsComponent;
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

        // Convert the position from pixels -> meters now so it's
        // easier to sync with Box2D, which works in meters, later
        float width = texture.region.getRegionWidth() * Constants.PIXELS_TO_METERS;
        float height = texture.region.getRegionHeight() * Constants.PIXELS_TO_METERS;

        // Cut in half since we need to convert from center-based position to
        // corner-based position. Assume the player art is twice as wide as
        // everything else so cut it in half again.
        width /= 4f;
        height /= 2f;

        // TODO Animation

        TransformComponent transform = new TransformComponent();
        Rectangle r = ((RectangleMapObject) object).getRectangle();
        float x = (r.x * Constants.PIXELS_TO_METERS) + width;
        float y = (r.y * Constants.PIXELS_TO_METERS) + height;
        transform.position.set(x, y, 1f);
        player.add(transform);

        PhysicsComponent physics = new PhysicsComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        physics.body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(width, height);
        physics.body.createFixture(box, 1f);
        box.dispose();
        player.add(physics);

        engine.addEntity(player);
    }
}