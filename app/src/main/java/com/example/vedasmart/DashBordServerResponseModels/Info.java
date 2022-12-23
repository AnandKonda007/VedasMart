package com.example.vedasmart.DashBordServerResponseModels;

public class Info {
    String _id;
    String ProductID;
    String subCategoryid;
    String Categoryid;
    String ProductName;
    String Brand;
    int MRP_Price;
    int VMART_Price;
    String description;
    String postDate;
    String ProductImage;
    Boolean Product_Status;
    String customerQuantity;
    String Netweight;
    String stockquantity;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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

    public int getMRP_Price() {
        return MRP_Price;
    }

    public void setMRP_Price(int MRP_Price) {
        this.MRP_Price = MRP_Price;
    }

    public int getVMART_Price() {
        return VMART_Price;
    }

    public void setVMART_Price(int VMART_Price) {
        this.VMART_Price = VMART_Price;
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

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public Boolean getProduct_Status() {
        return Product_Status;
    }

    public void setProduct_Status(Boolean product_Status) {
        Product_Status = product_Status;
    }

    public String getCustomerQuantity() {
        return customerQuantity;
    }

    public void setCustomerQuantity(String customerQuantity) {
        this.customerQuantity = customerQuantity;
    }

    public String getNetweight() {
        return Netweight;
    }

    public void setNetweight(String netweight) {
        Netweight = netweight;
    }

    public String getStockquantity() {
        return stockquantity;
    }

    public void setStockquantity(String stockquantity) {
        this.stockquantity = stockquantity;
    }
}
