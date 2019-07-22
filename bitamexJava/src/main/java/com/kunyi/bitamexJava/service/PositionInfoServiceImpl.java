package com.kunyi.bitamexJava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.PositionInfoService;
import com.kunyi.bitamexJava.model.CoinInfoTable;
import com.kunyi.bitamexJava.model.PairInfoTable;
import com.kunyi.bitamexJava.model.PositionInfoTable;
import com.kunyi.bitamexJava.util.StringUtil;
@Service
public class PositionInfoServiceImpl implements PositionInfoService{
	
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * 查询资金
	 */
	public List<PositionInfoTable> queryPositions(String phone, String realName, String idCardNo,
			String walletAddress) {
		try {
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
			List<PositionInfoTable> list = mongoTemplate.find(query, PositionInfoTable.class, "PositionInfoTable");
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 添加用户资金记录
	 */
	@Override
	public int addPosition(PositionInfoTable positionInfo){
		try{
    		mongoTemplate.insert(positionInfo, "PositionInfoTable");
    		return 1;
    	}catch(Exception e){
    		e.printStackTrace();
    		return -1;
    	}
	}
}
