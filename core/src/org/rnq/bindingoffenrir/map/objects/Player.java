package org.rnq.bindingoffenrir.map.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.Assets;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.*;

public class Player implements ObjectBuilder {
    @Override
    public boolean canBuild(MapObject object) {
        return "player".equals(object.getName());
    }

    @Override
    public void build(MapObject object, PooledEngine engine, World world) {
        Entity player = engine.createEntity();
        player.add(new PlayerComponent());

        TextureComponent texture = new TextureComponent();
        texture.region = Assets.instance.playerIdleStrip.getFrames()[0];
        player.add(texture);

        // Convert the position from pixels -> meters now so it's
        // easier to sync with Box2D later, which works in meters
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
        physics.body.setUserData(player);

        CollisionComponent collision = new CollisionComponent();
        player.add(collision);

        TypeComponent type = new TypeComponent();
        type.type = TypeComponent.Type.PLAYER;
        player.add(type);

        engine.addEntity(player);
    }
}
