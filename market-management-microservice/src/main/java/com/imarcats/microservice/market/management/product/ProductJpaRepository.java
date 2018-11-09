package com.imarcats.microservice.market.management.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imarcats.model.Product;
import com.imarcats.model.types.ActivationStatus;

public interface ProductJpaRepository extends JpaRepository<Product, String> {

	@Query(value = "SELECT p FROM Product p ORDER BY p._productCode")
	public Page<Product> findAllProductsFromCursor(Pageable pageable);
	
	@Query(value = "SELECT p FROM Product p where p._activationStatus=?1 ORDER BY p._productCode")
	public Page<Product> findProductsFromCursorByActivationStatus(ActivationStatus activationStatus, Pageable pageable);
}
