package com.example.iis5.imdb.Models;

/**
 * Created by IIS 5 on 24-11-2017.
 */

public class MyFavrotiesModel extends UpcomingMovieModel{

    private  String Title,releaseDate,image;
    private   int voteCount;
    private double voteAvg;

   public MyFavrotiesModel(String title, String releaseDate, int voteCount, String image, double voteAvg, double popularity) {
        Title = title;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.image = image;
        this.voteAvg = voteAvg;

    }

    public MyFavrotiesModel() {
    }

}
