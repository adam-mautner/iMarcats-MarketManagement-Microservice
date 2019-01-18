package com.imarcats.microservice.market.management.market;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imarcats.interfaces.client.v100.dto.MarketDto;
import com.imarcats.interfaces.client.v100.dto.types.ActivationStatus;
import com.imarcats.interfaces.client.v100.dto.types.PagedMarketListDto;
import com.imarcats.interfaces.client.v100.exception.MarketRuntimeException;
import com.imarcats.interfaces.server.v100.dto.mapping.MarketDtoMapping;
import com.imarcats.market.management.MarketManagementContext;
import com.imarcats.market.management.MarketManagementContextImpl;
import com.imarcats.microservice.market.management.ActivationDto;
import com.imarcats.microservice.market.management.ApprovalDto;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.types.PagedMarketList;

@RestController
@RequestMapping("/")
public class MarketRestController extends RestControllerBase {

	@RequestMapping(value = "/markets", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedMarketListDto getAllMarkets(@RequestParam("cursorString") Optional<String>cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage, 
			@RequestParam("activationStatus") Optional<String> activationStatus, 
			@RequestParam("instrument") Optional<String> instrument, 
			@RequestParam("marketOperator") Optional<String> marketOperator) {
		return getAllMarketsInternal(activationStatus, instrument, marketOperator, cursorString, numberOfItemsPerPage);
	}

	private PagedMarketListDto getAllMarketsInternal(Optional<String> activationStatus, Optional<String> instrument,
			Optional<String> marketOperator, Optional<String> cursorString, Optional<Integer> numberOfItemsPerPage) {
		Integer numberOfItemsPerPageInternal = numberOfItemsPerPage.orElse(10);
		String cursorStringInternal = cursorString.orElse(null);
		
		// check parameters - choice 
		int cnt = 0;
		if(activationStatus.isPresent()) {
			cnt++;
		}
		if(instrument.isPresent()) {
			cnt++;
		}
		if(marketOperator.isPresent()) {
			cnt++;
		}

		if(cnt > 1) {
			throw new RuntimeException("Redundant request parameter");
		}
		
		PagedMarketList list = null;
		if(activationStatus.isPresent()) {
			com.imarcats.model.types.ActivationStatus activationStatusObject = com.imarcats.model.types.ActivationStatus.valueOf(activationStatus.get());
			list = marketDatastore.findMarketModelsFromCursorByActivationStatus(activationStatusObject, cursorStringInternal, numberOfItemsPerPageInternal);
		} else if(instrument.isPresent()) {
			list = marketDatastore.findMarketModelsFromCursorByInstrument(instrument.get(), cursorStringInternal, numberOfItemsPerPageInternal);
		} else if(marketOperator.isPresent()) {
			list = marketDatastore.findMarketModelsFromCursorByMarketOperator(marketOperator.get(), cursorStringInternal, numberOfItemsPerPageInternal);
		} else {			
			list = marketDatastore.findAllMarketModelsFromCursor(cursorStringInternal, numberOfItemsPerPageInternal);
		}
		

		PagedMarketListDto dto = MarketDtoMapping.INSTANCE.toDto(list);
		
		return dto;
	}

	@Transactional
	@RequestMapping(value = "/markets", method = RequestMethod.POST, consumes = "application/json")
	public void createNewMarket(@RequestBody MarketDto market) {
		// TODO: Identify user
		String user = "Adam";
		MarketManagementContextImpl context = createMarketManagementContext();
		marketManagementSystem.createMarket(market, user, context);
	}

	@RequestMapping(value = "/markets/{marketCode}", method = RequestMethod.GET, produces = { "application/JSON" })
	public ResponseEntity<MarketDto> getMarket(@PathVariable String marketCode) {
		try {
			MarketDto marketDto = marketManagementSystem.getMarket(marketCode);
			return ResponseEntity.ok().body(marketDto);
		} catch (MarketRuntimeException e) {
			if (MarketRuntimeException.NON_EXISTENT_MARKET_OPERATOR.getLanguageKey()
					.equals(((MarketRuntimeException) e).getLanguageKey())) {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@Transactional
	@RequestMapping(value = "/markets/{marketCode}", method = RequestMethod.PUT, consumes = "application/json")
	public void updateMarket(@PathVariable String marketCode, @RequestBody MarketDto market) {
		// TODO: Identify user
		String user = "Adam";
		// we set the market operator code just to be on the safe side
		market.setMarketCode(marketCode);
		
		MarketManagementContextImpl context = createMarketManagementContext();
		marketManagementSystem.changeMarket(market, user, context);
	}

	@Transactional
	@RequestMapping(value = "/markets/{marketCode}", method=RequestMethod.DELETE)
    public void deleteMarket(@PathVariable String marketCode) {
    	// TODO: Identify user 
        String user = "Adam";
		MarketManagementContext context = createMarketManagementContext(); 
		marketManagementSystem.deleteMarket(marketCode, user, context);
	}

	@RequestMapping(value = "/approvedMarkets", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedMarketListDto getApprovedMarkets(@RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage) {
		return getMarketByActivationStatus(ActivationStatus.Approved, cursorString, numberOfItemsPerPage);
	}

	@Transactional
	@RequestMapping(value = "/approvedMarkets", method = RequestMethod.POST, consumes = "application/json")
	public void approveMarket(@RequestBody ApprovalDto approvalDto) {
    	// TODO: Identify user 
        String user = "Adam";
        
		MarketManagementContextImpl context = createMarketManagementContext();
		marketManagementSystem.approveMarket(approvalDto.getCode(), approvalDto.getLastUpdateTimestamp(), user, context);
	}

	@Transactional
	@RequestMapping(value = "/approvedMarkets/{MarketCode}", method = RequestMethod.DELETE)
	public void suspendMarket(@PathVariable String marketCode) {
    	// TODO: Identify user 
        String user = "Adam";

		MarketManagementContextImpl context = createMarketManagementContext();
		marketManagementSystem.suspendMarket(marketCode, user, context);
	}

	@RequestMapping(value = "/activeMarkets", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedMarketListDto getActivateMarkets(@RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage) {
		return getMarketByActivationStatus(ActivationStatus.Activated, cursorString, numberOfItemsPerPage);
	}

	private PagedMarketListDto getMarketByActivationStatus(ActivationStatus activationStatus, Optional<String> cursorString,
			Optional<Integer> numberOfItemsPerPage) {
		return getAllMarketsInternal(Optional.of(activationStatus.name()), Optional.ofNullable(null), Optional.ofNullable(null), cursorString, numberOfItemsPerPage);
	}

	@Transactional
	@RequestMapping(value = "/activeMarkets", method = RequestMethod.POST, consumes = "application/json")
	public void activateMarket(@RequestBody ActivationDto activationDto) {
    	// TODO: Identify user 
        String user = "Adam";
        
		MarketManagementContextImpl context = createMarketManagementContext();
		if(activationDto.isCallMarket()) {
			marketManagementSystem.activateCallMarket(activationDto.getCode(), activationDto.getNextCallDate(), activationDto.getNextMarketCallTime(), user, context);
		} else {			
			marketManagementSystem.activateMarket(activationDto.getCode(), user, context);
		}
	}

	@Transactional
	@RequestMapping(value = "/activeMarkets/{MarketCode}", method = RequestMethod.DELETE)
	public void deactivateMarket(@PathVariable String marketCode) {
    	// TODO: Identify user 
        String user = "Adam";

		MarketManagementContextImpl context = createMarketManagementContext();
		marketManagementSystem.deactivateMarket(marketCode, user, context);
	}
}
