package com.imarcats.microservice.market.management.market;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.MarketDatastore;
import com.imarcats.internal.server.interfaces.market.MarketInternal;
import com.imarcats.market.engine.market.MarketImpl;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.Market;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.PagedMarketList;

@Component("MarketDatastoreImpl")
public class MarketDatastoreImpl implements MarketDatastore {

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
	public MarketInternal findMarketBy(String code) {
		Optional<Market> byId = marketCrudRepository.findById(code);
		Market market = byId.orElse(null); 
		// TODO: Remove, this is not correct, this does not belong here
		return market != null ? new MarketImpl(market, null, null, null, null, null): null; 
	}
	
	@Override
	public Market updateMarket(Market changedMarket) {
		return marketCrudRepository.save(changedMarket);
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
