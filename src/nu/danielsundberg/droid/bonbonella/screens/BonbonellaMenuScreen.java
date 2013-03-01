package nu.danielsundberg.droid.bonbonella.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private OrthographicCamera camera;

    public BonbonellaMenuScreen(BonbonellaGameController controller) {
        this.controller = controller;
        controller.getAssetManager().load(BonbonellaGameController.BONBONELLA_MENU_SCREEN_IMAGE, Texture.class);
        controller.getAssetManager().finishLoading();
        camera = new OrthographicCamera();

    }

    @Override
    public void render(float timeSinceLastRender) {

        //
        // Clear and render splashscreen
        //
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        camera.position.x = splashTexture.getWidth()/2;
        camera.position.y = splashTexture.getHeight()/2;
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(splashTexture, 0, 0);
        spriteBatch.end();

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
        splashTexture = controller.getAssetManager().get(BonbonellaGameController.BONBONELLA_MENU_SCREEN_IMAGE, Texture.class);
        camera.viewportHeight = splashTexture.getHeight();
        camera.viewportWidth = splashTexture.getWidth();
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
