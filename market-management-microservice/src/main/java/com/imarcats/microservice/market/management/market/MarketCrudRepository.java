package com.imarcats.microservice.market.management.market;

import org.springframework.data.repository.CrudRepository;

import com.imarcats.model.Market;

public interface MarketCrudRepository extends CrudRepository<Market, String> {

}
