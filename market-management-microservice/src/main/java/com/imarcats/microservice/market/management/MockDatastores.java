package com.imarcats.microservice.market.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.imarcats.internal.server.infrastructure.testutils.MockDatastoresBase;
import com.imarcats.model.AssetClass;
import com.imarcats.model.BuyBook;
import com.imarcats.model.CircuitBreaker;
import com.imarcats.model.HaltRule;
import com.imarcats.model.Instrument;
import com.imarcats.model.Market;
import com.imarcats.model.MarketOperator;
import com.imarcats.model.OrderBookEntryFactory;
import com.imarcats.model.OrderBookEntryModel;
import com.imarcats.model.OrderBookFactory;
import com.imarcats.model.OrderBookModel;
import com.imarcats.model.Product;
import com.imarcats.model.SellBook;
import com.imarcats.model.types.ActivationStatus;
import com.imarcats.model.types.Address;
import com.imarcats.model.types.AuditInformation;
import com.imarcats.model.types.BusinessCalendar;
import com.imarcats.model.types.BusinessCalendarDay;
import com.imarcats.model.types.ChangeType;
import com.imarcats.model.types.Day;
import com.imarcats.model.types.DeliveryPeriod;
import com.imarcats.model.types.ExecutionSystem;
import com.imarcats.model.types.InstrumentType;
import com.imarcats.model.types.MarketState;
import com.imarcats.model.types.OrderRejectAction;
import com.imarcats.model.types.OrderSide;
import com.imarcats.model.types.OrderType;
import com.imarcats.model.types.Position;
import com.imarcats.model.types.ProductType;
import com.imarcats.model.types.Quote;
import com.imarcats.model.types.QuoteAndSize;
import com.imarcats.model.types.QuoteType;
import com.imarcats.model.types.RecurringActionDetail;
import com.imarcats.model.types.SecondaryOrderPrecedenceRuleType;
import com.imarcats.model.types.SettlementPrice;
import com.imarcats.model.types.SettlementType;
import com.imarcats.model.types.TimeOfDay;
import com.imarcats.model.types.TimePeriod;
import com.imarcats.model.types.TradingSession;
import com.imarcats.model.types.UnderlyingType;

public class MockDatastores extends MockDatastoresBase {

