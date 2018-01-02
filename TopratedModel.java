package com.example.iis5.imdb.Models;

/**
 * Created by IIS 5 on 06-12-2017.
 */

public class TopratedModel {
    private  String Title
            ,releaseDate,image,decription,status;
    private   int voteCount,id;
    private double voteAvg,budget ,revenue;
    private String popularity;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public TopratedModel(String decription, String status, double budget, double revenue) {
        this.decription = decription;
        this.status = status;
        this.budget = budget;
        this.revenue = revenue;
    }
    public TopratedModel(int id, String title, String releaseDate, int voteCount, String image, double voteAvg, String popularity) {
        Title = title;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.id=id;
        this.image = image;
        this.voteAvg = voteAvg;
        this.popularity = popularity;
    }
    public TopratedModel() {
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

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}


