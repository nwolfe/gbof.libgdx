package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.PhysicsComponent;
import org.rnq.bindingoffenrir.components.VelocityComponent;

public class MovementSystem extends IteratingSystem {
    public MovementSystem() {
        super(Family.all(VelocityComponent.class, PhysicsComponent.class).get(),
                Constants.PRIORITY_MOVEMENT);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocity = Components.velocity.get(entity);
        PhysicsComponent physics = Components.physics.get(entity);

        Vector2 curVel = physics.body.getLinearVelocity();
        if (curVel.x > -Constants.MAX_VELOCITY && curVel.x < Constants.MAX_VELOCITY) {
            Vector2 position = physics.body.getPosition();
            physics.body.applyLinearImpulse(
                    velocity.x, velocity.y, position.x, position.y, true);
        }

        // Velocity handled; reset component
        velocity.x = 0f;
        velocity.y = 0f;
    }
}
