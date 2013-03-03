package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

public class Bonbon extends Tile {

    public Bonbon(Texture texture, Body body) {
        super(texture, body);
    }

    public int getScoreValue() {
        return 200;
    }

}
