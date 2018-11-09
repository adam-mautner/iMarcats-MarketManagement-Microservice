package com.imarcats.microservice.market.management.product;

import org.springframework.data.repository.CrudRepository;

import com.imarcats.model.Product;

public interface ProductCrudRepository extends CrudRepository<Product, String> {

}