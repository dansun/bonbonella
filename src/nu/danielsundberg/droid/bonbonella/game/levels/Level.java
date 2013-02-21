package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 */
public abstract class Level extends Actor {

    protected World world;

    public Level(World world) {
        this.world = world;
    }

    public abstract float getFinishX();
    public abstract Level getNextLevel();

}
