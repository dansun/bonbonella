package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;
import nu.danielsundberg.droid.bonbonella.game.actors.Enemy;
import nu.danielsundberg.droid.bonbonella.game.actors.Tile;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
public abstract class AbstractLevel extends Actor {

    protected World world;
    protected String[] GROUND_MAP;
    protected List<Tile> groundTiles = new CopyOnWriteArrayList<Tile>();
    protected List<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
    protected Vector2 startposition;

    protected long startingTime, currentTime, endingTime;


    public AbstractLevel(World world, BonbonellaGameController controller, String[] level) {
        this.world = world;
        this.GROUND_MAP = level;
    }

    public final List<Enemy> getEnemies() {
        return enemies;
    }

    public final List<Tile> getTiles() {
        return groundTiles;
    }

    public final float getLevelWidth() {
        return GROUND_MAP[0].length()*Tile.TILE_SIZE;
    }

    public void removeTile(Tile tileToRemove) {
        groundTiles.remove(tileToRemove);
        Filter noContactFilter = new Filter();
        noContactFilter.maskBits = 0x0000;
        for(Fixture fixture : tileToRemove.getBody().getFixtureList()) {
            fixture.setFilterData(noContactFilter);
        }
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        //
        // Draw level tiles
        //
        for(Tile tile : groundTiles) {
            batch.draw(tile.getTexture(),
                    BonbonellaGame.convertToWorld(tile.getBody().getPosition().x-BonbonellaGame.convertToBox(Tile.TILE_SIZE/2)),
                    BonbonellaGame.convertToWorld(tile.getBody().getPosition().y-BonbonellaGame.convertToBox(Tile.TILE_SIZE/2)));
        }

        //
        // Draw enemies
        //
        for(Enemy enemy : enemies) {
            enemy.draw(batch, parentAlpha);
        }


    }

    public void resetLevel(long timeToRestart) {
        startingTime = System.currentTimeMillis();
        currentTime = startingTime;
        endingTime = startingTime+(timeToRestart);
        //
        // Also reset enemy positions
        //
        for(Enemy enemy : enemies) {
            enemy.resetPosition();
        }
    }

    public void setStartposition(Vector2 startposition) {
        this.startposition = startposition;
    }

    public Vector2 getStartposition() {
        return this.startposition;
    }

    public final void addGroundTile(Tile tileToAdd) {
        this.groundTiles.add(tileToAdd);
    }

    public final void addEnemy(Enemy enemyToAdd) {
        this.enemies.add(enemyToAdd);
    }

    public void act(float delta) {
        for(Enemy creep : enemies) {
            //
            // Check for removal
            //
            if(creep.getBody().getPosition().y < 0f) {
                world.destroyBody(creep.getBody());
                enemies.remove(creep);
            } else {
                creep.act(delta);
            }
        }

        //
        // Update time in game
        //
        currentTime = currentTime + new Float(delta*1000).longValue();
    }


    public abstract Level getNextLevel(World world);

    /**
     * Return time left on level.
     * @return
     */
    public int getTimeLeft() {
        int time = new Long((endingTime-currentTime)/1000).intValue();
        return time >= 0 ? time : 0;
    }

}
