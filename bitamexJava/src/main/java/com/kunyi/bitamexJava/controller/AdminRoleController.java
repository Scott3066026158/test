package com.kunyi.bitamexJava.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.kunyi.bitamexJava.dao.AdminRoleService;
import com.kunyi.bitamexJava.model.AdminRoleTable;
import com.kunyi.bitamexJava.model.JsonMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@Api(value = "/adminroleservice",description = "管理员角色信息Api")
@RestController
@RequestMapping("/adminroleservice")
public class AdminRoleController {
	@Autowired
	private AdminRoleService adminRoleDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Integer roleID = 0;
	
	@ApiOperation(value = "新增管理员角色")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "角色名",required = true),
		@ApiImplicitParam(name = "rights",paramType = "query",value = "角色对应的权限集合",required = true)
	})
	@RequestMapping(value = "/addrole",method = RequestMethod.GET)
	public String addRole(@RequestParam(value = "name")String name,
						  @RequestParam(value = "rights")String rights,
						  HttpServletRequest request){
		logger.info("请求url\"/addrole\",请求参数: name = " + name + " , rights = " + rights);
		HttpSession session = request.getSession();
		String founder = (String)session.getAttribute("name");
		AdminRoleTable aRoleTable = new AdminRoleTable();
		aRoleTable.setM_founder(founder);
		aRoleTable.setM_name(name);
//		String[] right = rights.split(",");
//		List<Integer> list = new ArrayList<>();
		if("".equals(name.trim())){
			JsonMessage json = new JsonMessage();
			json.setCode(250);
			json.setMsg("参数错误");
			logger.info("参数错误");
			return json.toJson();
		}
/*		for (String string : right) {
			Integer temp = Integer.parseInt(string);
			if(temp != null){
				list.add(temp);
			}else{
				JsonMessage json = new JsonMessage();
				json.setCode(250);
				json.setMsg("参数错误");
				return json.toJson();
			}
		}*/
		aRoleTable.setM_rights(rights);
		aRoleTable.setM_createTime(System.currentTimeMillis());
		synchronized (roleID) {
			aRoleTable.setM_roleID(roleID++);
		}
		return adminRoleDao.addRole(aRoleTable);
	}
	
	@ApiOperation(value = "条件查询管理员角色信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "角色名称",required = false),
		@ApiImplicitParam(name = "founder",paramType = "query",value = "角色创建人",required = false),
		@ApiImplicitParam(name = "startTime",paramType = "query",value = "范围查询的起始时间戳",required = false),
		@ApiImplicitParam(name = "endTime",paramType = "query",value = "范围查询的结束时间戳",required = false)
	})
	@RequestMapping(value = "/queryrole",method = RequestMethod.GET)
	public String queryRole(@RequestParam(value = "name",required = false)String name,
				 			@RequestParam(value = "founder",required = false)String founder,
				 			@RequestParam(value = "startTime",required = false)String startTime,
			   				@RequestParam(value = "endTime",required = false)String endTime){
		logger.info("请求url\"/queryrole\",请求参数: name = " + name + " , founder = " + founder + " , startTime = " + startTime + " , endTime = " + endTime);
		List<AdminRoleTable> list = adminRoleDao.queryRoles(name, founder, startTime, endTime);
		JsonMessage jMessage = new JsonMessage();
		if(list == null || list.size() == 0){
			jMessage.setCode(250);
			jMessage.setMsg("没有数据");
			logger.info("AdminRoleTable没有数据");
			return jMessage.toJson();
		}
		jMessage.setCode(200);
		jMessage.setMsg("查询成功");
		jMessage.setData(convertAdminRolesToJson(list));
		return jMessage.toJson();
	}
	
	/**
	 * 将查询得到的list数据转成json
	 * @param list
	 * @return
	 */
	private String convertAdminRolesToJson(List<AdminRoleTable> list) {
		if(list == null || list.size() == 0){
			return null;
		}
		int length = list.size();
		StringBuilder json = new StringBuilder();
		//json.append("{\"adminroles\":[");
		for(int i = 0; i < length; i++){
			json.append(list.get(i).ConvertObjectToJson());
			json.append(",");
		}
		if(json.length() != 0){
			json.deleteCharAt(json.length() - 1);
		}
		//json.append("]}");
		return json.toString();
	}
}
