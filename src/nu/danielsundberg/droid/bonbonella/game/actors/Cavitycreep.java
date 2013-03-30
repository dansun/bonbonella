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
import nu.danielsundberg.droid.bonbonella.util.Direction;

public class Cavitycreep extends Actor implements Enemy {

    private static String BONBONELLA_ENEMY_SQUISH  = "sprites/enemies/cavitycreep/bonbonella_enemy_squish.png";
    private static String BONBONELLA_ENEMY_WALK_LEFT_1  = "sprites/enemies/cavitycreep/bonbonella_enemy_walk_left_1.png";
    private static String BONBONELLA_ENEMY_WALK_LEFT_2  = "sprites/enemies/cavitycreep/bonbonella_enemy_walk_left_2.png";
    private static String BONBONELLA_ENEMY_WALK_LEFT_3  = "sprites/enemies/cavitycreep/bonbonella_enemy_walk_left_3.png";
    private static String BONBONELLA_ENEMY_WALK_RIGHT_1  = "sprites/enemies/cavitycreep/bonbonella_enemy_walk_right_1.png";
    private static String BONBONELLA_ENEMY_WALK_RIGHT_2  = "sprites/enemies/cavitycreep/bonbonella_enemy_walk_right_2.png";
    private static String BONBONELLA_ENEMY_WALK_RIGHT_3  = "sprites/enemies/cavitycreep/bonbonella_enemy_walk_right_3.png";

    private static String BONBONELLA_ENEMY_SOUND_DIE = "sound/enemies/cavitycreep/enemy_dies.ogg";
    private static String BONBONELLA_ENEMY_CHUCKLE = "sound/enemies/cavitycreep/enemy_chuckle.ogg";

    public final static float CREEP_SIZE = 15.5f;
    private final static float MAX_RUNNING_SPEED = 1f;
    private final static float RUNNING_ANIMATION_SPEED = 125f;

    private Texture squishTexture,
            walkRight1Texture,
            walkRight2Texture,
            walkRight3Texture,
            walkLeft1Texture,
            walkLeft2Texture,
            walkLeft3Texture;

    private Music enemyDiesSound,
                  chuckleSound;

    private Texture lastTexture, currentTexture;
    private Body body;

    private int lives = 1;

    private long lastDraw = System.currentTimeMillis();
    private long timeSinceLastRunningAnimation = 0l;
    private Direction direction = (Math.random()<0.5)?Direction.LEFT:Direction.RIGHT;

    private Vector2 startposition;

