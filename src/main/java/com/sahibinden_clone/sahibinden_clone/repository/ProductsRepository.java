package com.sahibinden_clone.sahibinden_clone.repository;

import com.sahibinden_clone.sahibinden_clone.entity.ProductStatus;
import com.sahibinden_clone.sahibinden_clone.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<Products, UUID> {
    Optional<Products> findByProductid(Long productid);

    Page<Products> findAllByStatus(ProductStatus status, Pageable pageable);


    @Query("""
        SELECT p FROM Products p
        WHERE (:brand IS NULL OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%')))
          AND (:model IS NULL OR LOWER(p.model) LIKE LOWER(CONCAT('%', :model, '%')))
          AND (:series IS NULL OR LOWER(p.series) LIKE LOWER(CONCAT('%', :series, '%')))
          AND (:fuelType IS NULL OR LOWER(p.fueltype) = LOWER(:fuelType))
          AND (:transmissionType IS NULL OR LOWER(p.transmissiontype) = LOWER(:transmissionType))
          AND (:color IS NULL OR LOWER(p.color) = LOWER(:color))
          AND (:minYear IS NULL OR p.productionyear >= :minYear)
          AND (:maxYear IS NULL OR p.productionyear <= :maxYear)
          AND (:minMileage IS NULL OR p.mileage >= :minMileage)
          AND (:maxMileage IS NULL OR p.mileage <= :maxMileage)
          AND (:hasAccidentRecord IS NULL OR p.hasaccidentrecord = :hasAccidentRecord)
        """)
    Page<Products> search(
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
    );
}
