package org.rnq.bindingoffenrir.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent implements Component {
    public final Vector3 position = new Vector3();
    public final Vector2 scale = new Vector2(1f, 1f);
    public float rotation = 0f;
    public boolean isHidden = false;
}
