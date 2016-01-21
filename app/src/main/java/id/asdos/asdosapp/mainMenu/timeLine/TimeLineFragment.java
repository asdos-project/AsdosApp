package id.asdos.asdosapp.mainMenu.timeLine;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.data.DataService;
import id.asdos.asdosapp.data.Question;
import id.asdos.asdosapp.mainMenu.adapter.TimelineAdapter;

/**
 * Created by kuwali on 07/01/16.
 */
public class TimeLineFragment extends Fragment {
    private List<Question> questions = new ArrayList<Question>();
    private TimelineAdapter adapter;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_timeline,container,false);

        ListView timelineListView = (ListView) view.findViewById(R.id.timelineListView);
        timelineListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new TimelineAdapter(getContext(),questions);
        DataService.getInstance(getContext()).getQuestionsList(getQuestionListListener);
    }

    public void refresh() {
        DataService.getInstance(getContext()).getQuestionsList(getQuestionListListener);
    }

    DataService.GetQuestionListListener getQuestionListListener = new DataService.GetQuestionListListener() {
        @Override
        public void onResponse(boolean success, String message, List<Question> questionsList) {
            if (success) {
                questions.clear();
                adapter.notifyDataSetChanged();
                questions.addAll(questionsList);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_timeline, menu);
    }
}
