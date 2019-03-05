package org.rnq.bindingoffenrir;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

class Assets {
    static Assets instance;

    Texture sampleBgImg;
    TiledMap sampleLevel;
//    TextureRegion[] playerIdleFrames;

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
//        manager.load(image("player_idle.png"), Texture.class);

        manager.finishLoading();

        sampleBgImg = manager.get(image("badlogic.jpg"));
        sampleLevel = manager.get(map("sample/sample_level.tmx"));

//        Texture idle_sheet = manager.get(image("player_idle.png"));
//        int cols = 2;
//        int rows = 1;
//        TextureRegion[][] tmp = TextureRegion.split(idle_sheet,
//                idle_sheet.getWidth() / cols,
//                idle_sheet.getHeight() / rows);
//        playerIdleFrames = new TextureRegion[cols * rows];
//        int index = 0;
//        for (int i = 0; i < rows; i++)
//            for (int j = 0; j < cols; j++)
//                playerIdleFrames[index++] = tmp[i][j];
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
