package nu.danielsundberg.droid.bonbonella;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import nu.danielsundberg.droid.bonbonella.screens.BonbonellaSplashScreen;

/**
 *  Game implementation of Bonbonella
 */
public class BonbonellaGameController extends Game {

    private BonbonellaActivity parentActivity;

    static final float WORLD_TO_BOX = 0.01f;
    static final float BOX_TO_WORLD = 100f;

    public static final String BONBONELLA_SPLASH_SCREEN_IMAGE = "bonbonella_splash.png";
    public static final String BONBONELLA_MENU_SCREEN_IMAGE = "bonbonella_menu.png";

    private AssetManager assetManager;

    public BonbonellaGameController(BonbonellaActivity parentActivity) {
        this.parentActivity = parentActivity;
        this.assetManager = new AssetManager();
    }

    @Override
    public void create() {
        this.setScreen(new BonbonellaSplashScreen(this));
    }

    public AssetManager getAssetManager() {
        return this.assetManager;
    }

}
