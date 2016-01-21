package id.asdos.asdosapp.mainMenu.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.auth.login.LoginSocialActivity;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.data.DataService;

/**
 * Created by kuwali on 13/01/16.
 */
public class ProfileKakakActivity extends AppCompatActivity {

    private ImageView mProfileImageView;
    private TextView mNameTextView;
    private TextView mJurusanTextView;
    private TextView mUniversitasTextView;
    private TextView mLevelTextView;
    private TextView mJumlahJawabTextView;
    private TextView mPCTextView;
    private ImageView mBintangImageView;

    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_kakak);

        if (getIntent().hasExtra("user")) {
            activeUser = (User) getIntent().getExtras().getSerializable("user");
        } else {
            activeUser = UserService.getInstance(this).getCurrentUser();
        }

        if (activeUser == null) {
            finish();
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarProfileKakak);
        setSupportActionBar(mToolbar);

        mProfileImageView = (ImageView) findViewById(R.id.profileKakakImageView);
        mNameTextView = (TextView) findViewById(R.id.nameKakakTextView);
        mJurusanTextView = (TextView) findViewById(R.id.jurusanTextView);
        mUniversitasTextView = (TextView) findViewById(R.id.universitasTextView);
        mLevelTextView = (TextView) findViewById(R.id.levelTextView);
        mJumlahJawabTextView = (TextView) findViewById(R.id.jumlahJawabTextView);
        mPCTextView = (TextView) findViewById(R.id.pcTextView);
        mBintangImageView = (ImageView) findViewById(R.id.bintangImageView);

        mProfileImageView.setImageBitmap(UserService.getInstance(this).getProfileImage(activeUser));
        mNameTextView.setText(activeUser.getName());
        mJurusanTextView.setText(activeUser.getMajor());
        mUniversitasTextView.setText(activeUser.getUniversity());
        mLevelTextView.setText("Level : " + activeUser.getLevel());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_kakak,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            Toast.makeText(this,"Settings is pressed",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_logout) {
            UserService.getInstance(this).logout();
            DataService.getInstance(this).serializeData();
            Intent intent = new Intent(this, LoginSocialActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.menu_search) {
            Toast.makeText(this,"Search is pressed",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_notification) {
            Toast.makeText(this,"Notification is pressed",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_about) {
            Toast.makeText(this,"About is pressed",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_faq) {
            Toast.makeText(this,"FAQ is pressed",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_policy) {
            Toast.makeText(this,"Policy is pressed",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_profile) {
            Intent intent;
            if (activeUser.getUserType().equals(User.UserType.KAKAK)) {
                intent = new Intent(this, ProfileKakakActivity.class);
            } else {
                intent = new Intent(this, ProfileAdikActivity.class);
            }
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

