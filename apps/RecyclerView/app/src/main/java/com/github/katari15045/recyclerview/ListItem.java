package com.github.katari15045.recyclerview;

/**
 * Created by Saketh Katari on 22-02-2018.
 */

public class ListItem {
    private String title = null;
    private String authors = null;
    private int image = -1;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setAuthors(String authors){
        this.authors = authors;
    }

    public String getAuthors(){
        return authors;
    }

    public void setImage(int image){
        this.image = image;
    }

    public int getImage(){
        return image;
    }

}
