package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import nu.danielsundberg.droid.bonbonella.game.actors.Tile;

public interface Level {

    public Vector2 getStartposition();
    public void drawBackground(Camera camera, SpriteBatch batch);
    public Level getNextLevel(World world);
    public int getTimeLeft();
    public void act(float deltaTimeSincelastCall);
    public long getConfiguredTime();
    public void resetLevel(long timeToResetTo);
    public void removeTile(Tile tileToRemove);
    public float getLevelWidth();

}
