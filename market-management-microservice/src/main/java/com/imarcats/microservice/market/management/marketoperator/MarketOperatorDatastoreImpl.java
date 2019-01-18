package com.imarcats.microservice.market.management.marketoperator;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.imarcats.internal.server.infrastructure.datastore.MarketOperatorDatastore;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.MarketOperator;
import com.imarcats.model.Product;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.PagedMarketOperatorList;

@Component("MarketOperatorDatastoreImpl")
public class MarketOperatorDatastoreImpl implements MarketOperatorDatastore {
	
    @PersistenceContext
    private EntityManager em;
    
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
	/**
	 * We need this explicit update here, because market management system (for reason of convenience) 
	 * will actually feed in a non-entity here (an copy created directly from the DTO), so updates from 
	 * this object will not be automatically propagated the DB (dirty writing is not working here 
	 * - reason being the object is not real entity)
	 */
	public MarketOperator updateMarketOperator(MarketOperator changedMarketOperator) {
		// CRUD repo's save() will not work here correctly, because it is using check - if a new entity is passed - and 
		// calls persists() for the entity - causing ID uniqueness violation   
		// return marketOperatorCrudRepository.save(changedMarketOperator);
		
		// Object has to be freshly loaded here in order to make sure we have the latest version 
		MarketOperator marketOperator = findMarketOperatorByCode(changedMarketOperator.getCode());
		// also version number has to be manually propagated 
		changedMarketOperator.setVersionNumber(marketOperator.getVersionNumber());
		return em.merge(changedMarketOperator);
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
