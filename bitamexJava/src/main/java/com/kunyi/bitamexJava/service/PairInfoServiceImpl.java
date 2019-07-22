package com.kunyi.bitamexJava.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.PairInfoService;
import com.kunyi.bitamexJava.model.CoinInfoTable;
import com.kunyi.bitamexJava.model.PairInfoDetailTable;
import com.kunyi.bitamexJava.model.PairInfoTable;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class PairInfoServiceImpl implements PairInfoService{
	@Autowired
	private MongoTemplate mongoTemplate;
	/**
	 * 查询所有币种
	 */
	@Override
	public List<PairInfoTable> queryPairs(String pair, String startDate, String endDate)
	{
		try{
    		Query query = new Query();
    		if(!(StringUtil.isEmpty(pair))){
    			query.addCriteria(Criteria.where("m_tradePair").is(pair));
    		}
    		boolean startFlag = !("NaN".equals(startDate) || StringUtil.isEmpty(startDate));
			boolean endFlag = !("NaN".equals(endDate) || StringUtil.isEmpty(endDate));
			if(startFlag && endFlag){
				query.addCriteria(Criteria.where("m_createTime").gte(Long.parseLong(startDate)).lte(Long.parseLong(endDate)));
			}else if(startFlag){
    			query.addCriteria(Criteria.where("m_createTime").gte(Long.parseLong(startDate)));
    		}else if(endFlag){
    			query.addCriteria(Criteria.where("m_createTime").lte(Long.parseLong(endDate)));
    		}
    		query.with(new Sort(Sort.Direction.DESC,   "m_createTime"));
    		List<PairInfoTable> list = mongoTemplate.find(query, PairInfoTable.class, "PairInfoTable");
    		return list;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
	}
	
	/**
	 * 查询交易对子详细信息
	 * @return
	 */
	private PairInfoDetailTable queryPairInfoDetail(String market, String coin)
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("m_market").is(market));
		query.addCriteria(Criteria.where("m_coin").is(coin));
		PairInfoDetailTable pairInfoDetail = mongoTemplate.findOne(query, PairInfoDetailTable.class, "PairInfoDetailTable");
		return pairInfoDetail;
		
	}
	
	/**
	 * 查询交易对子详细信息
	 * @return
	 */
	private PairInfoTable queryPairInfo(String tradePair)
	{
		Query query = new Query();
		query.addCriteria(Criteria.where("m_tradePair").is(tradePair));
		PairInfoTable pairInfo = mongoTemplate.findOne(query, PairInfoTable.class, "PairInfoTable");
		return pairInfo;
	}
	
	@Override
	public int addPair(PairInfoDetailTable pairInfoDetail, String username)
	{
		try{
    		boolean flag = pairInfoDetail.isError();
    		if(!flag) return -1;
    		PairInfoTable pairInfo = pairInfoDetail.createPairInfoTable(username);
    		if(queryPairInfoDetail(pairInfoDetail.getM_market(), pairInfoDetail.getM_coin()) != null){
    			return -2;
    		}
    		if(queryPairInfo(pairInfo.getM_tradePair()) != null){
    			return -2;
    		}
    		mongoTemplate.insert(pairInfoDetail, "PairInfoDetailTable");
    		mongoTemplate.insert(pairInfo, "PairInfoTable");
    		return 1;
    	}catch(Exception e){
    		e.printStackTrace();
    		return -1;
    	}
	}
}
