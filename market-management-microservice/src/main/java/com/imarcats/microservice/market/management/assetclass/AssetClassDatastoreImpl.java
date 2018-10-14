package com.imarcats.microservice.market.management.assetclass;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.AssetClassDatastore;
import com.imarcats.internal.server.infrastructure.testutils.MockDatastoresBase;
import com.imarcats.model.AssetClass;
import com.imarcats.model.types.PagedAssetClassList;

@Component("AssetClassDatastoreImpl")
public class AssetClassDatastoreImpl extends MockDatastoresBase implements AssetClassDatastore {

	@Autowired
	private AssetClassCrudRepository assetClassCrudRepository;
	
	public AssetClassDatastoreImpl() {
		super();
		// create asset class 1	
		AssetClass assetClass = new AssetClass();
    	String assetClassName = "ADAM_ASSET_CLASS_1";
		assetClass.setName(assetClassName);
    	assetClass.setDescription("Test 1");

    	super.createAssetClass(assetClass);
    	
		// create asset class 2	
		assetClass = new AssetClass();
		String parent = assetClassName;
    	assetClassName = "ADAM_ASSET_CLASS_2";
		assetClass.setName(assetClassName);
    	assetClass.setDescription("Test 2");
    	assetClass.setParentName(parent);
    	
    	super.createAssetClass(assetClass);
	}

	@Override
	public PagedAssetClassList findAllTopLevelAssetClassesFromCursor(String cursorString_,
			int maxNumberOfAssetClassesOnPage_) {
		// TODO Auto-generated method stub
		return super.findAllTopLevelAssetClassesFromCursor(cursorString_, maxNumberOfAssetClassesOnPage_);
	}

	@Override
	public PagedAssetClassList findAssetClassesFromCursorByParent(String parentAssetClassName_, String cursorString_,
			int maxNumberOfAssetClassesOnPage_) {
		// TODO Auto-generated method stub
		return super.findAssetClassesFromCursorByParent(parentAssetClassName_, cursorString_, maxNumberOfAssetClassesOnPage_);
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
