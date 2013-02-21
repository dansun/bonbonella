package nu.danielsundberg.droid.bonbonella.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;
import nu.danielsundberg.droid.bonbonella.game.BonbonellaGame;

/**
 * Game renderer of Bonbonella
 */
public class BonbonellaGameScreen implements Screen {

    private BonbonellaGameController controller;
    private BonbonellaGame game;

    public BonbonellaGameScreen(BonbonellaGameController controller) {
        this.controller = controller;
    }

    @Override
    public void render(float timeSinceLastRender) {

        if(game.getGameState()!= BonbonellaGame.GameState.GAMEOVER) {

            controller.setScreen(new BonbonellaMenuScreen(controller));

        } else {

            //
            // Get gamecamera and update position
            //
            Camera camera = game.getCamera();
            camera.position.x = game.getBonbonella().getX() - 5f;

            //
            // Clear screen and update
            //
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.act(timeSinceLastRender);
            game.draw();
        }

    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        game = new BonbonellaGame();
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
