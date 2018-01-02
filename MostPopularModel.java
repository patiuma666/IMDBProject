package com.example.iis5.imdb.Models;

/**
 * Created by IIS 5 on 13-11-2017.
 */

public class MostPopularModel {

    private  String Title,releaseDate,image;
    private   int voteCount,movieid;
    private double voteAvg,popularity;

    public MostPopularModel(String title, String releaseDate, int voteCount, String image, double voteAvg, double popularity) {
        Title = title;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.image = image;
        this.voteAvg = voteAvg;
        this.popularity = popularity;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public MostPopularModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}



