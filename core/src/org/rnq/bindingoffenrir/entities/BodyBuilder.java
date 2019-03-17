package org.rnq.bindingoffenrir.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.components.PhysicsComponent;

public final class BodyBuilder {
    private final EntityBuilder entityBuilder;
    private final PhysicsComponent component;
    private final BodyDef bodyDef;

    private float width;
    private float height;
    private float density;
    private PolygonShape box;
    private Object userData;

    BodyBuilder(EntityBuilder entityBuilder, PhysicsComponent component) {
        this.entityBuilder = entityBuilder;
        this.component = component;
        bodyDef = new BodyDef();
        width = 0f;
        height = 0f;
        density = 0f;
        Gdx.app.log("body", "begin box2d body construction");
    }

    public BodyBuilder type(BodyDef.BodyType type) {
        Gdx.app.log("body", "type " + type);
        bodyDef.type = type;
        return this;
    }

    public BodyBuilder position(float x, float y) {
        Gdx.app.log("body", "position: " + x + ", " + y);
        bodyDef.position.set(x, y);
        return this;
    }

    public BodyBuilder dimensions(float width, float height) {
        Gdx.app.log("body", "dimensions: " + width + " x " + height);
        this.width = width;
        this.height = height;
        return this;
    }

    public BodyBuilder box() {
        Gdx.app.log("body", "box shape");
        box = new PolygonShape();
        box.setAsBox(width, height);
        return this;
    }

    /**
     * NOTE set density to 0f for Static bodies.
     */
    public BodyBuilder density(float density) {
        Gdx.app.log("body", "density " + density);
        this.density = density;
        return this;
    }

    public BodyBuilder userData(Object userData) {
        Gdx.app.log("body", "user data " + userData);
        this.userData = userData;
        return this;
    }

    public EntityBuilder physicsEnd(World world) {
        Gdx.app.log("body", "end box2d body construction");
        Body body = world.createBody(bodyDef);
        if (box != null) {
            body.createFixture(box, density);
            box.dispose();
            box = null;
        }
        body.setUserData(userData);
        entityBuilder.bodyBuildFinished(body, component);
        return entityBuilder;
    }
}
