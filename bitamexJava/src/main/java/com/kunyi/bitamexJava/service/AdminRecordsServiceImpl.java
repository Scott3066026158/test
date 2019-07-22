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

import com.kunyi.bitamexJava.dao.AdminRecordsService;
import com.kunyi.bitamexJava.model.AdminRecordsTable;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class AdminRecordsServiceImpl implements AdminRecordsService{
	@Autowired
	private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean addAdminRecords(AdminRecordsTable aRecordsTable) {
		if(aRecordsTable.getM_name() == null || aRecordsTable.getM_roleID() == null 
				|| aRecordsTable.getM_operation() == null){
			logger.info("新增操作记录----->请求参数错误");
			return false;
		}
		mongoTemplate.save(aRecordsTable,"AdminRecordsTable");
		return true;
	}

	@Override
	public List<AdminRecordsTable> queryAdminRecords(String name, Integer roleID,String startTime,String endTime) {
		Query query = new Query();
		if(!StringUtil.isEmpty(name)){
			query.addCriteria(Criteria.where("m_name").is(name));
		}
		if(!StringUtil.isEmpty(roleID)){
			query.addCriteria(Criteria.where("m_roleID").is(roleID));
		}
		
		if(!StringUtil.isTimeEmpty(startTime) && StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_operateTime").gte(Long.parseLong(startTime)));
    	}else if(StringUtil.isTimeEmpty(startTime) && !StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_operateTime").lte(Long.parseLong(endTime)));
    	}else if(!StringUtil.isTimeEmpty(startTime) && !StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_operateTime").lte(Long.parseLong(endTime)).
    				andOperator(Criteria.where("m_operateTime").gte(Long.parseLong(startTime))));
    	}
		query.with(new Sort(Sort.Direction.DESC,"m_operateTime"));
		List<AdminRecordsTable> list = mongoTemplate.find(query, AdminRecordsTable.class, "AdminRecordsTable");
		return list;
	}

}
