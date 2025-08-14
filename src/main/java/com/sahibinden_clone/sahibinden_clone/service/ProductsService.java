package com.sahibinden_clone.sahibinden_clone.service;

import com.sahibinden_clone.sahibinden_clone.dto.ProductsDTO;
import com.sahibinden_clone.sahibinden_clone.entity.Products;
import com.sahibinden_clone.sahibinden_clone.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productRepository;
    public Products addProduct (@RequestBody ProductsDTO productDTO) {
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

}
