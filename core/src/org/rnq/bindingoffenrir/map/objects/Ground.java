package org.rnq.bindingoffenrir.map.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.CollisionComponent;
import org.rnq.bindingoffenrir.components.PhysicsComponent;
import org.rnq.bindingoffenrir.components.TypeComponent;

public class Ground implements ObjectBuilder {
    @Override
    public boolean canBuild(MapObject object) {
        return "ground".equals(object.getName());
    }

    @Override
    public void build(MapObject object, PooledEngine engine, World world) {
        Entity ground = engine.createEntity();
        Rectangle r = ((RectangleMapObject) object).getRectangle();

        // Convert size and position from pixels -> meters now so it's
        // easier to sync with Box2D later, which works in meters
        float width = (r.getWidth() * Constants.PIXELS_TO_METERS) / 2f;
        float height = (r.getHeight() * Constants.PIXELS_TO_METERS) / 2f;
        float x = (r.x * Constants.PIXELS_TO_METERS) + width;
        float y = (r.y * Constants.PIXELS_TO_METERS) + height;

        PhysicsComponent physics = new PhysicsComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        physics.body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(width, height);
        physics.body.createFixture(box, 1f);
        box.dispose();
        ground.add(physics);
        physics.body.setUserData(ground);

        CollisionComponent collision = new CollisionComponent();
        ground.add(collision);

        TypeComponent type = new TypeComponent();
        type.type = TypeComponent.Type.GROUND;
        ground.add(type);

        engine.addEntity(ground);
    }
}
