package nu.danielsundberg.droid.bonbonella.game;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbonella;
import nu.danielsundberg.droid.bonbonella.game.actors.Cavitycreep;
import nu.danielsundberg.droid.bonbonella.game.actors.Enemy;
import nu.danielsundberg.droid.bonbonella.game.levels.Level;
import nu.danielsundberg.droid.bonbonella.game.levels.Level1;
import nu.danielsundberg.droid.bonbonella.game.levels.Tile;
import nu.danielsundberg.droid.bonbonella.util.Direction;

import java.math.BigDecimal;

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

    public static float round(float d, int scale, boolean roundUp) {
        int mode = (roundUp) ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN;
        return new BigDecimal(d).setScale(scale, mode).floatValue();
    }

    public BonbonellaGame(BonbonellaGameController controller) {
        this.controller = controller;
        world = new World(GRAVITY, true);
        world.setAutoClearForces(true);
        world.setContactListener(this);

        level = new Level1(world, controller);
        addActor(level);

        bonbonella = new Bonbonella(world, controller);
        bonbonella.setStartposition(level.getStartposition().x, level.getStartposition().y);
        bonbonella.resetPosition();
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

    public void draw(Camera camera) {
        //
        // Call level drawBackground before drawing all actors
        //
        float w,h,x,y;
        w = camera.viewportWidth;
        h = camera.viewportHeight;
        x = camera.position.x;
        y = camera.position.y;
        level.drawBackground(camera, getSpriteBatch());
        setViewport(w,h, true);
        camera.position.x = x;
        camera.position.y = y;

        //
        // Draw level and player.
        //
        super.draw();
    }

    @Override
    public void act(float timeSinceLastRender) {
        super.act(timeSinceLastRender);

        if (gameState == GameState.LEVEL) {
            level = level.getNextLevel();
            bonbonella.setStartposition(level.getStartposition().x, level.getStartposition().y);
            bonbonella.resetPosition();
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

            world.step(1 / 60f, 8, 3);
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
                handleBonbonellaCreepCollision(contact.getFixtureA(), contact.getFixtureB());
            }

        } else if(contact.getFixtureA().getBody().getUserData().getClass().isAssignableFrom(Cavitycreep.class)) {
            if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Bonbonella.class)) {
                //
                // Creep has collided with bonbonella
                //
                Log.i(this.getClass().getSimpleName(), "Cavitycreep hit bonbonella.");
                handleBonbonellaCreepCollision(contact.getFixtureB(), contact.getFixtureA());
            } else if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Tile.class)) {
                //
                // Creep has collided with a tile.
                //
                handleCreepHittingWall(contact.getFixtureB(), contact.getFixtureA());
            } else if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Cavitycreep.class)) {
                //
                // Creep has collided with another creep
                //
                handleCreepHittingCreep(contact.getFixtureA(), contact.getFixtureB());
            }
        } else if(contact.getFixtureA().getBody().getUserData().getClass().isAssignableFrom(Tile.class)) {
            if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Cavitycreep.class)) {
                //
                // Tile has collided with creep
                //
                handleCreepHittingWall(contact.getFixtureA(), contact.getFixtureB());
            } else if(contact.getFixtureB().getBody().getUserData().getClass().isAssignableFrom(Bonbonella.class)) {
                if((contact.getFixtureB().getBody().getPosition().y-convertToBox(Bonbonella.BONBONELLA_SIZE/2)) >
                        (contact.getFixtureA().getBody().getPosition().y)){
                }
            }
        }
    }

    private void handleBonbonellaCreepCollision(Fixture bonbonellaFixture, Fixture creepFixture) {
        if((bonbonellaFixture.getBody().getPosition().y-convertToBox(Bonbonella.BONBONELLA_SIZE/2)) >
                (creepFixture.getBody().getPosition().y)) {
            //
            // Bonbonella is over creep.
            //
            ((Enemy)creepFixture.getBody().getUserData()).die();

        } else {
            ((Bonbonella)bonbonellaFixture.getBody().getUserData()).die();
            if(((Enemy)creepFixture.getBody().getUserData()).getDirection().equals(Direction.LEFT) &&
                    ((Enemy)creepFixture.getBody().getUserData()).getBody().getPosition().x < bonbonellaFixture.getBody().getPosition().x) {
                ((Enemy)creepFixture.getBody().getUserData()).changeDirection();
            } else if(((Enemy)creepFixture.getBody().getUserData()).getDirection().equals(Direction.RIGHT) &&
                    ((Enemy)creepFixture.getBody().getUserData()).getBody().getPosition().x > bonbonellaFixture.getBody().getPosition().x) {
                ((Enemy)creepFixture.getBody().getUserData()).changeDirection();
            }
        }
    }

    private void handleCreepHittingCreep(Fixture firstCreepFixture, Fixture secondCreepFixture) {
        if(firstCreepFixture.getBody().getPosition().y >=
                secondCreepFixture.getBody().getPosition().y-convertToBox(Cavitycreep.CREEP_SIZE/2) &&
                firstCreepFixture.getBody().getPosition().y <=
                        secondCreepFixture.getBody().getPosition().y+convertToBox((Cavitycreep.CREEP_SIZE/2))) {
            //
            // Creep hit other creep
            //
            Cavitycreep theFirstCreep = (Cavitycreep) firstCreepFixture.getBody().getUserData();
            Cavitycreep theSecondCreep = (Cavitycreep) secondCreepFixture.getBody().getUserData();
            if(theFirstCreep.getDirection().equals(Direction.LEFT) &&
                    secondCreepFixture.getBody().getPosition().x < firstCreepFixture.getBody().getPosition().x) {
                theFirstCreep.changeDirection();
            } else if(theFirstCreep.getDirection().equals(Direction.RIGHT) &&
                    secondCreepFixture.getBody().getPosition().x > firstCreepFixture.getBody().getPosition().x) {
                theFirstCreep.changeDirection();
            }
            if(theSecondCreep.getDirection().equals(Direction.LEFT) &&
                    firstCreepFixture.getBody().getPosition().x < secondCreepFixture.getBody().getPosition().x) {
                theSecondCreep.changeDirection();
            } else if(theSecondCreep.getDirection().equals(Direction.RIGHT) &&
                    firstCreepFixture.getBody().getPosition().x > secondCreepFixture.getBody().getPosition().x) {
                theSecondCreep.changeDirection();
            }
        }
    }

    private void handleCreepHittingWall(Fixture wallFixture, Fixture creepFixture) {
        if(creepFixture.getBody().getPosition().y >=
                wallFixture.getBody().getPosition().y-convertToBox(16f/2) &&
                creepFixture.getBody().getPosition().y <=
                        wallFixture.getBody().getPosition().y+convertToBox((16f/2))) {
            //
            // Creep hit wall
            //
            Cavitycreep theCreep = (Cavitycreep) creepFixture.getBody().getUserData();
            if(theCreep.getDirection().equals(Direction.LEFT) &&
                wallFixture.getBody().getPosition().x < creepFixture.getBody().getPosition().x) {
                theCreep.changeDirection();
            } else if(theCreep.getDirection().equals(Direction.RIGHT) &&
                    wallFixture.getBody().getPosition().x > creepFixture.getBody().getPosition().x) {
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
