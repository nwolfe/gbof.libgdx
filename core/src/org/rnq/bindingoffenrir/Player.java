package org.rnq.bindingoffenrir;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    private final Animation<TextureRegion> idleAnimation;
    private float stateTime = 0f;

    public Player() {
        idleAnimation = new Animation<TextureRegion>(0.15f,
                Assets.instance.playerIdleStrip.getFrames());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = idleAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, 10, 16);
    }
}
