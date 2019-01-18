package com.imarcats.microservice.market.management.market;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.MarketDatastore;
import com.imarcats.internal.server.interfaces.market.MarketInternal;
import com.imarcats.market.engine.market.MarketImpl;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.Market;
import com.imarcats.model.MarketOperator;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.PagedMarketList;

@Component("MarketDatastoreImpl")
public class MarketDatastoreImpl implements MarketDatastore {

    @PersistenceContext
    private EntityManager em;
	
	@Autowired
	private MarketCrudRepository marketCrudRepository;
	
	@Autowired
	private MarketJpaRepository marketJpaRepository;
	
	
	@Override
	public String createMarket(Market market) {
		return marketCrudRepository.save(market).getCode();
	}

	@Override
	public void deleteMarket(String code) {
		marketCrudRepository.deleteById(code);
	}

	@Override
	// TODO: Remove, this is not correct, this lookup does not belong here
	public MarketInternal findMarketBy(String code) {
		Optional<Market> byId = marketCrudRepository.findById(code);
		Market market = byId.orElse(null); 
		return market != null ? new MarketImpl(market, null, null, null, null, null): null; 
	}
	
	@Override
	/**
	 * We need this explicit update here, because market management system (for reason of convenience) 
	 * will actually feed in a non-entity here (an copy created directly from the DTO), so updates from 
	 * this object will not be automatically propagated the DB (dirty writing is not working here 
	 * - reason being the object is not real entity)
	 */
	public Market updateMarket(Market changedMarket) {
		// CRUD repo's save() will not work here correctly, because it is using check - if a new entity is passed - and 
		// calls persists() for the entity - causing ID uniqueness violation   
		// return marketCrudRepository.save(changedMarket);
		
		// Object has to be freshly loaded here in order to make sure we have the latest version 
		Market market = findMarketBy(changedMarket.getCode()).getMarketModel();
		// also version number has to be manually propagated 
		changedMarket.setVersionNumber(market.getVersionNumber());
		return em.merge(changedMarket);
	}

	@Override
	public PagedMarketList findAllMarketModelsFromCursor(String cursorString, int numberOnPage) {
		return createPagedMarketList(marketJpaRepository.findAllMarketModelsFromCursor(RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	@Override
	public Market[] findMarketModelsByBusinessEntity(String businessEntity) {
		List<Market> market = marketJpaRepository.findMarketModelsByBusinessEntity(businessEntity);
		return market.toArray(new Market[market.size()]);
	}

	@Override
	public Market[] findMarketModelsByInstrument(String instrument) {
		List<Market> market = marketJpaRepository.findMarketModelsByInstrument(instrument);
		return market.toArray(new Market[market.size()]);
	}

	@Override
	public Market[] findMarketModelsByMarketOperator(String marketOperator) {
		List<Market> market = marketJpaRepository.findMarketModelsByMarketOperator(marketOperator);
		return market.toArray(new Market[market.size()]);
	}

	@Override
	public PagedMarketList findMarketModelsFromCursorByActivationStatus(ActivationStatus activationStatus, String cursorString, int numberOnPage) {
		return createPagedMarketList(marketJpaRepository.findMarketModelsFromCursorByActivationStatus(activationStatus, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	@Override
	public PagedMarketList findMarketModelsFromCursorByInstrument(String instrument, String cursorString, int numberOnPage) {
		return createPagedMarketList(marketJpaRepository.findMarketModelsFromCursorByInstrument(instrument, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	@Override
	public PagedMarketList findMarketModelsFromCursorByMarketOperator(String marketOperator, String cursorString, int numberOnPage) {
		return createPagedMarketList(marketJpaRepository.findMarketModelsFromCursorByMarketOperator(marketOperator, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	private PagedMarketList createPagedMarketList(Page<Market> page) {
		PagedMarketList list = new PagedMarketList();
		list.setMarkets(page.getContent().toArray(new Market[page.getContent().size()]));
		list.setCursorString(""+(page.getNumber() + 1));
		list.setMaxNumberOfMarketsOnPage(page.getNumberOfElements());
		 
		return list;
	}
}
