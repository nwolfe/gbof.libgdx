package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.ComponentMapper;
import org.rnq.bindingoffenrir.components.*;

public class Components {
    static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
    static final ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);
    static final ComponentMapper<TypeComponent> type = ComponentMapper.getFor(TypeComponent.class);
}
