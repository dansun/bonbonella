package nu.danielsundberg.droid.bonbonella.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbonella;
import nu.danielsundberg.droid.bonbonella.game.actors.Cavitycreep;
import nu.danielsundberg.droid.bonbonella.game.actors.Enemy;
import nu.danielsundberg.droid.bonbonella.game.levels.*;
import nu.danielsundberg.droid.bonbonella.util.Direction;

import java.math.BigDecimal;

/**
 * Game handler for an instance of a bonbonella game
 */
public class BonbonellaGame extends Stage implements ContactListener {

    private static String BONBONELLA_DEATH_SOUND       = "sound/bonbonella_dies.ogg";
    private static String BONBONELLA_RUNNING_SOUND       = "sound/bonbonella_running.ogg";

    public final static Vector2 GRAVITY = new Vector2(0, -9.82f);

    private BonbonellaGameController controller;

    private World world;
    private Bonbonella bonbonella;
    private Level level;
    private GameState gameState;

    private Music deathMusic;
    private Music runningMusic;

    public BitmapFont FONT = new BitmapFont(Gdx.files.internal("fonts/bonbonella.fnt"), false);
    public static final float WORLD_TO_BOX=0.01f;
    public static final float BOX_TO_WORLD=100f;

    public BonbonellaGame(BonbonellaGameController controller) {
        this.controller = controller;

        boolean loading = false;
        if(!controller.getAssetManager().isLoaded(BONBONELLA_DEATH_SOUND)) {
            controller.getAssetManager().load(BONBONELLA_DEATH_SOUND, Music.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_RUNNING_SOUND)) {
            controller.getAssetManager().load(BONBONELLA_RUNNING_SOUND, Music.class);
            loading = true;
        }
        if(loading) {
            controller.getAssetManager().finishLoading();
        }

        deathMusic = controller.getAssetManager().get(BONBONELLA_DEATH_SOUND, Music.class);
        runningMusic = controller.getAssetManager().get(BONBONELLA_RUNNING_SOUND, Music.class);

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

        //
        // Draw hud
        //
        getSpriteBatch().begin();
        FONT.setScale(0.75f);
        FONT.setColor(Color.WHITE);
        FONT.draw(getSpriteBatch(), "Bonbonella X " + bonbonella.getLives() + "  Score " + bonbonella.getScore() + "  Time " + level.getTimeLeft() + "",
                camera.position.x - camera.viewportWidth / 2 + FONT.getLineHeight(),
                camera.position.y + camera.viewportHeight / 2 - FONT.getLineHeight());
        getSpriteBatch().end();
    }

