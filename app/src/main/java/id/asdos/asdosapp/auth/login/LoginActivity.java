package id.asdos.asdosapp.auth.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.mainMenu.MainActivity;
import id.asdos.asdosapp.R;

/**
 * Created by kuwali on 08/01/16.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView forgotPasswordTextView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        if (UserService.getInstance(this).getCurrentUser() != null) {
            goToMainActivity();
            return;
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(mToolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        forgotPasswordTextView = (TextView) findViewById(R.id.forgotPasswordTextView);

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Not ready yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean login(String username, String password) {
        progressDialog.show();
        UserService.getInstance(this).login(username, password, loginListener);
        if (UserService.getInstance(this).getCurrentUser() != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_submit_login) {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            return login(username, password);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    UserService.LoginListener loginListener = new UserService.LoginListener() {
        @Override
        public void onResponse(boolean loggedIn, String message, User user) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            if(loggedIn) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", user.getUsername());
                editor.putString("pass", user.getPassword());
                editor.commit();
                goToMainActivity();
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginSocialActivity.class);
        startActivity(intent);
        finish();
    }
}
