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

import com.kunyi.bitamexJava.dao.PositionInfoService;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.model.PairInfoDetailTable;
import com.kunyi.bitamexJava.model.PairInfoTable;
import com.kunyi.bitamexJava.model.PositionInfoTable;

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
 * @create 2019-05-23
 */
@CrossOrigin
@RestController
@Api(value = "/positioninfoservice",description="资金管理api")
@RequestMapping(value = "/positioninfoservice")
public class PositionInfoController {
	@Autowired
	private PositionInfoService positionInfoDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "查询用户资金")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "phone",value = "手机号码",paramType = "query",required = false),
		@ApiImplicitParam(name = "realname",value = "实名", paramType = "query",required = false),
		@ApiImplicitParam(name = "idcardno",value = "身份证号码",paramType = "query",required = false),
		@ApiImplicitParam(name = "walletaddress",value = "钱包地址",paramType = "query",required = false)
	})
    @RequestMapping(value = "/queryposition",method = RequestMethod.POST)
    public String queryPosition(@RequestParam(value="phone", required = false)String phone,
    						   @RequestParam(value="realname", required = false)String realname,
    						   @RequestParam(value="idcardno", required = false)String idcardno,
    						   @RequestParam(value="walletaddress", required = false)String walletaddress){
    	JsonMessage jsonMessage = new JsonMessage();
    	logger.info("phone = " + phone + ", realname = " + realname + ", idcardno = " + idcardno
    			+ ",walletaddress=" + walletaddress);
		List<PositionInfoTable> list = positionInfoDao.queryPositions(phone, realname, idcardno, walletaddress);
		if(list == null || list.size() == 0){
			jsonMessage.setCode(250);
			jsonMessage.setMsg("服务器错误");
			logger.info("服务器错误");
			return jsonMessage.toJson();
		}
		jsonMessage.setCode(200);
		jsonMessage.setMsg("查询成功");
		jsonMessage.setData(convertPositionInfoToJson(list));
        return jsonMessage.toJson();
    }
    
    /**
     * 添加用户资金记录
     * 
     */
    @RequestMapping(value = "/addposition",method = RequestMethod.POST)
    public String addPosition(@RequestBody PositionInfoTable positionInfo){
    	logger.info(positionInfo.toString());
    	JsonMessage jsonMessage = new JsonMessage();
		int result = positionInfoDao.addPosition(positionInfo);
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
	 * 将PairInfoTable的链表转化成json字符串用于传输
	 * @param list
	 */
	private  String convertPositionInfoToJson(List<PositionInfoTable> list) {
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
