package nu.danielsundberg.droid.bonbonella.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nu.danielsundberg.droid.bonbonella.BonbonellaGameController;

/**
 * Splashscreen of bonbonella, while loading all resources.
 */
public class BonbonellaSplashScreen implements Screen {

    private SpriteBatch spriteBatch;
    private Texture splashTexture;
    private BonbonellaGameController controller;

    public BonbonellaSplashScreen(BonbonellaGameController controller) {
        this.controller = controller;
    }

    @Override
    public void render(float timeSinceLastRender) {

        //
        // Clear and render splashscreen
        //
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(splashTexture, 0, 0);
        spriteBatch.end();

        //
        // Go to menu if touched
        //
        if(Gdx.input.justTouched()) {
            controller.setScreen(new BonbonellaMenuScreen(controller));
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
