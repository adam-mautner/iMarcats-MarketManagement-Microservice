package com.imarcats.microservice.market.management.instrument;

import org.springframework.data.repository.CrudRepository;

import com.imarcats.model.Instrument;

public interface InstrumentCrudRepository extends CrudRepository<Instrument, String> {

}
