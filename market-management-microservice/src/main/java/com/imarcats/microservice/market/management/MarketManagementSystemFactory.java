package com.imarcats.microservice.market.management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

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

	@Bean
	public LocalEntityManagerFactoryBean entityManagerFactory(){
	     LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();

	    factoryBean.setPersistenceUnitName("iMarcats");
	    return factoryBean;
	}
	
	@Bean
	public MarketManagementAdminSystem createMarketManagementSystem(AssetClassDatastore assetClassDatastore, ProductDatastore productDatastore, InstrumentDatastore instrumentDatastore, MarketOperatorDatastore marketOperatorDatastore, MarketDatastore marketDatastore) {
		
		MockDatastoresBase mockDataStores = new MockDatastoresBase();
		
		AuditTrailEntryDatastore auditTrailEntryDatastore = mockDataStores;
		MatchedTradeDatastore matchedTradeDatastore = mockDataStores;
		MarketTimer marketTimer = null;
		
		return new MarketManagementAdminSystem(assetClassDatastore, productDatastore, instrumentDatastore, 
				marketOperatorDatastore, marketDatastore, auditTrailEntryDatastore, matchedTradeDatastore, marketTimer);
	}
	
}
