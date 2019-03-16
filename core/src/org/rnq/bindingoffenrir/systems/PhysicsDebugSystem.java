package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.Constants;

public class PhysicsDebugSystem extends IteratingSystem {
    private final Box2DDebugRenderer debugRenderer;
    private final World world;
    private final OrthographicCamera camera;
    private boolean enabled;

    public PhysicsDebugSystem(World world, OrthographicCamera camera) {
        super(Family.all().get(), Constants.PRIORITY_PHYSICSDEBUG);
        this.world = world;
        this.camera = camera;
        debugRenderer = new Box2DDebugRenderer();
        enabled = false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            enabled = !enabled;
        if (enabled)
            debugRenderer.render(world, camera.combined);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
