package com.imarcats.microservice.market.management.instrument;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.imarcats.model.Instrument;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.UnderlyingType;

public interface InstrumentJpaRepository extends JpaRepository<Instrument, String> {

	@Query(value = "SELECT i FROM Instrument i ORDER BY i._instrumentCode")
	public Page<Instrument> findAllInstrumentsFromCursor(Pageable pageable);
	
	@Query(value = "SELECT i FROM Instrument i where i._activationStatus=?1 ORDER BY i._instrumentCode")
	public Page<Instrument> findInstrumentsFromCursorByActivationStatus(ActivationStatus activationStatus, Pageable pageable);

	@Query(value = "SELECT i FROM Instrument i where i._assetClassName=?1 ORDER BY i._instrumentCode")
	public Page<Instrument> findInstrumentsFromCursorByAssetClass(String assetClass, Pageable pageable);

	@Query(value = "SELECT i FROM Instrument i where i._underlyingCode=?1 and i._underlyingType=?2 ORDER BY i._instrumentCode")
	public Page<Instrument> findInstrumentsFromCursorByUnderlying(String underlyingCode, UnderlyingType underlyingType, Pageable pageable);

	@Query(value = "SELECT i FROM Instrument i where i._underlyingCode=?1 and i._underlyingType=?2 ORDER BY i._instrumentCode")
	public List<Instrument> findInstrumentsByUnderlying(String underlyingCode, UnderlyingType underlyingType);
}
