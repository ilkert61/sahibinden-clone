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

@Service
public class ProductsService {

    private final ProductsRepository productRepository;

    public ProductsService(ProductsRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Products addProduct(ProductsDTO productDTO) {
        Products product = new Products();
        product.setBrand(productDTO.getBrand());
        product.setModel(productDTO.getModel());
        product.setColor(productDTO.getColor());
        product.setFueltype(productDTO.getFuelType());
        product.setEnginevolume(productDTO.getEngineVolume());
        product.setHasaccidentrecord(productDTO.isHasAccidentRecord());
        product.setMileage(productDTO.getMileage());
        product.setSeries(productDTO.getSeries());
        product.setProductionyear(productDTO.getProductionYear());
        product.setTransmissiontype(productDTO.getTransmissionType());
        return productRepository.save(product);
    }

    // ---- Listeleme (filtresiz) ----
    public Page<Products> list(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Products> listByStatus(ProductStatus status, Pageable pageable) {
        if (status == null) return list(pageable);
        return productRepository.findAllByStatus(status, pageable);}

    // ---- Arama (parametre geldikçe filtre uygula) ----
    public Page<Products> search(
            String brand,
            String model,
            String series,
            String fuelType,
            String transmissionType,
            String color,
            Short minYear,
            Short maxYear,
            Integer minMileage,
            Integer maxMileage,
            Boolean hasAccidentRecord,
            Pageable pageable
    ) {
        // Tüm filtreler boş ise normal listelemeye düş
        if (isAllEmpty(brand, model, series, fuelType, transmissionType, color,
                minYear, maxYear, minMileage, maxMileage, hasAccidentRecord)) {
            return list(pageable);
        }
        return productRepository.search(
                emptyToNull(brand),
                emptyToNull(model),
                emptyToNull(series),
                emptyToNull(fuelType),
                emptyToNull(transmissionType),
                emptyToNull(color),
                minYear,
                maxYear,
                minMileage,
                maxMileage,
                hasAccidentRecord,
                pageable
        );
    }

    // ---- Kısmi Güncelleme: yalnızca productid ile ----
    @Transactional
    public Products editByProductId(Long productid, EditProductRequest req) {
        Products p = productRepository.findByProductid(productid)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı."));

        boolean changed = false;

        if (req.getBrand() != null && !req.getBrand().isBlank()) {
            String v = req.getBrand().trim();
            if (!v.equals(p.getBrand())) { p.setBrand(v); changed = true; }
        }
        if (req.getModel() != null && !req.getModel().isBlank()) {
            String v = req.getModel().trim();
            if (!v.equals(p.getModel())) { p.setModel(v); changed = true; }
        }
        if (req.getSeries() != null && !req.getSeries().isBlank()) {
            String v = req.getSeries().trim();
            if (!v.equals(p.getSeries())) { p.setSeries(v); changed = true; }
        }
        if (req.getProductionYear() != null) {
            Short v = req.getProductionYear();
            if (!v.equals(p.getProductionyear())) { p.setProductionyear(v); changed = true; }
        }
        if (req.getFuelType() != null && !req.getFuelType().isBlank()) {
            String v = req.getFuelType().trim();
            if (!v.equals(p.getFueltype())) { p.setFueltype(v); changed = true; }
        }
        if (req.getEngineVolume() != null) {
            Short v = req.getEngineVolume();
            if (!v.equals(p.getEnginevolume())) { p.setEnginevolume(v); changed = true; }
        }
        if (req.getTransmissionType() != null && !req.getTransmissionType().isBlank()) {
            String v = req.getTransmissionType().trim();
            if (!v.equals(p.getTransmissiontype())) { p.setTransmissiontype(v); changed = true; }
        }
        if (req.getMileage() != null) {
            Integer v = req.getMileage();
            if (!v.equals(p.getMileage())) { p.setMileage(v); changed = true; }
        }
        if (req.getColor() != null && !req.getColor().isBlank()) {
            String v = req.getColor().trim();
            if (!v.equals(p.getColor())) { p.setColor(v); changed = true; }
        }
        if (req.getHasAccidentRecord() != null) {
            Boolean v = req.getHasAccidentRecord();
            if (!v.equals(p.getHasaccidentrecord())) { p.setHasaccidentrecord(v); changed = true; }
        }

        if (!changed) {
            throw new IllegalArgumentException("Güncellenecek bir alan göndermediniz veya değerler aynı.");
        }
        return productRepository.save(p);
    }

    @Transactional
    public Products changeStatus(Long productid, ProductStatus status) {
        Products p = productRepository.findByProductid(productid)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı."));
        if (status == null) throw new IllegalArgumentException("Geçersiz status değeri.");
        if (status.equals(p.getStatus())) return p; // değişiklik yok
        p.setStatus(status);
        return productRepository.save(p);
    }

    // ---- helpers ----
    private String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s.trim();
    }

    private boolean isAllEmpty(
            String brand, String model, String series, String fuelType, String transmissionType, String color,
            Short minYear, Short maxYear, Integer minMileage, Integer maxMileage, Boolean hasAccidentRecord
    ) {
        return (emptyToNull(brand) == null)
                && (emptyToNull(model) == null)
                && (emptyToNull(series) == null)
                && (emptyToNull(fuelType) == null)
                && (emptyToNull(transmissionType) == null)
                && (emptyToNull(color) == null)
                && minYear == null && maxYear == null
                && minMileage == null && maxMileage == null
                && hasAccidentRecord == null;
    }
}
