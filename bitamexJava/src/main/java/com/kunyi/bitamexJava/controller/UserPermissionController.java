package com.kunyi.bitamexJava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.UserPermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@Api(value = "/userpermissionservice",description = "用户权限Api")
@RestController
@RequestMapping("/userpermissionservice")
public class UserPermissionController {
	@Autowired
	private UserPermissionService uPermissionService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiIgnore
	@RequestMapping(value = "/getuserpermission",method = RequestMethod.GET)
	public String getUserPermission(@RequestParam(value = "userid",required = false)Integer userID){
		return uPermissionService.getUserPermission(userID);
	}
	
	@ApiIgnore
	@RequestMapping(value = "/setuserpermission",method = RequestMethod.GET)
	public String setUserPermission(@RequestParam(value = "userid",required = false)Integer userID,
									@RequestParam(value = "userpermission",required = false)Integer userpermission){
		return uPermissionService.setUserPermission(userID,userpermission);
	}
	
	@ApiOperation("用户KYC审核")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "用户昵称",required = true),
		@ApiImplicitParam(name = "result",paramType = "query",value = "审核结果，审核通过：result=success, 不通过：result=failed",required = true)
	})
	@RequestMapping(value = "/audituser",method = RequestMethod.GET)
	public String auditUser(@RequestParam(value = "name")String name,
			@RequestParam(value = "result")String result){
		logger.info("请求url\"/audituser\", 参数值: name = " + name + ", result = " + result);
		return uPermissionService.auditUser(name,result);
	}
}
