package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.InputComponent;
import org.rnq.bindingoffenrir.components.PlayerComponent;
import org.rnq.bindingoffenrir.components.VelocityComponent;

public class PlayerInputSystem extends IteratingSystem {
    public PlayerInputSystem() {
        super(Family.all(
                PlayerComponent.class,
                InputComponent.class,
                VelocityComponent.class).get(),
                Constants.PRIORITY_PLAYERINPUT);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InputComponent input = Components.input.get(entity);
        VelocityComponent velocity = Components.velocity.get(entity);

        if (input.left) {
            velocity.x = -1f;
            input.left = false;
        }
        if (input.right) {
            velocity.x = 1f;
            input.right = false;
        }
        if (input.jump) {
            velocity.y = 10f;
            input.jump = false;
        }
    }
}
