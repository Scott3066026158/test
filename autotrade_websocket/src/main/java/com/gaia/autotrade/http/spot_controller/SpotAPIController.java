package com.gaia.autotrade.http.spot_controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpotAPIController {

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "access_key") String access_key) {

		return null;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestParam(value = "access_key") String access_key) {

		return null;
	}
}
