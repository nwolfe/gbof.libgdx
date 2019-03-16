package org.rnq.bindingoffenrir.map.objects;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

public interface ObjectBuilder {
    boolean canBuild(MapObject object);
    void build(MapObject object, PooledEngine engine, World world);

    ObjectBuilder[] all = new ObjectBuilder[]{
            new Ground(),
            new Player()
    };
}
