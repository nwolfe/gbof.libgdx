package org.rnq.bindingoffenrir.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.rnq.bindingoffenrir.Assets;

public class Player extends Actor {
    static class Builder implements Actors.Builder<Player> {
        @Override
        public boolean canBuild(MapObject object) {
            return "player".equals(object.getName());
        }

        @Override
        public Player build(MapObject object) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();
            return new Player(r.x, r.y);
        }
    }

    private final Animation<TextureRegion> idleAnimation;
    private float stateTime = 0f;

    private Player(float x, float y) {
        idleAnimation = new Animation<TextureRegion>(0.15f,
                Assets.instance.playerIdleStrip.getFrames());
        setX(x);
        setY(y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = idleAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, getX(), getY());
    }
}
