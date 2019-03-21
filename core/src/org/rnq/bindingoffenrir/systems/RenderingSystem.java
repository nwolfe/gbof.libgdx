package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.components.TextureComponent;
import org.rnq.bindingoffenrir.components.TransformComponent;
import org.rnq.bindingoffenrir.map.LevelManager;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {
    private final LevelManager levelManager;
    private final SpriteBatch batch;
    private final Array<Entity> renderQueue;
    public final OrthographicCamera camera;

    public RenderingSystem(SpriteBatch batch, LevelManager levelManager) {
        super(Family.all(
                TransformComponent.class, TextureComponent.class).get(),
                new ZComparator(), Constants.PRIORITY_RENDERING);
        this.levelManager = levelManager;
        this.batch = batch;
        renderQueue = new Array<Entity>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.GAME_WIDTH_IN_TILES,
                Constants.GAME_HEIGHT_IN_TILES);
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
        // disable/enable blending ?
        levelManager.currentLevel().render(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Entity entity : renderQueue)
            renderEntity(entity);
        batch.end();

        renderQueue.clear();
    }

    private void renderEntity(Entity entity) {
        TransformComponent transform = Components.transform.get(entity);
        if (transform.isHidden)
            return;
        TextureComponent texture = Components.texture.get(entity);
        if (texture.region != null)
            renderRegion(texture.region, transform);
        else if (texture.texture != null)
            renderTexture(texture.texture, transform);
    }

    private void renderRegion(TextureRegion region, TransformComponent transform) {
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        float originX = width / 2f;
        float originY = height / 2f;
        float x = transform.position.x - originX;
        float y = transform.position.y - originY;
        batch.draw(region,
                x, y, originX, originY, width, height,
                transform.scale.x * Constants.PIXELS_TO_METERS,
                transform.scale.y * Constants.PIXELS_TO_METERS,
                transform.rotation);
    }

    private void renderTexture(Texture texture, TransformComponent transform) {
        float width = texture.getWidth();
        float height = texture.getHeight();
        float originX = width / 2f;
        float originY = height / 2f;
        float x = transform.position.x - originX;
        float y = transform.position.y - originY;
//        System.out.println("drawing texture");
        batch.draw(texture, x, y, width, height, 1, 1, 1, 2, transform.flipX, false);
        batch.draw(texture,
                x, y, originX, originY, width, height,
                transform.scale.x * Constants.PIXELS_TO_METERS,
                transform.scale.y * Constants.PIXELS_TO_METERS,
                transform.rotation, 0, 0, 0, 0, transform.flipX, false);
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
