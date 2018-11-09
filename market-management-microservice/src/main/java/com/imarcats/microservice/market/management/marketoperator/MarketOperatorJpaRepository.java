package com.imarcats.microservice.market.management.marketoperator;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imarcats.model.MarketOperator;
import com.imarcats.model.types.ActivationStatus;

public interface MarketOperatorJpaRepository extends JpaRepository<MarketOperator, String> {

	@Query(value = "SELECT m FROM MarketOperator m ORDER BY m._code")
	public Page<MarketOperator> findAllMarketOperatorsFromCursor(Pageable pageable);
	
	@Query(value = "SELECT m FROM MarketOperator m where m._activationStatus=?1 ORDER BY m._code")
	public Page<MarketOperator> findMarketOperatorsFromCursorByActivationStatus(ActivationStatus activationStatus, Pageable pageable);

	@Query(value = "SELECT m FROM MarketOperator m where m._ownerUserID=?1 ORDER BY m._code")
	public Page<MarketOperator> findMarketOperatorsFromCursorByUser(String user, Pageable pageable);

	@Query(value = "SELECT m FROM MarketOperator m where m._businessEntityCode=?1 ORDER BY m._code")
	public List<MarketOperator> findMarketOperatorsByBusinessEntity(String businessEntity);
	
	@Query(value = "SELECT m FROM MarketOperator m where m._ownerUserID=?1 ORDER BY m._code")
	public List<MarketOperator> findMarketOperatorsByUser(String user);

}
