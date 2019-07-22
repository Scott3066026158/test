package com.kunyi.bitamexJava.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.AdminRecordsService;
import com.kunyi.bitamexJava.model.AdminRecordsTable;
import com.kunyi.bitamexJava.model.JsonMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@Api(value = "/adminrecordservice",description = "管理员操作记录Api")
@RestController
@RequestMapping(value = "/adminrecordservice")
public class AdminRecordController {
	@Autowired
	private AdminRecordsService adminRecordsDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ApiOperation(value = "条件查询管理员操作记录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "管理员姓名",required = false),
		@ApiImplicitParam(name = "roleid",paramType = "query",value = "管理员角色",required = false),
		@ApiImplicitParam(name = "startTime",paramType = "query",value = "范围查询的起始时间戳",required = false),
		@ApiImplicitParam(name = "endTime",paramType = "query",value = "范围查询的结束时间戳",required = false)
	})
	@RequestMapping(value = "/queryadminrecords",method = RequestMethod.GET)
	public String queryAdminRecords(@RequestParam(value = "name",required = false)String name,
			  						@RequestParam(value = "roleid",required = false)Integer roleID,
			  						@RequestParam(value = "startTime",required = false)String startTime,
		    						@RequestParam(value = "endTime",required = false)String endTime){
		logger.info("请求url:\"/queryadminrecords\",请求参数: name = " + name + " , " + "roleid = " + roleID + " , " + "startTime = " + startTime + " , " + "endTime = " + endTime);
		JsonMessage jsonMessage = new JsonMessage();
		List<AdminRecordsTable> list = adminRecordsDao.queryAdminRecords(name, roleID,startTime,endTime);
		if(list == null || list.size() == 0){
			jsonMessage.setCode(250);
			jsonMessage.setMsg("没有数据");
			logger.info("没有操作数据");
			return jsonMessage.toJson();
		}
		jsonMessage.setCode(200);
		jsonMessage.setMsg("查询成功");
		jsonMessage.setData(convertAdminRecordListToJson(list));
		return jsonMessage.toJson();
	}
	
	/**
	 * 将查询到的数据转化成json字符串
	 * @param list
	 * @return
	 */
	private String convertAdminRecordListToJson(List<AdminRecordsTable> list){
		if(list == null || list.size() == 0){
			return null;
		}
		int length = list.size();
		StringBuilder json = new StringBuilder();
		//json.append("{\"adminrecords\":[");
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
