package id.asdos.asdosapp.auth.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.auth.register.RegisterActivity;
import id.asdos.asdosapp.R;
import id.asdos.asdosapp.mainMenu.MainActivity;

/**
 * Created by kuwali on 08/01/16.
 */
public class LoginSocialActivity extends AppCompatActivity {
    private ImageView facebookButton;
    private ImageView twitterButton;
    private Button registerButton;
    private TextView alreadyHaveAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_social_media);

        if (UserService.getInstance(this).getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
            String username = sharedPreferences.getString("username",null);
            String pass = sharedPreferences.getString("pass",null);
            if (username != null && pass != null) {
                UserService.getInstance(this).login(username, pass, loginListener);
            }
        }


        facebookButton = (ImageView) findViewById(R.id.loginFacebookButton);
        twitterButton = (ImageView) findViewById(R.id.loginTwitterButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        alreadyHaveAccountTextView = (TextView) findViewById(R.id.alreadyHaveAccountTextView);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Facebook Button is pressed", Toast.LENGTH_SHORT).show();
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Twitter Button is pressed", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alreadyHaveAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    UserService.LoginListener loginListener = new UserService.LoginListener() {
        @Override
        public void onResponse(boolean loggedIn, String message, User user) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if(loggedIn) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", user.getUsername());
                editor.putString("pass", user.getPassword());
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return;
            }
        }
    };
}
