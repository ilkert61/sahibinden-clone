package com.sahibinden_clone.sahibinden_clone.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "productid", nullable = false)
    private String productId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "series")
    private String series;

    @Column(name = "productionyear")
    private Short productionYear;

    @Column(name = "fueltype")
    private String fuelType;

    @Column(name = "enginevolume")
    private Short engineVolume;

    @Column(name = "transmissiontype")
    private String transmissionType;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "color")
    private String color;

    @Column(name = "hasaccidentrecord")
    private Boolean hasAccidentRecord;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "owner_username")
    private String ownerUsername;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;
}