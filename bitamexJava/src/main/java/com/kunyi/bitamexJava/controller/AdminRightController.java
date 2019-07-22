package com.kunyi.bitamexJava.controller;

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

import com.kunyi.bitamexJava.dao.AdminRightService;
import com.kunyi.bitamexJava.model.AdminRightTable;
import com.kunyi.bitamexJava.model.JsonMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@Api(value = "/adminrightservice",description = "管理员权限Api")
@RestController
@RequestMapping("/adminrightservice")
public class AdminRightController {
	@Autowired
	private AdminRightService adminRightDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "新增管理员权限")
	@ApiImplicitParam(name = "name",paramType = "query",value = "权限名",required = true)
	@RequestMapping(value = "/addright",method = RequestMethod.GET)
	public String addRight(@RequestParam(value = "name")String name,HttpServletRequest request){
		HttpSession session = request.getSession();
		String founder = (String)session.getAttribute("name");
		logger.info("请求url\"/addright\",请求参数: name = " + name + " , " +  "founder = " + founder);
		AdminRightTable aRightTable = new AdminRightTable();
		aRightTable.setM_name(name);
		aRightTable.setM_founder(founder);
		aRightTable.setM_createTime(System.currentTimeMillis());
		boolean ret = adminRightDao.addRight(aRightTable);
		JsonMessage jMessage = new JsonMessage();
		jMessage.setCode(ret ? 200 : 250);
		jMessage.setMsg(ret ? "新增成功" : "新增失败");
		return jMessage.toJson();
	}
	
	@ApiOperation(value = "条件查询管理员权限信息")
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
		logger.info("请求url\"/queryright\",请求参数: name = " + name + " , founder = " + founder + " , startTime = " + startTime + " , endTime = " + endTime);
		List<AdminRightTable> list = adminRightDao.queryRight(name, founder, startTime,endTime);
		JsonMessage jMessage = new JsonMessage();
		if(list == null || list.size() == 0){
			jMessage.setCode(250);
			jMessage.setMsg("没有数据");
			logger.info("AdminRightTable没有数据");
			return jMessage.toJson();
		}
		jMessage.setCode(200);
		jMessage.setMsg("查询成功");
		jMessage.setData(convertAdminRightsToJson(list));
		return jMessage.toJson();
	}
	
	/**
	 * 将AdminInfoTable的链表转化成json字符串用于传输
	 * @param list
	 */
	private  String convertAdminRightsToJson(List<AdminRightTable> list) {
		if(list == null || list.size() == 0){
			return null;
		}
		int length = list.size();
		StringBuilder json = new StringBuilder();
		//json.append("{\"adminrights\":[");
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
