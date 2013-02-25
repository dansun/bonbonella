package nu.danielsundberg.droid.bonbonella.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Cavitycreep extends Actor {
    private static String BONBONELLA_ENEMY_SQUISH  = "bonbonella_enemy_squish.png";
    private static String BONBONELLA_ENEMY_WALK_LEFT_1  = "bonbonella_enemy_walk_left_1.png";
    private static String BONBONELLA_ENEMY_WALK_LEFT_2  = "bonbonella_enemy_walk_left_2.png";
    private static String BONBONELLA_ENEMY_WALK_LEFT_3  = "bonbonella_enemy_walk_left_3.png";
    private static String BONBONELLA_ENEMY_WALK_RIGHT_1  = "bonbonella_enemy_walk_right_1.png";
    private static String BONBONELLA_ENEMY_WALK_RIGHT_2  = "bonbonella_enemy_walk_right_2.png";
    private static String BONBONELLA_ENEMY_WALK_RIGHT_3  = "bonbonella_enemy_walk_right_3.png";

    public final static float CREEP_SIZE = 16f;
    private final static float MAX_RUNNING_SPEED = 1f;
    private final static float RUNNING_ANIMATION_SPEED = 125f;

    private Texture squishTexture,
            walkRight1Texture,
            walkRight2Texture,
            walkRight3Texture,
            walkLeft1Texture,
            walkLeft2Texture,
            walkLeft3Texture;

    private World world;

    private Texture lastTexture;
    private Body body;

    private long lastDraw = System.currentTimeMillis();
    private long timeSinceLastRunningAnimation = 0l;
    private Direction direction = (Math.random()<0.5)?Direction.LEFT:Direction.RIGHT;

    public Cavitycreep(World world, BonbonellaGameController controller, float positionX, float positionY) {
        this.world = world;

        boolean loading = false;
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_SQUISH)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_SQUISH, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_WALK_LEFT_1)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_WALK_LEFT_1, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_WALK_LEFT_2)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_WALK_LEFT_2, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_WALK_LEFT_3)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_WALK_LEFT_3, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_WALK_RIGHT_1)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_WALK_RIGHT_1, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_WALK_RIGHT_2)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_WALK_RIGHT_2, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_WALK_RIGHT_3)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_WALK_RIGHT_3, Texture.class);
            loading = true;
        }

        if(loading) {
            controller.getAssetManager().finishLoading();
        }

        squishTexture = controller.getAssetManager().get(BONBONELLA_ENEMY_SQUISH, Texture.class);
        walkRight1Texture = controller.getAssetManager().get(BONBONELLA_ENEMY_WALK_RIGHT_1, Texture.class);
        walkRight2Texture = controller.getAssetManager().get(BONBONELLA_ENEMY_WALK_RIGHT_2, Texture.class);
        walkRight3Texture = controller.getAssetManager().get(BONBONELLA_ENEMY_WALK_RIGHT_3, Texture.class);
        walkLeft1Texture = controller.getAssetManager().get(BONBONELLA_ENEMY_WALK_LEFT_1, Texture.class);
        walkLeft2Texture = controller.getAssetManager().get(BONBONELLA_ENEMY_WALK_LEFT_2, Texture.class);
        walkLeft3Texture = controller.getAssetManager().get(BONBONELLA_ENEMY_WALK_LEFT_3, Texture.class);
        lastTexture = walkRight1Texture;


        BodyDef bd = new BodyDef();
        bd.position.set(BonbonellaGame.convertToBox(positionX), BonbonellaGame.convertToBox(positionY));
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.linearDamping = 0.1f;
        bd.angularDamping = 0.5f;
        bd.fixedRotation = true;

        FixtureDef fd = new FixtureDef();
        fd.density = 0.1f;
        fd.friction = 0.1f;
        fd.restitution = 0.1f;

        PolygonShape creepbox = new PolygonShape();
        creepbox.setAsBox(BonbonellaGame.convertToBox(CREEP_SIZE / 2),
                BonbonellaGame.convertToBox(CREEP_SIZE / 2));

        body = world.createBody(bd);
        fd.shape = creepbox;
        body.createFixture(fd);
        body.setUserData(this);

        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(BonbonellaGame.convertToWorld(body.getPosition().x-BonbonellaGame.convertToBox(CREEP_SIZE/2)),
                BonbonellaGame.convertToWorld(body.getPosition().y-BonbonellaGame.convertToBox(CREEP_SIZE/2)));

    }

    public void act(float timeSinceLastRender) {
        super.act(timeSinceLastRender);
        if(direction.equals(Direction.LEFT)) {
            addForce(BonbonellaGame.convertToBox(-0.2f), 0f);
        } else {
            addForce(BonbonellaGame.convertToBox(0.2f),0f);
        }
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(BonbonellaGame.convertToWorld(body.getPosition().x-BonbonellaGame.convertToBox(CREEP_SIZE/2)),
                BonbonellaGame.convertToWorld(body.getPosition().y-BonbonellaGame.convertToBox(CREEP_SIZE/2)));
    }

    public void changeDirection() {
        if(direction.equals(Direction.LEFT)) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }

    }

    public void addForce(float x, float y) {
        if(Math.abs(body.getLinearVelocity().x)<MAX_RUNNING_SPEED) {
            body.applyForceToCenter(x, y);
        }
    }

    private float getRoundedLinearVelocityY() {
        DecimalFormat decimalFormat = new DecimalFormat( "#0.00" );
        return new BigDecimal(decimalFormat.format(body.getLinearVelocity().y)).floatValue();
    }

    private float getRoundedLinearVelocityX() {
        DecimalFormat decimalFormat = new DecimalFormat( "#0.00" );
        return new BigDecimal(decimalFormat.format(body.getLinearVelocity().x)).floatValue();
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

        float timeSinceLastdraw = System.currentTimeMillis()-lastDraw;


        Texture creepTexture = lastTexture;
        float velocityX = getRoundedLinearVelocityX();
        float velocityY = getRoundedLinearVelocityY();
        float currentAnimationSpeedCap =
                RUNNING_ANIMATION_SPEED - (RUNNING_ANIMATION_SPEED*(Math.abs(velocityX)/MAX_RUNNING_SPEED));

        if(velocityX > 0) {
            if(velocityY != 0) {
                //
                // Do nothing
                //
            } else {
                if(timeSinceLastRunningAnimation>currentAnimationSpeedCap) {
                    if(lastTexture.equals(walkRight1Texture)) {
                        creepTexture = walkRight2Texture;
                    } else if(lastTexture.equals(walkRight2Texture)) {
                        creepTexture = walkRight3Texture;
                    } else {
                        creepTexture = walkRight1Texture;
                    }
                    timeSinceLastRunningAnimation = 0l;
                }  else {
                    timeSinceLastRunningAnimation += timeSinceLastdraw;
                }
            }
        } else if(velocityX < 0) {
            if(velocityY != 0) {
                //
                // Do nothing
                //
            } else {
                if(timeSinceLastRunningAnimation>currentAnimationSpeedCap) {
                    if(lastTexture.equals(walkLeft1Texture)) {
                        creepTexture = walkLeft2Texture;
                    } else if(lastTexture.equals(walkLeft2Texture)) {
                        creepTexture = walkLeft3Texture;
                    } else {
                        creepTexture = walkLeft1Texture;
                    }
                    timeSinceLastRunningAnimation = 0l;
                }
                else {
                    timeSinceLastRunningAnimation += timeSinceLastdraw;
                }
            }
        }

        lastTexture = creepTexture;

        batch.getTransformMatrix().setToRotation(0f,0f,1f, getRotation());
        batch.draw(creepTexture, getX(), getY());
        batch.getTransformMatrix().setToRotation(0f,0f,1f, -getRotation());
        lastDraw = System.currentTimeMillis();

    }

    public enum Direction {
        LEFT, RIGHT
    }

}