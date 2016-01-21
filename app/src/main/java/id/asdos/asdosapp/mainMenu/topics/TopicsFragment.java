package id.asdos.asdosapp.mainMenu.topics;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SearchViewCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.data.DataService;
import id.asdos.asdosapp.data.Topic;
import id.asdos.asdosapp.mainMenu.adapter.TopicsListAdapter;

/**
 * Created by kuwali on 07/01/16.
 */
public class TopicsFragment extends Fragment {
    private View view;
    private List<Topic> listTopic = new ArrayList<Topic>();
    private TopicsListAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_topics, container, false);

        SearchView searchView = (SearchView) view.findViewById(R.id.listTopicsSearchBar);
        ListView topicsListView = (ListView) view.findViewById(R.id.topicsListView);
        topicsListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new TopicsListAdapter(getContext(), R.layout.layout_list_topics, listTopic);
        DataService.getInstance(getContext()).getTopicList(getTopicListListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_topics, menu);
    }

    DataService.GetTopicListListener getTopicListListener = new DataService.GetTopicListListener() {
        @Override
        public void onResponse(boolean success, String message, List<Topic> topics) {
            if (success) {
                listTopic.clear();
                adapter.notifyDataSetChanged();
                listTopic.addAll(topics);
                adapter.notifyDataSetChanged();
            }
        }
    };
}
