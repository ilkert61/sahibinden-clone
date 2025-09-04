package com.sahibinden_clone.sahibinden_clone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @NotNull
    @Size(max = 100)
    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @NotNull
    @Size(max = 100)
    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @NotNull
    @Size(max = 100)
    @Column(name = "series", nullable = false, length = 100)
    private String series;

    @NotNull
    @Column(name = "productionyear", nullable = false)
    private Short productionyear;

    @NotNull
    @Size(max = 50)
    @Column(name = "fueltype", nullable = false, length = 50)
    private String fueltype;

    @NotNull
    @Column(name = "enginevolume", nullable = false)
    private Short enginevolume;

    @NotNull
    @Size(max = 50)
    @Column(name = "transmissiontype", nullable = false, length = 50)
    private String transmissiontype;

    @NotNull
    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @NotNull
    @Size(max = 50)
    @Column(name = "color", nullable = false, length = 50)
    private String color;

    @NotNull
    @Column(name = "hasaccidentrecord", nullable = false)
    private Boolean hasaccidentrecord;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProductStatus status;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @Size(max = 100)
    @Column(name = "owner_username", length = 100)
    private String ownerUsername;

    @Size(max = 255)
    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = ProductStatus.ACTIVE;
        }
    }
}
