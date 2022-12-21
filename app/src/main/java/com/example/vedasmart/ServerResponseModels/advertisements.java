package com.example.vedasmart.ServerResponseModels;

public class advertisements {
    String advertiesmentID;
    String CategoryID;
    String subCategoryID;
    String advertiesmentImage;

    public String getAdvertiesmentID() {
        return advertiesmentID;
    }

    public void setAdvertiesmentID(String advertiesmentID) {
        this.advertiesmentID = advertiesmentID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getSubCategoryID() {
        return subCategoryID;
    }

    public void setSubCategoryID(String subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    public String getAdvertiesmentImage() {
        return advertiesmentImage;
    }

    public void setAdvertiesmentImage(String advertiesmentImage) {
        this.advertiesmentImage = advertiesmentImage;
    }
}
