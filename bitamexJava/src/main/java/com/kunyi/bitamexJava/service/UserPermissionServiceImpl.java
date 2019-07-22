package com.kunyi.bitamexJava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.UserPermissionService;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.model.LoginInfo;
import com.kunyi.bitamexJava.model.UserClientTable;
import com.kunyi.bitamexJava.model.UserInfoTable;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {
	@Autowired
	private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String getUserPermission(Integer userID) {
		if(userID == null || userID == -1){
			return "-1";
		}
		Query query = new Query(Criteria.where("m_userID").is(userID));
		UserInfoTable logininfo = mongoTemplate.findOne(query, UserInfoTable.class,"UserInfoTable");
		if(logininfo == null){
			return "-2";
		}
		return String.valueOf(logininfo.getM_userClientType());
	}

	@Override
	public String setUserPermission(Integer userID, Integer userpermission) {
		if(userID == null || userpermission == null){
			return "-1";
		}
		Query query1 = new Query(Criteria.where("m_userID").is(userID));
		UserInfoTable logininfo = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if(logininfo == null){
			return "-2";
		}
		
		Update update1 = new Update().set("m_userClientType", userpermission);
		mongoTemplate.updateFirst(query1,update1, UserInfoTable.class, "UserInfoTable");
		return String.valueOf(userpermission);
	}

	@Override
	public String auditUser(String name, String result) {
		JsonMessage jMessage = new JsonMessage();
		if(name == null || result == null){
			jMessage.setCode(250);
			jMessage.setMsg("参数错误");
			logger.info("参数错误");
			return jMessage.toJson();
		}
		Query query1 = new Query(Criteria.where("m_userNickName").is(name));
		UserInfoTable logininfo = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if(logininfo == null){
			jMessage.setCode(250);
			jMessage.setMsg("参数错误");
			logger.info("参数错误");
			return jMessage.toJson();
		}
		if("success".equals(result)){
			Update update1 = new Update().set("m_userClientType", 3);
			mongoTemplate.updateFirst(query1,update1, UserInfoTable.class, "UserInfoTable");
			jMessage.setCode(200);
			jMessage.setMsg("成功");
		}else if ("failed".equals(result)) {
			Update update1 = new Update();
			if("".equals(logininfo.getM_userInfoPhone())){
				update1.set("m_userClientType", 0);
			}else{
				update1.set("m_userClientType", 1);
			}
			mongoTemplate.updateFirst(query1,update1, UserInfoTable.class, "UserInfoTable");
			jMessage.setCode(250);
			jMessage.setMsg("成功");
		}else {
			jMessage.setCode(250);
			jMessage.setMsg("参数错误");
			logger.info("参数错误");
		}
		return jMessage.toJson();
	}
	
}
