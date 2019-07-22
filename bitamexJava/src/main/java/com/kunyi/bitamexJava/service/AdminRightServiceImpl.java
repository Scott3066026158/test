package com.kunyi.bitamexJava.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.controller.AdminRecordController;
import com.kunyi.bitamexJava.dao.AdminRightService;
import com.kunyi.bitamexJava.model.AdminRightTable;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class AdminRightServiceImpl implements AdminRightService {
	@Autowired
	private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean addRight(AdminRightTable aRightTable) {
		if(aRightTable.getM_name() == null || aRightTable.getM_founder() == null){
			logger.info("参数为空:name = " + aRightTable.getM_name() + " , founder = " + aRightTable.getM_founder());
			return false;
		}
		Query query = new Query(Criteria.where("m_name").is(aRightTable.getM_name()));
		AdminRightTable aTable = mongoTemplate.findOne(query, AdminRightTable.class,"AdminRightTable");
		if(aTable != null){
			logger.info("管理员权限新增失败 :" + aRightTable.getM_name() + "已存在");
			return false;
		}
		mongoTemplate.save(aRightTable,"AdminRightTable");
		return true;
	}

	@Override
	public List<AdminRightTable> queryRight(String name, String founder, String startTime,String endTime) {
		Query query = new Query();
		if(!StringUtil.isEmpty(name)){
			query.addCriteria(Criteria.where("m_name").is(name));
		}
		if(!StringUtil.isEmpty(founder)){
			query.addCriteria(Criteria.where("m_founder").is(founder));
		}
		if(!StringUtil.isTimeEmpty(startTime) && StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_createTime").gte(Long.parseLong(startTime)));
    	}else if(StringUtil.isTimeEmpty(startTime) && !StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_createTime").lte(Long.parseLong(endTime)));
    	}else if(!StringUtil.isTimeEmpty(startTime) && !StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_createTime").lte(Long.parseLong(endTime)).
    				andOperator(Criteria.where("m_createTime").gte(Long.parseLong(startTime))));
    	}
		query.with(new Sort(Sort.Direction.DESC,"m_createTime"));
		List<AdminRightTable> list = mongoTemplate.find(query, AdminRightTable.class,"AdminRightTable");
		return list;
	}
}
