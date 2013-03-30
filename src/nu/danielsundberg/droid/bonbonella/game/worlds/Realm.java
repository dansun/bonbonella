package nu.danielsundberg.droid.bonbonella.game.worlds;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.levels.AbstractLevel;

public interface Realm {

    public void initRealmForLevel(BonbonellaGameController controller, String[] levelDescriptor, World physicsWorld, AbstractLevel levelToSetup);
    public void drawBackground(Camera camera, SpriteBatch batch);
}
