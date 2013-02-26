package nu.danielsundberg.droid.bonbonella.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;
import nu.danielsundberg.droid.bonbonella.game.actors.Bonbonella;

/**
 * Game renderer of Bonbonella
 */
public class BonbonellaGameScreen implements Screen {

    public static final int VIRTUAL_WIDTH = 480;
    public static final int VIRTUAL_HEIGHT = 320;

    private BonbonellaGameController controller;
    private BonbonellaGame game;
    private Box2DDebugRenderer debugRenderer;

    public BonbonellaGameScreen(BonbonellaGameController controller) {
        this.controller = controller;
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float timeSinceLastRender) {

        if(game.getGameState().equals(BonbonellaGame.GameState.GAMEOVER)) {

            controller.setScreen(new BonbonellaMenuScreen(controller));

        } else {

            //
            // Step game
            //
            game.act(timeSinceLastRender);
            //
            // Get gamecamera and update position
            //
            Camera camera = game.getCamera();
            camera.update();

            game.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, true);
            if(game.getBonbonella().getX() >= camera.position.x) {
                float levelCameraEnd = game.getLevel().getLevelWidth()-VIRTUAL_WIDTH/2-Bonbonella.BONBONELLA_SIZE;
                if(game.getBonbonella().getX() <= levelCameraEnd) {
                    camera.position.x = game.getBonbonella().getX();
                } else {
                    camera.position.x = levelCameraEnd;
                }

            }

            //
            // Clear screen and update
            //
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            game.draw();

            debugRenderer.render(game.getWorld(),
                    camera.combined.cpy().scale(
                            BonbonellaGame.BOX_TO_WORLD,
                            BonbonellaGame.BOX_TO_WORLD,
                            1f));

        }

    }

    @Override
    public void resize(int width, int height) {
        game.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, true);
    }

    @Override
    public void show() {
        game = new BonbonellaGame(controller);
        game.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, true);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {}

}
