package com.sahibinden_clone.sahibinden_clone.repository;

import com.sahibinden_clone.sahibinden_clone.entity.Products;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductsRepository extends CrudRepository <Products, UUID>  {

}
