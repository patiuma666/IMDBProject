package com.example.iis5.imdb.Models;

/**
 * Created by IIS 5 on 07-12-2017.
 */

public class MyWatchlistModel {

    String image,title;

    public MyWatchlistModel(String image, String title) {
        this.image = image;
        this.title = title;
    }

    public MyWatchlistModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
