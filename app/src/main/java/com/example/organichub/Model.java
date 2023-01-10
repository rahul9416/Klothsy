package com.example.organichub;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Model implements Serializable {
    @Exclude
    String imageUrl;
    String name;
    String price;
    String description;
    String quant;
    String key;


    public Model(){}

    public Model(String imageUrl, String name, String price, String description, String quant){
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.quant = quant;
        this.key = key;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
