package com.imarcats.microservice.market.management.product;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @PersistenceContext
    private EntityManager em;
	
	@Autowired
	private ProductCrudRepository productCrudRepository;
	
	@Autowired
	private ProductJpaRepository productJpaRepository;
	
	@Override
	public String createProduct(Product product) {
		return productCrudRepository.save(product).getCode();
	}

	@Override
	/**
	 * We need this explicit update here, because market management system (for reason of convenience) 
	 * will actually feed in a non-entity here (an copy created directly from the DTO), so updates from 
	 * this object will not be automatically propagated the DB (dirty writing is not working here 
	 * - reason being the object is not real entity)
	 */
	public Product updateProduct(Product changedProduct) {
		// CRUD repo's save() will not work here correctly, because it is using check - if a new entity is passed - and 
		// calls persists() for the entity - causing ID uniqueness violation   
		// return productCrudRepository.save(changedProduct);
		
		// Object has to be freshly loaded here in order to make sure we have the latest version 
		Product product = findProductByCode(changedProduct.getProductCode());
		// also version number has to be manually propagated 
		changedProduct.setVersionNumber(product.getVersionNumber());
		return em.merge(changedProduct);
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
