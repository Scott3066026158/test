package com.kunyi.bitamexJava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.CoinInfoService;
import com.kunyi.bitamexJava.model.CoinInfoDetailTable;
import com.kunyi.bitamexJava.model.CoinInfoTable;
import com.kunyi.bitamexJava.util.StringUtil;
import com.mongodb.client.result.UpdateResult;

@Service
public class CoinInfoServiceImpl implements CoinInfoService{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	/**
	 * 查询所有币种
	 */
    @Override
	public List<CoinInfoTable> queryCoins(String coin, String startDate, String endDate)
	{
    	try{
    		Query query = new Query();
    		if(!(StringUtil.isEmpty(coin))){
    			query.addCriteria(Criteria.where("m_simpleName").is(coin));
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
    		List<CoinInfoTable> list = mongoTemplate.find(query, CoinInfoTable.class, "CoinInfoTable");
    		return list;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
	}
    
    /**
     * 查询一个币种信息
     */
    private CoinInfoTable queryCoinInfo(String coinName)
    {
    	Query query = new Query();
		query.addCriteria(Criteria.where("m_simpleName").is(coinName));
		CoinInfoTable coinInfo = mongoTemplate.findOne(query, CoinInfoTable.class, "CoinInfoTable");
		return coinInfo;
    }
    
    /**
     * 查询一个币种详细信息
     */
    private CoinInfoDetailTable queryCoinInfoDetail(String coinName)
    {
    	Query query = new Query();
		query.addCriteria(Criteria.where("m_simpleName").is(coinName));
		CoinInfoDetailTable coinInfoDetail = mongoTemplate.findOne(query, CoinInfoDetailTable.class, "CoinInfoDetailTable");
		return coinInfoDetail;
    }
    
    /**
	 * 添加一个币种
	 */
    @Override
	public int addCoin(CoinInfoDetailTable coinInfoDetail)
	{
    	try{
    		boolean flag = coinInfoDetail.isError();
    		if(!flag) return -1;
    		CoinInfoTable coinInfo = coinInfoDetail.createCoinInfoTale();
    		if(queryCoinInfoDetail(coinInfoDetail.getM_simpleName()) != null){
    			return -2;
    		}
    		if(queryCoinInfo(coinInfo.getM_simpleName()) != null){
    			return -2;
    		}
    		mongoTemplate.insert(coinInfoDetail, "CoinInfoDetailTable");
    		mongoTemplate.insert(coinInfo, "CoinInfoTable");
    		return 1;
    	}catch(Exception e){
    		e.printStackTrace();
    		return -1;
    	}
	}
    
    /**
	 * 更新币种提币状态
	 */
    @Override
	public int updateCoinStatus(String coinName, String status)
	{
    	try {
    		int state = Integer.parseInt(status);
    		Query query = new Query();
    		query.addCriteria(Criteria.where("m_simpleName").is(coinName));
    		Update updateCoinInfo = new Update();
    		updateCoinInfo.set("m_status", state);
    		UpdateResult result = mongoTemplate.updateFirst(query, updateCoinInfo, CoinInfoTable.class, "CoinInfoTable");
    		if(result.getMatchedCount() == 0){
    			return -2;
    		}
    		return 1;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
	}
    
    
}
