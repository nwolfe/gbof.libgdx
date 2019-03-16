package org.rnq.bindingoffenrir.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public enum Type {
        PLAYER,
        ENEMY,
        GROUND,
        // SCENERY,
        OTHER
    }
    public Type type = Type.OTHER;
}
