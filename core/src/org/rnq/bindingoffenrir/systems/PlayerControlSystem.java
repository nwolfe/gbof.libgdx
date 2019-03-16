package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.InputComponent;
import org.rnq.bindingoffenrir.components.PlayerComponent;

public class PlayerControlSystem extends IteratingSystem {
    public PlayerControlSystem() {
        super(Family.all(PlayerComponent.class, InputComponent.class).get(),
                Constants.PRIORITY_PLAYERCONTROL);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        InputComponent input = Components.input.get(entity);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.A))
            input.left = true;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                Gdx.input.isKeyPressed(Input.Keys.D))
            input.right = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            input.jump = true;
    }
}
