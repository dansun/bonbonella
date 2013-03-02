package nu.danielsundberg.droid.bonbonella.game.levels;

import android.util.Log;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbonella;
import nu.danielsundberg.droid.bonbonella.game.actors.Cavitycreep;
import nu.danielsundberg.droid.bonbonella.game.actors.Enemy;

/**
 * Level 1 of Bonbonella game
 */
public class Level1 extends Level {

    protected BonbonellaGameController controller;

    public static final String BONBONELLA_SPRITE_INVISIBLE = "sprites/bonbonella_invisible_tile.png";
    public static final String BONBONELLA_SPRITE_GROUND_EDGE_LEFT = "sprites/bonbonella_ground_edge_left.png";
    public static final String BONBONELLA_SPRITE_GROUND_EDGE_RIGHT = "sprites/bonbonella_ground_edge_right.png";
    public static final String BONBONELLA_SPRITE_GROUND_1 = "sprites/bonbonella_ground_1.png";
    public static final String BONBONELLA_SPRITE_GROUND_2 = "sprites/bonbonella_ground_2.png";
    public static final String BONBONELLA_SPRITE_GROUND_3 = "sprites/bonbonella_ground_3.png";
    public static final String BONBONELLA_SPRITE_GROUND_4 = "sprites/bonbonella_ground_4.png";
    public static final String BONBONELLA_SPRITE_STONE = "sprites/bonbonella_stone_tile.png";


    public static final String BONBONELLA_BACKGROUND_SKY = "sprites/bonbonella_background_sky.png";
    public static final String BONBONELLA_BACKGROUND_1 = "sprites/bonbonella_background_1.png";
    public static final String BONBONELLA_BACKGROUND_2 = "sprites/bonbonella_background_2.png";
    public static final String BONBONELLA_BACKGROUND_CLOUDS = "sprites/bonbonella_background_clouds.png";

    private Texture invisibleTexture;
    private Texture ground1Texture;
    private Texture ground2Texture;
    private Texture ground3Texture;
    private Texture ground4Texture;
    private Texture groundEdgeLeftTexture;
    private Texture groundEdgeRightTexture;
    private Texture stoneTexture;
    private Texture backgroundSkyTexture;
    private Texture background1Texture;
    private Texture background2Texture;
    private Texture backgroundCloudsTexture;

    private Vector2 startposition;

    private static final String[] LEVEL1 =
           {"S S                                                                                    SSSSSSSSSSSSSSSSSSSSS                                                                                                                  S S S",
            "SSS                                                                                    SS                                                                                                                                     SSSSS",
            "SSS                                                                                    SS                                                                                                                                     SSSSS",
            "SSS                                                                                    SS                                                                                                                                     SSSSS",
            "SS                                                                                     SS    SSSSSSSSSSSSSSSSS                                    SSSSS                                                                          SS",
            "SS                                                                                     SS                   SS                                                                                                                   SS",
            "SS                                                                                     SS                   SS                                               SSSSS                                                               SS",
            "SS                                                                                     SS                                                                                                                                       FSS",
            "SS                                                                                     SSSSSSSSSSSSSS                                                                                                                        SSSSSS",
            "SS  X                    SS    SS                                                                                                                                                                                                 S",
            "SS                     SSSS    SSSS                   SSSS                                                                                                                                                                        S",
            "SS                C  SSSSSS    SSSSSS        C        SSSS                                                         S        C             S    C                               S                                                  S",
            "MMMMMMMMMMMMMMMMMMMMMMMMMMR    LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR  LR  LR  LR  LMMMMMMMMMMMMMMMMMMMMMMMR    LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM"};