	public MockDatastores() {
		super();
		
		// create and add a product 
		Product product = new Product();
		product.setProductCode("ADAMS_PRODUCT");
		product.setName("Test Product");
		product.setDescription("Test Product");
		product.setType(ProductType.Financial);
		product.setProductDefinitionDocument("test");
		product.setActivationStatus(ActivationStatus.Created);
		AuditInformation creationAudit = new AuditInformation();
		creationAudit.setDateTime(new Date());
		creationAudit.setUserID("Adam");
		product.setCreationAudit(creationAudit);
		
		createProduct(product);
		
		// create asset class 1	
		AssetClass assetClass = new AssetClass();
    	String assetClassName = "ADAM_ASSET_CLASS_1";
		assetClass.setName(assetClassName);
    	assetClass.setDescription("Test 1");

    	createAssetClass(assetClass);
    	
		// create asset class 2	
		assetClass = new AssetClass();
		String parent = assetClassName;
    	assetClassName = "ADAM_ASSET_CLASS_2";
		assetClass.setName(assetClassName);
    	assetClass.setDescription("Test 2");
    	assetClass.setParentName(parent);
    	
    	createAssetClass(assetClass);
    	
    	// create instrument 
		Instrument instrument1 = new Instrument();
		instrument1.setInstrumentCode("ADAMS_INSTRUMENT_1");
		instrument1.setName("Test Instrument");
		instrument1.setDescription("Test Instrument");
		instrument1.setDenominationCurrency("USD");
		instrument1.setContractSize(100);
		instrument1.setContractSizeUnit("MWh");
		instrument1.setDeliveryLocation(createAddress());
		instrument1.setDeliveryPeriod(DeliveryPeriod.T0);
		instrument1.setQuoteType(QuoteType.Price);
		instrument1.setRecordPurchaseAsPosition(Position.Long);
		instrument1.setSettlementPrice(SettlementPrice.Clean);
		instrument1.setSettlementType(SettlementType.PhysicalDelivery);
		instrument1.setRollable(true);
		instrument1.setActivationStatus(ActivationStatus.Created);

		instrument1.setUnderlyingType(UnderlyingType.Product);
		instrument1.setUnderlyingCode(product.getCode());
		if (instrument1.getUnderlyingType() == UnderlyingType.Product) {
			instrument1.setType(InstrumentType.Spot);
		} else {
			instrument1.setType(InstrumentType.Derivative);
		}
		
		instrument1.setAssetClassName(assetClassName); 
		
		createInstrument(instrument1);
    	
    	// create market operator 
		MarketOperator marketOperator = new MarketOperator();		
		marketOperator.setCode("ADAMS_MARKET_OPERATOR_1");
		marketOperator.setName("TestName");
		marketOperator.setDescription("TestDescr");
		marketOperator.setMarketOperatorAgreement("TestAggrement");
		marketOperator.setOwnerUserID("Test1");
		marketOperator.setBusinessEntityCode("TEST_BE");
		marketOperator.setActivationStatus(ActivationStatus.Approved);
		
		createMarketOperator(marketOperator);
		
    	// create market 
		Market marketModel = new Market();
		marketModel.updateLastUpdateTimestampAndVersion();
		
		marketModel.setMarketCode("ADAMS_MARKET_1");
		
		marketModel.setMarketOperatorCode(marketOperator.getCode());
		
		marketModel.setActivationStatus(ActivationStatus.Activated);
		
		marketModel.setClosingQuote(Quote.createQuote(10));
		
		marketModel.setPreviousClosingQuote(Quote.createQuote(11));
		
		marketModel.setHaltLevel(11);
		
		QuoteAndSize lastTrade = new QuoteAndSize();
		lastTrade.setQuote(Quote.createQuote(12));
		lastTrade.setSize(100);
		marketModel.setLastTrade(lastTrade);
		
		QuoteAndSize prevLastTrade = new QuoteAndSize();
		prevLastTrade.setQuote(Quote.createQuote(123));
		prevLastTrade.setSize(110);
		marketModel.setPreviousLastTrade(prevLastTrade);

		QuoteAndSize bid = new QuoteAndSize();
		bid.setQuote(Quote.createQuote(122));
		bid.setSize(101);
		marketModel.setPreviousBestBid(bid);
		
		QuoteAndSize ask = new QuoteAndSize();
		ask.setQuote(Quote.createQuote(123));
		ask.setSize(102);
		marketModel.setPreviousBestAsk(ask);

		QuoteAndSize bidCurr = new QuoteAndSize();
		bidCurr.setQuote(Quote.createQuote(122));
		bidCurr.setSize(101);
		marketModel.setCurrentBestBid(bidCurr);

		QuoteAndSize askCurr = new QuoteAndSize();
		askCurr.setQuote(Quote.createQuote(123));
		askCurr.setSize(102);
		marketModel.setCurrentBestAsk(askCurr);
		
		marketModel.setNextMarketCallDate(new Date());
		
		marketModel.setOpeningQuote(Quote.createQuote(16));
		
		marketModel.setPreviousOpeningQuote(Quote.createQuote(17));
		
		marketModel.setState(MarketState.Open);
		
		marketModel.setBusinessEntityCode("TestBusinessEntity");

		marketModel.setCircuitBreaker(createCircuitBreaker());
		
		marketModel.setCommission(10.0);
		
		marketModel.setExecutionSystem(ExecutionSystem.CallMarketWithSingleSideAuction);
		
		marketModel.setInstrumentCode(instrument1.getCode());
		
		marketModel.setMarketOperationContract("MyContract");
		
		marketModel.setMaximumContractsTraded(100);
		
		marketModel.setMinimumContractsTraded(10);
		
		marketModel.setMinimumQuoteIncrement(12);
		
		marketModel.setName("MyTestMarket");
		
		marketModel.setQuoteType(QuoteType.Yield);
		
		marketModel.setSecondaryOrderPrecedenceRules(createSecondaryPercedence());
		
		marketModel.setTradingDayEnd(createTimeOfDay());
		
		marketModel.setTradingHours(createTimePeriod());
		
		marketModel.setTradingSession(TradingSession.NonContinuous);
		
		marketModel.setDescription("Test Test Test");
		
		marketModel.setBusinessCalendar(createBusinessCalendar());
		
		marketModel.setCommissionCurrency("USD");
		
		marketModel.setBuyBook((BuyBook) createBook(OrderSide.Buy));
		
		marketModel.setSellBook((SellBook) createBook(OrderSide.Sell));
		
		marketModel.setCreationAudit(createAudit());
		
		marketModel.setChangeAudit(createAudit());
		
		marketModel.setApprovalAudit(createAudit());
		
		marketModel.setSuspensionAudit(createAudit());
		
		marketModel.setRolloverAudit(createAudit());
		
		marketModel.setActivationAudit(createAudit());
		
		marketModel.setDeactivationAudit(createAudit());
		
		marketModel.setMarketCallActionKey((long) 1);
		
		marketModel.setMarketOpenActionKey((long) 2);
		
		marketModel.setMarketCloseActionKey((long) 3);
		
		marketModel.setMarketReOpenActionKey((long) 4);
		
		marketModel.setMarketMaintenanceActionKey((long) 6);
		
		marketModel.setCallMarketMaintenanceActionKey((long) 7);
		
		marketModel.setMarketOperationDays(RecurringActionDetail.OnBusinessDaysAndWeekdays);
		
		marketModel.setAllowHiddenOrders(true);
		
		marketModel.setAllowSizeRestrictionOnOrders(true);
		
		marketModel.setMarketTimeZoneID("TestTZ");
		
		marketModel.setClearingBank("TestBank");
		
		createMarket(marketModel);
		
	}

