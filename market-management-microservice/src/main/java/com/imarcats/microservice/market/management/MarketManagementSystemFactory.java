package com.imarcats.microservice.market.management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imarcats.internal.server.infrastructure.datastore.AssetClassDatastore;
import com.imarcats.internal.server.infrastructure.datastore.AuditTrailEntryDatastore;
import com.imarcats.internal.server.infrastructure.datastore.InstrumentDatastore;
import com.imarcats.internal.server.infrastructure.datastore.MarketDatastore;
import com.imarcats.internal.server.infrastructure.datastore.MarketOperatorDatastore;
import com.imarcats.internal.server.infrastructure.datastore.MatchedTradeDatastore;
import com.imarcats.internal.server.infrastructure.datastore.ProductDatastore;
import com.imarcats.internal.server.infrastructure.testutils.MockDatastoresBase;
import com.imarcats.internal.server.infrastructure.timer.MarketTimer;
import com.imarcats.market.management.admin.MarketManagementAdminSystem;

@Configuration
public class MarketManagementSystemFactory {

	private MockDatastores mockDatastores = new MockDatastores();
	
	@Bean
	public ProductDatastore createProductDatastore() {
		return mockDatastores;
	}
	
	@Bean
	public MarketManagementAdminSystem createMarketManagementSystem() {
		
		MockDatastoresBase mockDataStores = mockDatastores;
		
		AssetClassDatastore assetClassDatastore = mockDataStores;
		ProductDatastore productDatastore = mockDataStores;
		InstrumentDatastore instrumentDatastore = mockDataStores;
		MarketOperatorDatastore marketOperatorDatastore = mockDataStores;
		MarketDatastore marketDatastore = mockDataStores;
		AuditTrailEntryDatastore auditTrailEntryDatastore = mockDataStores;
		MatchedTradeDatastore matchedTradeDatastore = mockDataStores;
		MarketTimer marketTimer = null;
		
		return new MarketManagementAdminSystem(assetClassDatastore, productDatastore, instrumentDatastore, 
				marketOperatorDatastore, marketDatastore, auditTrailEntryDatastore, matchedTradeDatastore, marketTimer);
	}
	
}
