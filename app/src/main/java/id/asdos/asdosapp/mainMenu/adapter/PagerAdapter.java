package id.asdos.asdosapp.mainMenu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.mainMenu.list_kakak.ListKakakFragment;
import id.asdos.asdosapp.mainMenu.new_question.NewQuestionFragment;
import id.asdos.asdosapp.mainMenu.profile.ProfileKakakActivity;
import id.asdos.asdosapp.mainMenu.profile.ProfileAdikActivity;
import id.asdos.asdosapp.mainMenu.timeLine.TimeLineFragment;
import id.asdos.asdosapp.mainMenu.topics.TopicsFragment;

/**
 * Created by kuwali on 07/01/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;
    private User.UserType userType;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, User.UserType userType) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.userType = userType;
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof TimeLineFragment) {
            ((TimeLineFragment) object).refresh();
        }
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TimeLineFragment timeLineFragment = new TimeLineFragment();
                return timeLineFragment;
            case 2:
                TopicsFragment topicsFragment= new TopicsFragment();
                return topicsFragment;
            case 1:
                NewQuestionFragment newQuestionFragment = new NewQuestionFragment();
                return newQuestionFragment;
            case 3:
                ListKakakFragment listKakakFragment = new ListKakakFragment();
                return listKakakFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
