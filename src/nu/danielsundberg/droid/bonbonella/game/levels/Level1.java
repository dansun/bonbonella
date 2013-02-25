package nu.danielsundberg.droid.bonbonella.game.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;

import java.util.ArrayList;
import java.util.List;

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

    private Texture invisibleTexture;
    private Texture ground1Texture;
    private Texture ground2Texture;
    private Texture ground3Texture;
    private Texture ground4Texture;
    private Texture groundEdgeLeftTexture;
    private Texture groundEdgeRightTexture;

    private static final float GROUND_SIZE = 16f;



    private static final String[] GROUND_MAP = {"I                                                                      I",
                                                "I                                                                      I",
                                                "I                                                                      I",
                                                "I                                                                      I",
                                                "I                                                                      I",
                                                "I                                                                      I",
                                                "I                                                                      I",
                                                "I        LMMR                                                          I",
                                                "I                                                                      I",
                                                "I                                                                      I",
                                                "I                                                                      I",
                                                "I                                                                      I",
                                                "ILMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMRI"};

    private List<Tile> groundTiles = new ArrayList<Tile>();


    public Level1(World world, BonbonellaGameController controller) {
        super(world, controller);
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
                    default:
                        break;
                }

                if(groundTexture!=null) {
                    BodyDef groundBodyDef =new BodyDef();
                    groundBodyDef.position.set(
                            BonbonellaGame.convertToBox(GROUND_SIZE*tileColumn) +
                                    BonbonellaGame.convertToBox(GROUND_SIZE/2),
                            BonbonellaGame.convertToBox(GROUND_SIZE*((GROUND_MAP.length-1)-tileRow)) +
                                    BonbonellaGame.convertToBox(GROUND_SIZE/2));
                    groundBodyDef.type = BodyDef.BodyType.StaticBody;

                    FixtureDef fd = new FixtureDef();
                    fd.density = 0.1f;
                    fd.friction = 0.1f;
                    fd.restitution = 0f;

                    Body groundBody = world.createBody(groundBodyDef);

                    EdgeShape groundBox = new EdgeShape ();
                    Vector2 lowerLeft = new Vector2();
                    Vector2 lowerRight = new Vector2();
                    Vector2 upperRight = new Vector2();
                    Vector2 upperLeft = new Vector2();

                    lowerLeft.set(0-BonbonellaGame.convertToBox(GROUND_SIZE/2),
                            0-BonbonellaGame.convertToBox(GROUND_SIZE/2));
                    lowerRight.set(BonbonellaGame.convertToBox(GROUND_SIZE/2),
                            0-BonbonellaGame.convertToBox(GROUND_SIZE/2));
                    upperRight.set(BonbonellaGame.convertToBox(GROUND_SIZE/2),
                            BonbonellaGame.convertToBox(GROUND_SIZE/2));
                    upperLeft.set(0-BonbonellaGame.convertToBox(GROUND_SIZE/2),
                            BonbonellaGame.convertToBox(GROUND_SIZE/2));

                    groundBox.set(lowerLeft, lowerRight);
                    groundBody.createFixture(groundBox, 0);
                    groundBox.set(upperLeft, upperRight);
                    groundBody.createFixture(groundBox, 0);
                    groundBox.set(upperLeft, lowerLeft);
                    groundBody.createFixture(groundBox, 0);
                    groundBox.set(lowerRight, upperRight);
                    groundBody.createFixture(groundBox, 0);

                    groundTiles.add(new Tile(groundTexture, groundBody));
                }

            }
        }



    }

    public float getFinishX() {
        return BonbonellaGame.convertToWorld(200f);
    }

    public Level getNextLevel() {
        return new Level1(world, controller);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        for(Tile tile : groundTiles) {
            batch.draw(tile.getTexture(),
                    BonbonellaGame.convertToWorld(tile.getBody().getPosition().x-BonbonellaGame.convertToBox(GROUND_SIZE/2)),
                            BonbonellaGame.convertToWorld(tile.getBody().getPosition().y-BonbonellaGame.convertToBox(GROUND_SIZE/2)));
        }
    }

}
