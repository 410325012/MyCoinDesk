package com.aqi.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aqi.model.Coin;
import com.aqi.service.CoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "MyCoinDesk相關api")
@RestController
@RequestMapping("v1")
public class CoinController {
	
	@Autowired
	CoinService coinService;
	
	@ApiOperation("取得所有coin")
	@GetMapping("coin")
	public List<Coin> getMyCoinDesk() throws JsonMappingException, JsonProcessingException, ParseException {
		coinService.updateFromCoinDesk();
		return coinService.findAll();
	}
	
	@ApiOperation("新增coin")
	@PostMapping("coin")
	public Coin postMyCoinDesk(Coin c){
		return coinService.save(c);
	}
	
	@ApiOperation("取得coin")
	@GetMapping("coin/{code}")
	public Coin getMyCoinDeskByCode(@PathVariable(value = "code") String code){
		return coinService.findByCode(code);
	}
	
	@ApiOperation("修改coin")
	@PutMapping("coin/{code}")
	public Coin putMyCoinDeskByCode(@PathVariable(value = "code") String code,String name,Float rate_float){
		Coin c=new Coin();
		c.setCode(code);
		c.setName(name);
		c.setRate_float(rate_float);
		return coinService.save(c);
	}
	
	@ApiOperation("刪除coin")
	@DeleteMapping("coin/{code}")
	public void deleteMyCoinDeskByCode(@PathVariable(value = "code") String code){
		coinService.deleteByCode(code);
	}
	
}
