package com.pp.cc.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pp.cc.vo.CurrencyConversion;
import com.pp.cc.web.clients.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	@Autowired
	CurrencyExchangeProxy currencyExchangeProxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion convertCurrency(@PathVariable("from") String from,
			@PathVariable("to") String to, @PathVariable("quantity") BigDecimal quantity) {
		Map<String,String> paramMap=new HashMap<>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		//new CurrencyConversion(1000L, from, to, quantity, BigDecimal.ONE, BigDecimal.ONE, "");
		CurrencyConversion currencyConversion=new RestTemplate()
		.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
				CurrencyConversion.class, 
				paramMap).getBody();
		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculatedValue(quantity.multiply(currencyConversion.getConversionMultiple()));
		return currencyConversion;
				
		
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getCurrency(@PathVariable("from") String from,
			@PathVariable("to") String to, @PathVariable("quantity") BigDecimal quantity) {
		Map<String,String> paramMap=new HashMap<>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		//new CurrencyConversion(1000L, from, to, quantity, BigDecimal.ONE, BigDecimal.ONE, "");
		
		CurrencyConversion currencyConversion=currencyExchangeProxy.retriveExchangeValue(from, to);
		
		currencyConversion.setQuantity(quantity);
		currencyConversion.setTotalCalculatedValue(quantity.multiply(currencyConversion.getConversionMultiple()));
		return currencyConversion;
				
		
	}
}