	protected Address createAddress() {
		Address address = new Address();
		
		address.setCity("New York");
		address.setState("NY");
		address.setCountry("USA");
		address.setStreet("523 E78th Street");
		address.setPostalCode("10021");
		
		return address;
	}
	
	protected CircuitBreaker createCircuitBreaker() {
		CircuitBreaker circuitBreaker = new CircuitBreaker();
		
		circuitBreaker.setMaximumQuoteImprovement(100);
		circuitBreaker.setOrderRejectAction(OrderRejectAction.RejectAutomatically);
		
		List<HaltRule> haltRules = new ArrayList<HaltRule>();
		HaltRule haltRule1 = new HaltRule();
		haltRule1.setChangeType(ChangeType.Absolute);
		haltRule1.setHaltPeriod(100);
		haltRule1.setQuoteChangeAmount(10);
		haltRules.add(haltRule1);
		
		HaltRule haltRule2 = new HaltRule();
		haltRule2.setChangeType(ChangeType.Percentage);
		haltRule2.setHaltPeriod(10);
		haltRule2.setQuoteChangeAmount(14);
		haltRules.add(haltRule2);
		
		circuitBreaker.setHaltRules(haltRules);
		
		return circuitBreaker;
	}
	
	protected List<SecondaryOrderPrecedenceRuleType> createSecondaryPercedence() {
		
		List<SecondaryOrderPrecedenceRuleType> list = new ArrayList<SecondaryOrderPrecedenceRuleType>();

		list.add(SecondaryOrderPrecedenceRuleType.TimePrecedence);
		list.add(SecondaryOrderPrecedenceRuleType.DisplayPrecedence);
		
		return list;
	}
	
	private TimeOfDay createTimeOfDay() {
		TimeOfDay time = new TimeOfDay();
		time.setHour(10);
		time.setMinute(15);
		time.setSecond(10);
		
		time.setTimeZoneID("CET");
		
		return time;
	}

