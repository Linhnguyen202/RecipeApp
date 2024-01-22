package com.example.recipeapp.model;

import java.io.Serializable;
import java.util.List;

public class CategoryResponse implements Serializable {
    private List<Category> categories;
    public CategoryResponse(){

    }
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public CategoryResponse(List<Category> categories) {
        this.categories = categories;
    }
}
