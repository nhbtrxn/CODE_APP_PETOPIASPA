package com.nhom9.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    @SerializedName("product_id")
     String productId;
    @SerializedName("product_name")
     String productName;
    @SerializedName("category")
     String productCategory;
    @SerializedName("brand")
     String productBrand;
    @SerializedName("detail")
     String productDetail;
    @SerializedName("image")
     List<String> productImage;
    @SerializedName("number_sold")
    Double productNumberSold;
    @SerializedName("rate")
    Double productRate;
    @SerializedName("attributes")
    private List<ProductAttribute> attributes;
     // Constructor
    public Product(String productId, String productName, String productCategory, String productBrand, String productDetail, List<String> productImage, Double productNumberSold, Double productRate, List<ProductAttribute> attributes) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productBrand = productBrand;
        this.productDetail = productDetail;
        this.productImage = productImage;
        this.productNumberSold = productNumberSold;
        this.productRate = productRate;
        this.attributes = attributes;
    }
    // Getter and Setter
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public List<String> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<String> productImage) {
        this.productImage = productImage;
    }

    public Double getProductNumberSold() {
        return productNumberSold;
    }

    public void setProductNumberSold(Double productNumberSold) {
        this.productNumberSold = productNumberSold;
    }

    public Double getProductRate() {
        return productRate;
    }

    public void setProductRate(Double productRate) {
        this.productRate = productRate;
    }

    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }
}