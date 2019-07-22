package com.kunyi.bitamexJava.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kunyi.bitamexJava.dao.CoinInfoService;
import com.kunyi.bitamexJava.model.CoinInfoDetailTable;
import com.kunyi.bitamexJava.model.CoinInfoTable;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.service.AdminInfoServiceImpl;
import com.kunyi.bitamexJava.util.StringUtil;

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
 * @create 2019-05-21
 */
@CrossOrigin
@RestController
@Api(value="/coininfoservice",description = "币种管理api")
@RequestMapping(value = "/coininfoservice")
public class CoinInfoController {
	@Autowired
	private CoinInfoService coinInfoDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "查询币种")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "coin",paramType = "query",value = "币种名",required = false),
		@ApiImplicitParam(name = "startdate",paramType = "query",value = "开始时间",required = false),
		@ApiImplicitParam(name = "enddate",paramType = "query",value = "结束时间",required = false)
	})
    @RequestMapping(value = "/querycoin",method = RequestMethod.POST)
    public String queryCoin(@RequestParam(value="coin", required = false)String coin,
    						   @RequestParam(value="startdate", required = false)String startDate,
    						   @RequestParam(value="enddate", required = false)String endDate){
		logger.info("coin = " + coin + ", startdate = " + startDate + ", enddate = " + endDate);
    	JsonMessage jsonMessage = new JsonMessage();
		List<CoinInfoTable> list = coinInfoDao.queryCoins(coin, startDate, endDate);
		if(list == null || list.size() == 0){
			jsonMessage.setCode(250);
			jsonMessage.setMsg("服务器异常");
			logger.info("没有数据");
		}else {
			jsonMessage.setCode(200);
			jsonMessage.setMsg("查询成功");
			jsonMessage.setData(convertCoinInfosToJson(list));
		}
        return jsonMessage.toJson();
    }
	
	
	@ApiOperation(value = "添加币种")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "coinInfoDetail",paramType = "query",value = "币种信息详细",required = true)
	})
    @RequestMapping(value = "/addcoin",method = RequestMethod.POST)
    public String addCoin(@RequestBody CoinInfoDetailTable coinInfoDetail)
    {
    	JsonMessage jsonMessage = new JsonMessage();
    	if(coinInfoDetail == null) {
    		jsonMessage.setCode(250);
			jsonMessage.setMsg("参数异常");
			logger.info("CoinInfoDetailTable对象是null");
			return jsonMessage.toJson();
    	}
    	logger.info(coinInfoDetail.toString());
    	
		int result = coinInfoDao.addCoin(coinInfoDetail);
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
				break;	
		}
        return jsonMessage.toJson();
    }
    
	@ApiOperation(value = "更新币种的可用状态")
	@ApiImplicitParam(name = "coinInfoDetail",paramType = "query",value = "币种信息详细",required = true)
    @RequestMapping(value = "/updatecoinstatus",method = RequestMethod.POST)
    public String updateCoinStatus(@RequestParam(value="coin")String coinName,
    							   @RequestParam(value="status")String status)
    {
		logger.info("coin = " + coinName + ", status = " + status);
    	JsonMessage jsonMessage = new JsonMessage();
    	if(StringUtil.isEmpty(coinName))
    	{
    		jsonMessage.setCode(250);
			jsonMessage.setMsg("参数异常");
			logger.info("参数异常");
			return jsonMessage.toJson();
    	}
		int result = coinInfoDao.updateCoinStatus(coinName, status);
		switch(result) {
			case -1:
				jsonMessage.setCode(250);
				jsonMessage.setMsg("提币状态异常");
				logger.info("提币状态异常");
				break;
			case -2:
				jsonMessage.setCode(250);
				jsonMessage.setMsg("数据库不存在");
				logger.info("数据库不存在");
				break;
			default:
				jsonMessage.setCode(200);
				jsonMessage.setMsg("添加成功"); 
				break;
		}
        return jsonMessage.toJson();
    }
    
    /**
	 * 将CoinInfoTable的链表转化成json字符串用于传输
	 * @param list
	 */
	private  String convertCoinInfosToJson(List<CoinInfoTable> list) {
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
