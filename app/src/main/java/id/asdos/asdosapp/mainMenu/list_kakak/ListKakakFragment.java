package id.asdos.asdosapp.mainMenu.list_kakak;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.mainMenu.adapter.UserListAdapter;
import id.asdos.asdosapp.mainMenu.profile.ProfileKakakActivity;

/**
 * Created by kuwali on 14/01/16.
 */
public class ListKakakFragment extends Fragment {
    private List<User> users = new ArrayList<User>();
    private UserListAdapter adapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_list_kakak, container, false);

        ListView userListView = (ListView) view.findViewById(R.id.listKakakListView);
        userListView.setAdapter(adapter);
        userListView.setOnItemClickListener(onItemClickListener);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new UserListAdapter(getContext(), users);
        UserService.getInstance(getContext()).getKakakList(getUserListListener);
    }

    UserService.GetUserListListener getUserListListener = new UserService.GetUserListListener() {
        @Override
        public void onResponse(boolean success, String message, List<User> userList) {
            if (success) {
                users.clear();
                adapter.notifyDataSetChanged();
                users.addAll(userList);
                adapter.notifyDataSetChanged();
            }
        }
    };

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), ProfileKakakActivity.class);
            intent.putExtra("user",users.get(position));
            startActivity(intent);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_kakak, menu);
    }
}
