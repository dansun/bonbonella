package nu.danielsundberg.droid.bonbonella.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 */
public class Bonbonella extends Actor {


    private static String BONBONELLA_SPRITE_JUMP_RIGHT  = "bonbonella_sprite_jump_right.png";
    private static String BONBONELLA_SPRITE_JUMP_LEFT   = "bonbonella_sprite_jump_left.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_1  = "bonbonella_sprite_run_left_1.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_2  = "bonbonella_sprite_run_left_2.png";
    private static String BONBONELLA_SPRITE_RUN_LEFT_3  = "bonbonella_sprite_run_left_3.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_1 = "bonbonella_sprite_run_right_1.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_2 = "bonbonella_sprite_run_right_2.png";
    private static String BONBONELLA_SPRITE_RUN_RIGHT_3 = "bonbonella_sprite_run_right_3.png";
    private static String BONBONELLA_SPRITE_SLIDE_LEFT  = "bonbonella_sprite_slide_left.png";
    private static String BONBONELLA_SPRITE_SLIDE_RIGHT = "bonbonella_sprite_slide_right.png";
    private static String BONBONELLA_SPRITE_STAND       = "bonbonella_sprite_stand.png";

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

    private Texture lastTexture;
    private Body body;

    private int lives;
    private long lastLog = System.currentTimeMillis();
    private long lastDraw = System.currentTimeMillis();
    private long timeSinceLastRunningAnimation = 0l;
    private Vector2 lastGoodPosition;

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
        lastGoodPosition = new Vector2(bd.position.x, bd.position.y);

    }

    public void die() {
        lives--;
        if(lives <= 0) {
            Filter noContactFilter = new Filter();
            noContactFilter.maskBits = 0x0000;
            for(Fixture fixture : body.getFixtureList()) {
                fixture.setFilterData(noContactFilter);
            }
        }
    }


    public void act(float timeSinceLastRender) {
        super.act(timeSinceLastRender);
        if(lives > 0) {
            if(body.getPosition().y < 0-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)) {
                die();
                body.setTransform(lastGoodPosition.x, lastGoodPosition.y, body.getAngle());
                body.applyForceToCenter(-BonbonellaGame.convertToBox(body.getLinearVelocity().x),
                        -BonbonellaGame.convertToBox(body.getLinearVelocity().y));
            }
            setRotation(MathUtils.radiansToDegrees * body.getAngle());
            setPosition(BonbonellaGame.convertToWorld(body.getPosition().x-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)),
                    BonbonellaGame.convertToWorld(body.getPosition().y-BonbonellaGame.convertToBox(BONBONELLA_SIZE/2)));
        } else {
            if(body.getPosition().y < 0) {

            }
        }

    }



    public void updateLastGoodPosition(float x, float y) {
        lastGoodPosition.x = x;
        lastGoodPosition.y = y;
    }


    public void addForce(float x, float y) {
        if(Math.abs(body.getLinearVelocity().x)<MAX_RUNNING_SPEED) {
            body.applyForceToCenter(x, y);
        } else {
            body.applyForceToCenter(0f, y);
        }
    }

    public void addImpulse(float x, float y) {
        if(getRoundedLinearVelocityY()==0) {
            addForcedImpulse(x,y);
        }
    }

    public void addForcedImpulse(float x, float y) {
        body.applyLinearImpulse(x, y ,body.getPosition().x, body.getPosition().y);
    }

    public int getLives() {
        return lives;
    }

    private float getRoundedLinearVelocityY() {
        DecimalFormat decimalFormat = new DecimalFormat( "#0.00" );
        return new BigDecimal(decimalFormat.format(body.getLinearVelocity().y)).floatValue();
    }

    private float getRoundedLinearVelocityX() {
        DecimalFormat decimalFormat = new DecimalFormat( "#0.00" );
        return new BigDecimal(decimalFormat.format(body.getLinearVelocity().x)).floatValue();
    }



    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

        float timeSinceLastdraw = System.currentTimeMillis()-lastDraw;


        Texture bonbonellaTexture = lastTexture;
        float velocityX = getRoundedLinearVelocityX();
        float velocityY = getRoundedLinearVelocityY();
        float currentAnimationSpeedCap =
                RUNNING_ANIMATION_SPEED - (RUNNING_ANIMATION_SPEED*(Math.abs(velocityX)/MAX_RUNNING_SPEED));
        if(velocityX > 0) {
            if(velocityY != 0) {
                bonbonellaTexture = jumpRightTexture;
            } else {
                if(timeSinceLastRunningAnimation>currentAnimationSpeedCap) {
                    if(lastTexture.equals(runRight1Texture)) {
                        bonbonellaTexture = runRight2Texture;
                    } else if(lastTexture.equals(runRight2Texture)) {
                        bonbonellaTexture = runRight3Texture;
                    } else {
                        bonbonellaTexture = runRight1Texture;
                    }
                    timeSinceLastRunningAnimation = 0l;
                }  else {
                    timeSinceLastRunningAnimation += timeSinceLastdraw;
                }
            }
        } else if(velocityX < 0) {
            if(velocityY != 0) {
                bonbonellaTexture = jumpLeftTexture;
            } else {
                if(timeSinceLastRunningAnimation>currentAnimationSpeedCap) {
                    if(lastTexture.equals(runLeft1Texture)) {
                        bonbonellaTexture = runLeft2Texture;
                    } else if(lastTexture.equals(runLeft2Texture)) {
                        bonbonellaTexture = runLeft3Texture;
                    } else {
                        bonbonellaTexture = runLeft1Texture;
                    }
                    timeSinceLastRunningAnimation = 0l;
                }
                else {
                    timeSinceLastRunningAnimation += timeSinceLastdraw;
                }
            }
        } else {
            bonbonellaTexture = standTexture;
        }

        lastTexture = bonbonellaTexture;

        batch.getTransformMatrix().setToRotation(0f,0f,1f, getRotation());
        batch.draw(bonbonellaTexture, getX(), getY());
        batch.getTransformMatrix().setToRotation(0f,0f,1f, -getRotation());
        lastDraw = System.currentTimeMillis();

    }



}
