package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import org.rnq.bindingoffenrir.components.TextureComponent;
import org.rnq.bindingoffenrir.components.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {
    static final float PPM = 16.0f;
    static final float VIEWPORT_WIDTH = Gdx.graphics.getWidth() / PPM;
    static final float VIEWPORT_HEIGHT = Gdx.graphics.getHeight() / PPM;
    public static final float PIXELS_TO_METERS = 1.0f / PPM;

    private final SpriteBatch batch;
    private final Array<Entity> renderQueue;
    public final OrthographicCamera camera;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(
                TransformComponent.class, TextureComponent.class).get(),
                new ZComparator());
        this.batch = batch;
        renderQueue = new Array<Entity>();
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(VIEWPORT_WIDTH / 2f, VIEWPORT_HEIGHT / 2f, 0);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // renderQueue.sort(); ?
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        batch.begin();
        for (Entity entity : renderQueue)
            renderEntity(entity);
        batch.end();
        renderQueue.clear();
    }

    private void renderEntity(Entity entity) {
        TextureComponent texture = Components.texture.get(entity);
        TransformComponent transform = Components.transform.get(entity);
        if (texture.region == null || transform.isHidden)
            return;
        float width = texture.region.getRegionWidth();
        float height = texture.region.getRegionHeight();
        float originX = width / 2f;
        float originY = height / 2f;
        float x = transform.position.x - originX;
        float y = transform.position.y - originY;
        batch.draw(texture.region,
                x, y, originX, originY, width, height,
                transform.scale.x * PIXELS_TO_METERS,
                transform.scale.y * PIXELS_TO_METERS,
                transform.rotation);
    }

    private static final class ZComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity a, Entity b) {
            return (int) Math.signum(
                    Components.transform.get(b).position.z -
                    Components.transform.get(a).position.z);
        }
    }
}
