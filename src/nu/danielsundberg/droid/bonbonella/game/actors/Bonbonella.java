package nu.danielsundberg.droid.bonbonella.game.actors;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

/**
 *
 */
public class Bonbonella extends Actor {

    private World world;
    private Body body;

    private int lives;

    public Bonbonella(World world) {
        this.world = world;

        BodyDef bd = new BodyDef();
        bd.position.set(getX(), getY());
        bd.type = BodyDef.BodyType.DynamicBody;

        FixtureDef fd = new FixtureDef();
        fd.density = 0.1f;
        fd.friction = 0.5f; // trenje
        fd.restitution = 0.3f; // odbijanje

        if (body!=null) removeBodySafely(body);

        body = world.createBody(bd);

        lives = 3;

    }

    public void act(float timeSinceLastRender) {

    }

    /**
     * Safe way to remove body from the world. Remember that you cannot have any
     * references to this body after calling this
     *
     * @param body that will be removed from the physic world
     *
     */
    public void removeBodySafely(Body body) {
        final List<JointEdge> list = body.getJointList();
        while (list.size() > 0) {
            world.destroyJoint(list.get(0).joint);
        }
        world.destroyBody(body);
    }


    public int getLives() {
        return lives;
    }


}
