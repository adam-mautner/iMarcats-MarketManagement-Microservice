package com.imarcats.microservice.market.management;

import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.imarcats.internal.server.infrastructure.datastore.AssetClassDatastore;
import com.imarcats.internal.server.infrastructure.datastore.AuditTrailEntryDatastore;
import com.imarcats.internal.server.infrastructure.datastore.InstrumentDatastore;
import com.imarcats.internal.server.infrastructure.datastore.MarketDatastore;
import com.imarcats.internal.server.infrastructure.datastore.MarketOperatorDatastore;
import com.imarcats.internal.server.infrastructure.datastore.MatchedTradeDatastore;
import com.imarcats.internal.server.infrastructure.datastore.ProductDatastore;
import com.imarcats.internal.server.infrastructure.testutils.MockDatastoresBase;
import com.imarcats.internal.server.infrastructure.timer.MarketTimer;
import com.imarcats.internal.server.infrastructure.timer.TimerAction;
import com.imarcats.market.management.admin.MarketManagementAdminSystem;
import com.imarcats.model.types.BusinessCalendar;
import com.imarcats.model.types.RecurringActionDetail;
import com.imarcats.model.types.TimeOfDay;

@Configuration
@EnableTransactionManagement
public class MarketManagementSystemFactory {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		factoryBean.setPersistenceUnitName("iMarcats");
		return factoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public MarketManagementAdminSystem createMarketManagementSystem(AssetClassDatastore assetClassDatastore,
			ProductDatastore productDatastore, InstrumentDatastore instrumentDatastore,
			MarketOperatorDatastore marketOperatorDatastore, MarketDatastore marketDatastore) {

		MockDatastoresBase mockDataStores = new MockDatastoresBase();

		AuditTrailEntryDatastore auditTrailEntryDatastore = mockDataStores;
		MatchedTradeDatastore matchedTradeDatastore = mockDataStores;
		MarketTimer marketTimer = new MarketTimer() {
			
			@Override
			public Long scheduleToTime(Date arg0, TimeOfDay arg1, RecurringActionDetail arg2, BusinessCalendar arg3,
					boolean arg4, TimerAction arg5) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Long scheduleRelative(long arg0, TimerAction arg1) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Long scheduleAbsolute(Date arg0, boolean arg1, TimerAction arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Long rescheduleToTime(Long arg0, Date arg1, TimeOfDay arg2, boolean arg3, Long arg4) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Long rescheduleRelative(Long arg0, long arg1, Long arg2) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Long rescheduleAbsolute(Long arg0, Date arg1, boolean arg2, Long arg3) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void cancel(Long arg0) {
				// TODO Auto-generated method stub
				
			}
		};

		return new MarketManagementAdminSystem(assetClassDatastore, productDatastore, instrumentDatastore,
				marketOperatorDatastore, marketDatastore, auditTrailEntryDatastore, matchedTradeDatastore, marketTimer);
	}

}
