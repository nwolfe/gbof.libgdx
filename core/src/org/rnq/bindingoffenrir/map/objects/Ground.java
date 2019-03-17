package org.rnq.bindingoffenrir.map.objects;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.components.TypeComponent;
import org.rnq.bindingoffenrir.entities.EntityBuilder;

public class Ground implements ObjectFactory {
    @Override
    public boolean canBuild(MapObject object) {
        return "ground".equals(object.getName());
    }

    @Override
    public void build(MapObject object, PooledEngine engine, World world) {
        new EntityBuilder(engine)
                .type(TypeComponent.Type.GROUND)
                .physicsBegin((RectangleMapObject) object)
                    .box()
                    .type(BodyDef.BodyType.StaticBody)
                    .density(0f)
                .physicsEnd(world)
                .collision()
                .build();
    }
}
