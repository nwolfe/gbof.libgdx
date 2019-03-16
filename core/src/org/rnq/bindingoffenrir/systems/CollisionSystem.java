package org.rnq.bindingoffenrir.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.*;
import org.rnq.bindingoffenrir.components.CollisionComponent;
import org.rnq.bindingoffenrir.components.PlayerComponent;
import org.rnq.bindingoffenrir.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    public CollisionSystem() {
        // Only handle Player collisions
        // (for now? or separate systems for other types?)
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collision = Components.collision.get(entity);
        if (collision.collidedWith != null) {
            TypeComponent type = Components.type.get(collision.collidedWith);
            if (type != null) {
                switch (type.type) {
                    case ENEMY:
                        System.out.println("player hit enemy");
                        break;
                    case OTHER:
                        System.out.println("player hit other");
                }
                // Collision handled; clear the component
                collision.collidedWith = null;
            }
        }
    }

    public static final class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
        @Override
        public void beginContact(Contact contact) {
            Fixture fa = contact.getFixtureA();
            Fixture fb = contact.getFixtureB();
            if (fa.getBody().getUserData() instanceof Entity) {
                Entity e = (Entity) fa.getBody().getUserData();
                handleCollision(e, fb);
            } else if (fb.getBody().getUserData() instanceof Entity) {
                Entity e = (Entity) fb.getBody().getUserData();
                handleCollision(e, fa);
            }
        }

        private void handleCollision(Entity e, Fixture f) {
            if (f.getBody().getUserData() instanceof Entity) {
                Entity other = (Entity) f.getBody().getUserData();
                CollisionComponent collisionA = Components.collision.get(e);
                CollisionComponent collisionB = Components.collision.get(other);
                if (collisionA != null)
                    collisionA.collidedWith = other;
                else if (collisionB != null)
                    collisionB.collidedWith = e;
            }
        }

        @Override
        public void endContact(Contact contact) {
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
        }
    }
}
