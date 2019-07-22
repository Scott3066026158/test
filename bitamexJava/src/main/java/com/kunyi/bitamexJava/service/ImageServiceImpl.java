package com.kunyi.bitamexJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.ImageService;
import com.kunyi.bitamexJava.model.LoginInfo;
import com.kunyi.bitamexJava.model.UserInfoTable;

@Service
public class ImageServiceImpl implements ImageService{
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Override
	public String getUserId(String phone) {
		Query query = new Query(Criteria.where("m_userInfoPhone").is(phone));
		UserInfoTable loginInfo = mongoTemplate.findOne(query, UserInfoTable.class,"UserInfoTable");
		if(loginInfo != null){
			return String.valueOf(loginInfo.getM_userID());
		}
		return "";
	}

}
