package nu.danielsundberg.droid.bonbonella.game.worlds;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;
import nu.danielsundberg.droid.bonbonella.game.actors.Tile;

public class AbstractRealm {

    protected int times(float endOfViewport, float endOfTexture) {
        int count = 0;
        while(endOfViewport > 0) {
            endOfViewport -= endOfTexture;
            if(endOfViewport > 0) {
                count++;
            }
        }
        return count;
    }

    protected final Body createBodyForTile(World world, String[] leveldescriptor, int tileColumn, int tileRow) {
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(
                BonbonellaGame.convertToBox(Tile.TILE_SIZE * tileColumn) +
                        BonbonellaGame.convertToBox(Tile.TILE_SIZE/2),
                BonbonellaGame.convertToBox(Tile.TILE_SIZE*((leveldescriptor.length-1)-tileRow)) +
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

}
