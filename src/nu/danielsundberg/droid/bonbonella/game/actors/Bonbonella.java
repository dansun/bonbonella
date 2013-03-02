package nu.danielsundberg.droid.bonbonella.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;

/**
 *
 */
public class Bonbonella extends Actor {


    private static String BONBONELLA_SPRITE_JUMP_RIGHT  = "sprites/bonbonella_sprite_jump_right.png";
    private static String BONBONELLA_SPRITE_JUMP_LEFT   = "sprites/bonbonella_sprite_jump_left.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_1  = "sprites/bonbonella_sprite_run_left_1.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_2  = "sprites/bonbonella_sprite_run_left_2.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_3  = "sprites/bonbonella_sprite_run_left_3.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_1 = "sprites/bonbonella_sprite_run_right_1.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_2 = "sprites/bonbonella_sprite_run_right_2.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_3 = "sprites/bonbonella_sprite_run_right_3.png";
    private static String BONBONELLA_SPRITE_SLIDE_LEFT  = "sprites/bonbonella_sprite_slide_left.png";
    private static String BONBONELLA_SPRITE_SLIDE_RIGHT = "sprites/bonbonella_sprite_slide_right.png";
    private static String BONBONELLA_SPRITE_STAND       = "sprites/bonbonella_sprite_stand.png";

    public final static float BONBONELLA_SIZE = 32f;
    private final static float MAX_RUNNING_SPEED = 10f;
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

    private World world;

    private Texture currentTexture;
    private Texture lastTexture;
    private Body body;

    private int lives;
    private long lastDraw = System.currentTimeMillis();
    private long timeSinceLastRunningAnimation = 0l;

    private Vector2 startposition = new Vector2(0f,0f);
    private BonbonellaState bonbonellaState = BonbonellaState.ALIVE;

    public Bonbonella(World world, BonbonellaGameController controller) {
        this.world = world;

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

        PolygonShape bonbonellabox = new PolygonShape();
        bonbonellabox.setAsBox(BonbonellaGame.convertToBox(BONBONELLA_SIZE / 4), BonbonellaGame.convertToBox(BONBONELLA_SIZE / 2f));

        body = world.createBody(bd);
        fd.shape = bonbonellabox;
        body.createFixture(fd);
        body.setUserData(this);

        lives = 3;

    }

    public boolean isDead() {
        return bonbonellaState.equals(BonbonellaState.DEAD);
    }

    public void setStartposition(float x, float y) {
        this.startposition.x = x;
        this.startposition.y = y;
    }

    public void die() {
        bonbonellaState = BonbonellaState.DEAD;
        lives--;
        for(Fixture fixture : body.getFixtureList()) {
            Filter filter = fixture.getFilterData();
            filter.maskBits= 0x0000;
            fixture.setFilterData(filter);
        }
        body.applyForceToCenter(-BonbonellaGame.convertToBox(body.getLinearVelocity().x),
                -BonbonellaGame.convertToBox(body.getLinearVelocity().y));
        addForcedImpulse(0,BonbonellaGame.convertToBox(2f));

    }

    public void resetPosition() {
        body.setTransform(BonbonellaGame.convertToBox(startposition.x),
                BonbonellaGame.convertToBox(startposition.y),
                body.getAngle());
        body.applyForceToCenter(-BonbonellaGame.convertToBox(body.getLinearVelocity().x),
                -BonbonellaGame.convertToBox(body.getLinearVelocity().y));
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(BonbonellaGame.convertToWorld(body.getPosition().x-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)),
                BonbonellaGame.convertToWorld(body.getPosition().y-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)-BonbonellaGame.convertToBox(1f)));
        for(Fixture fixture : body.getFixtureList()) {
            Filter filter = fixture.getFilterData();
            filter.maskBits= (short)0xFFFF;
            fixture.setFilterData(filter);
        }
        bonbonellaState = BonbonellaState.ALIVE;
    }

    public void act(float timeSinceLastRender) {
        if(lives > 0) {

            setRotation(MathUtils.radiansToDegrees * body.getAngle());
            setPosition(BonbonellaGame.convertToWorld(body.getPosition().x-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)),
                    BonbonellaGame.convertToWorld(body.getPosition().y-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)-BonbonellaGame.convertToBox(1f)));
        }
    }

    public void addForce(float x, float y) {
        if(Math.abs(body.getLinearVelocity().x)<MAX_RUNNING_SPEED) {
            body.applyForceToCenter(x, y);
        } else {
            body.applyForceToCenter(0f, y);
        }
    }

    public void addImpulse(float x, float y) {
        if(BonbonellaGame.round(body.getLinearVelocity().y, 2, false)==0) {
            addForcedImpulse(x,y);
        }
    }

    public void addForcedImpulse(float x, float y) {
        body.applyLinearImpulse(x, y ,body.getPosition().x, body.getPosition().y);
    }

    public int getLives() {
        return lives;
    }


    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

        float timeSinceLastdraw = System.currentTimeMillis()-lastDraw;

        float velocityX = BonbonellaGame.round(body.getLinearVelocity().x, 2, false);
        float velocityY = BonbonellaGame.round(body.getLinearVelocity().y, 2, false);
        float currentAnimationSpeedCap =
                RUNNING_ANIMATION_SPEED - (RUNNING_ANIMATION_SPEED*(Math.abs(velocityX)/MAX_RUNNING_SPEED));

        lastTexture = currentTexture;
        if(velocityX > 0) {
            if(velocityY != 0) {
                currentTexture = jumpRightTexture;
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
        } else if(velocityX < 0) {
            if(velocityY != 0) {
                currentTexture = jumpLeftTexture;
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
        } else {
            currentTexture = standTexture;
        }



        batch.getTransformMatrix().setToRotation(0f,0f,1f, getRotation());
        batch.draw(currentTexture, getX(), getY());
        batch.getTransformMatrix().setToRotation(0f,0f,1f, -getRotation());
        lastDraw = System.currentTimeMillis();

    }


    public enum BonbonellaState {
        ALIVE, DEAD;
    }

}
