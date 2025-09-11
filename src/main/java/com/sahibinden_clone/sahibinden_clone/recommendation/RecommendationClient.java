package com.sahibinden_clone.sahibinden_clone.recommendation;

import com.sahibinden_clone.sahibinden_clone.entity.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationClient {

    private final RestTemplate restTemplate;
    private final String recommendationUrl;

    public RecommendationClient(RestTemplate restTemplate,
                                @Value("${recommendation.service.url}") String recommendationUrl) {
        this.restTemplate = restTemplate;
        this.recommendationUrl = recommendationUrl;
    }

    public List<Vehicle> getByBrandRecommendations(Vehicle target,
                                                   List<Vehicle> all) {
        VehicleDTO targetDto = mapToDto(target);
        List<VehicleDTO> allDto = all.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        RecommendationRequest req = new RecommendationRequest();
        req.setTarget(targetDto);
        req.setAll(allDto);

        List<VehicleDTO> recommendedDtos = restTemplate.exchange(
                recommendationUrl + "/recommend/by-similarity",
                HttpMethod.POST,
                new HttpEntity<>(req),
                new ParameterizedTypeReference<List<VehicleDTO>>() {}
        ).getBody();

        return recommendedDtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    private VehicleDTO mapToDto(Vehicle v) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(v.getId().toString());
        dto.setBrand(v.getBrand());
        dto.setModel(v.getModel());
        dto.setSeries(v.getSeries());
        dto.setProductionYear(v.getProductionYear());
        dto.setFuelType(v.getFuelType());
        dto.setEngineVolume(v.getEngineVolume());
        dto.setTransmissionType(v.getTransmissionType());
        dto.setMileage(v.getMileage());
        dto.setColor(v.getColor());
        dto.setHasAccidentRecord(v.getHasAccidentRecord());
        dto.setPrice(v.getPrice());
        return dto;
    }

    private Vehicle mapToEntity(VehicleDTO dto) {
        Vehicle v = new Vehicle();
        v.setId(UUID.fromString(dto.getId()));
        v.setBrand(dto.getBrand());
        v.setModel(dto.getModel());
        v.setSeries(dto.getSeries());
        v.setProductionYear(dto.getProductionYear());
        v.setFuelType(dto.getFuelType());
        v.setEngineVolume(dto.getEngineVolume());
        v.setTransmissionType(dto.getTransmissionType());
        v.setMileage(dto.getMileage());
        v.setColor(dto.getColor());
        v.setHasAccidentRecord(dto.getHasAccidentRecord());
        v.setPrice(dto.getPrice());
        return v;
    }
}