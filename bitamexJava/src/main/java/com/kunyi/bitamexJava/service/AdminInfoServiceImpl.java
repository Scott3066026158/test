package com.kunyi.bitamexJava.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.AdminInfoService;
import com.kunyi.bitamexJava.model.AdminInfoTable;
import com.kunyi.bitamexJava.model.AdminRoleTable;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class AdminInfoServiceImpl implements AdminInfoService{
	@Autowired
    private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean addAdminInfo(AdminInfoTable infoTable) {
		Query query = new Query(Criteria.where("m_account").is(infoTable.getM_account()));
		AdminInfoTable aTable = mongoTemplate.findOne(query, AdminInfoTable.class,"AdminInfoTable");
		if(aTable != null){
			logger.info("账户:" + infoTable.getM_account() + " 已存在");
			return false;
		}
		mongoTemplate.save(infoTable,"AdminInfoTable");
		return true;
	}

	@Override
	public boolean deleteAdminInfo(String account) {
		Query query = new Query(Criteria.where("m_account").is(account));
		AdminInfoTable aTable = mongoTemplate.findOne(query, AdminInfoTable.class,"AdminInfoTable");
		if(aTable == null){
			logger.info("账户:" + account + " 不存在");
			return false;
		}
		mongoTemplate.remove(query,AdminInfoTable.class,"AdminInfoTable");
		return true;
	}
	
	@Override
	public boolean updateAdminStatus(String account, Integer status) {
		Query query = new Query(Criteria.where("m_account").is(account));
		AdminInfoTable aTable = mongoTemplate.findOne(query, AdminInfoTable.class,"AdminInfoTable");
		if(aTable == null){
			logger.info("账户:" + account + " 不存在");
			return false;
		}
    	Update update = new Update().set("m_status", status);
    	mongoTemplate.updateFirst(query,update,AdminInfoTable.class,"AdminInfoTable");
		return true;
	}

	@Override
	public boolean updateAdminInfo(AdminInfoTable infoTable) {
		Query query = new Query(Criteria.where("m_account").is(infoTable.getM_account()));
    	AdminInfoTable aTable = mongoTemplate.findOne(query, AdminInfoTable.class,"AdminInfoTable");
    	if(aTable == null){
			logger.info("账户:" + infoTable.getM_account() + " 不存在");
    		return false;
    	}
    	Update update = new Update();
    	if(infoTable.getM_account() != null){
    		update.set("m_account", infoTable.getM_account());
    	}
    	if(infoTable.getM_name() != null){
    		update.set("m_name", infoTable.getM_name());
    	}
    	if(infoTable.getM_tel() != null){
    		update.set("m_tel", infoTable.getM_tel());
    	}
    	if(infoTable.getM_roleID() != null){
    		update.set("m_roleID", infoTable.getM_roleID());
    	}
    	if(infoTable.getM_pwd() != null){
    		update.set("m_pwd", infoTable.getM_pwd());
    	}
    	mongoTemplate.updateFirst(query, update, AdminInfoTable.class,"AdminInfoTable");
    	return true;
	}

	@Override
	public List<AdminInfoTable> queryAdminInfo(String name, String roleID, String startTime,String endTime) {
		Query query = new Query();
    	if(!StringUtil.isEmpty(name)){
    		query.addCriteria(Criteria.where("m_name").is(name));
    	}
    	if(!StringUtil.isEmpty(roleID)){
    		query.addCriteria(Criteria.where("m_roleID").is(roleID));
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
    	List<AdminInfoTable> list = mongoTemplate.find(query, AdminInfoTable.class,"AdminInfoTable");
    	return list;
	}

	@Override
	public String adminlogin(String account, String password, HttpSession session) {
		Query query = new Query(new Criteria().orOperator(
				Criteria.where("m_name").is(account),
				Criteria.where("m_tel").is(account),
				Criteria.where("m_account").is(account)));
		AdminInfoTable adminInfoTable = mongoTemplate.findOne(query, AdminInfoTable.class,"AdminInfoTable");
		JsonMessage jMessage = new JsonMessage();
		if(adminInfoTable == null){
			jMessage.setCode(250);
			jMessage.setMsg("用户不存在");
			logger.info("账户登录:" + account + " 不存在");
			return jMessage.toJson();
		}
		if(adminInfoTable.getM_status() == 0){
			jMessage.setCode(250);
			jMessage.setMsg("账户已被禁用");
			logger.info("账户登录:" + account + " 被禁用");
			return jMessage.toJson();
		}
		if(!adminInfoTable.getM_pwd().equals(password.trim())){
			jMessage.setCode(250);
			jMessage.setMsg("密码错误");
			logger.info("账户登录:" + account + " 密码错误");
			return jMessage.toJson();
		}
		Query query2 = new Query(Criteria.where("m_name").is(adminInfoTable.getM_roleID()));
		AdminRoleTable aRoleTable = mongoTemplate.findOne(query2, AdminRoleTable.class,"AdminRoleTable");
		if(aRoleTable == null){
			jMessage.setCode(250);
			jMessage.setMsg("用户角色未找到");
			logger.info("账户登录:" + account + " 用户角色未找到");
			return jMessage.toJson();
		}
		//登录成功将用户部分信息存入session
		session.setAttribute("name",adminInfoTable.getM_name());
		session.setAttribute("roleID",adminInfoTable.getM_roleID());
		session.setMaxInactiveInterval(600);
		jMessage.setCode(200);
		jMessage.setMsg("登录成功");
		logger.info("账户:" + account + " 登录成功");
		return jMessage.toJson();
	}
}
