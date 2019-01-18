package com.imarcats.microservice.market.management.instrument;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.InstrumentDatastore;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.Instrument;
import com.imarcats.model.MarketOperator;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.PagedInstrumentList;
import com.imarcats.model.types.UnderlyingType;

@Component("InstrumentDatastoreImpl")
public class InstrumentDatastoreImpl implements InstrumentDatastore {

    @PersistenceContext
    private EntityManager em;
    
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
	/**
	 * We need this explicit update here, because market management system (for reason of convenience) 
	 * will actually feed in a non-entity here (an copy created directly from the DTO), so updates from 
	 * this object will not be automatically propagated the DB (dirty writing is not working here 
	 * - reason being the object is not real entity)
	 */
	public Instrument updateInstrument(Instrument changedInstrument) {
		// CRUD repo's save() will not work here correctly, because it is using check - if a new entity is passed - and 
		// calls persists() for the entity - causing ID uniqueness violation   
		// return instrumentCrudRepository.save(changedInstrument);
		
		// Object has to be freshly loaded here in order to make sure we have the latest version 
		Instrument instrument = findInstrumentByCode(changedInstrument.getCode());
		// also version number has to be manually propagated 
		changedInstrument.setVersionNumber(instrument.getVersionNumber());
		return em.merge(changedInstrument);
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
