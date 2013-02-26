package nu.danielsundberg.droid.bonbonella.game.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import nu.danielsundberg.droid.bonbonella.util.Direction;

public interface Enemy {

    public Body getBody();
    public void changeDirection();
    public void addForce(float x, float y);
    public void addImpulse(float x, float y);
    public Direction getDirection();
    public void draw(SpriteBatch batch, float parentAlpha);
    public void act(float timeSinceLastAct);
    public void die();

}
