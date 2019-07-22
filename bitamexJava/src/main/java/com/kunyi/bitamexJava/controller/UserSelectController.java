package com.kunyi.bitamexJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kunyi.bitamexJava.dao.UserSelectService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/userselectservice")
public class UserSelectController {

	@Autowired
	private UserSelectService userSelectService;
	
	@RequestMapping(value = "/get",method = RequestMethod.POST)
	public String getUserSelectData(@RequestParam(value = "traderid",required = false)String traderId){
		return userSelectService.getUserData(traderId);
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public String addUserSelectData(@RequestParam(value = "traderid",required = false)String traderId,
									@RequestParam(value = "code",required = false)String code){
		return userSelectService.addUserData(traderId,code);
	}
	
	@RequestMapping(value = "/del",method = RequestMethod.POST)
	public String delUserSelectData(@RequestParam(value = "traderid",required = false)String traderId,
									@RequestParam(value = "code",required = false)String code){
		return userSelectService.delUserData(traderId,code);
	}
}
