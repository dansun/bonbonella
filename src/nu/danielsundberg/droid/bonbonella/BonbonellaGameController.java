package nu.danielsundberg.droid.bonbonella;

import com.badlogic.gdx.Game;
import nu.danielsundberg.droid.bonbonella.screens.BonbonellaSplashScreen;

/**
 *  Game implementation of Bonbonella
 */
public class BonbonellaGameController extends Game {

    private BonbonellaActivity parentActivity;


    public BonbonellaGameController(BonbonellaActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public void create() {
        this.setScreen(new BonbonellaSplashScreen(this));
    }

}
