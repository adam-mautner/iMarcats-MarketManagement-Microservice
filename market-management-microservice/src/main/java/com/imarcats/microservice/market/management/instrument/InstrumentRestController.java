package com.imarcats.microservice.market.management.instrument;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imarcats.interfaces.client.v100.dto.InstrumentDto;
import com.imarcats.interfaces.client.v100.dto.types.ActivationStatus;
import com.imarcats.interfaces.client.v100.dto.types.PagedInstrumentListDto;
import com.imarcats.interfaces.client.v100.exception.MarketRuntimeException;
import com.imarcats.interfaces.server.v100.dto.mapping.InstrumentDtoMapping;
import com.imarcats.market.management.MarketManagementContext;
import com.imarcats.microservice.market.management.ApprovalDto;
import com.imarcats.microservice.market.management.RestControllerBase;
import com.imarcats.model.types.PagedInstrumentList;

@RestController
@RequestMapping("/")
public class InstrumentRestController extends RestControllerBase {

	@RequestMapping(value = "/instruments", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedInstrumentListDto getAllInstruments(@RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage, 
			@RequestParam("activationStatus") Optional<String> activationStatus, 
			@RequestParam("assetClass") Optional<String> assetClass, 
			@RequestParam("underlying") Optional<String> underlying, 
			@RequestParam("underlyingType") Optional<String> underlyingType) {
		return getAllInstrumentsInternal(activationStatus, assetClass, underlying, underlyingType, cursorString,
				numberOfItemsPerPage);
	}

	private PagedInstrumentListDto getAllInstrumentsInternal(Optional<String> activationStatus,
			Optional<String> assetClass, Optional<String> underlying, Optional<String> underlyingType,
			Optional<String> cursorString, Optional<Integer> numberOfItemsPerPage) {
		Integer numberOfItemsPerPageInternal = numberOfItemsPerPage.orElse(10);
		String cursorStringInternal = cursorString.orElse(null);

		// check parameters - choice 
		int cnt = 0;
		if(activationStatus.isPresent()) {
			cnt++;
		}
		if(assetClass.isPresent()) {
			cnt++;
		}
		if(underlying.isPresent()) {
			cnt++;
		}

		if(cnt > 1) {
			throw new RuntimeException("Redundant request parameter");
		}
		
		// check parameters - underlying and underlying type 
		if(underlying.isPresent() && !underlyingType.isPresent()) {
			throw new RuntimeException("Underlying type must be also present");
		}
		
		PagedInstrumentList list = null;
		if(activationStatus.isPresent()) {
			com.imarcats.model.types.ActivationStatus activationStatusObject = com.imarcats.model.types.ActivationStatus.valueOf(activationStatus.get());
			list = instrumentDatastore.findInstrumentsFromCursorByActivationStatus(activationStatusObject, cursorStringInternal, numberOfItemsPerPageInternal);
		} else if(assetClass.isPresent()) {
			list = instrumentDatastore.findInstrumentsFromCursorByAssetClass(assetClass.get(), cursorStringInternal, numberOfItemsPerPageInternal);
		}  else if(underlying.isPresent()) {
			com.imarcats.model.types.UnderlyingType underlyingTypeObject = com.imarcats.model.types.UnderlyingType.valueOf(underlyingType.get());
			list = instrumentDatastore.findInstrumentsFromCursorByUnderlying(underlying.get(), underlyingTypeObject, cursorStringInternal, numberOfItemsPerPageInternal);
		} else {			
			list = instrumentDatastore.findAllInstrumentsFromCursor(cursorStringInternal, numberOfItemsPerPageInternal);
		}

		PagedInstrumentListDto dto = InstrumentDtoMapping.INSTANCE.toDto(list);
		
		return dto;
	}

	@RequestMapping(value = "/instruments", method = RequestMethod.POST, consumes = "application/json")
	public void createNewInstrument(@RequestBody InstrumentDto instrument) {
		// TODO: Identify user
		String user = "Adam";
		marketManagementSystem.createInstrument(instrument, user);
	}

	@RequestMapping(value = "/instruments/{instrumentCode}", method = RequestMethod.GET, produces = { "application/JSON" })
	public ResponseEntity<InstrumentDto> getInstrument(@PathVariable String instrumentCode) {
		try {
			InstrumentDto instrumentDto = marketManagementSystem.getInstrument(instrumentCode);
			return ResponseEntity.ok().body(instrumentDto);
		} catch (MarketRuntimeException e) {
			if (MarketRuntimeException.NON_EXISTENT_INSTRUMENT.getLanguageKey()
					.equals(((MarketRuntimeException) e).getLanguageKey())) {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@RequestMapping(value = "/instruments/{instrumentCode}", method = RequestMethod.PUT, consumes = "application/json")
	public void updateInstrument(@PathVariable String instrumentCode, @RequestBody InstrumentDto instrument) {
		// TODO: Identify user
		String user = "Adam";
		// we set the instrument code just to be on the safe side
		instrument.setInstrumentCode(instrumentCode);
		marketManagementSystem.changeInstrument(instrument, user);
	}

	@RequestMapping(value = "/instruments/{instrumentCode}", method=RequestMethod.DELETE)
    public void deleteInstrument(@PathVariable String instrumentCode) {
    	// TODO: Identify user 
        String user = "Adam";
		MarketManagementContext context = createMarketManagementContext(); 
		marketManagementSystem.deleteInstrument(instrumentCode, user, context);
	}

	@RequestMapping(value = "/approvedInstruments", method = RequestMethod.GET, produces = { "application/JSON" })
	public PagedInstrumentListDto getApprovedInstrument(@RequestParam("cursorString") Optional<String> cursorString,
			@RequestParam("numberOfItemsPerPage") Optional<Integer> numberOfItemsPerPage) {
		return getAllInstrumentsInternal(Optional.of(ActivationStatus.Approved.name()), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), cursorString,
				numberOfItemsPerPage);
	}

	@RequestMapping(value = "/approvedInstruments", method = RequestMethod.POST, consumes = "application/json")
	public void approveInstrument(@RequestBody ApprovalDto approvalDto) {
    	// TODO: Identify user 
        String user = "Adam";
		marketManagementSystem.approveInstrument(approvalDto.getCode(), approvalDto.getLastUpdateTimestamp(), user);
	}

	@RequestMapping(value = "/approvedInstruments/{instrumentCode}", method = RequestMethod.DELETE)
	public void suspendInstrument(@PathVariable String instrumentCode) {
    	// TODO: Identify user 
        String user = "Adam";
		marketManagementSystem.suspendInstrument(instrumentCode, user);
	}
}
