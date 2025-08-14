package com.sahibinden_clone.sahibinden_clone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Generated;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
public class Products {


    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "productid", insertable = false, updatable = false)
    private Long productid;



    @NotNull
    @Size(max = 100)
    @Column(name = "brand",nullable = false, length = 100)
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
    @Column(name = "fueltype", length = 50,nullable = false)
    private String fueltype;

    @NotNull
    @Column(name = "enginevolume", nullable = false)
    private Short enginevolume;

    @NotNull
    @Size(max = 50)
    @Column(name = "transmissiontype", length = 50,nullable = false)
    private String transmissiontype;

    @NotNull
    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @NotNull
    @Size(max = 50)
    @Column(name = "color", length = 50,nullable = false)
    private String color;

    @NotNull
    @Column(name = "hasaccidentrecord", nullable = false)
    private Boolean hasaccidentrecord;

}
