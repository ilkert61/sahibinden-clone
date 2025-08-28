package com.sahibinden_clone.sahibinden_clone.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditProductRequest {
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Size(max = 100)
    private String brand;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Size(max = 100)
    private String model;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Size(max = 100)
    private String series;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Short productionYear;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Size(max = 50)
    private String fuelType;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Short engineVolume;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Size(max = 50)
    private String transmissionType;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Min(0)
    private Integer mileage;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Size(max = 50)
    private String color;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) @Min(0)
    private Integer price;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean hasAccidentRecord;
}
