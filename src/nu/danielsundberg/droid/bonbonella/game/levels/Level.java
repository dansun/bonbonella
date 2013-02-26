package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;
import nu.danielsundberg.droid.bonbonella.game.actors.Enemy;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
public abstract class Level extends Actor {



    protected World world;
    protected String[] GROUND_MAP;
    protected List<Tile> groundTiles = new CopyOnWriteArrayList<Tile>();
    protected List<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();

    public Level(World world, BonbonellaGameController controller, String[] level) {
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

    protected final Body createBodyForTile(int tileColumn, int tileRow) {
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(
                BonbonellaGame.convertToBox(Tile.TILE_SIZE * tileColumn) +
                        BonbonellaGame.convertToBox(Tile.TILE_SIZE/2),
                BonbonellaGame.convertToBox(Tile.TILE_SIZE*((GROUND_MAP.length-1)-tileRow)) +
                        BonbonellaGame.convertToBox(Tile.TILE_SIZE/2));
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        Body groundBody = world.createBody(groundBodyDef);

        EdgeShape groundBox = new EdgeShape ();
        Vector2 lowerLeft = new Vector2();
        Vector2 lowerRight = new Vector2();
        Vector2 upperRight = new Vector2();
        Vector2 upperLeft = new Vector2();

        lowerLeft.set(0-BonbonellaGame.convertToBox(Tile.TILE_SIZE/2),
                0-BonbonellaGame.convertToBox(Tile.TILE_SIZE/2));
        lowerRight.set(BonbonellaGame.convertToBox(Tile.TILE_SIZE/2),
                0-BonbonellaGame.convertToBox(Tile.TILE_SIZE/2));
        upperRight.set(BonbonellaGame.convertToBox(Tile.TILE_SIZE/2),
                BonbonellaGame.convertToBox(Tile.TILE_SIZE/2));
        upperLeft.set(0-BonbonellaGame.convertToBox(Tile.TILE_SIZE/2),
                BonbonellaGame.convertToBox(Tile.TILE_SIZE/2));

        groundBox.set(lowerLeft, lowerRight);
        groundBody.createFixture(groundBox, 0);
        groundBox.set(upperLeft, upperRight);
        groundBody.createFixture(groundBox, 0);
        groundBox.set(upperLeft, lowerLeft);
        groundBody.createFixture(groundBox, 0);
        groundBox.set(lowerRight, upperRight);
        groundBody.createFixture(groundBox, 0);
        return groundBody;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

        //
        // Draw enemies
        //
        for(Enemy enemy : enemies) {
            enemy.draw(batch, parentAlpha);
        }

        //
        // Draw level tiles
        //
        for(Tile tile : groundTiles) {
            batch.draw(tile.getTexture(),
                    BonbonellaGame.convertToWorld(tile.getBody().getPosition().x-BonbonellaGame.convertToBox(Tile.TILE_SIZE/2)),
                    BonbonellaGame.convertToWorld(tile.getBody().getPosition().y-BonbonellaGame.convertToBox(Tile.TILE_SIZE/2)));
        }
    }


    public abstract Level getNextLevel();

}
