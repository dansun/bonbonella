package nu.danielsundberg.droid.bonbonella.game.levels.realm1;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.levels.AbstractLevel;
import nu.danielsundberg.droid.bonbonella.game.levels.Level;
import nu.danielsundberg.droid.bonbonella.game.worlds.Realm1;

/**
 * Realm 1 Level 4 of Bonbonella game
 */
public class Level4 extends AbstractLevel implements Level {

    protected BonbonellaGameController controller;
    private Realm1 realm;

    public Level4(World world, BonbonellaGameController controller) {
        super(world, controller, Realm1.LEVEL4);
        this.controller = controller;
        this.realm = new Realm1();
        realm.initRealmForLevel(controller, Realm1.LEVEL4, world, this);
        resetLevel(Realm1.LEVEL4_TIME);
    }

    public Level getNextLevel(World world) {
        return new Level5(world, controller);
    }

    public void drawBackground(Camera camera, SpriteBatch batch) {
        realm.drawBackground(camera, batch);
    }

    public long getConfiguredTime() {
        return Realm1.LEVEL4_TIME;
    }


}
