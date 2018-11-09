package com.imarcats.microservice.market.management.market;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imarcats.model.Market;
import com.imarcats.model.types.ActivationStatus;

public interface MarketJpaRepository extends JpaRepository<Market, String> {

	@Query(value = "SELECT m FROM Market m ORDER BY m._marketCode")
	public Page<Market> findAllMarketModelsFromCursor(Pageable pageable);
	
	@Query(value = "SELECT m FROM Market m where m._activationStatus=?1 ORDER BY m._marketCode")
	public Page<Market> findMarketModelsFromCursorByActivationStatus(ActivationStatus activationStatus, Pageable pageable);

	@Query(value = "SELECT m FROM Market m where m._instrumentCode=?1 ORDER BY m._marketCode")
	public Page<Market> findMarketModelsFromCursorByInstrument(String instrument, Pageable pageable);
	
	@Query(value = "SELECT m FROM Market m where m._marketOperatorCode=?1 ORDER BY m._marketCode")
	public Page<Market> findMarketModelsFromCursorByMarketOperator(String marketOperator, Pageable pageable);

	@Query(value = "SELECT m FROM Market m where m._businessEntityCode=?1 ORDER BY m._marketCode")
	public List<Market> findMarketModelsByBusinessEntity(String businessEntity);
	
	@Query(value = "SELECT m FROM Market m where m._instrumentCode=?1 ORDER BY m._marketCode")
	public List<Market> findMarketModelsByInstrument(String instrument);
	
	@Query(value = "SELECT m FROM Market m where m._marketOperatorCode=?1 ORDER BY m._marketCode")
	public List<Market> findMarketModelsByMarketOperator(String marketOperator);
	
}
