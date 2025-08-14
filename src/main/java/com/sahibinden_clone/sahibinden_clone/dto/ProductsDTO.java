package com.sahibinden_clone.sahibinden_clone.dto;

import lombok.Data;

@Data

public class ProductsDTO {
    private String brand;
    private String model;
    private String series;
    private short productionYear;
    private String fuelType;
    private short engineVolume;
    private String transmissionType;
    private int mileage;
    private String color;
    private boolean hasAccidentRecord;

}

