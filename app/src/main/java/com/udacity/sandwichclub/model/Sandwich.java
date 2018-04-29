package com.udacity.sandwichclub.model;

import java.util.ArrayList;
import java.util.List;

public class Sandwich {

    private String mainName;
    private List<String> alsoKnownAs;
    private String placeOfOrigin;
    private String description;
    private String image;
    private List<String> ingredients;
    
    /**
     * No args constructor for use in serialization
     */
    public Sandwich() {
        alsoKnownAs = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    public Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients) {
        this.mainName = mainName;
        this.alsoKnownAs = alsoKnownAs;
        this.placeOfOrigin = placeOfOrigin;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
    }

    public String getMainName() {
        return mainName;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
