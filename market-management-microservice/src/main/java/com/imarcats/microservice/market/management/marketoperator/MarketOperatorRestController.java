package com.imarcats.microservice.market.management.marketoperator;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imarcats.interfaces.client.v100.dto.MarketOperatorDto;
import com.imarcats.interfaces.client.v100.dto.types.ActivationStatus;
import com.imarcats.interfaces.client.v100.dto.types.PagedMarketOperatorListDto;
import com.imarcats.interfaces.client.v100.exception.MarketRuntimeException;
import com.imarcats.interfaces.server.v100.dto.mapping.MarketOperatorDtoMapping;
import com.imarcats.market.management.MarketManagementContext;
import com.imarcats.microservice.market.management.ApprovalDto;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.types.PagedMarketOperatorList;

@RestController
@RequestMapping("/")
public class MarketOperatorRestController extends RestControllerBase {

	@RequestMapping(value = "/marketOperators", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedMarketOperatorListDto getAllMarketOperators(@RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage, 
			@RequestParam("activationStatus") Optional<String> activationStatus,
			@RequestParam("user") Optional<String> user) {
		return getAllMarketOperatorsInternal(activationStatus, user, cursorString, numberOfItemsPerPage);
	}

	private PagedMarketOperatorListDto getAllMarketOperatorsInternal(Optional<String> activationStatus,
			Optional<String> user, Optional<String> cursorString, Optional<Integer> numberOfItemsPerPage) {
		Integer numberOfItemsPerPageInternal = numberOfItemsPerPage.orElse(10);
		String cursorStringInternal = cursorString.orElse(null);
		
		// check parameters - choice 
		if(activationStatus.isPresent() && user.isPresent()) {
			throw new RuntimeException("Redundant request parameter");
		}
		
		PagedMarketOperatorList list = null;
		if(activationStatus.isPresent()) {
			com.imarcats.model.types.ActivationStatus activationStatusObject = com.imarcats.model.types.ActivationStatus.valueOf(activationStatus.get());
			list = marketOperatorDatastore.findMarketOperatorsFromCursorByActivationStatus(activationStatusObject, cursorStringInternal, numberOfItemsPerPageInternal);
		} else if(user.isPresent()) {
			list = marketOperatorDatastore.findMarketOperatorsFromCursorByUser(user.get(), cursorStringInternal, numberOfItemsPerPageInternal);
		} else {			
			list = marketOperatorDatastore.findAllMarketOperatorsFromCursor(cursorStringInternal, numberOfItemsPerPageInternal);
		}

				
		PagedMarketOperatorListDto dto = MarketOperatorDtoMapping.INSTANCE.toDto(list);
		
		return dto;
	}

	@RequestMapping(value = "/marketOperators", method = RequestMethod.POST, consumes = "application/json")
	public void createNewMarketOperator(@RequestBody MarketOperatorDto marketOperator) {
		// TODO: Identify user
		String user = "Adam";
		marketManagementSystem.createMarketOperator(marketOperator, user);
	}

	@RequestMapping(value = "/marketOperators/{marketOperatorCode}", method = RequestMethod.GET, produces = { "application/JSON" })
	public ResponseEntity<MarketOperatorDto> getMarketOperator(@PathVariable String marketOperatorCode) {
		try {
			MarketOperatorDto marketOperatorDto = marketManagementSystem.getMarketOperator(marketOperatorCode);
			return ResponseEntity.ok().body(marketOperatorDto);
		} catch (MarketRuntimeException e) {
			if (MarketRuntimeException.NON_EXISTENT_MARKET_OPERATOR.getLanguageKey()
					.equals(((MarketRuntimeException) e).getLanguageKey())) {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@RequestMapping(value = "/marketOperators/{marketOperatorCode}", method = RequestMethod.PUT, consumes = "application/json")
	public void updateMarketOperator(@PathVariable String marketOperatorCode, @RequestBody MarketOperatorDto marketOperator) {
		// TODO: Identify user
		String user = "Adam";
		// we set the market operator code just to be on the safe side
		marketOperator.setCode(marketOperatorCode);
		marketManagementSystem.changeMarketOperator(marketOperator, user);
	}

	@RequestMapping(value = "/marketOperators/{marketOperatorCode}", method=RequestMethod.DELETE)
    public void deleteMarketOperator(@PathVariable String marketOperatorCode) {
    	// TODO: Identify user 
        String user = "Adam";
		MarketManagementContext context = createMarketManagementContext(); 
		marketManagementSystem.deleteMarketOperator(marketOperatorCode, user, context);
	}

	@RequestMapping(value = "/approvedMarketOperators", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedMarketOperatorListDto getApprovedMarketOperator(@RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage) {

		return getAllMarketOperatorsInternal(Optional.of(ActivationStatus.Approved.name()), Optional.ofNullable(null), cursorString, numberOfItemsPerPage);
	}

	@RequestMapping(value = "/approvedMarketOperators", method = RequestMethod.POST, consumes = "application/json")
	public void approveMarketOperator(@RequestBody ApprovalDto approvalDto) {
    	// TODO: Identify user 
        String user = "Adam";
		marketManagementSystem.approveMarketOperator(approvalDto.getCode(), approvalDto.getLastUpdateTimestamp(), user);
	}

	@RequestMapping(value = "/approvedMarketOperators/{MarketOperatorCode}", method = RequestMethod.DELETE)
	public void suspendMarketOperator(@PathVariable String marketOperatorCode) {
    	// TODO: Identify user 
        String user = "Adam";
		marketManagementSystem.suspendMarketOperator(marketOperatorCode, user);
	}
}
