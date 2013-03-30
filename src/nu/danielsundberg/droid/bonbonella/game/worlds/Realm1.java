package nu.danielsundberg.droid.bonbonella.game.worlds;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbonella;
import nu.danielsundberg.droid.bonbonella.game.actors.Cavitycreep;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbon;
import nu.danielsundberg.droid.bonbonella.game.actors.Finish;
import nu.danielsundberg.droid.bonbonella.game.levels.AbstractLevel;
import nu.danielsundberg.droid.bonbonella.game.actors.Tile;

public class Realm1 extends AbstractRealm implements Realm {

    /**
     * Realm assets
     */
    public static final String BONBONELLA_SPRITE_INVISIBLE = "sprites/realms/realm1/bonbonella_invisible_tile.png";
    public static final String BONBONELLA_SPRITE_GROUND_EDGE_LEFT = "sprites/realms/realm1/bonbonella_ground_edge_left.png";
    public static final String BONBONELLA_SPRITE_GROUND_EDGE_RIGHT = "sprites/realms/realm1/bonbonella_ground_edge_right.png";
    public static final String BONBONELLA_SPRITE_GROUND_1 = "sprites/realms/realm1/bonbonella_ground_1.png";
    public static final String BONBONELLA_SPRITE_GROUND_2 = "sprites/realms/realm1/bonbonella_ground_2.png";
    public static final String BONBONELLA_SPRITE_GROUND_3 = "sprites/realms/realm1/bonbonella_ground_3.png";
    public static final String BONBONELLA_SPRITE_GROUND_4 = "sprites/realms/realm1/bonbonella_ground_4.png";
    public static final String BONBONELLA_SPRITE_STONE = "sprites/realms/realm1/bonbonella_stone_tile.png";
    public static final String BONBONELLA_SPRITE_FINISH = "sprites/realms/realm1/bonbonella_finish_sprite.png";

    public static final String BONBONELLA_SPRITE_BONBON = "sprites/realms/realm1/bonbonella_bonbon_sprite.png";

    public static final String BONBONELLA_BACKGROUND_SKY = "sprites/realms/realm1/bonbonella_background_sky.png";
    public static final String BONBONELLA_BACKGROUND_1 = "sprites/realms/realm1/bonbonella_background_1.png";
    public static final String BONBONELLA_BACKGROUND_2 = "sprites/realms/realm1/bonbonella_background_2.png";
    public static final String BONBONELLA_BACKGROUND_CLOUDS = "sprites/realms/realm1/bonbonella_background_clouds.png";

    public Texture invisibleTexture,
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


    /**
     * Realm levels
     */
    public static final long LEVEL1_TIME = 120000;
    public static final String[] LEVEL1 = {
            "S S                                                                                    S S S S       S S S S                                                                                                                  S S S",
            "SSS                                                                                    SSSSSSS       SSSSSSS                                                                                                                  SSSSS",
            "SSS                                                                                    SSSSSSSSSSSSSSSSSSSSS                                                                                                                  SSSSS",
            "SSS        B                                                                           SS                                                         B B B                                                                       SSSSS",
            "SS        B B     SS                                                                   SS  B B B B B B B B B                                                                                                                     SS",
            "SS       B B B                                                                         SS  B                                                      SSSSS      B B B                                                               SS",
            "SS      S  S  S                                                                        SS  B   SSSSSSSSSSSSS                                                                                                                     SS",
            "SS                       B B B B                                                       SS  B B B B B B    SS                                         B B B   SSSSS                                                              FSS",
            "SS                                                    B  B                             SS                 SS                                                                                                                 SSSSSS",
            "SS     SSSSSSSSS         SS    SS                                                      SSSSSSSSSSSSSSS    SS                                         SSSSS                                                                        S",
            "SS    B B B B B B      SSSS    SSSS    B B B B B B B  SSSS               B   B   B                                    B B B B B B B B B B   B B B B B B B B B B B B B B B B B B                                                   S",
            "SS X              C  SSSSSS    SSSSSS        C        SSSS                                                         S        C             S    C                               S                                                  S",
            "MMMMMMMMMMMMMMMMMMMMMMMMMMR    LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR  LR  LR  LR  LMMMMMMMMMMMMMMMMMMMMMMMR    LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM"
    };

