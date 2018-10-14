package com.imarcats.microservice.market.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.imarcats.interfaces.client.v100.dto.MatchedTradeDto;
import com.imarcats.interfaces.client.v100.dto.types.MarketDataType;
import com.imarcats.interfaces.client.v100.notification.ListenerCallUserParameters;
import com.imarcats.interfaces.client.v100.notification.MarketDataChange;
import com.imarcats.interfaces.client.v100.notification.NotificationType;
import com.imarcats.interfaces.client.v100.notification.PropertyChanges;
import com.imarcats.internal.server.infrastructure.datastore.AssetClassDatastore;
import com.imarcats.internal.server.infrastructure.datastore.InstrumentDatastore;
import com.imarcats.internal.server.infrastructure.datastore.MarketDatastore;
import com.imarcats.internal.server.infrastructure.datastore.MarketOperatorDatastore;
import com.imarcats.internal.server.infrastructure.datastore.ProductDatastore;
import com.imarcats.internal.server.infrastructure.marketdata.MarketDataSessionImpl;
import com.imarcats.internal.server.infrastructure.marketdata.MarketDataSource;
import com.imarcats.internal.server.infrastructure.marketdata.PersistedMarketDataChangeListener;
import com.imarcats.internal.server.infrastructure.notification.NotificationBroker;
import com.imarcats.internal.server.infrastructure.notification.PersistedListener;
import com.imarcats.internal.server.infrastructure.notification.properties.PersistedPropertyChangeListener;
import com.imarcats.internal.server.infrastructure.notification.properties.PropertyChangeBroker;
import com.imarcats.internal.server.infrastructure.notification.properties.PropertyChangeSessionImpl;
import com.imarcats.internal.server.infrastructure.notification.trades.TradeNotificationBroker;
import com.imarcats.internal.server.infrastructure.notification.trades.TradeNotificationSession;
import com.imarcats.internal.server.infrastructure.notification.trades.TradeNotificationSessionImpl;
import com.imarcats.market.management.MarketManagementContextImpl;
import com.imarcats.market.management.admin.MarketManagementAdminSystem;
import com.imarcats.model.types.DatastoreKey;

public class RestControllerBase {

	@Autowired
	protected MarketManagementAdminSystem marketManagementSystem;

	@Autowired
	@Qualifier("Mock")
	protected ProductDatastore productDatastore;

	@Autowired
	@Qualifier("Mock")
	protected InstrumentDatastore instrumentDatastore;
	
	@Autowired
	@Qualifier("Mock")
	protected MarketOperatorDatastore marketOperatorDatastore;

	@Autowired
	@Qualifier("Mock")
	protected MarketDatastore marketDatastore;
	
	@Autowired
	@Qualifier("AssetClassDatastoreImpl")
	protected AssetClassDatastore assetClassDatastore;
	
	// TODO: Warning test code, remove later 
	public static MarketManagementContextImpl createMarketManagementContext() {

		MockMarketDataSource marketDataSource = new MockMarketDataSource();
		MockPropertyChangeBroker propertyChangeBroker = new MockPropertyChangeBroker();

		MarketDataSessionImpl marketDataSession = new MarketDataSessionImpl(marketDataSource);
		PropertyChangeSessionImpl propertyChangeSession = new PropertyChangeSessionImpl(propertyChangeBroker);

		TradeNotificationBroker tradeNotificationBroker = new MockTradeNotificationBroker();
		TradeNotificationSession tradeNotificationSession = new TradeNotificationSessionImpl(tradeNotificationBroker);

		MarketManagementContextImpl context = new MarketManagementContextImpl(marketDataSession, propertyChangeSession,
				tradeNotificationSession);
		return context;
	}

	private static class MockPropertyChangeBroker implements PropertyChangeBroker {
		@Override
		public Long addPropertyChangeListener(DatastoreKey observedObjectKey_, Class observedObjectClass_,
				PersistedPropertyChangeListener listener_) {return null;}
		@Override
		public NotificationBroker getNotificationBroker() {return new MockNotificationBroker();}
		@Override
		public void notifyListeners(PropertyChanges[] propertyChanges_) {}
		@Override
		public void removePropertyChangeListener(Long listenerID_) {}
	}
	private static class MockTradeNotificationBroker implements TradeNotificationBroker {
		@Override
		public NotificationBroker getNotificationBroker() {return null;}
		@Override
		public void notifyListeners(MatchedTradeDto[] matchedTrades_) {}
	}
	private static class MockMarketDataSource implements MarketDataSource {
		@Override
		public Long addMarketDataChangeListener(String marketCode_, MarketDataType marketDataType_,
				PersistedMarketDataChangeListener listener_) {return null;}
		@Override
		public NotificationBroker getNotificationBroker() {return null;}
		@Override
		public void notifyListeners(MarketDataChange[] marketDataChanges_) {}
		@Override
		public void removeMarketDataChangeListener(Long listenerID_) {}
	}
	private static class MockNotificationBroker implements NotificationBroker {
		@Override
		public Long addListener(DatastoreKey observedObject_,
				Class observedObjectClass_, NotificationType notificationType_,
				String filterString_, PersistedListener listener_) {return null;}
		@Override
		public void notifyListeners(DatastoreKey observedObject_,
				Class observedObjectClass_, NotificationType notificationType_,
				String filterString_, ListenerCallUserParameters parameters_) {}
		@Override
		public void removeAllListeners(DatastoreKey observedObjectKey_,
				Class observedObjectClass_) {}
		@Override
		public void removeListener(Long listenerKey_) {}
	}
	
}
