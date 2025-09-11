package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.entity.Vehicle;
import com.sahibinden_clone.sahibinden_clone.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public Vehicle findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Araç bulunamadı"));
    }

    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    public Vehicle save(Vehicle vehicle) {
        return repository.save(vehicle);
    }
}