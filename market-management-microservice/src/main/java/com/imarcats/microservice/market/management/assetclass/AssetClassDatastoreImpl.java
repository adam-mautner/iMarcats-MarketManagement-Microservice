package com.imarcats.microservice.market.management.assetclass;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.AssetClassDatastore;
import com.imarcats.model.AssetClass;
import com.imarcats.model.types.PagedAssetClassList;

@Component("AssetClassDatastoreImpl")
public class AssetClassDatastoreImpl implements AssetClassDatastore {

	@Autowired
	private AssetClassCrudRepository assetClassCrudRepository;
	
	@Autowired
	private AssetClassJpaRepository assetClassJpaRepository;
	
	public AssetClassDatastoreImpl() {
		super();
	}

	@Override
	public PagedAssetClassList findAllTopLevelAssetClassesFromCursor(String cursorString_,
			int maxNumberOfAssetClassesOnPage_) {
		return createPagedAssetClassList(assetClassJpaRepository.findAllTopLevelAssetClassesFromCursor(createPageable(cursorString_, maxNumberOfAssetClassesOnPage_)));
	}

	@Override
	public PagedAssetClassList findAssetClassesFromCursorByParent(String parentAssetClassName_, String cursorString_,
			int maxNumberOfAssetClassesOnPage_) {
		return createPagedAssetClassList(assetClassJpaRepository.findAssetClassesFromCursorByParent(parentAssetClassName_, createPageable(cursorString_, maxNumberOfAssetClassesOnPage_)));
	}
	
	@Override
	public PagedAssetClassList findAllAssetClassesFromCursor(String cursorString_,
			int maxNumberOfAssetClassesOnPage_) {
		return createPagedAssetClassList(assetClassJpaRepository.findAllAssetClassesFromCursor(createPageable(cursorString_, maxNumberOfAssetClassesOnPage_)));
	}

	
	private Pageable createPageable(String cursorString_,
			int maxNumberOfAssetClassesOnPage_) {
		 return PageRequest.of(cursorString_ != null ? Integer.parseInt(cursorString_) : 0, maxNumberOfAssetClassesOnPage_);
	}
	
	private PagedAssetClassList createPagedAssetClassList(Page<AssetClass> page) {
		 PagedAssetClassList pagedAssetClassList = new PagedAssetClassList();
		 pagedAssetClassList.setAssetClasses(page.getContent().toArray(new AssetClass[page.getContent().size()]));
		 pagedAssetClassList.setCursorString(""+page.getNumber());
		 pagedAssetClassList.setMaxNumberOfAssetClassesOnPage(page.getNumberOfElements());
		 
		 return pagedAssetClassList;
	}
	
	@Override
	public String createAssetClass(AssetClass assetClass_) {
		return assetClassCrudRepository.save(assetClass_).getCode();
	}

	@Override
	public AssetClass findAssetClassByName(String name_) {
		Optional<AssetClass> byId = assetClassCrudRepository.findById(name_);
		return byId.orElse(null); 
	}

	@Override
	public AssetClass updateAssetClass(AssetClass changedAssetClassModel_) {
		return assetClassCrudRepository.save(changedAssetClassModel_);
	}

	@Override
	public void deleteAssetClass(String name_) {
		assetClassCrudRepository.deleteById(name_);
	}	
}
