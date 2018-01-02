package com.example.iis5.imdb.Models;

/**
 * Created by IIS 5 on 07-12-2017.
 */

public class PosterModel {
    String image ;

    public PosterModel() {
    }

    public PosterModel(String image) {
        this.image = image;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
