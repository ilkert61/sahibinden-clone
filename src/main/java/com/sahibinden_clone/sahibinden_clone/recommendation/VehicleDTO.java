package com.sahibinden_clone.sahibinden_clone.recommendation;

public class VehicleDTO {

    private String id;
    private String brand;
    private String model;
    private String series;
    private Short productionYear;
    private String fuelType;
    private Short engineVolume;
    private String transmissionType;
    private Integer mileage;
    private String color;
    private Boolean hasAccidentRecord;
    private Integer price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Short getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Short productionYear) {
        this.productionYear = productionYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Short getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(Short engineVolume) {
        this.engineVolume = engineVolume;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getHasAccidentRecord() {
        return hasAccidentRecord;
    }

    public void setHasAccidentRecord(Boolean hasAccidentRecord) {
        this.hasAccidentRecord = hasAccidentRecord;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
