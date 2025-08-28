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

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductsService productsService;
    public ProductController(ProductsService productsService) { this.productsService = productsService; }

    // Public liste
    @GetMapping("/list")
    public Page<Products> list(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               @RequestParam(defaultValue = "productionyear,desc") String sort) {
        Pageable pageable = toPageable(page, size, sort);
        return productsService.list(pageable);
    }

    // Kullanıcının ilanları
    @GetMapping("/my")
    public Page<Products> my(@RequestParam String username,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int size,
                             @RequestParam(defaultValue = "productionyear,desc") String sort) {
        Pageable pageable = toPageable(page, size, sort);
        return productsService.listByOwnerUsername(username, pageable);
    }

    // Ürün ekle
    @PostMapping("/addProduct")
    public Products add(@RequestBody ProductsDTO dto) {
        return productsService.addProduct(dto);
    }

    // Sahiplik kontrollü güncelleme
    @PutMapping("/editproduct/{productid}")
    public Products editOwned(@PathVariable Long productid,
                              @RequestParam String username,
                              @RequestBody EditProductRequest req) {
        return productsService.editByProductIdOwned(productid, req, username);
    }

    // Status değiştir
    @PutMapping("/status/{productid}")
    public Products changeStatus(@PathVariable Long productid,
                                 @RequestParam("value") ProductStatus value) {
        return productsService.changeStatus(productid, value);
    }

    @DeleteMapping("/delete/{productid}")
    public void deleteOwned(@PathVariable Long productid,
                            @RequestParam String username) {
        productsService.deleteByProductIdOwned(productid, username);
    }

    @GetMapping("/{productid}")
    public Products getById(@PathVariable Long productid) {
        return productsService.getById(productid);
    }

    private Pageable toPageable(int page, int size, String sortParam) {
        String[] parts = sortParam.split(",");
        String field = parts[0];
        Sort sort = (parts.length > 1 && "asc".equalsIgnoreCase(parts[1]))
                ? Sort.by(field).ascending() : Sort.by(field).descending();
        return PageRequest.of(page, size, sort);
    }

    @PostMapping("/image/{productid}")
    public Products uploadImage(@PathVariable Long productid,
                                @RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) throw new IllegalArgumentException("Dosya boş.");

        Products p = productsService.getById(productid); // mevcut metot
        Files.createDirectories(Path.of("uploads"));

        String ext = Optional.ofNullable(file.getOriginalFilename())
                .filter(n -> n.contains("."))
                .map(n -> n.substring(n.lastIndexOf('.')))
                .orElse(".jpg");

        String name = "p" + productid + "-" + System.currentTimeMillis() + ext;
        Path target = Path.of("uploads", name);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        p.setImageUrl("/files/" + name);
        return productsService.save(p);
    }

    @DeleteMapping("/image/{productid}")
    public Products deleteImage(@PathVariable Long productid) {
        Products p = productsService.getById(productid);
        p.setImageUrl(null);
        return productsService.save(p);
    }
}
