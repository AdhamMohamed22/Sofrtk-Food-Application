package com.example.sofrtk.Pojos;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("strCategoryThumb")
    String categoryImage;
    @SerializedName("strCategory")
    String categoryTitle;
    @SerializedName("strCategoryDescription")
    String categoryDescription;

    public Category(String categoryImage, String categoryTitle, String categoryDescription) {
        this.categoryImage = categoryImage;
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
