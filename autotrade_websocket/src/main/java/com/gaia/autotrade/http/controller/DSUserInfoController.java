package com.gaia.autotrade.http.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaia.autotrade.http.entity.User;

@RestController
public class DSUserInfoController {

	@RequestMapping(value = "/get/tradelimit/today", method = RequestMethod.POST)
	public String getTradeLimitytoday(@RequestBody User user) {
		
		System.out.println(user);
		return null;
	}
}
