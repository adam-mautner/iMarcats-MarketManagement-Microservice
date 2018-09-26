package com.imarcats.microservice.market.management;

import java.util.Date;

import com.imarcats.interfaces.client.v100.dto.types.TimeOfDayDto;

public class ActivationDto {
	private String code; 
	private Date nextCallDate;
	private TimeOfDayDto nextMarketCallTime;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getNextCallDate() {
		return nextCallDate;
	}
	public void setNextCallDate(Date nextCallDate) {
		this.nextCallDate = nextCallDate;
	}
	public TimeOfDayDto getNextMarketCallTime() {
		return nextMarketCallTime;
	}
	public void setNextMarketCallTime(TimeOfDayDto nextMarketCallTime) {
		this.nextMarketCallTime = nextMarketCallTime;
	}
	
	public boolean isCallMarket() {
		return (getNextCallDate() != null && getNextMarketCallTime() != null);
	}
	
}
