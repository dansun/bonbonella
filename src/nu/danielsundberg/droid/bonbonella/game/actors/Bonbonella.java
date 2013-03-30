package nu.danielsundberg.droid.bonbonella.game.actors;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Bonbonella, epic bonbon hunter / pink princess.
 */
public class Bonbonella extends Actor {

    private static String BONBONELLA_SPRITE_JUMP_RIGHT  = "sprites/bonbonella/bonbonella_sprite_jump_right.png";
    private static String BONBONELLA_SPRITE_JUMP_LEFT   = "sprites/bonbonella/bonbonella_sprite_jump_left.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_1  = "sprites/bonbonella/bonbonella_sprite_run_left_1.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_2  = "sprites/bonbonella/bonbonella_sprite_run_left_2.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_3  = "sprites/bonbonella/bonbonella_sprite_run_left_3.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_1 = "sprites/bonbonella/bonbonella_sprite_run_right_1.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_2 = "sprites/bonbonella/bonbonella_sprite_run_right_2.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_3 = "sprites/bonbonella/bonbonella_sprite_run_right_3.png";
    private static String BONBONELLA_SPRITE_SLIDE_LEFT  = "sprites/bonbonella/bonbonella_sprite_slide_left.png";
    private static String BONBONELLA_SPRITE_SLIDE_RIGHT = "sprites/bonbonella/bonbonella_sprite_slide_right.png";
    private static String BONBONELLA_SPRITE_STAND       = "sprites/bonbonella/bonbonella_sprite_stand.png";

    private static String BONBONELLA_SOUND_JUMPS = "sound/bonbonella/bonbonella_jump.ogg";
    private static String BONBONELLA_SOUND_SUPER_JUMPS = "sound/bonbonella/bonbonella_super_jump.ogg";
    private static String BONBONELLA_SOUND_EATS_BONBON_1 = "sound/bonbonella/bonbonella_oh_1.ogg";
    private static String BONBONELLA_SOUND_EATS_BONBON_2 = "sound/bonbonella/bonbonella_mhm_1.ogg";

    public final static float BONBONELLA_SLIDEVALUE = 0.001f;
    public final static float BONBONELLA_SIZE = 29f;
    private final static float MAX_RUNNING_SPEED = 200f;
    private final static float BONUS_JUMPING_SPEED = (MAX_RUNNING_SPEED/100)*90f;
    private final static float RUNNING_ANIMATION_SPEED = 125f;

    private Texture jumpRightTexture,
                    jumpLeftTexture,
                    runRight1Texture,
                    runRight2Texture,
                    runRight3Texture,
                    runLeft1Texture,
                    runLeft2Texture,
                    runLeft3Texture,
                    slideRightTexture,
                    slideLeftTexture,
                    standTexture;

    private Music jumpSound,
                  superJumpSound,
                  eatSound1,
                  eatSound2;
    private Texture currentTexture;
    private Texture lastTexture;
    private Body body;

    private int lives, score;
    private List<Bonbon> bonbons = new CopyOnWriteArrayList<Bonbon>();

    private long lastDraw = System.currentTimeMillis();
    private long timeSinceLastRunningAnimation = 0l;

    private Vector2 startposition = new Vector2(0f,0f);
    private BonbonellaState bonbonellaState = BonbonellaState.ALIVE;

    private Vector2 lastVelocity;

    private boolean isJumping = false;

