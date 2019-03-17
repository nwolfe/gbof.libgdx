package org.rnq.bindingoffenrir.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.*;

public final class EntityBuilder {
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
        Gdx.app.log("entity", "type " + type);
        TypeComponent t = new TypeComponent();
        t.type = type;
        entity.add(t);
        return this;
    }

    public EntityBuilder state(String state, boolean isLooping) {
        Gdx.app.log("entity", "state " + state);
        StateComponent s = new StateComponent();
        s.set(state);
        s.isLooping = isLooping;
        entity.add(s);
        return this;
    }

    public EntityBuilder animation(String state, Animation<TextureRegion> animation) {
        Gdx.app.log("entity", "animation for " + state);
        AnimationComponent a = entity.getComponent(AnimationComponent.class);
        if (a == null) {
            a = new AnimationComponent();
            entity.add(a);
        }
        a.animations.put(state, animation);
        return this;
    }

    public EntityBuilder texture(TextureRegion region) {
        Gdx.app.log("entity", "texture region " + region);
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
        Gdx.app.log("entity", "transform from texture");
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

    public BodyBuilder physicsBegin() {
        Gdx.app.log("entity", "physics body from texture/transform");
        PhysicsComponent physics = new PhysicsComponent();
        Vector3 position = entity.getComponent(TransformComponent.class).position;
        Vector2 widthHeight = getDimensionsFromTexture();
        return new BodyBuilder(this, physics)
                .position(position.x, position.y)
                .dimensions(widthHeight.x, widthHeight.y)
                .userData(entity);
    }

    public BodyBuilder physicsBegin(RectangleMapObject object) {
        Gdx.app.log("entity", "physics body from rectangle map object");
        PhysicsComponent physics = new PhysicsComponent();
        Rectangle r = object.getRectangle();
        Vector2 scaled = applyTransformations(r.getWidth(), r.getHeight());
        float width = scaled.x;
        float height = scaled.y;
        float x = (r.x * Constants.PIXELS_TO_METERS) + width;
        float y = (r.y * Constants.PIXELS_TO_METERS) + height;
        return new BodyBuilder(this, physics)
                .position(x, y)
                .dimensions(width, height)
                .userData(entity);
    }

    void bodyBuildFinished(Body body, PhysicsComponent physics) {
        physics.body = body;
        entity.add(physics);
    }

    /**
     * Additional width scaling can be applied, such as when the
     * player image happens to be wider than everything else.
     */
    public EntityBuilder setWidthScaling(float scaleWidth) {
        Gdx.app.log("entity", "additional width scaling set to " + scaleWidth);
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
