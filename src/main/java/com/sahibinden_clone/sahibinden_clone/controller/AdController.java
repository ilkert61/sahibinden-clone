package com.sahibinden_clone.sahibinden_clone.controller;

import com.sahibinden_clone.sahibinden_clone.entity.Vehicle;
import com.sahibinden_clone.sahibinden_clone.service.VehicleService;
import com.sahibinden_clone.sahibinden_clone.recommendation.RecommendationClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final VehicleService vehicleService;
    private final RecommendationClient recommendationClient;

    public AdController(VehicleService vehicleService,
                        RecommendationClient recommendationClient) {
        this.vehicleService = vehicleService;
        this.recommendationClient = recommendationClient;
    }

    @GetMapping("/{id}")
    public Vehicle getAdById(@PathVariable UUID id) {      // ← UUID olarak değişti
        return vehicleService.findById(id);
    }

    @GetMapping
    public List<Vehicle> getAllAds() {
        return vehicleService.findAll();
    }

    @GetMapping("/{id}/recommendations")
    public List<Vehicle> getRecommendations(@PathVariable UUID id) {  // ← UUID
        Vehicle target = vehicleService.findById(id);
        List<Vehicle> all   = vehicleService.findAll();
        return recommendationClient.getByBrandRecommendations(target, all);
    }
}