    public static final long LEVEL2_TIME = 120000;
    public static final String[] LEVEL2 = {
            "S                S                                  ",
            "S                S                                  ",
            "S                S                                 S",
            "S                S                                 S",
            "S   B         S  S                                 S",
            "S  B B       S   S                                 S",
            "S  IBI      S   S                                  S",
            "S   I      S   S                                   S",
            "S         S   S                                    S",
            "S        S   S                                     S",
            "S       S                                          S",
            "S X    S        S  C  C  C                        FS",
            "LMMMMMMRIIIIILMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR"
    };

    public static final long LEVEL3_TIME = 120000;
    public static final String[] LEVEL3 = {
            "S S S                                                         BBBBB      BBB                          B  B                                              S S S",
            "SSSSS                                                                    B  B                                                                           SSSSS",
            "SSSSS               B                                         IIIII      B  B    B  B  B  B  B  B  B         B                                          SSSSS",
            "SSSSS              B B                                                   BBB                                                                            SSSSS",
            "SSS               BBBBB                                  BBB             SSSSS  B  SSSSSSSSSSSSSSSSSSS                                                   BB S",
            "SSS    BB        BB   BB                                B                                                       B                                        BB S",
            "SSS                                        BBB          B                        B  B  B  B  B  B  B  B                                                     S",
            "SSS              IIIIIIIIIII               B  B          BBB                                            B                                                  FS",
            "SSS                                        BBBB                            SSSSSSSSSSSSSSSSSSSSSSSSSSS            B                                      SSSS",
            "SSS       B                      SSS       B   B         SSS                                            B               B                             SSSSSSS",
            "SSS          BB  B               SSS       BBBB          SSS           B  B  B  B  B  B  B  B  B  B  B               B     B                        SSSSSSSSS",
            "SSS X     S         C           SSSSS            C   C  SSSS            S             C     C     C       SS                                      SSSSSSSSSSS",
            "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR    LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR   LMMMMMMMMMMMMMMMMM"
    };

    public static final long LEVEL4_TIME = 120000;
    public static final String[] LEVEL4 = {
            "I                                                                                                                                                                                                                                                                                      I",
            "I                                                                                                                                                                        BBBBB                                                                                                         I",
            "I                                                                                                                                                                        BBBBB                                                                                                         I",
            "I                                                                                                                                                                                                                                                                                      I",
            "I                                                                                                                                                                          S                                                                                                           I",
            "I                            B B                                                                                                                                         B   B                                                                                                         I",
            "I                            SSS                                                                                         SSSSSS                                          S   S                       BBB                                                                               I",
            "I                                                                                             IIII                                                                     B   B   B                                                    BBB                                                I",
            "I                          B B B B                            SSS                  SSS                                        B       B B B                            S   S   S                    S   S                                          B B B B B B                         I",
            "I                          SSSSSSS                            SSS                  SSS                                        S       SSSSS                                                        SS   SS                        SS   SS          SSSSSSSSSSS                         I",
            "I                                              SSS            SSS                  SSS                                                                      S                                     SSS   SSS                      SSS   SSS                                 F           I",
            "I  X                                           SSS      C     SSS      C     C     SSS                                                                     SSS                     C  C  C  C    SSSS   SSSS                    SSSS   SSSS        C        C             SSS          I",
            "LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR    LMMMMMMMMMMMMMMMMMMMMMMMMMMMMR     LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR   LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR"
    };

    public static final long LEVEL5_TIME = 120000;
    public static final String[] LEVEL5 = {
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S X            C  C  C                           FS",
            "LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR"
    };

    public static final long LEVEL6_TIME = 120000;
    public static final String[] LEVEL6 = {
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S X            C  C  C                           FS",
            "LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR"
    };

    public static final long LEVEL7_TIME = 120000;
    public static final String[] LEVEL7 = {
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S X            C  C  C                           FS",
            "LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR"
    };

    public static final long LEVEL8_TIME = 120000;
    public static final String[] LEVEL8 = {
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S                                                 S",
            "S X            C  C  C                           FS",
            "LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMR"
    };


