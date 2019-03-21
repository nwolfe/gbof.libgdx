package org.rnq.bindingoffenrir.map.objects;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.Assets;
import org.rnq.bindingoffenrir.components.TypeComponent;
import org.rnq.bindingoffenrir.entities.EntityBuilder;

public class Baddie implements ObjectFactory {
    private enum State {
        IDLE
    }

    @Override
    public boolean canBuild(MapObject object) {
        return "baddie_l".equals(object.getName()) ||
                "baddie_r".equals(object.getName());
    }

    @Override
    public void build(MapObject object, PooledEngine engine, World world) {
        Texture baddie_r = Assets.instance.baddieImg.get();
        boolean flipX = false;
        if ("baddie_l".equals(object.getName())) {
            flipX = true;
        }
        new EntityBuilder(engine)
                .type(TypeComponent.Type.ENEMY)
                .state(State.IDLE.name(), true)
                .texture(baddie_r)
                .transform((RectangleMapObject) object, 1f, flipX)
                .physicsBegin()
                    .box()
                    .type(BodyDef.BodyType.DynamicBody)
                    .density(0f)
                .physicsEnd(world)
                .collision()
                .build();
    }
}
