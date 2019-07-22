package com.kunyi.bitamexJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.UserRegisterService;
import com.kunyi.bitamexJava.model.UserClientTable;
import com.kunyi.bitamexJava.model.UserInfoTable;
import com.kunyi.bitamexJava.util.QEncodeUtil;

import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@ApiIgnore
@RestController
@RequestMapping("/userregisterservice")
public class UserRegisterController {
	@Autowired
	private UserRegisterService userRegisterService;
	
	@RequestMapping(value = "/registeruser",method = RequestMethod.GET)
	public String userRegister(@RequestParam(value = "username",required = false)String username,
							   @RequestParam(value = "password",required = false)String password,
							   @RequestParam(value = "registertype",required = false)String registertype,
							   @RequestParam(value = "appid",required = false)String appid,
							   @RequestParam(value = "sysname",required = false)String sysname,
							   @RequestParam(value = "sysno",required = false)String sysno,
							   @RequestParam(value = "inviter",required = false)Integer inviter){
		//要配置redis
		UserInfoTable uTable = new UserInfoTable();
		uTable.setM_traderName(username);
		uTable.setM_userPassword(QEncodeUtil.AesEncrypt(password));
		uTable.setM_inviter(inviter + "");
		
		return userRegisterService.userRegister(uTable,registertype);
	}
}
