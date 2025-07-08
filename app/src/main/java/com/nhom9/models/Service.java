package com.nhom9.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Service implements Serializable {

    @SerializedName("service_id")
    String serviceId;

    @SerializedName("category")
    String serviceCategory;

    @SerializedName("detail")
    String serviceDetail;

    @SerializedName("image")
    List<String> serviceImage;

    @SerializedName("service_name")
    String serviceName;

    @SerializedName("num_rate")
    Integer serviceNumRate;


    @SerializedName("num_used")
    Integer serviceNumUsed;

    @SerializedName("rate")
    Double serviceRate;


    @SerializedName("attributes")
    List<ServiceAttribute> attributes;

    public Service(String serviceId, String serviceCategory, String serviceDetail, List<String> serviceImage, String serviceName, Integer serviceNumRate, Integer serviceNumUsed, Double serviceRate, List<ServiceAttribute> attributes) {
        this.serviceId = serviceId;
        this.serviceCategory = serviceCategory;
        this.serviceDetail = serviceDetail;
        this.serviceImage = serviceImage;
        this.serviceName = serviceName;
        this.serviceNumRate = serviceNumRate;
        this.serviceNumUsed = serviceNumUsed;
        this.serviceRate = serviceRate;
        this.attributes = attributes;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public List<String> getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(List<String> serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getServiceNumRate() {
        return serviceNumRate;
    }

    public void setServiceNumRate(Integer serviceNumRate) {
        this.serviceNumRate = serviceNumRate;
    }

    public Integer getServiceNumUsed() {
        return serviceNumUsed;
    }

    public void setServiceNumUsed(Integer serviceNumUsed) {
        this.serviceNumUsed = serviceNumUsed;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public List<ServiceAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ServiceAttribute> attributes) {
        this.attributes = attributes;
    }
}
