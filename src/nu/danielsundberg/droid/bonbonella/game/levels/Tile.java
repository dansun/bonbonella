package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

public class Tile {

    Texture texture;
    Body body;

    public Tile(Texture texture, Body body) {
        this.texture = texture;
        this.body = body;
    }

    public Texture getTexture() {
        return texture;
    }

    public Body getBody() {
        return body;
    }





}
