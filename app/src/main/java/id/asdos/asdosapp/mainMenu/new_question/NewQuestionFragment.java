package id.asdos.asdosapp.mainMenu.new_question;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.asdos.asdosapp.R;
import id.asdos.asdosapp.auth.model.User;
import id.asdos.asdosapp.auth.model.UserService;
import id.asdos.asdosapp.data.DataService;
import id.asdos.asdosapp.data.Question;

/**
 * Created by kuwali on 07/01/16.
 */
public class NewQuestionFragment extends Fragment {
    private View view;
    private EditText pertanyaanEditText;
    private TextView contohPertanyaanTextView;
    private ImageView pilihTopicButton;
    private TextView topikDipilihTextView;
    private ImageView submitPertanyaanButton;
    private ProgressDialog progressDialog;

    private boolean[] checked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_new_question,container,false);

        checked = new boolean[10];

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);

        pertanyaanEditText = (EditText) view.findViewById(R.id.pertanyaanEditText);
        contohPertanyaanTextView = (TextView) view.findViewById(R.id.contohPertanyaanTextView);
        pilihTopicButton = (ImageView) view.findViewById(R.id.pilihTopikButton);
        topikDipilihTextView = (TextView) view.findViewById(R.id.topikDipilihTextView);
        submitPertanyaanButton = (ImageView) view.findViewById(R.id.submitPertanyaanButton);

        submitPertanyaanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> idTopics = new ArrayList<Integer>();
                for (int i = 0; i <= 8; i++) {
                    if (checked[i]) {
                        idTopics.add(i);
                    }
                }
                String question = pertanyaanEditText.getText().toString();
                addTopic(question,idTopics);
            }
        });

        pilihTopicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] temp = new boolean[10];
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Pilih Topik:");
                alertDialogBuilder.setMultiChoiceItems(Question.TOPICS_TITLE, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            temp[which] = true;
                        } else if (temp[which]){
                            temp[which] = false;
                        }
                    }
                });

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i <= 8; i++) {
                            if (temp[i]) {
                                checked[i] = true;
                                builder.append(Question.TOPICS_TITLE[i]+" ");
                            }
                        }
                        topikDipilihTextView.setText(builder.toString());
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        contohPertanyaanTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Not Ready", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_question, menu);
    }

    private void addTopic(String question, List<Integer> idTopics) {
        progressDialog.show();
        User currUser = UserService.getInstance(getContext()).getCurrentUser();
        DataService.getInstance(getContext()).addQuestion(question, currUser, idTopics, addQuestionListener);
    }

    private DataService.AddQuestionListener addQuestionListener = new DataService.AddQuestionListener() {
        @Override
        public void onResponse(boolean added, String message, Question question) {
            progressDialog.dismiss();
            if (added) {
                Toast.makeText(getContext(),"Question asked", Toast.LENGTH_SHORT).show();
                pertanyaanEditText.setText("");
                topikDipilihTextView.setText("");
                checked = new boolean[10];
            }
        }
    };
}
