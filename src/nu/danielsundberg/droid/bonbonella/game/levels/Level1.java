package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Level 1 of Bonbonella game
 */
public class Level1 extends Level {

    private Set<Body> bodies;

    public Level1(World world) {
        super(world);
        bodies = new LinkedHashSet<Body>();
    }

    public float getFinishX() {
        return 10f;
    }

    public Level getNextLevel() {
        return new Level1(world);
    }

}
