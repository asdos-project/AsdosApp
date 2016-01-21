package id.asdos.asdosapp.mainMenu.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.asdos.asdosapp.auth.login.LoginSocialActivity;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.R;
import id.asdos.asdosapp.data.DataService;

/**
 * Created by kuwali on 07/01/16.
 */
public class ProfileAdikActivity extends AppCompatActivity {

    private ImageView mProfileImageView;
    private TextView mNameTextView;
    private TextView mSchoolTextView;
    private TextView mJumlahPertanyaanTextView;
    private TextView mAboutMeUserDetTextView;

    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_adik);

        activeUser = UserService.getInstance(this).getCurrentUser();

        if (activeUser==null) {
            finish();
        }

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbarProfileAdik);
        setSupportActionBar(mToolbar);

        mProfileImageView = (ImageView) findViewById(R.id.profileUserImageView);
        mNameTextView = (TextView) findViewById(R.id.nameUserTextView);
        mSchoolTextView = (TextView) findViewById(R.id.schoolTextView);
        mJumlahPertanyaanTextView = (TextView) findViewById(R.id.jumlahPertanyaanTextView);
        mAboutMeUserDetTextView = (TextView) findViewById(R.id.aboutMeUserDetTextView);

        mProfileImageView.setImageBitmap(UserService.getInstance(this).getProfileImage(activeUser));
        mNameTextView.setText(activeUser.getName());
        mSchoolTextView.setText(activeUser.getSchool());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_adik, menu);
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
