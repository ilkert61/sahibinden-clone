package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.EditProductRequest;
import com.sahibinden_clone.sahibinden_clone.dto.ProductsDTO;
import com.sahibinden_clone.sahibinden_clone.entity.ProductStatus;
import com.sahibinden_clone.sahibinden_clone.entity.Products;
import com.sahibinden_clone.sahibinden_clone.repository.ProductsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductsService {

    private final ProductsRepository productRepository;

    public ProductsService(ProductsRepository productRepository) {
        this.productRepository = productRepository;
    }

    /* ---------- Helpers ---------- */

    private Products getByIdOrThrow(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı."));
    }

    private boolean isBlank(String s) { return s == null || s.isBlank(); }
    private String nullIfBlank(String s) { return isBlank(s) ? null : s.trim(); }

    /* ---------- Queries ---------- */

    public Page<Products> list(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Products> listByOwnerUsername(String username, Pageable pageable) {
        return productRepository.findAllByOwnerUsernameIgnoreCase(username, pageable);
    }

    public Products getById(UUID id) {
        return getByIdOrThrow(id);
    }

    public Products save(Products p) {
        return productRepository.save(p);
    }

    public Page<Products> search(
            String brand, String model, String series, String fuelType, String transmissionType, String color,
            Short minYear, Short maxYear, Integer minMileage, Integer maxMileage, Boolean hasAccidentRecord,
            Pageable pageable
    ) {
        boolean allEmpty = (isBlank(brand) && isBlank(model) && isBlank(series) &&
                isBlank(fuelType) && isBlank(transmissionType) && isBlank(color) &&
                minYear == null && maxYear == null && minMileage == null &&
                maxMileage == null && hasAccidentRecord == null);
        if (allEmpty) return list(pageable);

        return productRepository.search(
                nullIfBlank(brand), nullIfBlank(model), nullIfBlank(series),
                nullIfBlank(fuelType), nullIfBlank(transmissionType), nullIfBlank(color),
                minYear, maxYear, minMileage, maxMileage, hasAccidentRecord, pageable
        );
    }

    /* ---------- Commands ---------- */

    public Products addProduct(ProductsDTO dto) {
        Products p = new Products();
        p.setBrand(dto.getBrand());
        p.setModel(dto.getModel());
        p.setColor(dto.getColor());
        p.setFueltype(dto.getFuelType());
        p.setEnginevolume(dto.getEngineVolume());
        p.setHasaccidentrecord(dto.isHasAccidentRecord());
        p.setMileage(dto.getMileage());
        p.setSeries(dto.getSeries());
        p.setProductionyear(dto.getProductionYear());
        p.setTransmissiontype(dto.getTransmissionType());
        p.setPrice(dto.getPrice());
        p.setOwnerUsername(dto.getOwnerUsername());
        p.setImageUrl(dto.getImageUrl());
        return productRepository.save(p);
    }

    public void deleteByIdOwned(UUID id, String username) {
        Products p = getByIdOrThrow(id);
        if (p.getOwnerUsername() == null || !username.equalsIgnoreCase(p.getOwnerUsername())) {
            throw new RuntimeException("Silmeye yetkiniz yok!");
        }
        productRepository.delete(p);
    }

    @Transactional
    public Products editByIdOwned(UUID id, EditProductRequest req, String username) {
        Products p = getByIdOrThrow(id);
        if (p.getOwnerUsername() == null || !p.getOwnerUsername().equalsIgnoreCase(username)) {
            throw new IllegalArgumentException("Bu ilan size ait değil.");
        }

        boolean changed = false;
        if (!isBlank(req.getBrand()) && !req.getBrand().equals(p.getBrand())) { p.setBrand(req.getBrand().trim()); changed = true; }
        if (!isBlank(req.getModel()) && !req.getModel().equals(p.getModel())) { p.setModel(req.getModel().trim()); changed = true; }
        if (!isBlank(req.getSeries()) && !req.getSeries().equals(p.getSeries())) { p.setSeries(req.getSeries().trim()); changed = true; }
        if (req.getProductionYear() != null && !req.getProductionYear().equals(p.getProductionyear())) { p.setProductionyear(req.getProductionYear()); changed = true; }
        if (!isBlank(req.getFuelType()) && !req.getFuelType().equals(p.getFueltype())) { p.setFueltype(req.getFuelType().trim()); changed = true; }
        if (req.getEngineVolume() != null && !req.getEngineVolume().equals(p.getEnginevolume())) { p.setEnginevolume(req.getEngineVolume()); changed = true; }
        if (!isBlank(req.getTransmissionType()) && !req.getTransmissionType().equals(p.getTransmissiontype())) { p.setTransmissiontype(req.getTransmissionType().trim()); changed = true; }
        if (req.getMileage() != null && !req.getMileage().equals(p.getMileage())) { p.setMileage(req.getMileage()); changed = true; }
        if (!isBlank(req.getColor()) && !req.getColor().equals(p.getColor())) { p.setColor(req.getColor().trim()); changed = true; }
        if (req.getPrice() != null && !req.getPrice().equals(p.getPrice())) { p.setPrice(req.getPrice()); changed = true; }
        if (req.getHasAccidentRecord() != null && !req.getHasAccidentRecord().equals(p.getHasaccidentrecord())) { p.setHasaccidentrecord(req.getHasAccidentRecord()); changed = true; }

        if (!changed) throw new IllegalArgumentException("Güncellenecek alan yok.");
        return productRepository.save(p);
    }

    @Transactional
    public Products changeStatus(UUID id, ProductStatus status) {
        Products p = getByIdOrThrow(id);
        if (status == null) throw new IllegalArgumentException("Geçersiz status değeri.");
        if (status.equals(p.getStatus())) return p;
        p.setStatus(status);
        return productRepository.save(p);
    }
}
