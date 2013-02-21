package nu.danielsundberg.droid.bonbonella.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbonella;
import nu.danielsundberg.droid.bonbonella.game.levels.Level;
import nu.danielsundberg.droid.bonbonella.game.levels.Level1;

/**
 * Game handler for an instance of a bonbonella game
 */
public class BonbonellaGame extends Stage implements ContactListener {

    public final static Vector2 GRAVITY = new Vector2(0,-9.82f);

    private World world;
    private Bonbonella bonbonella;
    private Level level;
    private GameState gameState;


    public BonbonellaGame() {
        world = new World(GRAVITY, false);
        bonbonella = new Bonbonella(world);
        gameState = GameState.PLAY;
        world.setContactListener(this);
    }

    public Bonbonella getBonbonella() {
        return this.bonbonella;
    }

    @Override
    public void act(float timeSinceLastRender) {
        super.act(timeSinceLastRender);

        if(level==null) {
           level = new Level1(world);
        } else {
            if(bonbonella.getX()>level.getFinishX()) {
                gameState = GameState.LEVEL;
            }
        }

        if(gameState == GameState.LEVEL) {
            level = level.getNextLevel();
            gameState = GameState.PLAY;
        }

        if(gameState == GameState.PLAY) {
            world.step(1/60f, 6, 2);
            bonbonella.act(timeSinceLastRender);
            level.act(timeSinceLastRender);
        }

        if(bonbonella.getLives()==0) {
            gameState = GameState.GAMEOVER;

        }

    }

    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    public enum GameState {
        PLAY, LEVEL, GAMEOVER
    }



}
