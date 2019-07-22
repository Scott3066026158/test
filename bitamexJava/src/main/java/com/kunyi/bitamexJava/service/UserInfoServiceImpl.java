package com.kunyi.bitamexJava.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.UserInfoService;
import com.kunyi.bitamexJava.model.JsonMessage;
import com.kunyi.bitamexJava.model.LoginInfo;
import com.kunyi.bitamexJava.model.UserClientTable;
import com.kunyi.bitamexJava.model.UserInfo;
import com.kunyi.bitamexJava.model.UserInfoTable;
import com.kunyi.bitamexJava.util.QEncodeUtil;
import com.kunyi.bitamexJava.util.StringUtil;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private MongoTemplate mongoTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String checkUserInfo(String username) {
		if(username == null || "".equals(username)){
			return "-1";
		}
		Query query2 = new Query(Criteria.where("m_traderName").is(username));
		UserInfoTable userClient = mongoTemplate.findOne(query2, UserInfoTable.class,"UserInfoTable");
		if(userClient == null){
			return "-3";
		}
		return "1";
	}

	@Override
	public String modifyUserPsaaword(String username, String newPassword) {
		if(username == null || newPassword == null || 
				"".equals(username) || "".equals(newPassword)){
			return "-1";
		}
		Query query2 = new Query(Criteria.where("m_traderName").is(username));
		UserInfoTable userClient = mongoTemplate.findOne(query2, UserInfoTable.class,"UserInfoTable");
		if(userClient == null){
			return "-3";
		}
		userClient.setM_userPassword(QEncodeUtil.AesEncrypt(newPassword));
		Update update2 = new Update().set("m_userPassword", userClient.getM_userPassword());
		mongoTemplate.updateFirst(query2, update2, UserInfoTable.class,"UserInfoTable");
		
		return QEncodeUtil.AesDecrypt(userClient.getM_userPassword());
	}

	@Override
	public String bindPhoneNumber(Integer userId, String phone) {
		Query query1 = new Query(Criteria.where("m_userID").is(userId));
		UserInfoTable logininfo = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if(logininfo == null){
			return "0";
		}
		Update update1 = new Update();
		if(logininfo.getM_userClientType() == 0){
			update1.set("m_userClientType", 1);
		}
		update1.set("m_userInfoPhone",  phone);
		mongoTemplate.updateFirst(query1, update1, UserInfoTable.class,"UserInfoTable");
		return "1";
	}

	@Override
	public String unBindPhoneNumber(Integer userId) {
		Query query1 = new Query(Criteria.where("m_userID").is(userId));
		UserInfoTable logininfo = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if(logininfo == null){
			return "2";
		}
		Update update1 = new Update();
		if(logininfo.getM_userClientType() == 1){
			update1.set("m_userClientType", 0);
		}
		update1.set("m_userInfoPhone", "");
		mongoTemplate.updateFirst(query1, update1, UserInfoTable.class,"UserInfoTable");
		return "3";
	}

	@Override
	public String fetchNumberOfUserInvited(Integer userId, String invitationCode) {
		if(userId == null || invitationCode == null || userId == 0 || "".equals(invitationCode)){
			return "0";
		}
		Query query1 = new Query(Criteria.where("m_userID").is(userId));
		UserInfoTable logininfo = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if(logininfo == null){
			return "0";
		}
		Query query2 = new Query(Criteria.where("m_inviter").is(invitationCode));
		List<UserInfoTable> list = mongoTemplate.find(query2, UserInfoTable.class,"UserInfoTable");
		if(list == null || list.size() == 0){
			return "0";
		}
		return String.valueOf(list.size());
	}

	@Override
	public String queryUserInfo(String phone, String name, String startTime,String endTime) {
		Query query = new Query();
		if(!StringUtil.isEmpty(phone)){
			query.addCriteria(Criteria.where("m_userInfoPhone").is(phone));
		}
		if(!StringUtil.isEmpty(name)){
			query.addCriteria(Criteria.where("m_userName").is(name));
		}
		if(!StringUtil.isTimeEmpty(startTime) && StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_userRegistTime").gt(Long.parseLong(startTime)));
    	}else if(StringUtil.isTimeEmpty(startTime) && !StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_userRegistTime").lt(Long.parseLong(endTime)));
    	}else if(!StringUtil.isTimeEmpty(startTime) && !StringUtil.isTimeEmpty(endTime)){
    		query.addCriteria(Criteria.where("m_userRegistTime").lt(Long.parseLong(endTime)).
    				andOperator(Criteria.where("m_userRegistTime").gt(Long.parseLong(startTime))));
    	}
		query.with(new Sort(Sort.Direction.DESC,"m_userRegistTime"));
		List<UserInfoTable> list = mongoTemplate.find(query, UserInfoTable.class,"UserInfoTable");
		JsonMessage json = new JsonMessage();
		if(list == null || list.size() == 0){
			json.setCode(250);
			json.setMsg("没有符合条件的数据");
			logger.info("没有符合条件的数据");
			return json.toJson();
		}
		json.setCode(200);
		json.setMsg("成功");
		json.setData(convertToUserInfoJson(list));
		return json.toJson();
	}

	@Override
	public String modifyUserInfo(String nickName,String area, String phone, String password, String loginright) {
		JsonMessage json = new JsonMessage();
		if(nickName == null){
			json.setCode(250);
			json.setMsg("参数错误");
			logger.info("参数错误");
			return json.toJson();
		}
		Query query1 = new Query(Criteria.where("m_userNickName").is(nickName));
		UserInfoTable uClientTable = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if(uClientTable == null){
			json.setCode(250);
			json.setMsg("查询用户出错");
			logger.info("查询用户出错, nickName = " + nickName);
			return json.toJson();	
		}
		Update update1 = new Update();
		if(phone != null){
			update1.set("m_userInfoPhone", phone);
		}
		if(area != null){
			update1.set("m_userAreaCode", area);
		}
		if(password != null){
			update1.set("m_userPassword", QEncodeUtil.AesEncrypt(password));
		}
		if(loginright != null){
			update1.set("m_loginlimit", (loginright.equals("1")) ? 1 : 0);
		}
		mongoTemplate.updateFirst(query1, update1, UserInfoTable.class,"UserInfoTable");
		json.setCode(200);
		json.setMsg("修改成功");
		return json.toJson();
	}

	
	/**
	 * 将UserClientTable数据转化成UserInfo的json数据
	 * @param list
	 */
	private String convertToUserInfoJson(List<UserInfoTable> list) {
		if(list == null || list.size() == 0){
			return null;
		}
		int length = list.size();
		List<UserInfo> userInfos = new ArrayList<>();
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(int i = 0; i < length; i++){
			UserInfo userInfo = new UserInfo();
			UserInfoTable uTable = list.get(i);
			userInfo.name = uTable.getM_userName();
			userInfo.nickName = uTable.getM_userNickName();
			userInfo.IDcard = uTable.getM_userInfoCID();
			userInfo.phone = uTable.getM_userInfoPhone();
			userInfo.registerTime = sFormat.format(new Date(uTable.getM_userRegistTime()));
			userInfo.role = (uTable.getM_userClientType() <= 3) ? "普通用户" : "会员";
			userInfo.range = "LV" + (uTable.getM_userClientType() + 1);
			switch (uTable.getM_userClientType()) {
			case 0:
			case 1:
				userInfo.authstatus = "未审核";
				break;
			case 2:
				userInfo.authstatus = "正在审核";
				break;
			default:
				userInfo.authstatus = "已审核";
				break;
			}
			userInfo.accountStatus = (uTable.getM_userPwdType() == 2) ? 0 : 1;
			userInfo.userSource = (uTable.getM_inviter() == null) ? "App注册" : "好友邀请";
			userInfo.areaCode = uTable.getM_userAreaCode();
			userInfos.add(userInfo);
		}
		StringBuilder sBuilder = new StringBuilder();
		for(int i = 0; i < length; i++){
			sBuilder.append(userInfos.get(i).ConvertObjectToJson());
			sBuilder.append(",");
		}
		if(sBuilder.length() > 1){
			sBuilder.deleteCharAt(sBuilder.length() - 1);
		}
		return sBuilder.toString();
	}
}
