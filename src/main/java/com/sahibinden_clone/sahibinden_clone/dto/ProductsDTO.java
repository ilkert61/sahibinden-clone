package com.sahibinden_clone.sahibinden_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
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
    private Integer price;
    private String ownerUsername;
    private String imageUrl;
}
