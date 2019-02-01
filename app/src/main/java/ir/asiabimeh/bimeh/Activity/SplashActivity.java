package ir.asiabimeh.bimeh.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.asiabimeh.bimeh.Helper.getVersionApp;
import ir.asiabimeh.bimeh.R;

public class SplashActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVersionApp getVersionApp = new getVersionApp();
                getVersionApp.connect(SplashActivity.this);

             //   Intent intent = new Intent(SplashActivity.this, FirstActivity.class);
              // startActivity(intent);
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {

    }
}
