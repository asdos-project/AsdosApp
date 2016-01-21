package id.asdos.asdosapp.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kuwali on 15/01/16.
 */
public class Answer implements Serializable {
    private int id;
    private int idQuestion;
    private int idUser;
    private String answer;
    private Date date;

    public Answer() {
        this.id = 0;
        this.idQuestion = 0;
        this.idUser = 0;
        this.answer = null;
        this.date = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
