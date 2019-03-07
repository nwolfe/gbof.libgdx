package org.rnq.bindingoffenrir;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends Actor implements StepCallback {
    static class Builder implements Objects.Builder {
        @Override
        public boolean canBuild(MapObject object) {
            return "player".equals(object.getName());
        }

        @Override
        public void build(MapObject object, Stage stage, World world) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();
            Player p = new Player(r.x, r.y, world);
            stage.addActor(p);
        }
    }

    // Animation
    private float stateTime = 0f;

    // Physics
    private final Body body;

    // State-specific behavior
    private final PlayerState idleState;
    private PlayerState currentState;

    private Player(float x, float y, World world) {
        idleState = new IdleState();
        TextureRegion frame = idleState.animation.getKeyFrames()[0];
        setBounds(x, y, frame.getRegionWidth(), frame.getRegionHeight());
        currentState = idleState;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x + getWidth() / 2, y + getHeight() / 2);
        body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        // Assume the player art is twice as wide as
        // everything else and scale it down accordingly
        box.setAsBox(getWidth() / 4, getHeight() / 2);
        body.createFixture(box, 1.0f);
        box.dispose();
        body.setUserData(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = currentState.getAnimationFrame(stateTime);
        batch.draw(frame, getX(), getY());
    }

    @Override
    public void onStep() {
        Vector2 v = body.getPosition();
        setPosition(v.x - getWidth() / 2, v.y - getHeight() / 2);
    }

    private abstract class PlayerState {
        Animation<TextureRegion> animation;

        abstract TextureRegion getAnimationFrame(float stateTime);
    }

    private class IdleState extends PlayerState {
        IdleState() {
            animation = new Animation<TextureRegion>(0.7f,
                    Assets.instance.playerIdleStrip.getFrames());
        }

        @Override
        public TextureRegion getAnimationFrame(float stateTime) {
            return animation.getKeyFrame(stateTime, true);
        }
    }
}
