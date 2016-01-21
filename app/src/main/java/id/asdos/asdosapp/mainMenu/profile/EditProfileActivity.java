package id.asdos.asdosapp.mainMenu.profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.mainMenu.MainActivity;

/**
 * Created by kuwali on 14/01/16.
 */
public class EditProfileActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;

    private ImageView mProfileEditProfileImageView;
    private EditText mNameEditProfileEditText;
    private EditText mEmailEditProfileEditText;
    private EditText mSchoolEditProfileEditText;
    private EditText mMajorEditProfileEditText;
    private Button mSubmitEditProfileButton;
    private ProgressDialog progressDialog;
    private Bitmap bitmap;

    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        activeUser = UserService.getInstance(this).getCurrentUser();

        if (activeUser != null) {
            goToMainActivity();
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarRegister);
        setSupportActionBar(mToolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        mProfileEditProfileImageView = (ImageView) findViewById(R.id.profileEditProfileImageView);
        mNameEditProfileEditText = (EditText) findViewById(R.id.nameEditProfileEditText);
        mEmailEditProfileEditText = (EditText) findViewById(R.id.emailEditProfileEditText);
        mSchoolEditProfileEditText = (EditText) findViewById(R.id.schoolEditProfileEditText);
        mMajorEditProfileEditText = (EditText) findViewById(R.id.majorEditProfileEditText);
        mSubmitEditProfileButton = (Button) findViewById(R.id.submitEditProfileButton);

        mProfileEditProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        mNameEditProfileEditText.setText(activeUser.getName());
        mEmailEditProfileEditText.setText(activeUser.getEmail());
        mSchoolEditProfileEditText.setText(activeUser.getUserType().equals(User.UserType.KAKAK) ? activeUser.getUniversity() : activeUser.getSchool());
        mMajorEditProfileEditText.setText(activeUser.getUserType().equals(User.UserType.KAKAK) ? activeUser.getMajor() : activeUser.getKelas());

        mSubmitEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHelper();
            }
        });
    }

    private void updateHelper() {
        String name = mNameEditProfileEditText.getText().toString();
        String email = mEmailEditProfileEditText.getText().toString();
        String school = mSchoolEditProfileEditText.getText().toString();
        String major = mMajorEditProfileEditText.getText().toString();

        update(name, email, school, major);
    }

    private void update(String name, String email, String school, String major) {
        progressDialog.show();
        UserService.getInstance(this).updateProfile(name, email, school, major, updateListener, bitmap);
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
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
                mProfileEditProfileImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private UserService.UpdateListener updateListener = new UserService.UpdateListener() {
        @Override
        public void onResponse(boolean updated, String message, User user) {
            progressDialog.dismiss();
            if (updated) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
