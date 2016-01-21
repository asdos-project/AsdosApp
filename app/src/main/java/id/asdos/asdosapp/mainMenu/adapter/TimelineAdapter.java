package id.asdos.asdosapp.mainMenu.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.data.Question;

/**
 * Created by kuwali on 17/01/16.
 */
public class TimelineAdapter extends BaseAdapter {
    private Context mContext;
    private List<Question> questions;

    private ImageView userProfileImageView;
    private TextView userNameTextView;
    private TextView dateTextView;
    private TextView questionTextView;
    private TextView topicsTextView;
    private TextView jumlahJawabanTextView;
    private ImageView jawabButton;

    public TimelineAdapter(Context mContext, List<Question> questions) {
        this.mContext = mContext;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return questions.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_timeline, null);
        }
        Question question = (Question) getItem(position);
        User user = UserService.getInstance(mContext).getUserById(question.getUserAsked().getId());

        userProfileImageView = (ImageView) convertView.findViewById(R.id.userProfileTimelineImageView);
        userNameTextView = (TextView) convertView.findViewById(R.id.userNameTimelineTextView);
        dateTextView = (TextView) convertView.findViewById(R.id.dateTimelineTextView);
        questionTextView = (TextView) convertView.findViewById(R.id.questionTimelineTextView);
        topicsTextView = (TextView) convertView.findViewById(R.id.topikTimelineTextView);
        jumlahJawabanTextView = (TextView) convertView.findViewById(R.id.jumlahJawabTimelineTextView);
        jawabButton = (ImageView) convertView.findViewById(R.id.jawabTimelineButton);

        userProfileImageView.setImageBitmap(UserService.getInstance(mContext).getProfileImage(user));
        userNameTextView.setText(user.getName());

        Date date = new Date();
        long diff = date.getTime() - question.getDate().getTime();
        diff = diff / DateUtils.SECOND_IN_MILLIS;
        if (diff >= 86400) {
            dateTextView.setText(Math.round(diff/86400) + " days");
        } else if (diff >= 3600) {
            dateTextView.setText(Math.round(diff/3600) + " hours");
        } else if (diff >= 60) {
            dateTextView.setText(Math.round(diff/60) + " minutes");
        } else {
            dateTextView.setText(Math.round(diff) + " seconds");
        }

        questionTextView.setText(question.getQuestion());
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < question.getIdCategory().size(); i++) {
            builder.append(" " + Question.TOPICS_TITLE[question.getIdCategory().get(i)]);
            /*if (i == 0) {
                builder.append(" " + Question.TOPICS_TITLE[question.getIdCategory().get(i)]);
            } else if (i == question.getIdCategory().size()-1) {
                builder.append(Question.TOPICS_TITLE[question.getIdCategory().get(i)]);
            } else {
                builder.append("; " + Question.TOPICS_TITLE[question.getIdCategory().get(i)]);
            }*/
        }
        topicsTextView.setText(builder.toString());

        jumlahJawabanTextView.setText(question.getListAnswer().size() + " jawaban");
        jawabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"Jawab dipencet", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
