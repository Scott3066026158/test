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

import com.kunyi.bitamexJava.dao.AdminRoleService;
import com.kunyi.bitamexJava.model.AdminRoleTable;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
	@Autowired
	private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String addRole(AdminRoleTable aRoleTable) {
		JsonMessage jMessage = new JsonMessage();
		if(aRoleTable.getM_founder() == null || aRoleTable.getM_name() == null 
				|| aRoleTable.getM_rights() == null){
			jMessage.setCode(250);
			jMessage.setMsg("参数错误");
			logger.info("参数错误");
			return jMessage.toJson();
		}
		Query query = new Query(Criteria.where("m_name").is(aRoleTable.getM_name()));
		AdminRoleTable aTable = mongoTemplate.findOne(query, AdminRoleTable.class,"AdminRoleTable");
		if(aTable != null){
			jMessage.setCode(250);
			jMessage.setMsg("角色已存在");
			logger.info("角色已存在");
			return jMessage.toJson();
		}
		mongoTemplate.save(aRoleTable,"AdminRoleTable");
		jMessage.setCode(200);
		jMessage.setMsg("新增成功");
		return jMessage.toJson();
	}

	@Override
	public List<AdminRoleTable> queryRoles(String name, String founder, String startTime,String endTime) {
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
		List<AdminRoleTable> list = mongoTemplate.find(query, AdminRoleTable.class,"AdminRoleTable");
		return list;
	}
}
