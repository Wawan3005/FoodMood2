package com.example.wawan.foodmood2;

import java.io.Serializable;

public class GooglePlace {

    private String name;
    private String category;
    private String rating;
    private String open;
    private double lat;
    private double lng;



    public GooglePlace() {
        this.name = "";
        this.rating = "";
        this.open = "";
        this.lat = 0;
        this.lng = 0;
        this.setCategory("");
    }

    public void afficher(){
        System.out.println(name);
        System.out.println(category);
        System.out.println(rating);
        System.out.println(open);
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public void setRating(String rating) {
        this.rating = rating;
    }


    public String getRating() {
        return rating;
    }

    public void setOpenNow(String open) {
        this.open = open;
    }


    public String getOpenNow() {
        return open;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public double getLng() {
        return lng;
    }
}

