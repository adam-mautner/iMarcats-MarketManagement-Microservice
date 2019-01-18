package com.imarcats.microservice.market.management.product;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imarcats.interfaces.client.v100.dto.ProductDto;
import com.imarcats.interfaces.client.v100.dto.types.ActivationStatus;
import com.imarcats.interfaces.client.v100.dto.types.PagedProductListDto;
import com.imarcats.interfaces.client.v100.exception.MarketRuntimeException;
import com.imarcats.interfaces.server.v100.dto.mapping.ProductDtoMapping;
import com.imarcats.market.management.MarketManagementContext;
import com.imarcats.microservice.market.management.ApprovalDto;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.types.PagedProductList;

@RestController
@RequestMapping("/")
public class ProductRestController extends RestControllerBase {

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedProductListDto getAllProducts(@RequestParam("cursorString")  Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage")  Optional<Integer> numberOfItemsPerPage, 
			@RequestParam("activationStatus") Optional<String> activationStatus) {
		return getProductsByActivationStatus(activationStatus, cursorString, numberOfItemsPerPage);
	}

	private PagedProductListDto getProductsByActivationStatus(Optional<String> activationStatus,
			Optional<String> cursorString, Optional<Integer> numberOfItemsPerPage) {
		Integer numberOfItemsPerPageInternal = numberOfItemsPerPage.orElse(10);
		String cursorStringInternal = cursorString.orElse(null);
		
		PagedProductList list = null;
		if(activationStatus.isPresent()) {
			com.imarcats.model.types.ActivationStatus activationStatusObject = com.imarcats.model.types.ActivationStatus.valueOf(activationStatus.get());
			list = productDatastore.findProductsFromCursorByActivationStatus(activationStatusObject, cursorStringInternal, numberOfItemsPerPageInternal);
		} else {			
			list = productDatastore.findAllProductsFromCursor(cursorStringInternal, numberOfItemsPerPageInternal);
		}
		
		PagedProductListDto dto = ProductDtoMapping.INSTANCE.toDto(list);
		
		return dto;
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST, consumes = "application/json")
	public void createNewProduct(@RequestBody ProductDto product) {
		// TODO: Identify user
		String user = "Adam";
		marketManagementSystem.createProduct(product, user);
	}

	@RequestMapping(value = "/products/{productCode}", method = RequestMethod.GET, produces = { "application/JSON" })
	public ResponseEntity<ProductDto> getProduct(@PathVariable String productCode) {
		try {
			ProductDto productDto = marketManagementSystem.getProduct(productCode);
			return ResponseEntity.ok().body(productDto);
		} catch (MarketRuntimeException e) {
			if (MarketRuntimeException.NON_EXISTENT_PRODUCT.getLanguageKey()
					.equals(((MarketRuntimeException) e).getLanguageKey())) {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@Transactional
	@RequestMapping(value = "/products/{productCode}", method = RequestMethod.PUT, consumes = "application/json")
	public void updateProduct(@PathVariable String productCode, @RequestBody ProductDto product) {
		// TODO: Identify user
		String user = "Adam";
		// we set the product code just to be on the safe side
		product.setProductCode(productCode);
		marketManagementSystem.changeProduct(product, user);
	}

	@Transactional
	@RequestMapping(value = "/products/{productCode}", method=RequestMethod.DELETE)
    public void deleteProduct(@PathVariable String productCode) {
    	// TODO: Identify user 
        String user = "Adam";
		MarketManagementContext context = createMarketManagementContext(); 
		marketManagementSystem.deleteProduct(productCode, user, context);
	}

	@RequestMapping(value = "/approvedProducts", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedProductListDto getApprovedProduct(@RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage) {
		return getProductsByActivationStatus(Optional.of(ActivationStatus.Approved.name()), cursorString, numberOfItemsPerPage);
	}

	@Transactional
	@RequestMapping(value = "/approvedProducts", method = RequestMethod.POST, consumes = "application/json")
	public void approveProduct(@RequestBody ApprovalDto approvalDto) {
    	// TODO: Identify user 
        String user = "Adam";
		marketManagementSystem.approveProduct(approvalDto.getCode(), approvalDto.getLastUpdateTimestamp(), user);
	}

	@Transactional
	@RequestMapping(value = "/approvedProducts/{productCode}", method = RequestMethod.DELETE)
	public void suspendProduct(@PathVariable String productCode) {
    	// TODO: Identify user 
        String user = "Adam";
		marketManagementSystem.suspendProduct(productCode, user);
	}
}
