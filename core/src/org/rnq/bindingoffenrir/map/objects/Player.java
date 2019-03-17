package org.rnq.bindingoffenrir.map.objects;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import org.rnq.bindingoffenrir.Assets;
import org.rnq.bindingoffenrir.components.TypeComponent;
import org.rnq.bindingoffenrir.entities.EntityBuilder;

public class Player implements ObjectFactory {
    private enum State {
        IDLE
    }

    @Override
    public boolean canBuild(MapObject object) {
        return "player".equals(object.getName());
    }

    @Override
    public void build(MapObject object, PooledEngine engine, World world) {
        TextureRegion[] idleFrames = Assets.instance.playerIdleStrip.getFrames();
        new EntityBuilder(engine)
                .player()
                .type(TypeComponent.Type.PLAYER)
                .state(State.IDLE.name(), true)
                .animation(State.IDLE.name(),
                        new Animation<TextureRegion>(0.7f, idleFrames))
                .texture(idleFrames[0])
                .setWidthScaling(0.5f)
                .transform((RectangleMapObject) object, 1f)
                .physicsBegin()
                    .box()
                    .type(BodyDef.BodyType.DynamicBody)
                    .density(0f)
                .physicsEnd(world)
                .collision()
                .input()
                .velocity()
                .build();
    }
}
