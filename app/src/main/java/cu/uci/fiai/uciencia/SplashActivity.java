package cu.uci.fiai.uciencia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.State;
import com.github.jorgecastillo.listener.OnStateChangeListener;

import cu.uci.fiai.uciencia.util.AppUCI;
import cu.uci.fiai.uciencia.util.Paths;

public class SplashActivity extends AppCompatActivity implements OnStateChangeListener {

    private static final String IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FillableLoader fillableLoader = (FillableLoader)
                findViewById(R.id.fillableLoader);

        SharedPreferences preferences = ((AppUCI) getApplication()).getPreferences();

        if (isFirstTimeLaunch(preferences)) {
            preferences.edit().putBoolean(IS_FIRST_TIME_LAUNCH, false).apply();
            fillableLoader.setStrokeDrawingDuration(4000);
            fillableLoader.setFillDuration(3500);
        }

        fillableLoader.setSvgPath(Paths.UCIENCIA);
        fillableLoader.start();
        fillableLoader.setOnStateChangeListener(this);
    }

    @Override
    public void onStateChange(int i) {
        switch (i) {
            case State.FINISHED:
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private boolean isFirstTimeLaunch(SharedPreferences preferences){
        if (preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true)) {
            return true;
        } else {
            return false;
        }
    }

}
