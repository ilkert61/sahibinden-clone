package com.sahibinden_clone.sahibinden_clone.repository;

import com.sahibinden_clone.sahibinden_clone.entity.ProductStatus;
import com.sahibinden_clone.sahibinden_clone.entity.Products;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductsRepositoryTest {

    @Autowired
    private ProductsRepository productsRepository;

    private Products product1;
    private Products product2;

    @BeforeEach
    void setUp() {
        product1 = new Products();
        product1.setId(UUID.randomUUID());
        product1.setBrand("BMW");
        product1.setModel("320i");
        product1.setSeries("3");
        product1.setProductionyear((short) 2020);
        product1.setFueltype("Benzin");
        product1.setEnginevolume((short) 2000);
        product1.setTransmissiontype("Otomatik");
        product1.setMileage(50000);
        product1.setColor("Siyah");
        product1.setHasaccidentrecord(false);
        product1.setStatus(ProductStatus.ACTIVE);
        product1.setPrice(50000);

        product2 = new Products();
        product2.setId(UUID.randomUUID());
        product2.setBrand("Audi");
        product2.setModel("A4");
        product2.setSeries("A");
        product2.setProductionyear((short) 2018);
        product2.setFueltype("Dizel");
        product2.setEnginevolume((short) 1900);
        product2.setTransmissiontype("Manuel");
        product2.setMileage(80000);
        product2.setColor("Beyaz");
        product2.setHasaccidentrecord(true);
        product2.setStatus(ProductStatus.SOLD);
        product2.setPrice(60000);

        productsRepository.save(product1);
        productsRepository.save(product2);
    }

    @Test
    void findById_shouldReturnCorrectProduct() {
        UUID id = product1.getId();
        assertThat(productsRepository.findById(id)).isPresent();
        assertThat(productsRepository.findById(id).get().getBrand()).isEqualTo("BMW");
    }

    @Test
    void findAllByStatus_shouldReturnOnlyActive() {
        Page<Products> page = productsRepository.findAllByStatus(ProductStatus.ACTIVE, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getBrand()).isEqualTo("BMW");
    }

    @Test
    void search_shouldFilterByBrand() {
        Page<Products> result = productsRepository.search(
                "bmw", null, null, null, null, null,
                null, null, null, null, null,
                PageRequest.of(0, 10)
        );
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getModel()).isEqualTo("320i");
    }

    @Test
    void search_shouldFilterByYearRange() {
        Page<Products> result = productsRepository.search(
                null, null, null, null, null, null,
                (short) 2019, (short) 2021,
                null, null, null,
                PageRequest.of(0, 10)
        );
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getBrand()).isEqualTo("BMW");
    }

    @Test
    void search_shouldReturnBoth_whenNoFilters() {
        Page<Products> result = productsRepository.search(
                null, null, null, null, null, null,
                null, null, null, null, null,
                PageRequest.of(0, 10)
        );
        assertThat(result.getTotalElements()).isEqualTo(2);
    }
}
