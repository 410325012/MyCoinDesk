package com.aqi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.aqi.model.Coin;
import com.aqi.model.CoinRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
public class CoinServiceImpl implements CoinService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CoinRepository coinRepository;
	
	@Override
	public List<Coin> findAll() {
		return coinRepository.findAll();
	}
	
	@Override
	public Coin findByCode(String code) {
		return coinRepository.findByCode(code.toUpperCase());
	}
	
	@Override
	public void deleteByCode(String code) {
		coinRepository.deleteByCode(code.toUpperCase());
	}
	
	@Override
	public Coin save(Coin c) {
		c.setCode(c.getCode().toUpperCase());
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm:00");
		Date date = new Date(System.currentTimeMillis());
		c.setUpdatetime(sdf.format(date));
		return coinRepository.save(c);
	}
	
	@Override
	public void updateFromCoinDesk() throws JsonMappingException, JsonProcessingException, ParseException {
		String response = restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice.json", String.class);
	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode rootNode = mapper.readValue(response, JsonNode.class);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        JsonNode time = rootNode.get("time");
        JsonNode updatedISO = time.get("updatedISO");
        Date dateISO = sdf.parse(updatedISO.asText());
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dateISO);
        rightNow.add(Calendar.HOUR,8);
        Date dateISO1=rightNow.getTime();
        String format_date = sdf2.format(dateISO1);
	    JsonNode bpi = rootNode.get("bpi");
	    JsonNode USD = bpi.get("USD");
	    Coin USDObj = mapper.convertValue(USD, Coin.class);
	    USDObj.setName("美元");
	    USDObj.setUpdatetime(format_date);
	    coinRepository.save(USDObj);
	    JsonNode GBP = bpi.get("GBP");
	    Coin GBPObj = mapper.convertValue(GBP, Coin.class);
	    GBPObj.setName("英鎊");
	    GBPObj.setUpdatetime(format_date);
	    coinRepository.save(GBPObj);
	    JsonNode EUR = bpi.get("EUR");
	    Coin EURObj = mapper.convertValue(EUR, Coin.class);
	    EURObj.setName("歐元");
	    EURObj.setUpdatetime(format_date);
	    coinRepository.save(EURObj);
	}
	
	@PostConstruct
	public void init() throws JsonMappingException, JsonProcessingException, ParseException {
		updateFromCoinDesk();
	}
}
