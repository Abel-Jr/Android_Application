package com.example.application1;

public class Annonces {
    private int id;
    private String name;
    private String categories;
    private String description;
    private String prices;

    public Annonces(int id , String name, String categories, String description, String prices) {
        this.name = name;
        this.categories = categories;
        this.description = description;
        this.prices = prices;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }
}
