package com.kunyi.bitamexJava.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.RechargeInfoService;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.model.RechargeInfoTable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@Api(value = "/rechargeinfoservice",description = "用户充值信息api")
@RequestMapping(value = "/rechargeinfoservice")
public class RechargeInfoController {
	@Autowired
	private RechargeInfoService rechargeInfoDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "查询用户充值信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "phone",value = "手机号码",paramType = "query",required = false),
		@ApiImplicitParam(name = "realname",value = "实名", paramType = "query",required = false),
		@ApiImplicitParam(name = "idcardno",value = "身份证号码",paramType = "query",required = false),
		@ApiImplicitParam(name = "walletaddress",value = "钱包地址",paramType = "query",required = false),
		@ApiImplicitParam(name = "coin",value = "币种", paramType = "query",required = false),
		@ApiImplicitParam(name = "startDate",value = "起始时间",paramType = "query",required = false),
		@ApiImplicitParam(name = "endDate",value = "结束时间",paramType = "query",required = false)
	})
    @RequestMapping(value = "/queryrecharge",method = RequestMethod.POST)
    public String queryWithdraw(@RequestParam(value="phone", required = false)String phone,
    						   @RequestParam(value="realName", required = false)String realName,
    						   @RequestParam(value="idCardNo", required = false)String idCardNo,
    						   @RequestParam(value="walletAddress", required = false)String walletAddress,
    						   @RequestParam(value="coin", required = false)String coin,
    						   @RequestParam(value="startDate", required = false)String startDate,
    						   @RequestParam(value="endDate", required = false)String endDate){
    	logger.info("phone = " + phone + ", realname = " + realName + ", idcardno = " + idCardNo
    			+ ", walletaddress = " + walletAddress +", coin = " + coin + ", startdate = " + startDate + ", enddate = " + endDate);
    	JsonMessage jsonMessage = new JsonMessage();
		List<RechargeInfoTable> list = rechargeInfoDao.queryRecharges(phone, realName, idCardNo, walletAddress, coin, startDate, endDate);
		if(list == null || list.size() == 0){
			jsonMessage.setCode(250);
			jsonMessage.setMsg("服务器异常");
			logger.info("服务器异常");
		}else {
			jsonMessage.setCode(200);
			jsonMessage.setMsg("查询成功");
			jsonMessage.setData(convertRechargeInfoToJson(list));
		}

		logger.info(jsonMessage.toJson());
        return jsonMessage.toJson();
    }
    
    @RequestMapping(value = "/addrecharge",method = RequestMethod.POST)
    public String addRecharge(@RequestBody RechargeInfoTable rechargeInfo){
    	JsonMessage jsonMessage = new JsonMessage();
    	if(rechargeInfo == null){
    		jsonMessage.setCode(250);
    		jsonMessage.setMsg("参数异常");
    		logger.info("addRecharge参数异常");
    	}
		int result  = rechargeInfoDao.addRecharge(rechargeInfo);
		switch(result) {
		case -1:
			jsonMessage.setCode(250);
			jsonMessage.setMsg("服务器异常");
			logger.info("服务器异常");
			break;
		default:
			jsonMessage.setCode(200);
			jsonMessage.setMsg("添加成功");
			break;
		}
        return jsonMessage.toJson();
    }
    
    /**
	 * 将WithdrawInfo的链表转化成json字符串用于传输
	 * @param list
	 */
	private  String convertRechargeInfoToJson(List<RechargeInfoTable> list) {
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