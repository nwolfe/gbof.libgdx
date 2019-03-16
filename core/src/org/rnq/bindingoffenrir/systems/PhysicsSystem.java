package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.PhysicsComponent;
import org.rnq.bindingoffenrir.components.TransformComponent;

public class PhysicsSystem extends IteratingSystem {
    private final World world;
    private final Array<Entity> bodies;
    private float time_accumulator;

    public PhysicsSystem(World world) {
        super(Family.all(PhysicsComponent.class, TransformComponent.class).get(),
                Constants.PRIORITY_PHYSICS);
        this.world = world;
        bodies = new Array<Entity>();
        time_accumulator = 0f;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float frameTime = Math.min(deltaTime, 0.25f);
        time_accumulator += frameTime;
        if (time_accumulator >= Constants.MAX_STEP_TIME) {
            world.step(Constants.MAX_STEP_TIME, 6, 2);
            time_accumulator -= Constants.MAX_STEP_TIME;

            for (Entity entity : bodies) {
                TransformComponent transform = Components.transform.get(entity);
                PhysicsComponent physics = Components.physics.get(entity);
                Vector2 position = physics.body.getPosition();
                transform.position.x = position.x;
                transform.position.y = position.y;
                transform.rotation = physics.body.getAngle() * MathUtils.radiansToDegrees;
            }
        }
        bodies.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodies.add(entity);
    }
}