    public void initRealmForLevel(BonbonellaGameController controller, String[] levelDescriptor, World physicsWorld, AbstractLevel levelToSetUp) {
        boolean loading = false;
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_GROUND_1)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_GROUND_1, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_GROUND_2)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_GROUND_2, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_GROUND_3)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_GROUND_3, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_GROUND_4)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_GROUND_4, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_GROUND_EDGE_LEFT)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_GROUND_EDGE_LEFT, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_GROUND_EDGE_RIGHT)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_GROUND_EDGE_RIGHT, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_INVISIBLE)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_INVISIBLE, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_STONE)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_STONE, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_FINISH)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_FINISH, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_BACKGROUND_CLOUDS)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_BACKGROUND_CLOUDS, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_BACKGROUND_2)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_BACKGROUND_2, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_BACKGROUND_1)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_BACKGROUND_1, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_BACKGROUND_SKY)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_BACKGROUND_SKY, Texture.class);
            loading = true;
        }
        if(!controller.getAssetManager().isLoaded(Realm1.BONBONELLA_SPRITE_BONBON)) {
            controller.getAssetManager().load(Realm1.BONBONELLA_SPRITE_BONBON, Texture.class);
            loading = true;
        }
        if(loading) {
            controller.getAssetManager().finishLoading();
        }

        invisibleTexture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_INVISIBLE, Texture.class);
        ground1Texture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_GROUND_1, Texture.class);
        ground2Texture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_GROUND_2, Texture.class);
        ground3Texture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_GROUND_3, Texture.class);
        ground4Texture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_GROUND_4, Texture.class);
        groundEdgeLeftTexture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_GROUND_EDGE_LEFT, Texture.class);
        groundEdgeRightTexture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_GROUND_EDGE_RIGHT, Texture.class);
        stoneTexture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_STONE, Texture.class);
        background1Texture = controller.getAssetManager().get(Realm1.BONBONELLA_BACKGROUND_1, Texture.class);
        background2Texture = controller.getAssetManager().get(Realm1.BONBONELLA_BACKGROUND_2, Texture.class);
        backgroundCloudsTexture = controller.getAssetManager().get(Realm1.BONBONELLA_BACKGROUND_CLOUDS, Texture.class);
        backgroundSkyTexture = controller.getAssetManager().get(Realm1.BONBONELLA_BACKGROUND_SKY, Texture.class);
        bonbonTexture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_BONBON, Texture.class);
        finishTexture = controller.getAssetManager().get(Realm1.BONBONELLA_SPRITE_FINISH, Texture.class);

        for(int tileRow = levelDescriptor.length-1; tileRow >= 0; tileRow--) {
            for(int tileColumn = 0; tileColumn < levelDescriptor[tileRow].length(); tileColumn++) {

                Texture groundTexture = null;

                switch(levelDescriptor[tileRow].charAt(tileColumn)) {
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
                        levelToSetUp.addEnemy(new Cavitycreep(physicsWorld, controller,
                                (Cavitycreep.CREEP_SIZE * tileColumn) + (Cavitycreep.CREEP_SIZE / 2),
                                (Cavitycreep.CREEP_SIZE * ((levelDescriptor.length - 1) - tileRow)) + (Cavitycreep.CREEP_SIZE / 2)));
                        break;
                    case 'F':
                        Body finishBody = createBodyForTile(physicsWorld, levelDescriptor, tileColumn, tileRow);
                        Finish finish = new Finish(finishTexture, finishBody);
                        finishBody.setUserData(finish);
                        levelToSetUp.addGroundTile(finish);
                        break;
                    case 'X':
                        levelToSetUp.setStartposition(new Vector2(Tile.TILE_SIZE*tileColumn + Bonbonella.BONBONELLA_SIZE/2 ,
                                ((levelDescriptor.length-1)-tileRow)*Tile.TILE_SIZE + Bonbonella.BONBONELLA_SIZE/2));
                        break;
                    case 'B':
                        Body bonbonBody = createBodyForTile(physicsWorld, levelDescriptor, tileColumn, tileRow);
                        Bonbon bonbon = new Bonbon(bonbonTexture, bonbonBody);
                        bonbonBody.setUserData(bonbon);
                        levelToSetUp.addGroundTile(bonbon);
                        break;
                    default:
                        break;
                }

                if(groundTexture!=null) {
                    Body groundBody = createBodyForTile(physicsWorld, levelDescriptor, tileColumn, tileRow);
                    Tile tile = new Tile(groundTexture, groundBody);
                    groundBody.setUserData(tile);
                    levelToSetUp.addGroundTile(tile);
                }
            }
        }
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

}
