package org.rnq.bindingoffenrir;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.Collection;

public class Actors {
    private static final Collection<Builder<? extends Actor>> all =
            new ArrayList<Builder<? extends Actor>>();
    static {
        // Register all map object builders here...
        all.add(new Player.Builder());
    }

    interface Builder<T extends Actor> {
        boolean canBuild(MapObject object);
        T build(MapObject object);
    }

    public static Actor build(MapObject object) {
        for (Builder<? extends Actor> builder : all)
            if (builder.canBuild(object))
                return builder.build(object);
        return null;
    }
}