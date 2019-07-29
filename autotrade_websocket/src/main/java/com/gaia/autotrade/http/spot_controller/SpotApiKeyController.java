package com.gaia.autotrade.http.spot_controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SpotApiKeyController {

	@RequestMapping(value = "/open/api_key/create", method = RequestMethod.POST)
	public String createApiKey(@RequestParam(value = "username", required = true)String username,
							   @RequestParam(value = "remark", required = true)String remark, 
						       @RequestParam(value = "privilege", required = true)String privilege, 
						       @RequestParam(value = "ipgroup", required = false)String ipgroup) {
		
		return null;
	}
	
	@RequestMapping(value = "/open/api_key/list", method = RequestMethod.GET)
	public String getApiKeyList(@RequestParam(value = "access_key", required = true)String access_key) {
		return null;
	}
	
	@RequestMapping(value = "/open/api_key/delete", method = RequestMethod.POST)
	public String deleteApiKey(@RequestParam(value = "access_key", required = true)String access_key) {
		return null;
	}
}
