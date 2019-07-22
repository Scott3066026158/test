package com.kunyi.bitamexJava.controller;

import com.kunyi.bitamexJava.dao.TraderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 成交表
 *
 * @author GAIA
 * @create 2019-06-04-10:09
 */
@RestController
@Api(value = "/traderInfo",description = "用户成交订单信息APi")
@RequestMapping("/traderInfo")
public class TraderInfoController {
    @Autowired
    private TraderInfoService traderInfoService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value = "条件查询用户成交订单数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "币种,例AE/BIC",paramType = "query",required = false,dataType = "String"),
            @ApiImplicitParam(name = "traderId",value = "用户交易ID，例PA004DLF",paramType = "query",required = false,dataType = "String"),
            @ApiImplicitParam(name = "direction",value = "买卖方向,买66，卖83",paramType = "query",required = false,dataType = "String")
    })
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String query(@RequestParam(value = "code",required = false)String code,
                        @RequestParam(value = "traderId",required = false)String traderId,
                        @RequestParam(value = "direction",required = false)Integer direction){
    	logger.info("code = " + code + ", traderId = " + traderId + ", direction = " + direction);
        return traderInfoService.queryAll(traderId,code,direction);
    }
}
