package org.rnq.bindingoffenrir;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Ground {
    static class Builder implements Objects.Builder {
        @Override
        public boolean canBuild(MapObject object) {
            return "ground".equals(object.getName());
        }

        @Override
        public void build(MapObject object, Stage stage, World world) {
            Rectangle r = ((RectangleMapObject) object).getRectangle();
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(r.x + r.getWidth() / 2, r.y + r.getHeight() / 2);
            Body body = world.createBody(bodyDef);
            PolygonShape box = new PolygonShape();
            box.setAsBox(r.getWidth() / 2, r.getHeight() / 2);
            body.createFixture(box, 1.0f);
            box.dispose();
        }
    }
}
