package com.example.vedasmart.ServerResponseModels;

public class bestSellings {
    String ProductID;
    String subCategoryid;
    String Categoryid;
    String ProductName;
    String Brand;
    String MRP_Price;
    String VMART_Price;
    String quantity;
    String description;
    String postDate;
    String banersID;
    String ProductImage;
    String Product_Status;

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getSubCategoryid() {
        return subCategoryid;
    }

    public void setSubCategoryid(String subCategoryid) {
        this.subCategoryid = subCategoryid;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getMRP_Price() {
        return MRP_Price;
    }

    public void setMRP_Price(String MRP_Price) {
        this.MRP_Price = MRP_Price;
    }

    public String getVMART_Price() {
        return VMART_Price;
    }

    public void setVMART_Price(String VMART_Price) {
        this.VMART_Price = VMART_Price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getBanersID() {
        return banersID;
    }

    public void setBanersID(String banersID) {
        this.banersID = banersID;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProduct_Status() {
        return Product_Status;
    }

    public void setProduct_Status(String product_Status) {
        Product_Status = product_Status;
    }
}
