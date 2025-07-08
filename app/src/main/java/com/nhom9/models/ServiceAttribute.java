package com.nhom9.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceAttribute implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("service_attributes_id")
    private String serviceAttributesId;

    @SerializedName("min_price")
    private Double minPrice;

    @SerializedName("max_price")
    private Double maxPrice;

    @SerializedName("min_size")
    private Double minSize;

    @SerializedName("max_size")
    private Double maxSize;

    @SerializedName("unit")
    private String unit;

    @SerializedName("service_id")
    private String serviceId;

    @SerializedName("dye_option")
    private String dyeOption;
    // Constructor
    public ServiceAttribute(String id, String serviceAttributesId, Double minPrice, Double maxPrice,
                            Double minSize, Double maxSize, String unit, String serviceId, String dyeOption) {
        this.id = id;
        this.serviceAttributesId = serviceAttributesId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.unit = unit;
        this.serviceId = serviceId;
        this.dyeOption = dyeOption;
    }
    // Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceAttributesId() {
        return serviceAttributesId;
    }

    public void setServiceAttributesId(String serviceAttributesId) {
        this.serviceAttributesId = serviceAttributesId;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinSize() {
        return minSize;
    }

    public void setMinSize(Double minSize) {
        this.minSize = minSize;
    }

    public Double getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Double maxSize) {
        this.maxSize = maxSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getDyeOption() {
        return dyeOption;
    }

    public void setDyeOption(String dyeOption) {
        this.dyeOption = dyeOption;
    }
}
