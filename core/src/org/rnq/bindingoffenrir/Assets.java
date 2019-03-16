package org.rnq.bindingoffenrir;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.Collection;

// Put things here that can be loaded by the LibGDX AssetManager class.
// Click into the definition to see what classes it supports.
@SuppressWarnings("SpellCheckingInspection")
public final class Assets {
    public Asset<Texture> sampleBgImg = image("badlogic.jpg");
    public Asset<TiledMap> sampleLevel = map("sample/sample_level.tmx");
    public Asset<Skin> uiSkin = skin("pixthulhu/pixthulhu-ui.json");
    public Spritesheet playerIdleStrip = spritesheet("player_idle.png", 2, 1);

    public static Assets instance;

    static void load() {
        if (instance == null)
            instance = new Assets();
    }

    static void dispose() {
        if (instance != null)
            instance.doDispose();
    }

    private static final Collection<Asset<?>> all = new ArrayList<Asset<?>>();
    public static class Asset<T> {
        private final String filename;
        private final Class<T> type;
        private T asset;
        private Asset(String filename, Class<T> type) {
            this.filename = filename;
            this.type = type;
            all.add(this);
        }
        private void load(AssetManager manager) {
            manager.load(filename, type);
        }
        protected void set(AssetManager manager) {
            asset = manager.get(filename, type);
        }
        public T get() {
            return asset;
        }

        @Override
        public String toString() {
            return String.format("%s[%s]", type.getSimpleName(), filename);
        }
    }

    // Assumes the spritesheet has only one animation in it
    public static class Spritesheet extends Asset<Texture> {
        private TextureRegion[] frames;
        private final int columns;
        private final int rows;

        private Spritesheet(String filename, int columns, int rows) {
            super(filename, Texture.class);
            this.columns = columns;
            this.rows = rows;
        }

        @Override
        protected void set(AssetManager manager) {
            super.set(manager);

            // Assume spritesheet contains frames of
            // equal size and they are all aligned
            TextureRegion[][] tmp = TextureRegion.split(get(),
                    get().getWidth() / columns, get().getHeight() / rows);

            // Place into one-dimensional array for Animation
            frames = new TextureRegion[columns * rows];
            int index = 0;
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < columns; ++j)
                    frames[index++] = tmp[i][j];
        }

        public TextureRegion[] getFrames() {
            return frames;
        }
    }

    private final AssetManager manager;

    private Assets() {
        manager = new AssetManager();
        manager.setLoader(TiledMap.class,
                new TmxMapLoader(manager.getFileHandleResolver()));
        for (Asset<?> asset : all)
            asset.load(manager);
        manager.finishLoading();
        for (Asset<?> asset : all)
            asset.set(manager);
    }

    private void doDispose() {
        manager.dispose();
    }

    private static Asset<Texture> image(String filename) {
        return new Asset<Texture>(
                String.format("assets/img/%s", filename), Texture.class);
    }

    private static Spritesheet spritesheet(String filename, int cols, int rows) {
        return new Spritesheet(String.format("assets/img/%s", filename), cols, rows);
    }

    private static Asset<TiledMap> map(String filename) {
        return new Asset<TiledMap>(
                String.format("assets/map/%s", filename), TiledMap.class);
    }

    private static Asset<Skin> skin(String filename) {
        return new Asset<Skin>(
                String.format("assets/skin/%s", filename), Skin.class);
    }
}
