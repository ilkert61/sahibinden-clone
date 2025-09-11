package com.sahibinden_clone.sahibinden_clone.repository;

import com.sahibinden_clone.sahibinden_clone.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
}