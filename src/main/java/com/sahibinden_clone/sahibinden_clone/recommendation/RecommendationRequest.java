package com.sahibinden_clone.sahibinden_clone.recommendation;

import java.util.List;

public class RecommendationRequest {

    private VehicleDTO target;
    private List<VehicleDTO> all;

    public VehicleDTO getTarget() {
        return target;
    }

    public void setTarget(VehicleDTO target) {
        this.target = target;
    }

    public List<VehicleDTO> getAll() {
        return all;
    }

    public void setAll(List<VehicleDTO> all) {
        this.all = all;
    }
}