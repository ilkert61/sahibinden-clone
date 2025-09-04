package com.sahibinden_clone.sahibinden_clone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahibinden_clone.sahibinden_clone.dto.ProductsDTO;
import com.sahibinden_clone.sahibinden_clone.entity.ProductStatus;
import com.sahibinden_clone.sahibinden_clone.entity.Products;
import com.sahibinden_clone.sahibinden_clone.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsService productsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addProduct_shouldReturnProduct() throws Exception {
        // DTO matching constructor (Short, Integer, Boolean types)
        ProductsDTO dto = new ProductsDTO(
                "BMW",              // brand
                "320i",             // model
                "3",                // series
                (short) 2020,       // productionYear
                "Benzin",           // fuelType
                (short) 2000,       // engineVolume
                "Otomatik",         // transmissionType
                50000,              // mileage
                "Siyah",            // color
                false,              // hasAccidentRecord
                850000,             // price
                "testuser",         // ownerUsername
                null                // imageUrl
        );

        UUID id = UUID.randomUUID();
        Products product = new Products();
        product.setId(id);
        product.setBrand("BMW");
        product.setModel("320i");

        given(productsService.addProduct(any())).willReturn(product);

        mockMvc.perform(post("/product/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("BMW"))
                .andExpect(jsonPath("$.model").value("320i"));
    }

    @Test
    void list_shouldReturnPage() throws Exception {
        Products p = new Products();
        p.setId(UUID.randomUUID());
        p.setBrand("BMW");

        given(productsService.list(any()))
                .willReturn(new PageImpl<>(List.of(p), PageRequest.of(0, 1), 1));

        mockMvc.perform(get("/product/list?page=0&size=1&sort=brand,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("BMW"));
    }

    @Test
    void changeStatus_shouldReturnUpdated() throws Exception {
        UUID id = UUID.randomUUID();

        Products p = new Products();
        p.setId(id);
        p.setBrand("BMW");
        p.setStatus(ProductStatus.SOLD);

        given(productsService.changeStatus(id, ProductStatus.SOLD)).willReturn(p);

        mockMvc.perform(put("/product/status/" + id + "?value=SOLD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SOLD"));
    }
}
