package com.sahibinden_clone.sahibinden_clone.controller;

import com.sahibinden_clone.sahibinden_clone.dto.EditProductRequest;
import com.sahibinden_clone.sahibinden_clone.dto.ProductsDTO;
import com.sahibinden_clone.sahibinden_clone.entity.ProductStatus;
import com.sahibinden_clone.sahibinden_clone.entity.Products;
import com.sahibinden_clone.sahibinden_clone.service.ProductsService;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    // Public list
    @GetMapping("/list")
    public Page<Products> list(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               @RequestParam(defaultValue = "productionyear,desc") String sort) {
        Pageable pageable = toPageable(page, size, sort);
        return productsService.list(pageable);
    }

    // User's products
    @GetMapping("/my")
    public Page<Products> my(@RequestParam String username,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int size,
                             @RequestParam(defaultValue = "productionyear,desc") String sort) {
        Pageable pageable = toPageable(page, size, sort);
        return productsService.listByOwnerUsername(username, pageable);
    }

    // Add product
    @PostMapping("/add")
    public Products add(@RequestBody ProductsDTO dto) {
        return productsService.addProduct(dto);
    }

    // Update owned product
    @PutMapping("/edit/{id}")
    public Products editOwned(@PathVariable UUID id,
                              @RequestParam String username,
                              @RequestBody EditProductRequest req) {
        return productsService.editByIdOwned(id, req, username);
    }

    // Change status
    @PutMapping("/status/{id}")
    public Products changeStatus(@PathVariable UUID id,
                                 @RequestParam("value") ProductStatus value) {
        return productsService.changeStatus(id, value);
    }

    // Delete product
    @DeleteMapping("/delete/{id}")
    public void deleteOwned(@PathVariable UUID id,
                            @RequestParam String username) {
        productsService.deleteByIdOwned(id, username);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public Products getById(@PathVariable UUID id) {
        return productsService.getById(id);
    }

    // Upload product image
    @PostMapping("/image/{id}")
    public Products uploadImage(@PathVariable UUID id,
                                @RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) throw new IllegalArgumentException("File is empty.");

        Products p = productsService.getById(id);
        Files.createDirectories(Path.of("uploads"));

        String ext = Optional.ofNullable(file.getOriginalFilename())
                .filter(n -> n.contains("."))
                .map(n -> n.substring(n.lastIndexOf('.')))
                .orElse(".jpg");

        String name = "p" + id + "-" + System.currentTimeMillis() + ext;
        Path target = Path.of("uploads", name);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        p.setImageUrl("/files/" + name);
        return productsService.save(p);
    }

    // Delete product image
    @DeleteMapping("/image/{id}")
    public Products deleteImage(@PathVariable UUID id) {
        Products p = productsService.getById(id);
        p.setImageUrl(null);
        return productsService.save(p);
    }

    // Helper for pagination & sorting
    private Pageable toPageable(int page, int size, String sortParam) {
        String[] parts = sortParam.split(",");
        String field = parts[0];
        Sort sort = (parts.length > 1 && "asc".equalsIgnoreCase(parts[1]))
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();
        return PageRequest.of(page, size, sort);
    }
}

