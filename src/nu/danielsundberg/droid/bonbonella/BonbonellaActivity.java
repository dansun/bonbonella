package nu.danielsundberg.droid.bonbonella;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class BonbonellaActivity extends AndroidApplication {

    private BonbonellaGameController game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new BonbonellaGameController(this);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useGL20 = true;
        configuration.useWakelock = true;
        configuration.hideStatusBar = true;
        configuration.touchSleepTime = 10;
        initialize(game, configuration);
    }
}
