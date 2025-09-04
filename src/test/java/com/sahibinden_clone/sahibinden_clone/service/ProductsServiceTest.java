package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.EditProductRequest;
import com.sahibinden_clone.sahibinden_clone.dto.ProductsDTO;
import com.sahibinden_clone.sahibinden_clone.entity.ProductStatus;
import com.sahibinden_clone.sahibinden_clone.entity.Products;
import com.sahibinden_clone.sahibinden_clone.repository.ProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private Products product;
    private UUID productId;
    private final String ownerUsername = "testUser";

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

        product = new Products();
        product.setId(productId);
        product.setBrand("BMW");
        product.setModel("320i");
        product.setSeries("3");
        product.setProductionyear((short) 2020);
        product.setFueltype("Benzin");
        product.setEnginevolume((short) 2000);
        product.setTransmissiontype("Otomatik");
        product.setMileage(50000);
        product.setColor("Siyah");
        product.setHasaccidentrecord(false);
        product.setStatus(ProductStatus.ACTIVE);
        product.setOwnerUsername(ownerUsername); // important for editByIdOwned
    }

    @Test
    void addProduct_shouldSave() {
        ProductsDTO dto = new ProductsDTO();
        dto.setBrand("BMW");
        dto.setModel("320i");
        dto.setSeries("3");
        dto.setProductionYear((short) 2020);
        dto.setFuelType("Benzin");
        dto.setEngineVolume((short) 2000);
        dto.setTransmissionType("Otomatik");
        dto.setMileage(50000);
        dto.setColor("Siyah");
        dto.setHasAccidentRecord(false);
        dto.setOwnerUsername(ownerUsername);

        when(productsRepository.save(any(Products.class))).thenReturn(product);

        Products saved = productsService.addProduct(dto);

        assertThat(saved.getBrand()).isEqualTo("BMW");
        verify(productsRepository, times(1)).save(any(Products.class));
    }

    @Test
    void list_shouldReturnProducts() {
        when(productsRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(product)));

        Page<Products> page = productsService.list(PageRequest.of(0,20));

        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getBrand()).isEqualTo("BMW");
    }

    @Test
    void editByIdOwned_shouldUpdateFields() {
        EditProductRequest req = new EditProductRequest();
        req.setColor("Kırmızı");

        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productsRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // Pass the username as required by the service
        Products updated = productsService.editByIdOwned(productId, req, ownerUsername);

        assertThat(updated.getColor()).isEqualTo("Kırmızı");
    }

    @Test
    void changeStatus_shouldUpdateStatus() {
        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productsRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Products updated = productsService.changeStatus(productId, ProductStatus.SOLD);

        assertThat(updated.getStatus()).isEqualTo(ProductStatus.SOLD);
    }
}
