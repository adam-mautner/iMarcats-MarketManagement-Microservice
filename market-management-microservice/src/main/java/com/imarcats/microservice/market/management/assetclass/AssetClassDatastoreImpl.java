package com.imarcats.microservice.market.management.assetclass;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.AssetClassDatastore;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.AssetClass;
import com.imarcats.model.MarketOperator;
import com.imarcats.model.types.PagedAssetClassList;

@Component("AssetClassDatastoreImpl")
public class AssetClassDatastoreImpl implements AssetClassDatastore {

    @PersistenceContext
    private EntityManager em;
    
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
		return createPagedAssetClassList(assetClassJpaRepository.findAllTopLevelAssetClassesFromCursor(RestControllerBase.createPageable(cursorString_, maxNumberOfAssetClassesOnPage_)));
	}

	@Override
	public PagedAssetClassList findAssetClassesFromCursorByParent(String parentAssetClassName_, String cursorString_,
			int maxNumberOfAssetClassesOnPage_) {
		return createPagedAssetClassList(assetClassJpaRepository.findAssetClassesFromCursorByParent(parentAssetClassName_, RestControllerBase.createPageable(cursorString_, maxNumberOfAssetClassesOnPage_)));
	}
	
	@Override
	public PagedAssetClassList findAllAssetClassesFromCursor(String cursorString_,
			int maxNumberOfAssetClassesOnPage_) {
		return createPagedAssetClassList(assetClassJpaRepository.findAllAssetClassesFromCursor(RestControllerBase.createPageable(cursorString_, maxNumberOfAssetClassesOnPage_)));
	}
	
	private PagedAssetClassList createPagedAssetClassList(Page<AssetClass> page) {
		 PagedAssetClassList pagedAssetClassList = new PagedAssetClassList();
		 pagedAssetClassList.setAssetClasses(page.getContent().toArray(new AssetClass[page.getContent().size()]));
		 pagedAssetClassList.setCursorString(""+(page.getNumber() + 1));
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
	/**
	 * We need this explicit update here, because market management system (for reason of convenience) 
	 * will actually feed in a non-entity here (an copy created directly from the DTO), so updates from 
	 * this object will not be automatically propagated the DB (dirty writing is not working here 
	 * - reason being the object is not real entity)
	 */
	public AssetClass updateAssetClass(AssetClass changedAssetClassModel_) {
		// return assetClassCrudRepository.save(changedAssetClassModel_);
		// CRUD repo's save() will not work here correctly, because it is using check - if a new entity is passed - and 
		// calls persists() for the entity - causing ID uniqueness violation   
		// return marketOperatorCrudRepository.save(changedMarketOperator);
		
		// Object has to be freshly loaded here in order to make sure we have the latest version 
		AssetClass assetClass = findAssetClassByName(changedAssetClassModel_.getCode());
		// also version number has to be manually propagated 
		changedAssetClassModel_.setVersionNumber(assetClass.getVersionNumber());
		return em.merge(changedAssetClassModel_);
	}

	@Override
	public void deleteAssetClass(String name_) {
		assetClassCrudRepository.deleteById(name_);
	}	
}
