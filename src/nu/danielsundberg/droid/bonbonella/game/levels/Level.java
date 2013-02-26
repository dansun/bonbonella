package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.actors.Cavitycreep;

import java.util.List;

/**
 *
 */
public abstract class Level extends Actor {

    protected World world;

    public Level(World world, BonbonellaGameController controller) {
        this.world = world;
    }

    public abstract List<Cavitycreep> getEnemies();
    public abstract float getLevelWidth();
    public abstract Level getNextLevel();

}
