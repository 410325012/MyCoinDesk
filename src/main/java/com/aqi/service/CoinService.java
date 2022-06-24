package com.aqi.service;

import java.text.ParseException;
import java.util.List;

import com.aqi.model.Coin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface CoinService {
	
	public List<Coin> findAll();
	public void updateFromCoinDesk() throws JsonMappingException, JsonProcessingException, ParseException;
	public Coin findByCode(String code);
	public Coin save(Coin c);
	public void deleteByCode(String code);

}
