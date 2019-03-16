package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.ComponentMapper;
import org.rnq.bindingoffenrir.components.*;

final class Components {
    static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
    static final ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);
    static final ComponentMapper<TypeComponent> type = ComponentMapper.getFor(TypeComponent.class);
    static final ComponentMapper<StateComponent> state = ComponentMapper.getFor(StateComponent.class);
    static final ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);
    static final ComponentMapper<InputComponent> input = ComponentMapper.getFor(InputComponent.class);
    static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
}
