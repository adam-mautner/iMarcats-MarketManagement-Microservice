package com.imarcats.microservice.market.management.assetclass;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imarcats.interfaces.client.v100.dto.AssetClassDto;
import com.imarcats.interfaces.client.v100.dto.types.PagedAssetClassListDto;
import com.imarcats.interfaces.client.v100.exception.MarketRuntimeException;
import com.imarcats.interfaces.server.v100.dto.mapping.AssetClassDtoMapping;
import com.imarcats.market.management.MarketManagementContext;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.types.PagedAssetClassList;

@RestController
@RequestMapping("/")
public class AssetClassRestController extends RestControllerBase {

	@RequestMapping(value = "/assetClasses", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedAssetClassListDto getTopLevelAssetClasses(@RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage) {
		return getAssetClassesInternal(null, cursorString, numberOfItemsPerPage);
	}

	@RequestMapping(value = "/assetClasses/**/{parent}", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedAssetClassListDto getAssetClassesByParent(@PathVariable String parent, @RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage) {
		return getAssetClassesInternal(parent, cursorString, numberOfItemsPerPage);
	}
	
	@RequestMapping(value = "/assetClasses/**/{assetClassName}/details", method = RequestMethod.GET, produces = { "application/JSON" })
	public ResponseEntity<AssetClassDto> getAssetClass(@PathVariable String assetClassName) {
		try {
			AssetClassDto assetClassDto = marketManagementSystem.getAssetClass(assetClassName);
			return ResponseEntity.ok().body(assetClassDto);
		} catch (MarketRuntimeException e) {
			if (MarketRuntimeException.NON_EXISTENT_ASSET_CLASS.getLanguageKey()
					.equals(((MarketRuntimeException) e).getLanguageKey())) {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@Transactional
	@RequestMapping(value = "/assetClasses/**/{assetClassName}/details", method = RequestMethod.DELETE)
	public void deleteAssetClass(@PathVariable String assetClassName) {
		// TODO: Identify user
		String user = "Adam";
		
		MarketManagementContext context = createMarketManagementContext();
		
		marketManagementSystem.deleteAssetClass(assetClassName, user, context);
	}

	@Transactional
	@RequestMapping(value = "/assetClasses/{assetClassName}/details", method = RequestMethod.PUT, consumes = { "application/JSON" })
	public void updateTopLevelAssetClass(@PathVariable String assetClassName, @RequestBody AssetClassDto assetClass) {
		// TODO: Identify user
		String user = "Adam";
		
		changeAssetClassInternal(assetClass, assetClassName, null, user);
	}
	
	@Transactional
	@RequestMapping(value = "/assetClasses/**/{parent}/{assetClassName}/details", method = RequestMethod.PUT, consumes = { "application/JSON" })
	public void updateAssetClass(@PathVariable String parent, @PathVariable String assetClassName, @RequestBody AssetClassDto assetClass) {
		// TODO: Identify user
		String user = "Adam";
		
		changeAssetClassInternal(assetClass, assetClassName, parent, user);
	}
	
	@Transactional
	@RequestMapping(value = "/assetClasses", method = RequestMethod.POST, consumes = { "application/JSON" })
	public void createTopLevelAssetClass(@RequestBody AssetClassDto assetClass) {
		// TODO: Identify user
		String user = "Adam";

		createAssetClassInternal(assetClass, null, user);
	}
	
	@Transactional
	@RequestMapping(value = "/assetClasses/**/{parent}", method = RequestMethod.POST, consumes = { "application/JSON" })
	public void createAssetClass(@PathVariable String parent, @RequestBody AssetClassDto assetClass) {
		// TODO: Identify user
		String user = "Adam";

		createAssetClassInternal(assetClass, parent, user);
	}

	private void createAssetClassInternal(AssetClassDto assetClass, String parent, String user) {
		// sets asset class parent to the correct one 
		assetClass.setParentName(parent);
		
		marketManagementSystem.createAssetClass(assetClass, user);
	}
	
	private PagedAssetClassListDto getAssetClassesInternal(String parent, Optional<String> cursorString,
			Optional<Integer> numberOfItemsPerPage) {
		Integer numberOfItemsPerPageInternal = numberOfItemsPerPage.orElse(10);
		String cursorStringInternal = cursorString.orElse(null);

		PagedAssetClassList list = null;
		if(parent != null) {
			list = assetClassDatastore.findAssetClassesFromCursorByParent(parent, cursorStringInternal, numberOfItemsPerPageInternal);
		} else {
			list = assetClassDatastore.findAllTopLevelAssetClassesFromCursor(cursorStringInternal, numberOfItemsPerPageInternal);
		}
		
		PagedAssetClassListDto dto = AssetClassDtoMapping.INSTANCE.toDto(list);
		
		return dto;
	}
	
	private void changeAssetClassInternal(AssetClassDto assetClass, String name, String parent, String user) {
		assetClass.setName(name);
		// sets asset class parent to the correct one 
		assetClass.setParentName(parent);

		marketManagementSystem.changeAssetClass(assetClass, user);
	}
}
