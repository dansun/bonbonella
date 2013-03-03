package nu.danielsundberg.droid.bonbonella.game.levels;

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
 * Level 2 of Bonbonella game
 */
public class Level2 extends Level {

    protected BonbonellaGameController controller;

    public static final String BONBONELLA_SPRITE_INVISIBLE = "sprites/bonbonella_invisible_tile.png";
    public static final String BONBONELLA_SPRITE_GROUND_EDGE_LEFT = "sprites/bonbonella_ground_edge_left.png";
    public static final String BONBONELLA_SPRITE_GROUND_EDGE_RIGHT = "sprites/bonbonella_ground_edge_right.png";
    public static final String BONBONELLA_SPRITE_GROUND_1 = "sprites/bonbonella_ground_1.png";
    public static final String BONBONELLA_SPRITE_GROUND_2 = "sprites/bonbonella_ground_2.png";
    public static final String BONBONELLA_SPRITE_GROUND_3 = "sprites/bonbonella_ground_3.png";
    public static final String BONBONELLA_SPRITE_GROUND_4 = "sprites/bonbonella_ground_4.png";
    public static final String BONBONELLA_SPRITE_STONE = "sprites/bonbonella_stone_tile.png";
    public static final String BONBONELLA_SPRITE_FINISH = "sprites/bonbonella_finish_sprite.png";

    public static final String BONBONELLA_SPRITE_BONBON = "sprites/bonbonella_bonbon_sprite.png";

    public static final String BONBONELLA_BACKGROUND_SKY = "sprites/bonbonella_background_sky.png";
    public static final String BONBONELLA_BACKGROUND_1 = "sprites/bonbonella_background_1.png";
    public static final String BONBONELLA_BACKGROUND_2 = "sprites/bonbonella_background_2.png";
    public static final String BONBONELLA_BACKGROUND_CLOUDS = "sprites/bonbonella_background_clouds.png";

    private Texture invisibleTexture,
            ground1Texture,
            ground2Texture,
            ground3Texture,
            ground4Texture,
            groundEdgeLeftTexture,
            groundEdgeRightTexture,
            stoneTexture,
            backgroundSkyTexture,
            background1Texture,
            background2Texture,
            backgroundCloudsTexture,
            bonbonTexture,
            finishTexture;

    private Vector2 startposition;

    private long startingTime,
            currentTime,
            endingTime;

    private static final String[] LEVEL2 =
            {       "S                                                 S",
                    "S                                                 S",
                    "S                                                 S",
                    "S                                                 S",
                    "S                                                 S",
                    "S                                                 S",
                    "S                                                 S",
                    "S                                                 S",
                    "S                                                 S",
                    "S X            C  C  C                           FS",
                    "LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR"};


    public Level2(World world, BonbonellaGameController controller) {
        super(world, controller, LEVEL2);
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
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_FINISH)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_FINISH, Texture.class);
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
        if(!controller.getAssetManager().isLoaded(BONBONELLA_SPRITE_BONBON)) {
            controller.getAssetManager().load(BONBONELLA_SPRITE_BONBON, Texture.class);
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
        bonbonTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_BONBON, Texture.class);
        finishTexture = controller.getAssetManager().get(BONBONELLA_SPRITE_FINISH, Texture.class);

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
                        Finish finish = new Finish(finishTexture, finishBody);
                        finishBody.setUserData(finish);
                        groundTiles.add(finish);
                        break;
                    case 'X':
                        this.startposition = new Vector2(Tile.TILE_SIZE*tileColumn + Bonbonella.BONBONELLA_SIZE/2 ,
                                ((GROUND_MAP.length-1)-tileRow)*Tile.TILE_SIZE + Bonbonella.BONBONELLA_SIZE/2);
                        break;
                    case 'B':
                        Body bonbonBody = createBodyForTile(tileColumn, tileRow);
                        Bonbon bonbon = new Bonbon(bonbonTexture, bonbonBody);
                        bonbonBody.setUserData(bonbon);
                        groundTiles.add(bonbon);
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

           resetTimeLeft();

        }
    }

    public void resetTimeLeft() {
        startingTime = System.currentTimeMillis();
        currentTime = startingTime;
        endingTime = startingTime+(99000);
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

        //
        // Update time in game
        //
        currentTime = currentTime + new Float(delta*1000).longValue();
    }

    public Level getNextLevel(World world) {
        return new Level1(world, controller);
    }

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

    /**
     * Return time left on level.
     * @return
     */
    public int getTimeLeft() {
        int time = new Long((endingTime-currentTime)/1000).intValue();
        return time >= 0 ? time : 0;
    }

}
