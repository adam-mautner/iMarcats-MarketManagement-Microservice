package com.imarcats.microservice.market.management.assetclass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imarcats.model.AssetClass;

public interface AssetClassJpaRepository extends JpaRepository<AssetClass, String> {

	@Query(value = "SELECT a FROM AssetClass a where a._parentName is null ORDER BY a._name")
	public Page<AssetClass> findAllTopLevelAssetClassesFromCursor(Pageable pageable);

	@Query(value = "SELECT a FROM AssetClass a where a._parentName=?1 ORDER BY a._name")
	public Page<AssetClass> findAssetClassesFromCursorByParent(String parentAssetClassName_, Pageable pageable);
	
	@Query(value = "SELECT a FROM AssetClass a ORDER BY a._name")
	public Page<AssetClass> findAllAssetClassesFromCursor(Pageable pageable);
}
