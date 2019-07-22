package com.kunyi.bitamexJava.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.PairInfoService;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.model.PairInfoDetailTable;
import com.kunyi.bitamexJava.model.PairInfoTable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 主要负责接收管理员信息请求
 * 判断参数，处理交给dao层
 *
 * @author GAIA
 * @create 2019-05-22
 */
@CrossOrigin
@RestController
@Api(value="/pairinfoservice",description = "交易对子管理api")
@RequestMapping(value = "/pairinfoservice")
public class PairInfoController {
	@Autowired
	private PairInfoService pairInfoDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String serviceName = "[PairInfoController]";
	
	
	@ApiOperation(value = "查询交易对子")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pair",paramType = "query",value = "交易对子",required = false),
		@ApiImplicitParam(name = "startdate",paramType = "query",value = "起始时间",required = false),
		@ApiImplicitParam(name = "enddate",paramType = "query",value = "结束时间",required = false)
	})
    @RequestMapping(value = "/querypair",method = RequestMethod.POST)
    public String queryPair(@RequestParam(value="pair", required = false)String pair,
    						   @RequestParam(value="startdate", required = false)String startdate,
    						   @RequestParam(value="enddate", required = false)String enddate){
    	logger.info("pair = " + pair + ", startdate = " + startdate + ", enddate = " + enddate);
    	JsonMessage jsonMessage = new JsonMessage();
		List<PairInfoTable> list = pairInfoDao.queryPairs(pair, startdate, enddate);
		if(list == null || list.size() == 0){
			jsonMessage.setCode(250);
			jsonMessage.setMsg("服务器错误");
			logger.info("服务器错误");
		}else {
			jsonMessage.setCode(200);
			jsonMessage.setMsg("查询成功");
			jsonMessage.setData(convertPairInfosToJson(list));
		}
		
        return jsonMessage.toJson();
    }
    
	@ApiOperation(value = "添加交易对子")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pairInfoDetail",value = "交易对子信息详细",paramType = "query",required = true)
	})
    @RequestMapping(value = "/addpair",method = RequestMethod.POST)
    public String addPair(@RequestBody PairInfoDetailTable pairInfoDetail,HttpServletRequest req){
    	JsonMessage jsonMessage = new JsonMessage();
    	if(pairInfoDetail == null){
    		jsonMessage.setCode(250);
			jsonMessage.setMsg("参数异常");
			logger.info("pairInfoDetailTable对象是null");
			return jsonMessage.toJson();
    	}
    	HttpSession session = req.getSession(false);
    	if(session == null)
    	{
    		jsonMessage.setCode(250);
			jsonMessage.setMsg("用户登录失效");
			logger.info("用户登录失效");
			return jsonMessage.toJson();
    	}
    	String username = (String)session.getAttribute("name");
    	logger.info(serviceName + pairInfoDetail.toString());
		int result = pairInfoDao.addPair(pairInfoDetail, username);
		switch(result) {
		case -1:
			jsonMessage.setCode(250);
			jsonMessage.setMsg("参数异常");
			logger.info("参数异常");
			break;
		case -2:
			jsonMessage.setCode(250);
			jsonMessage.setMsg("添加失败，数据库已经存在");
			logger.info("添加失败，数据库已经存在");
			break;
		default:
			jsonMessage.setCode(200);
			jsonMessage.setMsg("添加成功");
			break;	
		}
        return jsonMessage.toJson();
    }
    /**
	 * 将PairInfoTable的链表转化成json字符串用于传输
	 * @param list
	 */
	private  String convertPairInfosToJson(List<PairInfoTable> list) {
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
