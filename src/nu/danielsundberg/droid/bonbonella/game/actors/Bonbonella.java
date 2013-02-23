package nu.danielsundberg.droid.bonbonella.game.actors;

import android.util.Log;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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

    private float statetime = 0f;
    private Texture lastTexture;

    private World world;
    private Body body;

    private int lives;

    private final static float MAX_RUNNING_SPEED = 10f;

    public Bonbonella(World world, BonbonellaGameController controller) {
        this.world = world;

        this.setWidth(32);
        this.setHeight(32);

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
        bd.position.set(0, BonbonellaGame.convertToBox(10f));
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.linearDamping = 0.1f;
        bd.angularDamping = 0.5f;

        FixtureDef fd = new FixtureDef();
        fd.density = 0.1f;
        fd.friction = 0.1f;
        fd.restitution = 0.1f;


        PolygonShape bonbonellabox = new PolygonShape();
        bonbonellabox.setAsBox(BonbonellaGame.convertToBox(getWidth()), BonbonellaGame.convertToBox(getHeight()));

        body = world.createBody(bd);
        fd.shape = bonbonellabox;
        body.createFixture(fd);

        lives = 3;

    }

    private long lastLog = System.currentTimeMillis();
    private long lastDraw = System.currentTimeMillis();

    public void act(float timeSinceLastRender) {
        super.act(timeSinceLastRender);
        statetime += timeSinceLastRender;

        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(BonbonellaGame.convertToWorld(body.getPosition().x/2),
                BonbonellaGame.convertToWorld(body.getPosition().y/2));
    }


    public void addForce(float x, float y) {
        body.applyForceToCenter(x, y);
    }

    public void addImpulse(float x, float y) {
        if(getRoundedLinearVelocityY()==0) {
            body.applyLinearImpulse(x, y ,body.getPosition().x, body.getPosition().y);
        }
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

        Texture bonbonellaTexture = lastTexture;
        float velocityX = getRoundedLinearVelocityX();
        float velocityY = getRoundedLinearVelocityY();
        if(System.currentTimeMillis()-lastLog>2000) {
            Log.i(getClass().getSimpleName(), "Vx:" + velocityX +
                    " Vy:" + velocityY);
            lastLog = System.currentTimeMillis();
        }
        if(velocityX > 0) {
            if(velocityY != 0) {
                bonbonellaTexture = jumpRightTexture;
            } else {
                if(System.currentTimeMillis()-lastDraw>10/velocityX) {
                    if(lastTexture.equals(runRight1Texture)) {
                        bonbonellaTexture = runRight2Texture;
                    } else if(lastTexture.equals(runRight2Texture)) {
                        bonbonellaTexture = runRight3Texture;
                    } else {
                        bonbonellaTexture = runRight1Texture;
                    }
                }
            }
        } else if(velocityX < 0) {
            if(velocityY != 0) {
                bonbonellaTexture = jumpLeftTexture;
            } else {
                if(System.currentTimeMillis()-lastDraw>10/-velocityX) {
                    if(lastTexture.equals(runLeft1Texture)) {
                        bonbonellaTexture = runLeft2Texture;
                    } else if(lastTexture.equals(runLeft2Texture)) {
                        bonbonellaTexture = runLeft3Texture;
                    } else {
                        bonbonellaTexture = runLeft1Texture;
                    }
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
