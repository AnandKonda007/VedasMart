package com.example.vedasmart.DashBordServerResponseModels;

import java.util.ArrayList;

public class CategoryInfo {
    String categoryName;
    String CategoryImage;
    String categoryID;
    private ArrayList<SubCategorysModel> subCategorys;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        CategoryImage = categoryImage;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public ArrayList<SubCategorysModel> getSubCategorys() {
        return subCategorys;
    }

    public void setSubCategorys(ArrayList<SubCategorysModel> subCategorys) {
        this.subCategorys = subCategorys;
    }
}
