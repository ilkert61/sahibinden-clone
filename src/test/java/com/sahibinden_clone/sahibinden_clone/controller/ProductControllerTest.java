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
        ProductsDTO dto = new ProductsDTO("BMW","320i","3",(short)2020,"Benzin",(short)2000,"Otomatik",50000,"Siyah",false);

        Products product = new Products();
        product.setId(UUID.randomUUID());
        product.setProductid(1001L);
        product.setBrand("BMW");
        product.setModel("320i");

        given(productsService.addProduct(any())).willReturn(product);

        mockMvc.perform(post("/product/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("BMW"));
    }

    @Test
    void list_shouldReturnPage() throws Exception {
        Products p = new Products();
        p.setBrand("BMW");
        given(productsService.list(any())).willReturn(new PageImpl<>(List.of(p), PageRequest.of(0,1), 1));

        mockMvc.perform(get("/product/list?page=0&size=1&sort=brand,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("BMW"));
    }

    @Test
    void changeStatus_shouldReturnUpdated() throws Exception {
        Products p = new Products();
        p.setProductid(1001L);
        p.setBrand("BMW");
        p.setStatus(ProductStatus.SOLD);

        given(productsService.changeStatus(1001L, ProductStatus.SOLD)).willReturn(p);

        mockMvc.perform(put("/product/status/1001?value=SOLD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SOLD"));
    }
}
