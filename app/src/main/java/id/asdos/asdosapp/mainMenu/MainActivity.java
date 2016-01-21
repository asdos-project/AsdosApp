package id.asdos.asdosapp.mainMenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import id.asdos.asdosapp.auth.login.LoginSocialActivity;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.R;
import id.asdos.asdosapp.data.DataService;
import id.asdos.asdosapp.mainMenu.profile.ProfileAdikActivity;
import id.asdos.asdosapp.mainMenu.profile.ProfileKakakActivity;
import id.asdos.asdosapp.mainMenu.timeLine.TimeLineFragment;

/**
 * Created by kuwali on 07/01/16.
 */
public class MainActivity extends AppCompatActivity  {
    private User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activeUser = UserService.getInstance(this).getCurrentUser();

        if (activeUser == null) {
            finish();
            return;
        }



        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setLogo(R.drawable.logo_asdos2);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.but_timeline));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.but_tanya));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.but_topics));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.but_group_kakak));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new id.asdos.asdosapp.mainMenu.adapter.PagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), activeUser.getUserType());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            Toast.makeText(this,"Settings is pressed",Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_logout) {
            UserService.getInstance(this).logout();
            SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("username");
            editor.remove("pass");
            editor.commit();
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
