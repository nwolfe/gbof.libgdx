package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.AnimationComponent;
import org.rnq.bindingoffenrir.components.StateComponent;
import org.rnq.bindingoffenrir.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {
    public AnimationSystem() {
        super(Family.all(
                AnimationComponent.class,
                TextureComponent.class,
                StateComponent.class).get(),
                Constants.PRIORITY_ANIMATION);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent animation = Components.animation.get(entity);
        StateComponent state = Components.state.get(entity);

        if (animation.animations.containsKey(state.get())) {
            TextureComponent texture = Components.texture.get(entity);
            Animation<TextureRegion> current = animation.animations.get(state.get());
            texture.region = current.getKeyFrame(state.time, state.isLooping);
        }

        state.time += deltaTime;
    }
}
