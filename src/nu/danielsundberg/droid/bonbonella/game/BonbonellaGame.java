package nu.danielsundberg.droid.bonbonella.game;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbonella;
import nu.danielsundberg.droid.bonbonella.game.actors.Cavitycreep;
import nu.danielsundberg.droid.bonbonella.game.levels.Level;
import nu.danielsundberg.droid.bonbonella.game.levels.Level1;
import nu.danielsundberg.droid.bonbonella.game.levels.Tile;

/**
 * Game handler for an instance of a bonbonella game
 */
public class BonbonellaGame extends Stage implements ContactListener {

    public final static Vector2 GRAVITY = new Vector2(0, -9.82f);

    private BonbonellaGameController controller;

    private World world;
    private Bonbonella bonbonella;
    private Level level;
    private GameState gameState;

    public static final float WORLD_TO_BOX=0.01f;
    public static final float BOX_TO_WORLD=100f;

    public static float convertToBox(float value){
        return value*WORLD_TO_BOX;
    }

    public static float convertToWorld(float value) {
        return value*BOX_TO_WORLD;
    }

    public BonbonellaGame(BonbonellaGameController controller) {
        this.controller = controller;
        world = new World(GRAVITY, true);
        world.setAutoClearForces(true);
        world.setContactListener(this);


        bonbonella = new Bonbonella(world, controller);
        addActor(bonbonella);

        gameState = GameState.PLAY;
    }

    public Bonbonella getBonbonella() {
        return this.bonbonella;
    }

    public Level getLevel() {
        return this.level;
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public void act(float timeSinceLastRender) {
        super.act(timeSinceLastRender);



        if (level == null) {
            level = new Level1(world, controller);
            addActor(level);
        } else {
            if (bonbonella.getX() > level.getFinishX()) {
                gameState = GameState.LEVEL;
            }
        }

        if (gameState == GameState.LEVEL) {
            level = level.getNextLevel();
            gameState = GameState.PLAY;
        }

        if (gameState == GameState.PLAY) {

            if(Gdx.input.isTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.input.getY();
                if(touchX < Gdx.graphics.getWidth()/2) {
                    bonbonella.addForce(convertToBox(-1f), convertToBox(0.1f));
                } else {
                    bonbonella.addForce(convertToBox(1f),convertToBox(0.1f));
                }
                if(touchY < Gdx.graphics.getHeight()/2) {
                    bonbonella.addImpulse(0, convertToBox(2f));
                }
            }

            world.step(1 / 60f, 8, 4);
            world.clearForces();
            bonbonella.act(timeSinceLastRender);
            level.act(timeSinceLastRender);
        }

        if (bonbonella.getLives() == 0) {
            gameState = GameState.GAMEOVER;

        }

    }

    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getBody().getUserData().getClass().isAssignableFrom(Bonbonella.class)) {
            if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Cavitycreep.class)) {
                //
                // Bonbonella has collided with a creep.
                //
                Log.i(this.getClass().getSimpleName(), "Bonbonella hit cavitycreep.");
            }
        } else if(contact.getFixtureA().getBody().getUserData().getClass().isAssignableFrom(Cavitycreep.class)) {
            if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Bonbonella.class)) {
                //
                // Creep has collided with bonbonella
                //
            } else if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Tile.class)) {
                //
                // Creep has collided with a tile.
                //
                handleCreepHittingWall(contact.getFixtureB(), contact.getFixtureA());
            }
        } else if(contact.getFixtureA().getBody().getUserData().getClass().isAssignableFrom(Tile.class)) {
            if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Cavitycreep.class)) {
                //
                // Tile has collided with creep
                //
                handleCreepHittingWall(contact.getFixtureA(), contact.getFixtureB());
            }
        }
    }

    private void handleCreepHittingWall(Fixture wall, Fixture creep) {
        if(creep.getBody().getPosition().y >=
                wall.getBody().getPosition().y-convertToBox(16f/2) &&
                creep.getBody().getPosition().y <=
                        wall.getBody().getPosition().y+convertToBox((16f/2))) {
            //
            // Creep hit wall
            //
            Cavitycreep theCreep = (Cavitycreep) creep.getBody().getUserData();
            if(theCreep.getDirection().equals(Cavitycreep.Direction.LEFT) &&
                wall.getBody().getPosition().x < creep.getBody().getPosition().x) {
                theCreep.changeDirection();
            } else if(theCreep.getDirection().equals(Cavitycreep.Direction.RIGHT) &&
                    wall.getBody().getPosition().x > creep.getBody().getPosition().x) {
                theCreep.changeDirection();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}

    public enum GameState {
        PLAY, LEVEL, GAMEOVER
    }


}
