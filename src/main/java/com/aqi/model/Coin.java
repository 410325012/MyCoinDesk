package com.aqi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@Entity @Table(name="coin")
@Component
public class Coin {
	@Id
	private String code;
	private String name;
	private Float rate_float;
	@ApiModelProperty(required = false, hidden = true)
	private String updatetime;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getRate_float() {
		return rate_float;
	}
	public void setRate_float(Float rate_float) {
		this.rate_float = rate_float;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
}
