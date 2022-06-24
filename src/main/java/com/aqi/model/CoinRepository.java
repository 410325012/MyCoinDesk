package com.aqi.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
	public Coin findByCode(String code);
	public void deleteByCode(String code);
}
