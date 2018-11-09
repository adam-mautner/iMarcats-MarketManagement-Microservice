package com.imarcats.microservice.market.management.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.ProductDatastore;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.Product;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.PagedProductList;

@Component("ProductDatastoreImpl")
public class ProductDatastoreImpl implements ProductDatastore {

	@Autowired
	private ProductCrudRepository productCrudRepository;
	
	@Autowired
	private ProductJpaRepository productJpaRepository;
	
	@Override
	public String createProduct(Product product) {
		return productCrudRepository.save(product).getCode();
	}

	@Override
	public Product updateProduct(Product changedProduct) {
		return productCrudRepository.save(changedProduct);
	}

	@Override
	public Product findProductByCode(String code) {
		Optional<Product> byId = productCrudRepository.findById(code);
		return byId.orElse(null); 
	}
	
	@Override
	public void deleteProduct(String code) {
		productCrudRepository.deleteById(code);
	}

	@Override
	public PagedProductList findAllProductsFromCursor(String cursorString, int numberOnPage) {
		return createPagedProductList(productJpaRepository.findAllProductsFromCursor(RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	@Override
	public PagedProductList findProductsFromCursorByActivationStatus(ActivationStatus activationStatus, String cursorString, int numberOnPage) {
		return createPagedProductList(productJpaRepository.findProductsFromCursorByActivationStatus(activationStatus, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}
	
	private PagedProductList createPagedProductList(Page<Product> page) {
		PagedProductList list = new PagedProductList();
		list.setProducts(page.getContent().toArray(new Product[page.getContent().size()]));
		list.setCursorString(""+(page.getNumber() + 1));
		list.setMaxNumberOfProductsOnPage(page.getNumberOfElements());
		 
		return list;
	}

}
