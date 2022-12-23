package com.example.vedasmart.DashBordServerResponseModels;

public class banners {
    String banersID;
    String CategoryID;
    String subCategoryID;
    String banerImage;

    public String getBanersID() {
        return banersID;
    }

    public void setBanersID(String banersID) {
        this.banersID = banersID;
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

    public String getBanerImage() {
        return banerImage;
    }

    public void setBanerImage(String banerImage) {
        this.banerImage = banerImage;
    }
}
