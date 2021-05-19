package com.example.mycollection.model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Can> cans;

    public Category() {
    }

    public Category(String name, ArrayList<Can> cans) {
        this.name = name;
        this.cans = cans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Can> getCans() {
        return cans;
    }

    public void setCans(ArrayList<Can> cans) {
        this.cans = cans;
    }

    @Override
    public String toString() {
        return name;
    }
}
