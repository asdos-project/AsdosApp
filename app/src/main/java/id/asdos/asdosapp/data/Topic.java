package id.asdos.asdosapp.data;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by kuwali on 16/01/16.
 */
public class Topic {
    private int id;
    private String name;
    private boolean selected;
    private int image;

    public Topic() {
        this.id = 0;
        this.name = null;
        this.selected = false;
        this.image = 0;
    }

    public Topic(int id, String name, boolean selected, int image) {
        this.id = id;
        this.name = name;
        this.selected = selected;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
