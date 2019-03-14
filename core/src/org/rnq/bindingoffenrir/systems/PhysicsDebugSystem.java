package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsDebugSystem extends IteratingSystem {
    private final Box2DDebugRenderer debugRenderer;
    private final World world;
    private final OrthographicCamera camera;

    public PhysicsDebugSystem(World world, OrthographicCamera camera) {
        super(Family.all().get());
        this.world = world;
        this.camera = camera;
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (Gdx.input.isKeyPressed(Input.Keys.F1))
            debugRenderer.render(world, camera.combined);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
