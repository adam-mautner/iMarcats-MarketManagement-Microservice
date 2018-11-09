package com.imarcats.microservice.market.management.instrument;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.InstrumentDatastore;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.Instrument;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.PagedInstrumentList;
import com.imarcats.model.types.UnderlyingType;

@Component("InstrumentDatastoreImpl")
public class InstrumentDatastoreImpl implements InstrumentDatastore {

	@Autowired
	private InstrumentCrudRepository instrumentCrudRepository;
	
	@Autowired
	private InstrumentJpaRepository instrumentJpaRepository;
	
	@Override
	public String createInstrument(Instrument instrument) {
		return instrumentCrudRepository.save(instrument).getCode();
	}

	@Override
	public void deleteInstrument(String code) {
		instrumentCrudRepository.deleteById(code);
	}
	@Override
	public Instrument findInstrumentByCode(String code) {
		Optional<Instrument> byId = instrumentCrudRepository.findById(code);
		return byId.orElse(null); 
	}

	@Override
	public Instrument updateInstrument(Instrument changedInstrument) {
		return instrumentCrudRepository.save(changedInstrument);
	}
	
	@Override
	public PagedInstrumentList findAllInstrumentsFromCursor(String cursorString, int numberOnPage) {
		return createPagedInstrumentList(instrumentJpaRepository.findAllInstrumentsFromCursor(RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	@Override
	public Instrument[] findInstrumentsByUnderlying(String underlyingCode, UnderlyingType underlyingType) {
		List<Instrument> instrumentsByUnderlying = instrumentJpaRepository.findInstrumentsByUnderlying(underlyingCode, underlyingType);
		return instrumentsByUnderlying.toArray(new Instrument[instrumentsByUnderlying.size()]);
	}

	@Override
	public PagedInstrumentList findInstrumentsFromCursorByActivationStatus(ActivationStatus activationStatus, String cursorString, int numberOnPage) {
		return createPagedInstrumentList(instrumentJpaRepository.findInstrumentsFromCursorByActivationStatus(activationStatus, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	@Override
	public PagedInstrumentList findInstrumentsFromCursorByAssetClass(String assetClass, String cursorString, int numberOnPage) {
		return createPagedInstrumentList(instrumentJpaRepository.findInstrumentsFromCursorByAssetClass(assetClass, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	@Override
	public PagedInstrumentList findInstrumentsFromCursorByUnderlying(String underlyingCode, UnderlyingType underlyingType, String cursorString, int numberOnPage) {
		return createPagedInstrumentList(instrumentJpaRepository.findInstrumentsFromCursorByUnderlying(underlyingCode, underlyingType, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	private PagedInstrumentList createPagedInstrumentList(Page<Instrument> page) {
		PagedInstrumentList list = new PagedInstrumentList();
		list.setInstruments(page.getContent().toArray(new Instrument[page.getContent().size()]));
		list.setCursorString(""+(page.getNumber() + 1));
		list.setMaxNumberOfInstrumentsOnPage(page.getNumberOfElements());
		 
		return list;
	}
}
