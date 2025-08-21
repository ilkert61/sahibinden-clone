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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private Products product;

    @BeforeEach
    void setUp() {
        product = new Products();
        product.setId(UUID.randomUUID());
        product.setProductid(1001L);
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
    }

    @Test
    void addProduct_shouldSave() {
        ProductsDTO dto = new ProductsDTO("BMW","320i","3",(short)2020,"Benzin",(short)2000,"Otomatik",50000,"Siyah",false);
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
    }

    @Test
    void editByProductId_shouldUpdateFields() {
        EditProductRequest req = new EditProductRequest();
        req.setColor("Kırmızı");

        when(productsRepository.findByProductid(1001L)).thenReturn(Optional.of(product));
        when(productsRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Products updated = productsService.editByProductId(1001L, req);

        assertThat(updated.getColor()).isEqualTo("Kırmızı");
    }

    @Test
    void changeStatus_shouldUpdateStatus() {
        when(productsRepository.findByProductid(1001L)).thenReturn(Optional.of(product));
        when(productsRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Products updated = productsService.changeStatus(1001L, ProductStatus.SOLD);

        assertThat(updated.getStatus()).isEqualTo(ProductStatus.SOLD);
    }
}
