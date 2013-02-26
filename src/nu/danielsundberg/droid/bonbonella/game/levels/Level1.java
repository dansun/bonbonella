package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.actors.Cavitycreep;
import nu.danielsundberg.droid.bonbonella.game.actors.Enemy;

/**
 * Level 1 of Bonbonella game
 */
public class Level1 extends Level {

    protected BonbonellaGameController controller;

    public static final String BONBONELLA_SPRITE_INVISIBLE = "bonbonella_invisible_tile.png";
    public static final String BONBONELLA_SPRITE_GROUND_EDGE_LEFT = "bonbonella_ground_edge_left.png";
    public static final String BONBONELLA_SPRITE_GROUND_EDGE_RIGHT = "bonbonella_ground_edge_right.png";
    public static final String BONBONELLA_SPRITE_GROUND_1 = "bonbonella_ground_1.png";
    public static final String BONBONELLA_SPRITE_GROUND_2 = "bonbonella_ground_2.png";
    public static final String BONBONELLA_SPRITE_GROUND_3 = "bonbonella_ground_3.png";
    public static final String BONBONELLA_SPRITE_GROUND_4 = "bonbonella_ground_4.png";
    public static final String BONBONELLA_SPRITE_STONE = "bonbonella_stone_tile.png";

    private Texture invisibleTexture;
    private Texture ground1Texture;
    private Texture ground2Texture;
    private Texture ground3Texture;
    private Texture ground4Texture;
    private Texture groundEdgeLeftTexture;
    private Texture groundEdgeRightTexture;
    private Texture stoneTexture;

    private static final String[] LEVEL1 = {"S                                                                      S",
                                            "S                                                                      S",
                                            "S                                                                      S",
                                            "S          C                                                           S",
                                            "S        LMMR                                                          S",
                                            "S                   LMMR                                               S",
                                            "S                 C S                                                  S",
                                            "S                LMMR                                                  S",
                                            "S        LMMR                                                          S",
                                            "S                        SS    SS                                      S",
                                            "S                      SSSS    SSS                                 F   S",
                                            "S                     SSSSS    SSSS     SS C SS                    S   S",
                                            "SLMMMMMMMMMMMMMMMMMMMMMMMMR    LMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMRS"};


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

}
