package com.gaia.autotrade.http.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaia.autotrade.http.entity.User;

@RestController
public class ApiKeyController {

	@RequestMapping(value = "/open/api_key/create", method = RequestMethod.POST)
	public String createApiKey(@RequestBody User user) {
		
		System.out.println(user);
		return null;
	}

	@RequestMapping(value = "/open/api_key/list", method = RequestMethod.GET)
	public String getApiKeyList(@RequestParam(value = "access_key", required = true) String access_key) {
		return null;
	}

	@RequestMapping(value = "/open/api_key/delete", method = RequestMethod.POST)
	public String deleteApiKey(@RequestParam(value = "access_key", required = true) String access_key) {
		return null;
	}
}
