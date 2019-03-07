package org.rnq.bindingoffenrir;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Collection;

public class Objects {
    private static final Collection<Builder> all = new ArrayList<Builder>();
    static {
        // Register all map object builders here...
        all.add(new Player.Builder());
    }

    interface Builder {
        boolean canBuild(MapObject object);
        void build(MapObject object, Stage stage, World world);
    }

    public static void build(MapObject object, Stage stage, World world) {
        for (Builder builder : all)
            if (builder.canBuild(object))
                builder.build(object, stage, world);
    }
}