    public Cavitycreep(World world, BonbonellaGameController controller, float positionX, float positionY) {

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
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_SOUND_DIE)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_SOUND_DIE, Music.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_ENEMY_CHUCKLE)) {
            controller.getAssetManager().load(BONBONELLA_ENEMY_CHUCKLE, Music.class);
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
        currentTexture = lastTexture;

        enemyDiesSound = controller.getAssetManager().get(BONBONELLA_ENEMY_SOUND_DIE, Music.class);
        chuckleSound = controller.getAssetManager().get(BONBONELLA_ENEMY_CHUCKLE, Music.class);

        startposition = new Vector2(positionX, positionY);

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

    public Body getBody() {
        return body;
    }

    public void chuckle() {
        if(!chuckleSound.isPlaying()) {
            chuckleSound.play();
        }
    }

    public void die() {
        lives--;
        if(lives <= 0) {
            Filter noContactFilter = new Filter();
            noContactFilter.maskBits = 0x0000;
            for(Fixture fixture : body.getFixtureList()) {
                fixture.setFilterData(noContactFilter);
            }
            if(!enemyDiesSound.isPlaying()) {
                enemyDiesSound.play();
            }
        }
    }

    public void act(float timeSinceLastRender) {
        if(lives > 0) {
            //
            // Move creep
            //
            if(direction.equals(Direction.LEFT)) {
                addForce(BonbonellaGame.convertToBox(-0.2f), 0f);
            } else {
                addForce(BonbonellaGame.convertToBox(0.2f),0f);
            }
        }
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(BonbonellaGame.convertToWorld(body.getPosition().x-BonbonellaGame.convertToBox(CREEP_SIZE/2)),
                BonbonellaGame.convertToWorld(body.getPosition().y-BonbonellaGame.convertToBox(CREEP_SIZE/2)-BonbonellaGame.convertToBox(1f)));

        //
        // Avoid enemy getting stuck.
        //
        if(body.getLinearVelocity().len()==0) {
            if(getDirection().equals(Direction.LEFT)) {
                addImpulse(BonbonellaGame.convertToBox(-0.01f),BonbonellaGame.convertToBox(0.01f));
            } else {
                addImpulse(BonbonellaGame.convertToBox(0.01f),BonbonellaGame.convertToBox(0.01f));
            }
        }

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

    public void addImpulse(float x, float y) {
        body.applyLinearImpulse(x, y ,body.getPosition().x, body.getPosition().y);
    }

    public Direction getDirection() {
        return direction;
    }

    public int getScoreValue() {
        return 100;
    }

    public void resetPosition() {
        body.setTransform(BonbonellaGame.convertToBox(startposition.x),
                BonbonellaGame.convertToBox(startposition.y),
                body.getAngle());
        body.getLinearVelocity().x = 0;
        body.getLinearVelocity().y = 0;
        body.setLinearVelocity(0f,0f);
        setRotation(MathUtils.radiansToDegrees * body.getAngle());
        setPosition(BonbonellaGame.convertToWorld(body.getPosition().x -
                BonbonellaGame.convertToBox(Cavitycreep.CREEP_SIZE / 2)),
                BonbonellaGame.convertToWorld(body.getPosition().y -
                        BonbonellaGame.convertToBox(Cavitycreep.CREEP_SIZE / 2) -
                        BonbonellaGame.convertToBox(1f)));
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

        float timeSinceLastdraw = System.currentTimeMillis()-lastDraw;

        float velocityX = BonbonellaGame.round(body.getLinearVelocity().x, 2, false);
        float velocityY = BonbonellaGame.round(body.getLinearVelocity().y, 2, false);
        float currentAnimationSpeedCap =
                RUNNING_ANIMATION_SPEED - (RUNNING_ANIMATION_SPEED*(Math.abs(velocityX)/MAX_RUNNING_SPEED));
        lastTexture = currentTexture;
        if(lives > 0) {
            if(velocityX > 0) {
                if(velocityY != 0) {
                    //
                    // Do nothing
                    //
                } else {
                    if(timeSinceLastRunningAnimation>currentAnimationSpeedCap) {
                        if(currentTexture.equals(walkRight1Texture)) {
                            currentTexture = walkRight2Texture;
                        } else if(currentTexture.equals(walkRight2Texture)) {
                            if(lastTexture.equals(walkRight1Texture)) {
                                currentTexture = walkRight3Texture;
                            } else {
                                currentTexture = walkRight1Texture;
                            }
                        } else {
                            currentTexture = walkRight2Texture;
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
                        if(currentTexture.equals(walkLeft1Texture)) {
                            currentTexture = walkLeft2Texture;
                        } else if(currentTexture.equals(walkLeft2Texture)) {
                            if(lastTexture.equals(walkLeft1Texture)) {
                                currentTexture = walkLeft3Texture;
                            } else {
                                currentTexture = walkLeft1Texture;
                            }
                        } else {
                            currentTexture = walkLeft2Texture;
                        }
                        timeSinceLastRunningAnimation = 0l;
                    }
                    else {
                        timeSinceLastRunningAnimation += timeSinceLastdraw;
                    }
                }
            }
        } else {
            currentTexture = squishTexture;
        }

        batch.getTransformMatrix().setToRotation(0f,0f,1f, getRotation());
        batch.draw(currentTexture, getX(), getY());
        batch.getTransformMatrix().setToRotation(0f,0f,1f, -getRotation());
        lastDraw = System.currentTimeMillis();

    }

}
