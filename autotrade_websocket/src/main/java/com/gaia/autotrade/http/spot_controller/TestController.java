package com.gaia.autotrade.http.spot_controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	public String delete(@RequestParam(value = "noticeid") String noticeid,
			@RequestParam(value = "traderid") String traderid, @RequestParam(value = "coincode") String coincode,
			@RequestParam(value = "balance") double balance, @RequestParam(value = "frozen") double frozen,
			@RequestParam(value = "cbalance") double cbalance, @RequestParam(value = "cfrozen") double cfrozen,
			@RequestParam(value = "reason") int reason, @RequestParam(value = "timestamp") long timestamp) {
		System.out.println("noticeid=" + noticeid + ",traderid=" + traderid + ",coincode=" + coincode + ",balance="
				+ balance + ",frozen=" + frozen + ",cbalance=" + cbalance + ",reason=" + reason + ",timestamp="
				+ timestamp);
		return "{\"code\" : 1}";
	}

	@RequestMapping(value = "/abc", method = RequestMethod.GET)
	public String create() {
		System.out.println("nihao");
		return "nihao";
	}
}
