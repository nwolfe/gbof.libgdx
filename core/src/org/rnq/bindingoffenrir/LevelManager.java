package org.rnq.bindingoffenrir;

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
