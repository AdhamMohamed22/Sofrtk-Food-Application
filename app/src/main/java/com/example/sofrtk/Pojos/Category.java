package com.example.sofrtk.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {
    @SerializedName("idCategory")
    String categoryId;
    @SerializedName("strCategoryThumb")
    String categoryImage;
    @SerializedName("strCategory")
    String categoryTitle;
    @SerializedName("strCategoryDescription")
    String categoryDescription;

    public Category(String categoryId,String categoryImage, String categoryTitle, String categoryDescription) {
        this.categoryId = categoryId;
        this.categoryImage = categoryImage;
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryId() {
        return categoryId;
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

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
