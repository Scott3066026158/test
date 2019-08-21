package com.gaia.autotrade.http.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {

	@RequestMapping(value = "/v1/dw/withdraw/api/create", method = RequestMethod.POST)
	public String addAdminInfo(@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "amount", required = true) String amount,
			@RequestParam(value = "currency", required = true) String currency,
			@RequestParam(value = "fee", required = false) String fee,
			@RequestParam(value = "chain", required = false) String chain,
			@RequestParam(value = "addr-tag", required = false) String addr_tag) {
		return null;
	}
}
