package org.rnq.bindingoffenrir;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends Actor {
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

    private final Animation<TextureRegion> idleAnimation;
    private float stateTime = 0f;
    private final Body body;

    private Player(float x, float y, World world) {
        idleAnimation = new Animation<TextureRegion>(0.7f,
                Assets.instance.playerIdleStrip.getFrames());
        TextureRegion frame = idleAnimation.getKeyFrames()[0];
        setBounds(x, y, frame.getRegionWidth(), frame.getRegionHeight());

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x + getWidth() / 2, y + getHeight() / 2);
        body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(getWidth() / 2, getHeight() / 2);
        body.createFixture(box, 1.0f);
        box.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = idleAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, getX(), getY());
    }
}
