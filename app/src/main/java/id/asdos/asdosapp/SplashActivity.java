package id.asdos.asdosapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import id.asdos.asdosapp.auth.login.LoginSocialActivity;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.mainMenu.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_WAIT_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (UserService.getInstance(getApplicationContext()).getCurrentUser() != null) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, LoginSocialActivity.class);
                }
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_WAIT_TIME);
    }

}
