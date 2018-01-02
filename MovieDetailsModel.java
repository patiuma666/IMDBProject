package com.example.iis5.imdb.Models;

/**
 * Created by IIS 5 on 04-12-2017.
 */

public class MovieDetailsModel {
    String decription,status;
    double budget,revenue;

    public MovieDetailsModel(String decription, String status, double budget, double revenue) {
        this.decription = decription;
        this.status = status;
        this.budget = budget;
        this.revenue = revenue;
    }

    public MovieDetailsModel() {
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
