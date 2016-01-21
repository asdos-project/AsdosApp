package id.asdos.asdosapp.mainMenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.data.DataService;
import id.asdos.asdosapp.data.Topic;

/**
 * Created by kuwali on 16/01/16.
 */
public class TopicsListAdapter extends ArrayAdapter<Topic> {

    private List<Topic> topicsList;
    private Context mContext;

    private ImageView logoTopicImageView;
    private TextView jurusanTopicTextView;
    private CheckBox listTopicCheckBox;

    public TopicsListAdapter(Context context, int resource, List<Topic> topicsList) {
        super(context, resource, topicsList);
        mContext = context;
        this.topicsList = topicsList;
    }

    @Override
    public int getCount() {
        return topicsList.size();
    }

    @Override
    public Topic getItem(int position) {
        return topicsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return topicsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_topics, null);
        }

        Topic topic = (Topic) getItem(position);

        logoTopicImageView = (ImageView) convertView.findViewById(R.id.logoTopicsListTopicsImageView);
        jurusanTopicTextView = (TextView) convertView.findViewById(R.id.jurusanTopicsListTopicsTextView);
        listTopicCheckBox = (CheckBox) convertView.findViewById(R.id.listTopicsCheckBox);

        logoTopicImageView.setImageResource(topic.getImage());
        jurusanTopicTextView.setText(topic.getName());
        listTopicCheckBox.setChecked(topic.isSelected());
        listTopicCheckBox.setTag(topic);

        listTopicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                Topic topicChosen = (Topic) checkBox.getTag();
                Toast.makeText(getContext(), topicChosen.getName() + " clicked", Toast.LENGTH_SHORT).show();
                topicChosen.setSelected(checkBox.isChecked());
                DataService.getInstance(getContext()).setTopicChosen(topicChosen.getId(),checkBox.isChecked());
            }
        });

        return convertView;
    }
}