    /**
     * Instance of Bonbonella, worlds cutest bonbon hunter / pink pricess.
     * @param world
     * @param controller
     */
    public Bonbonella(World world, BonbonellaGameController controller) {

        boolean loading = false;
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_JUMP_RIGHT)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_JUMP_RIGHT, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_JUMP_LEFT)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_JUMP_LEFT, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_RUN_LEFT_1)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_RUN_LEFT_1, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_RUN_LEFT_2)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_RUN_LEFT_2, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_RUN_LEFT_3)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_RUN_LEFT_3, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_RUN_RIGHT_1)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_RUN_RIGHT_1, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_RUN_RIGHT_2)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_RUN_RIGHT_2, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_RUN_RIGHT_3)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_RUN_RIGHT_3, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_SLIDE_LEFT)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_SLIDE_LEFT, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_SLIDE_RIGHT)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_SLIDE_RIGHT, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_STAND)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_STAND, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SOUND_JUMPS)) {
            controller.getAssetManager().load(BONBONELLA_SOUND_JUMPS, Music.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SOUND_EATS_BONBON_1)) {
            controller.getAssetManager().load(BONBONELLA_SOUND_EATS_BONBON_1, Music.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SOUND_EATS_BONBON_2)) {
            controller.getAssetManager().load(BONBONELLA_SOUND_EATS_BONBON_2, Music.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SOUND_SUPER_JUMPS)) {
            controller.getAssetManager().load(BONBONELLA_SOUND_SUPER_JUMPS, Music.class);
            loading = true;
        }
        if(loading) {
            controller.getAssetManager().finishLoading();
        }

        jumpRightTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_JUMP_RIGHT, Texture.class);
        jumpLeftTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_JUMP_LEFT, Texture.class);
        runRight1Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_RUN_RIGHT_1, Texture.class);
        runRight2Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_RUN_RIGHT_2, Texture.class);
        runRight3Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_RUN_RIGHT_3, Texture.class);
        runLeft1Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_RUN_LEFT_1, Texture.class);
        runLeft2Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_RUN_LEFT_2, Texture.class);
        runLeft3Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_RUN_LEFT_3, Texture.class);
        slideRightTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_SLIDE_RIGHT, Texture.class);
        slideLeftTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_SLIDE_LEFT, Texture.class);
        standTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_STAND, Texture.class);
        lastTexture = standTexture;
        currentTexture = lastTexture;

        superJumpSound = controller.getAssetManager().get(BONBONELLA_SOUND_SUPER_JUMPS, Music.class);
        jumpSound = controller.getAssetManager().get(BONBONELLA_SOUND_JUMPS, Music.class);
        eatSound1 = controller.getAssetManager().get(BONBONELLA_SOUND_EATS_BONBON_1, Music.class);
        eatSound2 = controller.getAssetManager().get(BONBONELLA_SOUND_EATS_BONBON_2, Music.class);

        createBody(world);

        lastVelocity = body.getLinearVelocity();
        lives = 3;
        score = 0;
    }

    /**
     * Adds score to bonbonella
     * @param score
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Create new bonbonella physics body
     * @param world
     */
    public void createBody(World world) {
        BodyDef bd = new BodyDef();
        bd.position.set(BonbonellaGame.convertToBox(32f), BonbonellaGame.convertToBox(32f));
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.linearDamping = 0.1f;
        bd.angularDamping = 0.5f;
        bd.fixedRotation = true;

        FixtureDef fd = new FixtureDef();
        fd.density = 0.1f;
        fd.friction = 0.1f;
        fd.restitution = 0.1f;

        PolygonShape bonbonellashape = new PolygonShape();
        bonbonellashape.setAsBox(BonbonellaGame.convertToBox(BONBONELLA_SIZE / 4), BonbonellaGame.convertToBox(BONBONELLA_SIZE / 2f));

        body = world.createBody(bd);
        fd.shape = bonbonellashape;
        body.createFixture(fd);
        body.setUserData(this);
    }

    public void eatBonbon(Bonbon bonbon) {
        bonbons.add(bonbon);
        addScore(bonbon.getScoreValue());
        if(!eatSound1.isPlaying() && !eatSound2.isPlaying()) {
            if(Math.random()<0.5) {
                eatSound1.play();
            } else {
                eatSound2.play();
            }
        }
    }

    /**
     * Return bonbonellas score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns true if bonbonella is dead.
     * @return
     */
    public boolean isDead() {
        return bonbonellaState.equals(BonbonellaState.DEAD);
    }

    /**
     * Sets bonbonellas start/reset position
     * @param x
     * @param y
     */
    public void setStartposition(float x, float y) {
        this.startposition.x = x;
        this.startposition.y = y;
    }

    /**
     * Kills bonbonella
     */
    public void die() {
        bonbonellaState = BonbonellaState.DEAD;
        lives--;
        for(Fixture fixture : body.getFixtureList()) {
            Filter filter = fixture.getFilterData();
            filter.maskBits= 0x0000;
            fixture.setFilterData(filter);
        }
        body.setLinearVelocity(0f,0f);
        addForcedImpulse(0,BonbonellaGame.convertToBox(2f));
    }

    /**
     * Reset position to previously set start position
     */
    public void resetPosition() {
        body.setTransform(BonbonellaGame.convertToBox(startposition.x),
                BonbonellaGame.convertToBox(startposition.y),
                body.getAngle());
        body.getLinearVelocity().x = 0;
        body.getLinearVelocity().y = 0;
        body.setLinearVelocity(0f,0f);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(BonbonellaGame.convertToWorld(body.getPosition().x - BonbonellaGame.convertToBox(BONBONELLA_SIZE / 2)),
                BonbonellaGame.convertToWorld(body.getPosition().y - BonbonellaGame.convertToBox(BONBONELLA_SIZE / 2) - BonbonellaGame.convertToBox(1f)));
        for(Fixture fixture : body.getFixtureList()) {
            Filter filter = fixture.getFilterData();
            filter.maskBits= (short)0xFFFF;
            fixture.setFilterData(filter);
        }
        bonbonellaState = BonbonellaState.ALIVE;
    }

    /**
     * Updates bonbonellas position and rotation
     * @param timeSinceLastRender
     */
    public void act(float timeSinceLastRender) {
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(BonbonellaGame.convertToWorld(body.getPosition().x-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)),
                    BonbonellaGame.convertToWorld(body.getPosition().y-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)-BonbonellaGame.convertToBox(1f)));
        //
        // Check if we should reset jumping
        //
        if(BonbonellaGame.round(body.getLinearVelocity().y, 2, false)<0 && isJumping) {
            //
            // Bonbonella is comming down (falling down or coming down from jump, no matter, we can reset it.)
            //
            isJumping = false;
        }
    }

    /**
     * Jumps bonbonella, jumps.
     */
    public void jump() {
        //
        // Verify that Bonbonella is not moving in y axis
        //
        if(BonbonellaGame.round(body.getLinearVelocity().y, 2, false)==0 && !isJumping) {
            //
            // Give boost if running at >= 90% of max speed else regular jump
            //
            if(Math.abs(BonbonellaGame.convertToWorld(body.getLinearVelocity().x)) >= BONUS_JUMPING_SPEED) {
                if(!superJumpSound.isPlaying()) {
                    superJumpSound.play();
                }
                addForcedImpulse(0, BonbonellaGame.convertToBox(2f));
            } else {
                if(!jumpSound.isPlaying()) {
                    jumpSound.play();
                }
                addForcedImpulse(0, BonbonellaGame.convertToBox(1.5f));
            }
            isJumping = true;
        }
    }

    /**
     * Add force to bonbonella physics body, limited in X axis by MAX_RUNNING_SPEED
     * @param x
     * @param y
     */
    public void addForce(float x, float y) {
        //
        // Limit speed in x axis to max running speed.
        //
        if(Math.abs(BonbonellaGame.convertToWorld(body.getLinearVelocity().x)) < MAX_RUNNING_SPEED) {
            body.applyForceToCenter(x, y);
        } else {
            body.applyForceToCenter(0f, y);
        }
    }

    /**
     * Add impulse to bonbonella physics body
     * @param x
     * @param y
     */
    public void addForcedImpulse(float x, float y) {
        body.applyLinearImpulse(x, y ,body.getPosition().x, body.getPosition().y);
    }

    /**
     * Returns number of lives bonbonella has left
     * @return
     */
    public int getLives() {
        return lives;
    }


    /**
     * Draws Bonbonella onto batch
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

        float timeSinceLastdraw = System.currentTimeMillis()-lastDraw;
        float velocityX = BonbonellaGame.round(body.getLinearVelocity().x, 2, false);
        float velocityY = BonbonellaGame.round(body.getLinearVelocity().y, 2, false);
        float currentAnimationSpeedCap =
                RUNNING_ANIMATION_SPEED - (RUNNING_ANIMATION_SPEED*(Math.abs(velocityX)/MAX_RUNNING_SPEED));

        //
        // Select sprite to render for Bonbonella
        //
        lastTexture = currentTexture;
        if(velocityX > 0) {
            if(velocityY != 0) {
                currentTexture = jumpRightTexture;
            } else {
                //
                // Slide or run
                //
                if(lastVelocity.x-body.getLinearVelocity().x>BONBONELLA_SLIDEVALUE) {
                    currentTexture = slideRightTexture;
                } else {
                    if(timeSinceLastRunningAnimation>currentAnimationSpeedCap) {
                        if(currentTexture.equals(runRight1Texture)) {
                            currentTexture = runRight2Texture;
                        } else if(currentTexture.equals(runRight2Texture)) {
                            if(lastTexture.equals(runRight1Texture)) {
                                currentTexture = runRight3Texture;
                            } else {
                                currentTexture = runRight1Texture;
                            }
                        } else {
                            currentTexture = runRight2Texture;
                        }
                        timeSinceLastRunningAnimation = 0l;
                    }  else {
                        timeSinceLastRunningAnimation += timeSinceLastdraw;
                    }
                }
            }
        } else if(velocityX < 0) {
            if(velocityY != 0) {
                currentTexture = jumpLeftTexture;
            } else {
                //
                // Slide or run
                //
                if(-(lastVelocity.x-body.getLinearVelocity().x)>BONBONELLA_SLIDEVALUE) {
                    currentTexture = slideLeftTexture;
                } else {
                    if(timeSinceLastRunningAnimation>currentAnimationSpeedCap) {
                        if(currentTexture.equals(runLeft1Texture)) {
                            currentTexture = runLeft2Texture;
                        } else if(currentTexture.equals(runLeft2Texture)) {
                            if(lastTexture.equals(runLeft1Texture)) {
                                currentTexture = runLeft3Texture;
                            } else {
                                currentTexture = runLeft1Texture;
                            }
                        } else {
                            currentTexture = runLeft2Texture;
                        }
                        timeSinceLastRunningAnimation = 0l;
                    }
                    else {
                        timeSinceLastRunningAnimation += timeSinceLastdraw;
                    }
                }
            }
        } else {
            currentTexture = standTexture;
        }

        //
        // Draw the selected sprite.
        //
        batch.getTransformMatrix().setToRotation(0f,0f,1f, getRotation());
        batch.draw(currentTexture, getX(), getY());
        batch.getTransformMatrix().setToRotation(0f,0f,1f, -getRotation());

        lastDraw = System.currentTimeMillis();
        lastVelocity = body.getLinearVelocity();

    }

    public enum BonbonellaState {
        ALIVE, DEAD;
    }

}
