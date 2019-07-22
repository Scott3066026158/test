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
import com.kunyi.bitamexJava.dao.UserRoleService;
import com.kunyi.bitamexJava.model.AdminRoleTable;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Integer roleID = 0;
	
	private final String USRE_ROLE_TABLE =  "UserRoleTable";
	@Override
	public String addRole(String name, String founder, String rights) {
		JsonMessage json = new JsonMessage();
		if(name == null || founder == null || rights == null){
			json.setCode(250);
			json.setMsg("参数错误");
			logger.info("参数错误");
			return json.toJson();
		}
		AdminRoleTable aRoleTable = new AdminRoleTable();
		aRoleTable.setM_name(name);
		aRoleTable.setM_founder(founder);
		aRoleTable.setM_rights(rights);
		aRoleTable.setM_createTime(System.currentTimeMillis());
		Query query = new Query(Criteria.where("m_name").is(name));
		AdminRoleTable adminRoleTable = mongoTemplate.findOne(query, AdminRoleTable.class,USRE_ROLE_TABLE);
		if(adminRoleTable != null){
			json.setCode(250);
			json.setMsg("角色名已存在");
			logger.info("角色名已存在");
			return json.toJson();
		}
		synchronized (roleID) {
			aRoleTable.setM_roleID(roleID++);
		}
		mongoTemplate.save(aRoleTable,USRE_ROLE_TABLE);
		json.setCode(200);
		json.setMsg("新增成功");
		return json.toJson();
	}

	@Override
	public String queryRole(String name, String founder, String startTime,String endTime) {
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
		List<AdminRoleTable> list = mongoTemplate.find(query, AdminRoleTable.class,USRE_ROLE_TABLE);
		JsonMessage msg = new JsonMessage();
		if(list == null || list.size() == 0){
			msg.setCode(250);
			msg.setMsg("没有记录");
			logger.info("没有记录");
			return msg.toJson();
		}
		msg.setCode(200);
		msg.setMsg("查询成功");
		msg.setData(convertAdminRolesToJson(list));
		return msg.toJson();
	}
	
	
	private String convertAdminRolesToJson(List<AdminRoleTable> list) {
		if(list == null || list.size() == 0){
			return null;
		}
		int length = list.size();
		StringBuilder json = new StringBuilder();
		//json.append("{\"adminroles\":[");
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
