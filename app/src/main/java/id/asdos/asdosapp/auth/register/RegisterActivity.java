package id.asdos.asdosapp.auth.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import id.asdos.asdosapp.auth.login.LoginSocialActivity;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.mainMenu.MainActivity;
import id.asdos.asdosapp.R;

/**
 * Created by kuwali on 08/01/16.
 */
public class RegisterActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;

    private EditText nameRegisterEditText;
    private EditText usernameRegisterEditText;
    private EditText emailRegisterEditText;
    private EditText passwordRegisterEditText;
    private EditText schoolRegisterEditText;
    private EditText majorRegisterEditText;
    private RadioGroup userTypeRadioGroup;
    private RadioButton adikRadioButton;
    private RadioButton kakakRadioButton;
    private ImageView profileRegisterImageView;
    private ProgressDialog progressDialog;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (UserService.getInstance(this).getCurrentUser() != null) {
            goToMainActivity();
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarRegister);
        setSupportActionBar(mToolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        nameRegisterEditText = (EditText) findViewById(R.id.nameEditProfileEditText);
        usernameRegisterEditText = (EditText) findViewById(R.id.usernameRegisterEditText);
        emailRegisterEditText = (EditText) findViewById(R.id.emailEditProfileEditText);
        passwordRegisterEditText = (EditText) findViewById(R.id.passwordRegisterEditText);
        profileRegisterImageView = (ImageView) findViewById(R.id.profileEditProfileImageView);
        schoolRegisterEditText = (EditText) findViewById(R.id.schoolEditProfileEditText);
        majorRegisterEditText = (EditText) findViewById(R.id.majorEditProfileEditText);
        userTypeRadioGroup = (RadioGroup) findViewById(R.id.userTypeRadioGroup);
        adikRadioButton = (RadioButton) findViewById(R.id.adikRadioButton);
        kakakRadioButton = (RadioButton) findViewById(R.id.kakakRadioButton);

        adikRadioButton.setChecked(true);

        profileRegisterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        userTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.adikRadioButton) {
                    schoolRegisterEditText.setHint("School");
                    majorRegisterEditText.setHint("Class");
                } else {
                    schoolRegisterEditText.setHint("University");
                    majorRegisterEditText.setHint("Major");
                }
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                if (bitmap != null) {
                    bitmap.recycle();
                }

                InputStream stream = getContentResolver().openInputStream(data.getData());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                bitmap = BitmapFactory.decodeStream(stream, null, options);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                double scale = 100.0/height;
                height = (int)(height*scale);
                width = (int)(width*scale);
                bitmap = Bitmap.createScaledBitmap(bitmap, width,height, false);
                stream.close();
                Toast.makeText(this,bitmap.getConfig().toString(),Toast.LENGTH_SHORT).show();
                profileRegisterImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_submit_register) {
            String name = nameRegisterEditText.getText().toString();
            String username = usernameRegisterEditText.getText().toString();
            String email = emailRegisterEditText.getText().toString();
            String password = passwordRegisterEditText.getText().toString();
            String school = schoolRegisterEditText.getText().toString();
            String major = majorRegisterEditText.getText().toString();

            register(name, username, email, password, school, major);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void register(String name, String username, String email, String password, String school, String major) {
        progressDialog.show();
        if (userTypeRadioGroup.getCheckedRadioButtonId() == R.id.adikRadioButton) {
            UserService.getInstance(this).registerAdik(name,username,password,email,school,major,registerListener,bitmap);
        } else {
            UserService.getInstance(this).registerKakak(name,username,password,email,school,major,registerListener,bitmap);
        }

    }

    private UserService.RegisterListener registerListener = new UserService.RegisterListener() {
        @Override
        public void onResponse(boolean registered, String message, User user) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (registered) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginSocialActivity.class);
        startActivity(intent);
        finish();
    }
}
