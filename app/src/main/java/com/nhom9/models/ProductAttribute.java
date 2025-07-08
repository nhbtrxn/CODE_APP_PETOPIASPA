package com.nhom9.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductAttribute implements Serializable {
    @SerializedName("product_attributes_id")
    String productAttributeId;
    @SerializedName("discountId")
    String productDiscountId;
    @SerializedName("price")
    Double productPrice;
    @SerializedName("product_id")
    String productId;
    @SerializedName("size")
    String productSize;
    @SerializedName("unit")
    String productUnit;

    // Constructor
    public ProductAttribute(String productAttributeId, String productDiscountId, Double productPrice, String productId, String productSize, String productUnit) {
        this.productAttributeId = productAttributeId;
        this.productDiscountId = productDiscountId;
        this.productPrice = productPrice;
        this.productId = productId;
        this.productSize = productSize;
        this.productUnit = productUnit;
    }
    // Getter and Setter
    public String getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(String productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public String getProductDiscountId() {
        return productDiscountId;
    }

    public void setProductDiscountId(String productDiscountId) {
        this.productDiscountId = productDiscountId;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
}
