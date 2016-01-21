package id.asdos.asdosapp.mainMenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;

/**
 * Created by kuwali on 14/01/16.
 */
public class UserListAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> mItems;

    private ImageView profileListKakakImageView;
    private TextView nameListKakakTextView;
    private TextView majorListKakakTextView;
    private TextView universityListKakakTextView;
    private ImageView bintangListKakakImageView;

    public UserListAdapter(Context context, List<User> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (User user : mItems) {
            if (user.getUserType().equals(User.UserType.KAKAK)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        if (mItems.get(position).getUserType().equals(User.UserType.KAKAK)) {
            return mItems.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_kakak, null);
        }
        User user = (User) getItem(position);

        profileListKakakImageView = (ImageView) convertView.findViewById(R.id.profileListKakakImageView);
        nameListKakakTextView = (TextView) convertView.findViewById(R.id.nameListKakakTextView);
        majorListKakakTextView = (TextView) convertView.findViewById(R.id.majorListKakakTextView);
        universityListKakakTextView = (TextView) convertView.findViewById(R.id.universityListKakakTextView);
        bintangListKakakImageView = (ImageView) convertView.findViewById(R.id.bintangListKakakImageView);

        profileListKakakImageView.setImageBitmap(UserService.getInstance(mContext).getProfileImage(user));
        nameListKakakTextView.setText(user.getName());
        majorListKakakTextView.setText(user.getMajor());
        universityListKakakTextView.setText(user.getUniversity());

        return convertView;
    }
}