	protected OrderBookModel createBook(OrderSide side_) {
		OrderBookModel book = OrderBookFactory.createBook(side_);
		
		createBookEntries(side_, book).forEach(book::add);
		
		return book;
	}
	
	protected List<OrderBookEntryModel> createBookEntries(OrderSide side, OrderBookModel book) {
		List<OrderBookEntryModel> orders = new ArrayList<OrderBookEntryModel>(); 
		
		OrderBookEntryModel orderBookEntry1 = OrderBookEntryFactory.createEntry(side);
		orderBookEntry1.updateLastUpdateTimestamp();
		orderBookEntry1.setLimitQuote(Quote.createQuote(10));
		orderBookEntry1.setOrderType(OrderType.Limit);
		Long lastVersionNumber = book.getVersionNumber();
		orderBookEntry1.addOrderKey(1234L, book);
		// test version number here
		// version number will be handled automatically, no need to test it
		// TODO: Do we need to update book version, when an order book entry is updated by adding new order?
		// assertTrue(lastVersionNumber < book.getVersionNumber());
		
		orderBookEntry1.addOrderKey(4567L, book);
		
		orders.add(orderBookEntry1);
		
		OrderBookEntryModel orderBookEntry2 = OrderBookEntryFactory.createEntry(side);
		orderBookEntry2.updateLastUpdateTimestamp();
		orderBookEntry2.setLimitQuote(Quote.createQuote(11));
		orderBookEntry2.setOrderType(OrderType.Limit);
		lastVersionNumber = book.getVersionNumber();
		orderBookEntry2.addOrderKey(0, 8901L, book);
		// test version number here
		// version number will be handled automatically, no need to test it
		// TODO: Do we need to update book version, when an order book entry is updated by adding new order?
		// assertTrue(lastVersionNumber < book.getVersionNumber());
		
		orderBookEntry2.addOrderKey(2345L, book);
		
		// add order to be removed 
		Long orderKey = 234567L;
		orderBookEntry2.addOrderKey(orderKey, book);
		
		// remove order and test version
		lastVersionNumber = book.getVersionNumber();
		orderBookEntry2.removeOrderKey(orderKey, book);
		// test version number here
		// version number will be handled automatically, no need to test it
		// TODO: Do we need to update book version, when an order book entry is updated by adding new order?
		// assertTrue(lastVersionNumber < book.getVersionNumber());
		
		orders.add(orderBookEntry2);
		return orders;
	}
	
	protected AuditInformation createAudit() {
		AuditInformation audit = new AuditInformation();
		audit.setUserID("Test User");
		audit.setDateTime(new Date());
		
		return audit;
	}
	
	protected BusinessCalendar createBusinessCalendar() {
		BusinessCalendar calendar = new BusinessCalendar();
		
		BusinessCalendarDay businessCalendarDay = new BusinessCalendarDay();
		businessCalendarDay.setDateString("2010/04/11");
		businessCalendarDay.setDay(Day.Holiday);
		calendar.getBusinessCalendarDays().add(businessCalendarDay);
		
	    businessCalendarDay = new BusinessCalendarDay();
		businessCalendarDay.setDateString("2010/04/12");
		businessCalendarDay.setDay(Day.BusinessDay);
		calendar.getBusinessCalendarDays().add(businessCalendarDay);
		
	    businessCalendarDay = new BusinessCalendarDay();
		businessCalendarDay.setDateString("2010/04/13");
		businessCalendarDay.setDay(Day.BusinessDay);
		calendar.getBusinessCalendarDays().add(businessCalendarDay);
		
		return calendar;
	}
	
	protected TimePeriod createTimePeriod() {
		TimePeriod timePeriod = new TimePeriod();
		
		TimeOfDay start = createTimeOfDay();
		
		TimeOfDay end = new TimeOfDay();
		end.setHour(11);
		end.setMinute(20);
		end.setSecond(11);
		end.setTimeZoneID("CET");
		
		timePeriod.setStartTime(start);
		timePeriod.setEndTime(end);
		
		return timePeriod;
	}
}
