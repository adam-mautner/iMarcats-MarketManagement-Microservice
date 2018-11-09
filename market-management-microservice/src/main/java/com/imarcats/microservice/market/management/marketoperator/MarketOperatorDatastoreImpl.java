package com.imarcats.microservice.market.management.marketoperator;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.MarketOperatorDatastore;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.MarketOperator;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.PagedMarketOperatorList;

@Component("MarketOperatorDatastoreImpl")
public class MarketOperatorDatastoreImpl implements MarketOperatorDatastore {
	
	@Autowired
	private MarketOperatorCrudRepository marketOperatorCrudRepository;
	
	@Autowired
	private MarketOperatorJpaRepository marketOperatorJpaRepository;
	
	@Override
	public String createMarketOperator(MarketOperator marketOperator) {
		return marketOperatorCrudRepository.save(marketOperator).getCode();
	}

	@Override
	public void deleteMarketOperator(String code) {
		marketOperatorCrudRepository.deleteById(code);
	}

	@Override
	public MarketOperator findMarketOperatorByCode(String code) {
		Optional<MarketOperator> byId = marketOperatorCrudRepository.findById(code);
		return byId.orElse(null); 
	}

	@Override
	public MarketOperator updateMarketOperator(MarketOperator changedMarketOperator) {
		return marketOperatorCrudRepository.save(changedMarketOperator);
	}

	@Override
	public PagedMarketOperatorList findAllMarketOperatorsFromCursor(String cursorString, int numberOnPage) {
		return createPagedMarketOperatorList(marketOperatorJpaRepository.findAllMarketOperatorsFromCursor(RestControllerBase.createPageable(cursorString, numberOnPage)));
	}
	
	@Override
	public MarketOperator[] findMarketOperatorsByBusinessEntity(String businessEntity) {
		List<MarketOperator> marketOperators = marketOperatorJpaRepository.findMarketOperatorsByBusinessEntity(businessEntity);
		return marketOperators.toArray(new MarketOperator[marketOperators.size()]);
	}

	@Override
	public MarketOperator[] findMarketOperatorsByUser(String user) {
		List<MarketOperator> marketOperators = marketOperatorJpaRepository.findMarketOperatorsByUser(user);
		return marketOperators.toArray(new MarketOperator[marketOperators.size()]);
	}

	@Override
	public PagedMarketOperatorList findMarketOperatorsFromCursorByActivationStatus(ActivationStatus activationStatus, String cursorString, int numberOnPage) {
		return createPagedMarketOperatorList(marketOperatorJpaRepository.findMarketOperatorsFromCursorByActivationStatus(activationStatus, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}

	@Override
	public PagedMarketOperatorList findMarketOperatorsFromCursorByUser(String user, String cursorString, int numberOnPage) {
		return createPagedMarketOperatorList(marketOperatorJpaRepository.findMarketOperatorsFromCursorByUser(user, RestControllerBase.createPageable(cursorString, numberOnPage)));
	}
	
	private PagedMarketOperatorList createPagedMarketOperatorList(Page<MarketOperator> page) {
		PagedMarketOperatorList list = new PagedMarketOperatorList();
		list.setMarketOperators(page.getContent().toArray(new MarketOperator[page.getContent().size()]));
		list.setCursorString(""+(page.getNumber() + 1));
		list.setMaxNumberOfMarketOperatorsOnPage(page.getNumberOfElements());
		 
		return list;
	}

}
