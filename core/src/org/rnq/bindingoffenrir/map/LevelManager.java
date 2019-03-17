package org.rnq.bindingoffenrir.map;

import org.rnq.bindingoffenrir.Assets;

public class LevelManager {
    private Level current;

    public LevelManager() {
        current = new Level(Assets.instance.sampleLevel.get());
    }

    public Level currentLevel() {
        return current;
    }

    public void dispose() {
        current.dispose();
    }
}
