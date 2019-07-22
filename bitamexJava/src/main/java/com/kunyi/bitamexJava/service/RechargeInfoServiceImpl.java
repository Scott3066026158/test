package com.kunyi.bitamexJava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.RechargeInfoService;
import com.kunyi.bitamexJava.model.RechargeInfoTable;
import com.kunyi.bitamexJava.model.WithdrawInfoTable;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class RechargeInfoServiceImpl implements RechargeInfoService{

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<RechargeInfoTable> queryRecharges(String phone, String realName, String idCardNo, String walletAddress,
			String coin, String startDate, String endDate) {
		try{
			Query query = new Query();
			if (!(StringUtil.isEmpty(phone))) {
				query.addCriteria(Criteria.where("m_phone").is(phone));
			}
			if (!(StringUtil.isEmpty(realName))) {
				query.addCriteria(Criteria.where("m_realName").is(realName));
			}
			if (!(StringUtil.isEmpty(idCardNo))) {
				query.addCriteria(Criteria.where("m_idCardNo").is(idCardNo));
			}
			if (!(StringUtil.isEmpty(walletAddress))) {
				query.addCriteria(Criteria.where("m_walletAddress").is(walletAddress));
			}
			if(!(StringUtil.isEmpty(coin))){
				query.addCriteria(Criteria.where("m_coin").is(coin));
			}
			boolean startFlag = !("NaN".equals(startDate) || StringUtil.isEmpty(startDate));
			boolean endFlag = !("NaN".equals(endDate) || StringUtil.isEmpty(endDate));
			if(startFlag && endFlag){
				query.addCriteria(Criteria.where("m_paymentDate").gte(Long.parseLong(startDate)).lte(Long.parseLong(endDate)));
			}else if(startFlag){
    			query.addCriteria(Criteria.where("m_paymentDate").gte(Long.parseLong(startDate)));
    		}else if(endFlag){
    			query.addCriteria(Criteria.where("m_paymentDate").lte(Long.parseLong(endDate)));
    		}
			List<RechargeInfoTable> list = mongoTemplate.find(query, RechargeInfoTable.class, "RechargeInfoTable");
    		return list;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
	}

	@Override
	public int addRecharge(RechargeInfoTable rechargeInfo) {
		try{
    		mongoTemplate.insert(rechargeInfo, "RechargeInfoTable");
    		return 1;
    	}catch(Exception e){
    		e.printStackTrace();
    		return -1;
    	}
	}
}
	