package org.rnq.bindingoffenrir;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

class Assets {
    public static Assets instance;

    public Texture sampleBgImg;
    public TiledMap sampleLevel;

    static void load() {
        if (instance == null)
            instance = new Assets();
    }

    static void dispose() {
        if (instance != null)
            instance.doDispose();
    }

    private final AssetManager manager;

    Assets() {
        manager = new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(manager.getFileHandleResolver()));
        manager.load(image("badlogic.jpg"), Texture.class);
        manager.load(map("sample/sample_level.tmx"), TiledMap.class);
        manager.finishLoading();
        sampleBgImg = manager.get(image("badlogic.jpg"));
        sampleLevel = manager.get(map("sample/sample_level.tmx"));
    }

    private void doDispose() {
        manager.dispose();
    }

    private static String image(String filename) {
        return String.format("assets/img/%s", filename);
    }

    private static String map(String filename) {
        return String.format("assets/map/%s", filename);
    }
}
