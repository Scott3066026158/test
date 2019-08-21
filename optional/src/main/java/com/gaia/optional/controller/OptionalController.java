package com.gaia.optional.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gaia.optional.entity.JsonMessage;
import com.gaia.optional.service.OptionalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Api(value = "/optional", description = "用户自选股管理Api")
@RequestMapping(value = "/optional")
public class OptionalController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OptionalService optService;

	/**
	 * add optional stock
	 * @param username
	 * @param code
	 * @return 用户所有的自选股
	 */
	@ApiOperation(value = "添加自选股")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", paramType = "add", value = "用户名"),
			@ApiImplicitParam(name = "code", paramType = "add", value = "交易对子") })
	@ApiResponses({ @ApiResponse(code = 200, message = "新增成功,并且返回用户所有的交易对子"),
			@ApiResponse(code = 0, message = "添加失败", response = JsonMessage.class) })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ResponseBody
	public String add(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "code", required = true) String code) {
		logger.info("addReqParam:username:" + username + ",code:" + code);
		JsonMessage resp = new JsonMessage();
		if (isNull(username, code)) {
			resp.setCode("0");
			resp.setData("");
			resp.setMsg("params error");
		} else {
			List<String> result = optService.add(username, code);
			if (result != null) {
				resp.setCode("200");
				resp.setData(result);
				resp.setMsg("ok");
			} else {
				resp.setCode("0");
				resp.setData("");
				resp.setMsg("add error");
			}
		}
		logger.info("addRespParam:" + JSON.toJSONString(resp));
		return JSON.toJSONString(resp);
	}
	
	@ApiOperation(value = "添加多个自选股")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", paramType = "addlist", value = "用户名"),
			@ApiImplicitParam(name = "codes", paramType = "addlist", value = "多个交易对子") })
	@ApiResponses({ @ApiResponse(code = 200, message = "新增成功,并且返回用户所有的交易对子"),
			@ApiResponse(code = 0, message = "添加失败", response = JsonMessage.class) })
	@RequestMapping(value = "/addlist", method = RequestMethod.GET)
	@ResponseBody
	public String addList(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "codes", required = true) String codes) {

		JsonMessage resp = new JsonMessage();
		if (isNull(username, codes)) {
			resp.setCode("0");
			resp.setData("");
			resp.setMsg("params error");
			return JSON.toJSONString(resp);
		}
		
		String[] codeArray = codes.split(",");
		for (String code : codeArray) {
			if(isNull(code)) {
				resp.setCode("0");
				resp.setData("");
				resp.setMsg("params error");
				return JSON.toJSONString(resp);
			}
		}
		
		List<String> result = optService.addList(username, codeArray);
		if (result != null) {
			resp.setCode("200");
			resp.setData(result);
			resp.setMsg("ok");
		} else {
			resp.setCode("0");
			resp.setData("");
			resp.setMsg("add error");
		}
		
		return JSON.toJSONString(resp);
	}
	
	@ApiOperation(value = "删除多个自选股")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", paramType = "dellist", value = "用户名"),
			@ApiImplicitParam(name = "codes", paramType = "dellist", value = "多个交易对子") })
	@ApiResponses({ @ApiResponse(code = 200, message = "新增成功,并且返回用户所有的交易对子"),
			@ApiResponse(code = 0, message = "添加失败", response = JsonMessage.class) })
	@RequestMapping(value = "/dellist", method = RequestMethod.GET)
	@ResponseBody
	public String delList(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "codes", required = true) String codes) {

		JsonMessage resp = new JsonMessage();
		if (isNull(username, codes)) {
			resp.setCode("0");
			resp.setData("");
			resp.setMsg("params error");
			return JSON.toJSONString(resp);
		}
		
		String[] codeArray = codes.split(",");
		for (String code : codeArray) {
			if(isNull(code)) {
				resp.setCode("0");
				resp.setData("");
				resp.setMsg("params error");
				return JSON.toJSONString(resp);
			}
		}
		
		List<String> result = optService.delList(username, codeArray);
		if (result != null) {
			resp.setCode("200");
			resp.setData(result);
			resp.setMsg("ok");
		} else {
			resp.setCode("0");
			resp.setData("");
			resp.setMsg("add error");
		}
		
		return JSON.toJSONString(resp);
	}

	/**
	 * 
	 * @param msg username 用户名
	 * @return 用户所有的自选股
	 */
	@ApiOperation(value = "查询自选股")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", paramType = "query", value = "用户名") })
	@ApiResponses({ @ApiResponse(code = 200, message = "查询成功,并且返回用户所有的交易对子"),
			@ApiResponse(code = 0, message = "查询失败", response = JsonMessage.class) })
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(@RequestParam(value = "username", required = true) String username) {
		logger.info("queryReqParam:username:" + username);
		JsonMessage resp = new JsonMessage();
		if (isNull(username)) {
			resp.setCode("0");
			resp.setData("");
			resp.setMsg("params error");
		} else {
			List<String> list = optService.query(username);
			resp.setCode("200");
			resp.setData(list);
			resp.setMsg("ok");
		}
		logger.info("queryRespParam:" + JSON.toJSONString(resp));
		return JSON.toJSONString(resp);
	}

	/**
	 * 
	 * @param msg username + code 用户名 + 交易对子
	 * @return 用户所有的自选股
	 */
	@ApiOperation(value = "删除自选股")
	@ApiImplicitParams({ @ApiImplicitParam(name = "username", paramType = "delete", value = "用户名"),
			@ApiImplicitParam(name = "code", paramType = "delete", value = "交易对子") })
	@ApiResponses({ @ApiResponse(code = 200, message = "删除成功,并且返回用户所有的交易对子"),
			@ApiResponse(code = 0, message = "删除失败", response = JsonMessage.class) })
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "code", required = true) String code) {
		logger.info("deleteReqParam:username:" + username + ",code:" + code);
		JsonMessage resp = new JsonMessage();
		if (isNull(username, code)) {
			resp.setCode("0");
			resp.setData("");
			resp.setMsg("params error");
		} else {
			List<String> result = optService.delete(username, code);
			if (result != null) {
				resp.setCode("200");
				resp.setData(result);
				resp.setMsg("ok");
			} else {
				resp.setCode("0");
				resp.setData("");
				resp.setMsg("delete error");
			}
		}
		logger.info("delRespParam:" + JSON.toJSONString(resp));
		return JSON.toJSONString(resp);
	}

	public boolean isNull(String... params) {
		for (String param : params) {
			if (param == null || param.equals("")) {
				return true;
			}
		}
		return false;
	}
}
