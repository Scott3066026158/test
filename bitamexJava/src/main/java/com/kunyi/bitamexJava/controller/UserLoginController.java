package com.kunyi.bitamexJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.UserLoginService;

import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@ApiIgnore
@RestController
@RequestMapping("/userloginservice")
public class UserLoginController {
	
	@Autowired
	private UserLoginService UserLoginService;
	
	@RequestMapping(value = "/userlogin",method = RequestMethod.GET)
	public String login(@RequestParam(value = "username",required = false)String username,
						@RequestParam(value = "password",required = false)String password,
						@RequestParam(value = "logintype",required = false)String logintype){
		
		return UserLoginService.userLogin(username, password, logintype);
	}
	
	@RequestMapping(value = "/smsauthentication",method = RequestMethod.GET)
	public String smsAuthened(@RequestParam(value = "userid",required = false)String userid,
							  @RequestParam(value = "password",required = false)String password){
		return UserLoginService.smsAuthened(userid,password);
	}
	
	@RequestMapping(value = "/fetchgooglesecretkey",method = RequestMethod.GET)
	public String fetchgooglesecretkey(@RequestParam(value = "userid",required = false)String userid){
		
		return UserLoginService.fetchgooglesecretkey(userid);
	}
	
	@RequestMapping(value = "/googleauthentication",method = RequestMethod.GET)
	public String fetchgooglesecretkey(@RequestParam(value = "userid",required = false)Integer userid,
									   @RequestParam(value = "password",required = false)String password,
									   @RequestParam(value = "googlecode",required = false)Integer googlecode){
		
		return UserLoginService.googleAuthened(userid,password,googlecode);
	}
	
	@RequestMapping(value = "/checkgooglecode",method = RequestMethod.GET)
	public String checkgooglecode(@RequestParam(value = "googlecode",required = false)Integer googlecode,
								  @RequestParam(value = "userid",required = false)Integer userid){
		return UserLoginService.checkGoogleCode(googlecode,userid);
	}
}
