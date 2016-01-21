package id.asdos.asdosapp.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.asdos.asdosapp.auth.model.User;

/**
 * Created by kuwali on 15/01/16.
 */
public class Question implements Serializable, Comparable<Question> {
    public static final String[] TOPICS_TITLE = new String[] {"Semua", "Teknik", "Ekonomi", "Sastra", "Kesehatan", "MIPA", "Hukum", "Seni", "Pertanian"};

    private int id;
    private String question;
    private User userAsked;
    private List<Integer> idCategory;
    private List<Answer> listAnswer;
    private Date date;

    public Question() {
        this.id = 0;
        this.question = null;
        this.userAsked = null;
        this.idCategory = new ArrayList<Integer>();
        this.date = new Date();
        this.listAnswer = new ArrayList<Answer>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public User getUserAsked() {
        return userAsked;
    }

    public void setUserAsked(User userAsked) {
        this.userAsked = userAsked;
    }

    public List<Integer> getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(List<Integer> idCategory) {
        this.idCategory.addAll(idCategory);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Answer> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(List<Answer> listAnswer) {
        this.listAnswer = listAnswer;
    }

    @Override
    public int compareTo(Question another) {
        return this.id <= another.getId() ? (this.id == another.getId() ? 0 : Integer.MAX_VALUE) : Integer.MIN_VALUE;
    }
}
