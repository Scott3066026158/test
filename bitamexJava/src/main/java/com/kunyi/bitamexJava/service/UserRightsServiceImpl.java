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

import com.kunyi.bitamexJava.dao.UserRightsService;
import com.kunyi.bitamexJava.model.AdminRightTable;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class UserRightsServiceImpl implements UserRightsService {

	@Autowired
	private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String USER_RIGHT_TABLE = "UserRightTable";
	
	@Override
	public String addRight(String name, String founder) {
		JsonMessage msg = new JsonMessage();
		if(name == null ||  founder == null){
			msg.setCode(250);
			msg.setMsg("参数错误");
			logger.info("参数错误");
			return msg.toJson();
		}
		Query query = new Query(Criteria.where("m_name").is(name));
		AdminRightTable adminRightTable = mongoTemplate.findOne(query, AdminRightTable.class,USER_RIGHT_TABLE);
		if(adminRightTable != null){
			msg.setCode(250);
			msg.setMsg("权限已存在");
			logger.info("权限已存在");
			return msg.toJson();
		}
		adminRightTable = new AdminRightTable();
		adminRightTable.setM_name(name);
		adminRightTable.setM_founder(founder);
		adminRightTable.setM_createTime(System.currentTimeMillis());
		mongoTemplate.save(adminRightTable,USER_RIGHT_TABLE);
		msg.setCode(200);
		msg.setMsg("新增权限成功");
		return msg.toJson();
	}

	@Override
	public String queryRight(String name, String founder, String startTime,String endTime) {
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
		List<AdminRightTable> list = mongoTemplate.find(query, AdminRightTable.class,USER_RIGHT_TABLE);
		JsonMessage msg = new JsonMessage();
		if(list == null || list.size() == 0){
			msg.setCode(250);
			msg.setMsg("没有记录");
			logger.info("没有记录");
			return msg.toJson();
		}
		msg.setCode(200);
		msg.setMsg("查询成功");
		msg.setData(convertAdminRightsToJson(list));
		return msg.toJson();
	}

	/**
	 * 将AdminInfoTable的链表转化成json字符串用于传输
	 * @param list
	 */
	private  String convertAdminRightsToJson(List<AdminRightTable> list) {
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