    @Override
    public void act(float timeSinceLastRender) {
        super.act(timeSinceLastRender);

        if (gameState == GameState.LEVEL) {


            //
            // Replace world with new world,
            // restart level and bonbonella.
            //
            bonbonella.remove();
            level.remove();
            world = new World(GRAVITY, true);
            world.setAutoClearForces(true);
            world.setContactListener(this);
            level = level.getNextLevel(world);
            addActor(level);
            bonbonella.createBody(world);
            bonbonella.setStartposition(level.getStartposition().x, level.getStartposition().y);
            bonbonella.resetPosition();
            addActor(bonbonella);
            addActor(level);
            gameState = GameState.PLAY;
        }

        if (gameState == GameState.PLAY) {
            if(!runningMusic.isPlaying()) {
                runningMusic.setLooping(true);
                runningMusic.play();
            }
            if(Gdx.input.isTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.input.getY();
                if(touchX < Gdx.graphics.getWidth()/2) {
                    bonbonella.addForce(convertToBox(-1f), 0f);
                } else {
                    bonbonella.addForce(convertToBox(1f),0f);
                }
                if(touchY < Gdx.graphics.getHeight()/2) {
                    bonbonella.jump();
                }
            }

            world.step(1 / 60f, 8, 3);
            bonbonella.act(timeSinceLastRender);
            level.act(timeSinceLastRender);

            if(!bonbonella.isDead() &&
                    bonbonella.getY() < 0 - Bonbonella.BONBONELLA_SIZE/2) {
                killBonbonella();
            } else if(level.getTimeLeft() <= 0) {
                killBonbonella();
            }
        }

        if(gameState == GameState.DIED) {
            world.step(1 / 60f, 8, 3);
            bonbonella.act(timeSinceLastRender);
            if(bonbonella.isDead()&&!deathMusic.isPlaying()) {
                if (bonbonella.getLives() == 0) {
                    gameState = GameState.GAMEOVER;
                } else {
                    if(bonbonella.getY() < 0 -Bonbonella.BONBONELLA_SIZE/2) {
                        bonbonella.resetPosition();
                        level.resetTimeLeft();
                        gameState = GameState.PLAY;
                    }
                }
            }
        }

    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void killBonbonella() {
        bonbonella.die();
        runningMusic.stop();
        deathMusic.play();
        gameState = GameState.DIED;
    }

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getBody().getUserData() instanceof Bonbonella) {
            if(contact.getFixtureB().getBody().getUserData() instanceof Cavitycreep) {
                //
                // Bonbonella has collided with a creep.
                //
                handleBonbonellaCreepCollision(contact.getFixtureA(), contact.getFixtureB());
            }

        } else if(contact.getFixtureA().getBody().getUserData() instanceof Cavitycreep) {
            if(contact.getFixtureB().getBody().getUserData().getClass().equals(Bonbonella.class)) {
                //
                // Creep has collided with bonbonella
                //
                handleBonbonellaCreepCollision(contact.getFixtureB(), contact.getFixtureA());
            } else if(contact.getFixtureB().getBody().getUserData() instanceof Tile) {
                //
                // Creep has collided with a tile.
                //
                handleCreepHittingWall(contact.getFixtureB(), contact.getFixtureA());
            } else if(contact.getFixtureB().getBody().getUserData() instanceof  Cavitycreep) {
                //
                // Creep has collided with another creep
                //
                handleCreepHittingCreep(contact.getFixtureA(), contact.getFixtureB());
            }
        } else if(contact.getFixtureA().getBody().getUserData() instanceof Tile) {
            if(contact.getFixtureB().getBody().getUserData() instanceof Cavitycreep) {
                //
                // Tile has collided with creep
                //
                handleCreepHittingWall(contact.getFixtureA(), contact.getFixtureB());
            } else if(contact.getFixtureB().getBody().getUserData() instanceof Bonbonella) {
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
            bonbonella.addScore(((Enemy)creepFixture.getBody().getUserData()).getScoreValue());
        } else {
            ((Enemy)creepFixture.getBody().getUserData()).chuckle();
            killBonbonella();
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
    public void preSolve(Contact contact, Manifold manifold) {

        if(contact.getFixtureA().getBody().getUserData() instanceof Tile) {
            //
            // Check for bonbon
            //
            if(contact.getFixtureA().getBody().getUserData() instanceof Bonbon) {
                if(contact.getFixtureB().getBody().getUserData() instanceof Bonbonella) {
                    bonbonella.eatBonbon(((Bonbon)contact.getFixtureA().getBody().getUserData()));
                    level.removeTile(((Tile)contact.getFixtureA().getBody().getUserData()));
                    contact.setEnabled(false);
                } else if(contact.getFixtureB().getBody().getUserData() instanceof Cavitycreep ) {
                    contact.setEnabled(false);
                }
            }
            //
            // Check for finish
            //
            if(contact.getFixtureA().getBody().getUserData() instanceof Finish) {
                if(contact.getFixtureB().getBody().getUserData() instanceof Bonbonella) {
                    gameState = GameState.LEVEL;
                    contact.setEnabled(false);
                } else if(contact.getFixtureB().getBody().getUserData() instanceof Cavitycreep ) {
                    contact.setEnabled(false);
                }
            }
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
    }

    public enum GameState {
        PLAY, DIED, LEVEL, GAMEOVER
    }


}
