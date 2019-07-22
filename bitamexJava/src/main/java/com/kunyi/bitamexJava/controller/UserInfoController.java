package com.kunyi.bitamexJava.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.UserInfoService;
import com.sun.prism.impl.BaseMesh.FaceMembers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "/userinfoservice",description = "用户信息Api")
@RestController
@RequestMapping("/userinfoservice")
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiIgnore
	@RequestMapping(value = "/addright", method = RequestMethod.GET)
	public String checkUserInfo(@RequestParam(value = "username")String username){
		return userInfoDao.checkUserInfo(username);
	} 
	
	@ApiIgnore
	@RequestMapping(value = "/modifypassword", method = RequestMethod.GET)
	public String modifyPassword(@RequestParam(value = "username")String username,
								 @RequestParam(value = "newpassword")String newPassword){
		return userInfoDao.modifyUserPsaaword(username, newPassword);
	}
	
	@ApiIgnore
	@RequestMapping(value = "/bindphonenumber", method = RequestMethod.GET)
	public String bindPhoneNumber(@RequestParam("userid")Integer userID,
								  @RequestParam("phone")String phone){
		
		return userInfoDao.bindPhoneNumber(userID, phone);
	}
	
	@ApiIgnore
	@RequestMapping(value = "/unbindphonenumber", method = RequestMethod.GET)
	public String unbindPhoneNumber(@RequestParam("userid")Integer userID){
		
		return userInfoDao.unBindPhoneNumber(userID);
	}
	
	@ApiIgnore
	@RequestMapping(value = "/fetchnumberofinvited", method = RequestMethod.GET)
	public String fetchNumberOfUserInvited(@RequestParam("userid")Integer userID,
										   @RequestParam("invitationcode")String invitationCode){
		return userInfoDao.fetchNumberOfUserInvited(userID, invitationCode);
	}
	
	@ApiOperation("条件查询用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "用户真实姓名",required = false),
		@ApiImplicitParam(name = "phone",paramType = "query",value = "用户手机号",required = false),
		@ApiImplicitParam(name = "startTime",paramType = "query",value = "范围查询的起始时间戳",required = false),
		@ApiImplicitParam(name = "endTime",paramType = "query",value = "范围查询的结束时间戳",required = false)
	})
	@RequestMapping(value = "/queryuserinfo", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String queryUserInfo(@RequestParam(value = "phone",required = false)String phone,
								@RequestParam(value = "name",required = false)String name,
								@RequestParam(value = "startTime",required = false)String startTime,
				   				 @RequestParam(value = "endTime",required = false)String endTime){
		logger.info("请求url\"/queryuserinfo\", 请求参数: phone = " + phone + ", name = " + name + ", startTime = " + startTime + ", endTime = " + endTime);
		return userInfoDao.queryUserInfo(phone, name, startTime,endTime);
	}
	
	@ApiOperation("修改用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "用户昵称",required = false),
		@ApiImplicitParam(name = "area",paramType = "query",value = "用户手机区号",required = false),
		@ApiImplicitParam(name = "phone",paramType = "query",value = "用户手机号",required = false),
		@ApiImplicitParam(name = "password",paramType = "query",value = "用户登录密码",required = false),
		@ApiImplicitParam(name = "loginright",paramType = "query",value = "用户登录权限",required = false)
	})
	@RequestMapping(value = "/modifyuserinfo", method = RequestMethod.GET)
	public String modifyUserInfo(@RequestParam(value = "name",required = false)String nickName,
								 @RequestParam(value = "area",required = false)String area,
								 @RequestParam(value = "phone",required = false)String phone,
								 @RequestParam(value = "password",required = false)String password,
								 @RequestParam(value = "loginright",required = false)String loginright){
		logger.info("请求url\"/modifyuserinfo\", 请求参数: name = " + nickName + ", area = " + area + ", phone = " + phone + ", password = " + password+ ", loginright = " + loginright);
		return userInfoDao.modifyUserInfo(nickName, area, phone, password, loginright);
	}
}
