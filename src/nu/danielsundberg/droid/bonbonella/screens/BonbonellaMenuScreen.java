package nu.danielsundberg.droid.bonbonella.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;

/**
 * Splashscreen of bonbonella, while loading all resources.
 */
public class BonbonellaMenuScreen implements Screen {

    private SpriteBatch spriteBatch;
    private Texture splashTexture;
    private BonbonellaGameController controller;

    public BonbonellaMenuScreen(BonbonellaGameController controller) {
        this.controller = controller;
    }

    @Override
    public void render(float timeSinceLastRender) {

        if(Gdx.input.justTouched()) {
            // TODO map menu alternatives
            controller.setScreen(new BonbonellaGameScreen(controller));
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {

        //
        // Instantiate assets
        //
        spriteBatch = new SpriteBatch();
        splashTexture = new Texture(Gdx.files.internal("splash.png"));

    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {

        //
        // Dispose assets
        //
        spriteBatch.dispose();
        splashTexture.dispose();

    }

}
