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
import com.kunyi.bitamexJava.dao.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@Api(value = "/userroleservice",description = "用户角色Api")
@RestController
@RequestMapping("/userroleservice")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "新增用户角色")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "角色名",required = true),
		@ApiImplicitParam(name = "rights",paramType = "query",value = "角色对应的权限集合",required = true)
	})
	@RequestMapping(value = "/addrole",method = RequestMethod.GET)
	public String addRight(@RequestParam(value = "name")String name,
						   @RequestParam(value = "rights")String rights,
						   HttpServletRequest request){
		logger.info("请求url\"/addrole\", 参数值: name = " + name + ", rights = " + rights);
		HttpSession session = request.getSession();
		String founder = (String)session.getAttribute("name");
		return userRoleService.addRole(name,founder,rights);
	}
	
	@ApiOperation(value = "条件查询用户角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "角色名称",required = false),
		@ApiImplicitParam(name = "founder",paramType = "query",value = "角色创建人",required = false),
		@ApiImplicitParam(name = "startTime",paramType = "query",value = "范围查询的起始时间戳",required = false),
		@ApiImplicitParam(name = "endTime",paramType = "query",value = "范围查询的结束时间戳",required = false)
	})
	@RequestMapping(value = "/queryrole",method = RequestMethod.GET)
	public String queryRight(@RequestParam(value = "name",required = false)String name,
						     @RequestParam(value = "founder",required = false)String founder,
						     @RequestParam(value = "startTime",required = false)String startTime,
				   			 @RequestParam(value = "endTime",required = false)String endTime){
		logger.info("请求url\"/queryrole\", 参数值: name = " + name + ", founder = " + founder + ", startTime = " + startTime + ", endTime = " + endTime);
		return userRoleService.queryRole(name,founder,startTime,endTime);
	}
}
