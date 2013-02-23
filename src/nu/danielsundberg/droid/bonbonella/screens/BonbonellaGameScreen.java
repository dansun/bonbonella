package nu.danielsundberg.droid.bonbonella.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;

/**
 * Game renderer of Bonbonella
 */
public class BonbonellaGameScreen implements Screen {

    private BonbonellaGameController controller;
    private BonbonellaGame game;

    public static final int VIRTUAL_WIDTH = 720;
    public static final int VIRTUAL_HEIGHT = 480;



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
            // Get gamecamera and update position
            //
            Camera camera = game.getCamera();
            camera.update();

            camera.viewportWidth = VIRTUAL_WIDTH;
            camera.viewportHeight = VIRTUAL_HEIGHT;
            game.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);

            if(game.getBonbonella().getX()>BonbonellaGame.UNIT_WIDTH/2 &&
                    game.getBonbonella().getX()<(game.getLevel().getFinishX()-BonbonellaGame.UNIT_WIDTH/2)) {
                camera.position.x = game.getBonbonella().getX();
            }


            //
            // Clear screen and update
            //
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            debugRenderer.render(game.getWorld(),camera.combined);
            game.act(timeSinceLastRender);
            game.draw();
        }

    }

    @Override
    public void resize(int width, int height) {
        game.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
        game.getCamera().position.x = VIRTUAL_WIDTH/2;
        game.getCamera().position.y = VIRTUAL_HEIGHT/2;
    }

    @Override
    public void show() {
        game = new BonbonellaGame(controller);
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
