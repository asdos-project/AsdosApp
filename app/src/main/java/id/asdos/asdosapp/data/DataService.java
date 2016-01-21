package id.asdos.asdosapp.data;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.auth.model.User;

/**
 * Created by kuwali on 16/01/16.
 */
public class DataService {
    private static String FILE_NAME = "data_qa.dat";

    private static DataService instance;

    public static DataService getInstance(Context context) {
        if (instance == null) {
            instance = new DataService(context);
        }
        return instance;
    }

    private Context context;
    private List<Question> questionList;
    private List<Topic> topicList;

    public DataService(Context context) {
        this.context = context;
        deserializeData();
    }

    public void setTopicChosen(int position, boolean checked) {
        topicList.get(position).setSelected(checked);
        serializeData();
    }

    public void getTopicList(GetTopicListListener getTopicListListener) {
        getTopicListListener.onResponse(true,"",topicList);
    }

    public void getQuestionsList(GetQuestionListListener getQuestionListListener) {
        if (topicList.get(0).isSelected()) {
            Collections.sort(questionList);
            getQuestionListListener.onResponse(true,"",questionList);
        } else {
            List<Question> questionList = new ArrayList<Question>();
            for (Question q : this.questionList) {
                for (int i : q.getIdCategory()) {
                    if (topicList.get(i).isSelected()) {
                        questionList.add(q);
                        break;
                    }
                }
            }
            Collections.sort(questionList);
            getQuestionListListener.onResponse(true,"",questionList);
        }

    }

    public void addQuestion(String question, User userAsked, List<Integer> idTopic, AddQuestionListener listener) {
        if (question == null) {
            if (listener != null) {
                listener.onResponse(false, "Isi pertanyaannya terlebih dahulu", null);
            }
        } else if (idTopic.isEmpty()) {
            if (listener != null) {
                listener.onResponse(false, "Pilih minimal 1 topik", null);
            }
        } else {
            new AddQuestionTask().execute(new Object[] {question, userAsked, idTopic, listener});
        }
    }

    private void newTopicList() {
        Topic topic = new Topic(0, "Semua", false, R.drawable.jur_semua);
        topicList.add(topic);
        topic = new Topic(1, "Teknik", false, R.drawable.jur_teknik);
        topicList.add(topic);
        topic = new Topic(2, "Ekonomi", false, R.drawable.jur_ekonomi);
        topicList.add(topic);
        topic = new Topic(3, "Sastra", false, R.drawable.jur_sastra_bahasa);
        topicList.add(topic);
        topic = new Topic(4, "Kesehatan", false, R.drawable.jur_kesehatan);
        topicList.add(topic);
        topic = new Topic(5, "MIPA", false, R.drawable.jur_mipa);
        topicList.add(topic);
        topic = new Topic(6, "Hukum", false, R.drawable.jur_hukum);
        topicList.add(topic);
        topic = new Topic(7, "Seni", false, R.drawable.jur_seni);
        topicList.add(topic);
        topic = new Topic(8, "Pertanian", false, R.drawable.jur_pertanian);
        topicList.add(topic);
    }

    private void deserializeData() {
        File file = context.getFileStreamPath(FILE_NAME);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                questionList = (List<Question>) objectInputStream.readObject();
                topicList = (List<Topic>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                questionList = new ArrayList<Question>();
                topicList = new ArrayList<Topic>();
                newTopicList();
            }
        } else {
            questionList = new ArrayList<Question>();
            topicList = new ArrayList<Topic>();
            newTopicList();
        }
    }

    public void serializeData() {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(questionList);
            objectOutputStream.writeObject(topicList);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AddQuestionTask extends AsyncTask<Object,Void,Question> {
        AddQuestionListener listener;

        @Override
        protected Question doInBackground(Object... objects) {
            String question = String.valueOf(objects[0]).trim().toString();
            User userAsked = (User) objects[1];
            List<Integer> idTopic = (List<Integer>) objects[2];
            listener = (AddQuestionListener) objects[3];

            Question questionCreated = new Question();
            questionCreated.setId(questionList.size());
            questionCreated.setQuestion(question);
            questionCreated.setUserAsked(userAsked);
            questionCreated.setIdCategory(idTopic);

            questionList.add(questionCreated);
            serializeData();
            return questionCreated;
        }

        @Override
        protected void onPostExecute(Question question) {
            super.onPostExecute(question);
            boolean added = (question == null) ? false : true;
            String message = added ? "Added" : "Not Valid";
            if (listener != null)
                listener.onResponse(added,message,question);
        }
    }

    public interface GetTopicListListener {
        public void onResponse(boolean success, String message, List<Topic> topics);
    }

    public interface AddQuestionListener {
        public void onResponse(boolean added, String message, Question question);
    }

    public interface GetQuestionListListener {
        public void onResponse(boolean success, String message, List<Question> questions);
    }
}