    public Level1(World world, BonbonellaGameController controller) {
        super(world, controller, LEVEL1);
        this.controller = controller;

        boolean loading = false;
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_GROUND_1)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_GROUND_1, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_GROUND_2)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_GROUND_2, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_GROUND_3)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_GROUND_3, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_GROUND_4)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_GROUND_4, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_GROUND_EDGE_LEFT)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_GROUND_EDGE_LEFT, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_GROUND_EDGE_RIGHT)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_GROUND_EDGE_RIGHT, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_INVISIBLE)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_INVISIBLE, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_STONE)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_STONE, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_BACKGROUND_CLOUDS)) {
            controller.getAssetManager().load(BONBONELLA_BACKGROUND_CLOUDS, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_BACKGROUND_2)) {
            controller.getAssetManager().load(BONBONELLA_BACKGROUND_2, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_BACKGROUND_1)) {
            controller.getAssetManager().load(BONBONELLA_BACKGROUND_1, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(BONBONELLA_BACKGROUND_SKY)) {
            controller.getAssetManager().load(BONBONELLA_BACKGROUND_SKY, Texture.class);
            loading = true;
        }
        if(loading) {
            controller.getAssetManager().finishLoading();
        }

        invisibleTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_INVISIBLE, Texture.class);
        ground1Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_GROUND_1, Texture.class);
        ground2Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_GROUND_2, Texture.class);
        ground3Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_GROUND_3, Texture.class);
        ground4Texture = controller.getAssetManager().get(BONBONELLA_SPRITE_GROUND_4, Texture.class);
        groundEdgeLeftTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_GROUND_EDGE_LEFT, Texture.class);
        groundEdgeRightTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_GROUND_EDGE_RIGHT, Texture.class);
        stoneTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_STONE, Texture.class);
        background1Texture = controller.getAssetManager().get(BONBONELLA_BACKGROUND_1, Texture.class);
        background2Texture = controller.getAssetManager().get(BONBONELLA_BACKGROUND_2, Texture.class);
        backgroundCloudsTexture = controller.getAssetManager().get(BONBONELLA_BACKGROUND_CLOUDS, Texture.class);
        backgroundSkyTexture = controller.getAssetManager().get(BONBONELLA_BACKGROUND_SKY, Texture.class);

        for(int tileRow = GROUND_MAP.length-1; tileRow >= 0; tileRow--) {
            for(int tileColumn = 0; tileColumn < GROUND_MAP[tileRow].length(); tileColumn++) {

                Texture groundTexture = null;

                switch(GROUND_MAP[tileRow].charAt(tileColumn)) {
                    case 'L':
                        groundTexture = groundEdgeLeftTexture;
                        break;
                    case 'M':
                        switch(1 + (int)(Math.random() * ((4 - 1) + 1))) {
                            case 1:
                                groundTexture = ground1Texture;
                                break;
                            case 2:
                                groundTexture = ground2Texture;
                                break;
                            case 3:
                                groundTexture = ground3Texture;
                                break;
                            case 4:
                                groundTexture = ground4Texture;
                                break;
                        }
                        break;
                    case 'R':
                        groundTexture = groundEdgeRightTexture;
                        break;
                    case 'I':
                        groundTexture = invisibleTexture;
                        break;
                    case 'S':
                        groundTexture = stoneTexture;
                        break;
                    case 'C':
                        enemies.add(new Cavitycreep(world, controller,
                               (Cavitycreep.CREEP_SIZE*tileColumn) + (Cavitycreep.CREEP_SIZE/2),
                               (Cavitycreep.CREEP_SIZE*((GROUND_MAP.length-1)-tileRow)) + (Cavitycreep.CREEP_SIZE/2)));
                        break;
                    case 'F':
                        Body finishBody = createBodyForTile(tileColumn, tileRow);
                        finishBody.setUserData(finishBody);
                        Finish finish = new Finish(invisibleTexture, finishBody);
                        groundTiles.add(finish);
                        break;
                    case 'X':
                        this.startposition = new Vector2(Tile.TILE_SIZE*tileColumn + Bonbonella.BONBONELLA_SIZE/2 ,
                                ((GROUND_MAP.length-1)-tileRow)*Tile.TILE_SIZE + Bonbonella.BONBONELLA_SIZE/2);
                        break;
                    default:
                        break;
                }

                if(groundTexture!=null) {
                    Body groundBody = createBodyForTile(tileColumn, tileRow);
                    Tile tile = new Tile(groundTexture, groundBody);
                    groundBody.setUserData(tile);
                    groundTiles.add(tile);
                }
            }
        }
    }

    public Vector2 getStartposition() {
        return this.startposition;
    }

    @Override
    public void act(float delta) {
        for(Enemy creep : enemies) {
            //
            // Check for removal
            //
            if(creep.getBody().getPosition().y < 0f) {
                world.destroyBody(creep.getBody());
                enemies.remove(creep);
            } else {
                creep.act(delta);
            }
        }
    }

    public Level getNextLevel() {
        return new Level1(world, controller);
    }


    private float lastX = 0;
    public void drawBackground(Camera camera, SpriteBatch batch) {

        camera.viewportWidth = backgroundSkyTexture.getWidth();
        camera.viewportHeight = backgroundSkyTexture.getHeight();

        batch.begin();
        float position, endOfTexture, endOfViewport, startOfViewport;
        endOfViewport = (camera.position.x + (camera.viewportWidth/2));
        startOfViewport = (camera.position.x - (camera.viewportWidth/2));

        batch.draw(backgroundSkyTexture, camera.position.x - backgroundSkyTexture.getWidth() / 2, 0f);

        position = (camera.position.x - backgroundCloudsTexture.getWidth()/2) * 0.75f;
        position = position+(backgroundCloudsTexture.getWidth()*(times(startOfViewport,
                (position + backgroundCloudsTexture.getWidth()))));
        endOfTexture = (position + backgroundCloudsTexture.getWidth());


        batch.draw(backgroundCloudsTexture, position,
                camera.viewportHeight - backgroundCloudsTexture.getHeight());
        if(endOfTexture < endOfViewport) {
            batch.draw(backgroundCloudsTexture, endOfTexture, camera.viewportHeight - backgroundCloudsTexture.getHeight());
        }

        position = (camera.position.x - background2Texture.getWidth()/2) * 0.50f;
        position = position+(background2Texture.getWidth()*(times(startOfViewport,
                (position + background2Texture.getWidth()))));
        endOfTexture = (position + background2Texture.getWidth());

        batch.draw(background2Texture, position, 0f);
        if(endOfTexture < endOfViewport) {
            batch.draw(background2Texture, endOfTexture, 0f);
        }

        position = (camera.position.x - background1Texture.getWidth()/2) * 0.25f;
        position = position+(background1Texture.getWidth()*(times(startOfViewport,
                (position + background1Texture.getWidth()))));
        endOfTexture = (position + background1Texture.getWidth());

        if(endOfTexture != lastX) {
            Log.i(this.getClass().getSimpleName(), "position:" + position +" Times:"+times(startOfViewport,endOfTexture)+ " endOfTexture:" + endOfTexture + " startOfViewPort:" + startOfViewport + " endOfViewPort:" + endOfViewport);
            lastX = endOfTexture;
        }

        batch.draw(background1Texture, position, 0f);
        if(endOfTexture < endOfViewport) {
            batch.draw(background1Texture, endOfTexture, 0f);
        }

        batch.end();

    }

    private int times(float endOfViewport, float endOfTexture) {
        int count = 0;
        while(endOfViewport > 0) {
            endOfViewport -= endOfTexture;
            if(endOfViewport > 0) {
                count++;
            }
        }
        return count;
    }

}
