package com.kunyi.bitamexJava.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.UserRightsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@Api(value = "/userrightservice",description = "用户权限服务Api")
@RestController
@RequestMapping("/userrightservice")
public class UserRightsController {
	@Autowired
	private UserRightsService userRightsService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "新增用户权限")
	@ApiImplicitParam(name = "name",paramType = "query",value = "权限名",required = true)
	@RequestMapping(value = "/addright",method = RequestMethod.GET)
	public String addRight(@RequestParam(value = "name")String name,
						   HttpServletRequest request){
		logger.info("请求url\"/addright\", 参数值: name = " + name);
		HttpSession session = request.getSession();
		String founder = (String)session.getAttribute("name");
		return userRightsService.addRight(name,founder);
	}
	
	@ApiOperation(value = "条件查询用户权限信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "权限名称",required = false),
		@ApiImplicitParam(name = "founder",paramType = "query",value = "权限创建人",required = false),
		@ApiImplicitParam(name = "startTime",paramType = "query",value = "范围查询的起始时间戳",required = false),
		@ApiImplicitParam(name = "endTime",paramType = "query",value = "范围查询的结束时间戳",required = false)
	})
	@RequestMapping(value = "/queryright",method = RequestMethod.GET)
	public String queryRight(@RequestParam(value = "name",required = false)String name,
						     @RequestParam(value = "founder",required = false)String founder,
						     @RequestParam(value = "startTime",required = false)String startTime,
				   			 @RequestParam(value = "endTime",required = false)String endTime){
		logger.info("请求url\"/queryright\", 参数值: name = " + name + ", founder = " + founder + ", startTime = " + startTime + ", endTime = " + endTime);
		return userRightsService.queryRight(name,founder,startTime,endTime);
	}
}
