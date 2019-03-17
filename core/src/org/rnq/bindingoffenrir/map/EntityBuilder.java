package org.rnq.bindingoffenrir.map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.*;

public class EntityBuilder {
    private final Entity entity;
    private final PooledEngine engine;

    private float scaleWidth;

    public EntityBuilder(PooledEngine engine) {
        entity = engine.createEntity();
        this.engine = engine;
        scaleWidth = 1f;
        Gdx.app.log("entity", "begin entity construction");
    }

    public void build() {
        Gdx.app.log("entity", "end entity construction");
        engine.addEntity(entity);
    }

    public EntityBuilder player() {
        Gdx.app.log("entity", "mark as player");
        entity.add(new PlayerComponent());
        return this;
    }

    public EntityBuilder type(TypeComponent.Type type) {
        Gdx.app.log("entity", "set type " + type);
        TypeComponent t = new TypeComponent();
        t.type = type;
        entity.add(t);
        return this;
    }

    public EntityBuilder state(String state, boolean isLooping) {
        Gdx.app.log("entity", "set state " + state);
        StateComponent s = new StateComponent();
        s.set(state);
        s.isLooping = isLooping;
        entity.add(s);
        return this;
    }

    public EntityBuilder animation(String state, Animation<TextureRegion> animation) {
        Gdx.app.log("entity", "set animation for " + state);
        AnimationComponent a = entity.getComponent(AnimationComponent.class);
        if (a == null) {
            a = new AnimationComponent();
            entity.add(a);
        }
        a.animations.put(state, animation);
        return this;
    }

    public EntityBuilder texture(TextureRegion region) {
        Gdx.app.log("entity", "set texture region");
        TextureComponent t = new TextureComponent();
        t.region = region;
        entity.add(t);
        return this;
    }

    public EntityBuilder collision() {
        Gdx.app.log("entity", "add collision");
        entity.add(new CollisionComponent());
        return this;
    }

    public EntityBuilder input() {
        Gdx.app.log("entity", "add input");
        entity.add(new InputComponent());
        return this;
    }

    public EntityBuilder velocity() {
        Gdx.app.log("entity", "add velocity");
        entity.add(new VelocityComponent());
        return this;
    }

    /**
     * Assumes {@link #texture(TextureRegion)} has been called as
     * this will attempt to use that texture for width/height values.
     */
    public EntityBuilder transform(RectangleMapObject object, float renderPriority) {
        Gdx.app.log("entity", "set transform from texture");
        TransformComponent t = new TransformComponent();
        float x = object.getRectangle().x * Constants.PIXELS_TO_METERS;
        float y = object.getRectangle().y * Constants.PIXELS_TO_METERS;
        Vector2 dimensions = getDimensionsFromTexture();
        x += dimensions.x; // width
        y += dimensions.y; // height
        t.position.set(x, y, renderPriority);
        entity.add(t);
        return this;
    }

    /**
     * Assumes {@link #texture(TextureRegion)} and
     * {@link #transform(RectangleMapObject, float)}
     * have been called.
     *
     * NOTE set density to 0f for static bodies.
     */
     // TODO extract physics* methods out into their own builder
     // TODO and provide methods to construct new builders on this class.
    public EntityBuilder physicsFromTextureTransform(BodyDef.BodyType type,
                                                     float density, World world)
    {
        Gdx.app.log("entity", "set physics from texture and transform");
        PhysicsComponent p = new PhysicsComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        Vector3 position = entity.getComponent(TransformComponent.class).position;
        bodyDef.position.set(position.x, position.y);
        p.body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        Vector2 dimensions = getDimensionsFromTexture();
        box.setAsBox(dimensions.x, dimensions.y);
        p.body.createFixture(box, density);
        box.dispose();
        p.body.setUserData(entity);
        entity.add(p);
        return this;
    }

    /**
     * NOTE set density to 0f for static bodies.
     */
    // TODO extract physics* methods out into their own builder
    // TODO and provide methods to construct new builders on this class.
    public EntityBuilder physics(RectangleMapObject object, BodyDef.BodyType type,
                                 float density, World world)
    {
        Gdx.app.log("entity", "set physics from rectangle object");
        Rectangle r = object.getRectangle();
        Vector2 scaled = applyTransformations(r.getWidth(), r.getHeight());
        float width = scaled.x;
        float height = scaled.y;
        float x = (r.x * Constants.PIXELS_TO_METERS) + width;
        float y = (r.y * Constants.PIXELS_TO_METERS) + height;
        PhysicsComponent p = new PhysicsComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x, y);
        p.body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(width, height);
        p.body.createFixture(box, density);
        box.dispose();
        p.body.setUserData(entity);
        entity.add(p);
        return this;
    }

    /**
     * Additional width scaling can be applied, such as when the
     * player image happens to be wider than everything else.
     */
    public EntityBuilder setWidthScaling(float scaleWidth) {
        Gdx.app.log("entity", "set additional width scaling to " + scaleWidth);
        this.scaleWidth = scaleWidth;
        return this;
    }

    /**
     * @return (width, height) as Vector(x, y) with additional scaling applied
     */
    private Vector2 getDimensionsFromTexture() {
        TextureRegion region = entity.getComponent(TextureComponent.class).region;
        return applyTransformations(region.getRegionWidth(), region.getRegionHeight());
    }

    /**
     * @return (width, height) as Vector(x, y) with scaling/PPM transformations applied
     */
    private Vector2 applyTransformations(float width, float height) {
        // Convert the position from pixels -> meters now so it's
        // easier to sync with Box2D later, which works in meters.
        float newWidth = width * Constants.PIXELS_TO_METERS;
        float newHeight = height * Constants.PIXELS_TO_METERS;

        // Cut the width/height in half so it's easier to calculate
        // distance from center to corner and configure Box2D shape.
        newWidth /= 2f;
        newHeight /= 2f;

        // Apply any additional width scaling, such as when the
        // player art happens to be wider than everything else.
        newWidth *= scaleWidth;

        return new Vector2(newWidth, newHeight);
    }
}
