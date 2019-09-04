package com.gaia.autotrade.http.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.http.entity.JsonMessage;

@RestController
public class ExchangeRateController {

	// 获取汇率
	@RequestMapping(value = "/exchangerate/get", method = RequestMethod.GET)
	public String getExchangeRate(@RequestParam(value = "symbol") String symbol) {
		JsonMessage jmsg = new JsonMessage();
		try {
			long ts = System.currentTimeMillis() / 1000;
			String url = "http://webforex.hermes.hexun.com/forex/quotelist?code=FOREX%s&column=code,price&_=%d";
			url = String.format(url, symbol, ts);
			RestTemplate restTemplate = new RestTemplate();
	        String notice = restTemplate.getForObject(url, String.class);
	        notice = notice.replace("(", "");
	        notice = notice.replace(")", "");
	        notice = notice.replace(";", "");
	        JSONObject json = JSON.parseObject(notice);
	        List<List<List<Object>>> list = (List<List<List<Object>>>)json.get("Data");
	        ExchangeRate data =  new ExchangeRate();
	        data.setCode((String)(list.get(0).get(0).get(0)));
	        data.setRate((list.get(0).get(0).get(1)));
	    	jmsg.setCode("200");
			jmsg.setData(data);
		}catch(Exception e) {
			jmsg.setCode("0");
			jmsg.setData("");
			jmsg.setErrMsg("An error has been encountered");
			e.printStackTrace();
		}
        return JSON.toJSONString(jmsg);
	}
	
}
class ExchangeRate
{
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getRate() {
		return rate;
	}
	public void setRate(Object rate) {
		this.rate = rate;
	}
	private String code;
	private Object rate;
}
