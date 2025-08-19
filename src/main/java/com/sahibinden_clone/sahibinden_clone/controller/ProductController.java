package com.sahibinden_clone.sahibinden_clone.controller;

import com.sahibinden_clone.sahibinden_clone.dto.EditProductRequest;
import com.sahibinden_clone.sahibinden_clone.dto.ProductsDTO;
import com.sahibinden_clone.sahibinden_clone.entity.ProductStatus;
import com.sahibinden_clone.sahibinden_clone.entity.Products;
import com.sahibinden_clone.sahibinden_clone.service.ProductsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/addProduct")
    public Products addProduct(@RequestBody ProductsDTO productsDTO) {
        return productsService.addProduct(productsDTO);
    }

    // ---- Listeleme ----
    // Örn: GET /product/list?page=0&size=20&sort=productionyear,desc
    @GetMapping("/list")
    public Page<Products> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "productionyear,desc") String sort
    ) {
        Pageable pageable = toPageable(page, size, sort);
        return productsService.list(pageable);
    }

    // ---- Arama (tek alan da verilebilir) ----
    // Örn: GET /product/search?brand=BMW
    @GetMapping("/search")
    public Page<Products> search(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String series,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String transmissionType,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Short minYear,
            @RequestParam(required = false) Short maxYear,
            @RequestParam(required = false) Integer minMileage,
            @RequestParam(required = false) Integer maxMileage,
            @RequestParam(required = false) Boolean hasAccidentRecord,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "productionyear,desc") String sort
    ) {
        Pageable pageable = toPageable(page, size, sort);
        return productsService.search(
                brand, model, series, fuelType, transmissionType, color,
                minYear, maxYear, minMileage, maxMileage, hasAccidentRecord,
                pageable
        );
    }


    @PutMapping("/editproduct/{productid:\\d+}")
    public Products editByProductId(@PathVariable("productid") Long productid,
                                    @RequestBody EditProductRequest req) {
        return productsService.editByProductId(productid, req);
    }

    @PutMapping("/editproduct/{productid}")
    public Products editByProductIdPath(@PathVariable("productid") Long productid,
                                        @RequestBody EditProductRequest req) {
        return productsService.editByProductId(productid, req);
    }

    // ---- Yalnızca status değiştir ----
    // Örn: PUT /product/status/148354?value=SOLD
    @PutMapping("/status/{productid}")
    public Products changeStatus(@PathVariable("productid") Long productid,
                                 @RequestParam("value") ProductStatus value) {
        return productsService.changeStatus(productid, value);
    }

    private Pageable toPageable(int page, int size, String sortParam) {
        String[] parts = sortParam.split(",");
        String field = parts[0];
        Sort sort = (parts.length > 1 && "asc".equalsIgnoreCase(parts[1]))
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        return PageRequest.of(page, size, sort);
    }
}
