package com.gaia.autotrade.http.spot_service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gaia.autotrade.owsock.service.TradeService;

@Service
public class SpotWalletService {

	private TradeService m_tradeService = TradeService.getInstance();

	@RequestMapping(value = "/v1/dw/withdraw/api/create", method = RequestMethod.POST)
	public String create(String address, String amount, String currency, String fee, String chain, String addr_tag) {
		// m_tradeService.ReqWithdrawal(flag, traderID, code, outAddr, memo, balance)
		return null;
	}
	
	
}
