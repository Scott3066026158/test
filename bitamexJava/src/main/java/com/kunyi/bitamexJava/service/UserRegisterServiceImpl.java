package com.kunyi.bitamexJava.service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.UserRegisterService;
import com.kunyi.bitamexJava.model.LoginInfo;
import com.kunyi.bitamexJava.model.UserClientTable;
import com.kunyi.bitamexJava.model.UserInfoTable;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	//服务端未出现异常,成功返回
	private static final String SUCCESS= "1";
	//客户端的参数异常
	private static final String INVALID_PARAM_EXCEPTION= "-1";
	//用户已经存在
	private static final String USER_EXIST = "-2";
	//注册类型异常
	private static final String REGISTER_TYPE_EXCEPTION = "-3";
	//redis获取traderid异常
	private static final String SERVICE_REDIS_EXCEPTION = "-4";
	//操作数据库失败
	private static final String DB_UPDATE_FAILD = "-5";
	
	/**
	 * GAIAID增长的USERID
	 */
	private static String REDIS_KEY_GAIAID_GENERATE_USERID = "GAIAID_GENERATE_USERID";
	
	/**
	 * Redis关键字 新的Trader池
	 */
	private static String REDIS_KEY_NEWTRADERID_POOL = "NEWTRADERID_POOL";
	
	/**
	 * Redis关键字 已经使用的Trader池
	 */
	private static String REDIS_KEY_USEDTRADERID_POOL = "USEDTRADERID_POOL";
	
	@Override
	public String userRegister(UserInfoTable uTable, String registertype) {
		long userid = redisTemplate.opsForValue().increment(REDIS_KEY_GAIAID_GENERATE_USERID);
		//生成一个四位随机数
		Random random = new Random();
		int ran = random.nextInt(8999) + 1000;
		
		//根据UserID和四位随机数生成用户自己的推广码
		String invitationCode = createInvitationCode(ran,(int)userid);
		uTable.setM_invitationCode(invitationCode);
		//判断参数是否正常
		if (IsEmpty(uTable.getM_traderName(), uTable.getM_userPassword(), registertype)) 
		{
			return INVALID_PARAM_EXCEPTION;
		}
		//判断注册类型
		if(!isRegisterType(registertype))
		{
			return REGISTER_TYPE_EXCEPTION;
		}
		//判断用户是否存在
		if (isUserExist(uTable.getM_traderName(),  registertype))
		{
			return USER_EXIST;
		}
		//判断用户邀请码是否存在
		if(!isInviterExist(uTable.getM_inviter())){
			uTable.setM_inviter("");
		}
		String traderID = null;
		if("email".equals(registertype))
		{
			traderID = GetNewTraderID(0);
		}
		else if("phone".equals(registertype))
		{
			traderID = GetNewTraderID(1);
		}
		//判断traderID是否正常生成
		if(IsEmpty(traderID))
		{
			return SERVICE_REDIS_EXCEPTION;
		}
		uTable.setM_traderID(traderID);
		uTable.setM_userPwdType(1);
		uTable.setM_userRegistTime(System.currentTimeMillis());
		uTable.setM_timestamp(uTable.getM_userRegistTime());
		uTable.setM_userInfoCID("");
		uTable.setM_userPwdLimit(0);
		uTable.setM_userNickName(uTable.getM_traderName());
		boolean isSuccess1 = AddUserInfoTable(uTable, registertype);
		if(!isSuccess1)
		{
			return DB_UPDATE_FAILD;
		}
		return SUCCESS;
	}
	
	/**
	 * 向用户表中添加数据
	 * @param uTable
	 * @param registertype
	 * @return
	 */
	private boolean AddUserInfoTable(UserInfoTable uTable, String registertype) {
		int userType = 0;
		if("phone".equals(registertype))
		{
			userType = 1;
		}
		uTable.setM_userClientType(userType);
		uTable.set_id(UUID.randomUUID().toString().replaceAll("-", ""));
		if("email".equals(registertype))
		{
			uTable.setM_userPwdSecurity(2);
			uTable.setM_userInfoMail(uTable.getM_traderName());
		}
		else if("phone".equals(registertype))
		{
			uTable.setM_userPwdSecurity(1);
			uTable.setM_userInfoPhone(uTable.getM_traderName());
		}
		mongoTemplate.save(uTable,"UserInfoTable");
		return true;
	
	}
	
	/**
	 * 判断用户名是否已经存在
	 * 
	 * @param mobile
	 * @param appId
	 * @return
	 */
	public boolean isUserExist(String username, String registerType) {
		Query query1 = new Query(Criteria.where("m_userName").is(username));
		UserInfoTable logininfo = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if (logininfo == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 创建邀请码
	 * @param ran
	 * @param userID
	 * @return
	 */
	private String createInvitationCode(int ran, int userID){
		String strUserID = userID + "";
		String strRan = ran + "";
		int i = 0;
		StringBuilder str = new StringBuilder();
		while(i < 4){
			str.append(strUserID.charAt(i));
			str.append(strRan.charAt(i++));
		}
		if(i == strRan.length() && i != strUserID.length()){
			return str.toString() + strUserID.substring(i);
		}
		
		return str.toString();
	}
	
	/**
	 * 检测字符串是否存在""或者Null
	 * @param strings 需要检测的字符串
	 * @return 传入的所有字符串中若存在""或者Null,返回true,若没有,返回false
	 */
	public boolean IsEmpty(String...strings)
	{
		for (String s : strings) {
			if (s == null || s.trim().equals("")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否是指定的注册类型
	 * @param registerType
	 * @return 如果是符合类型的是
	 */
	public boolean isRegisterType(String registerType)
	{
		boolean result = false;
		switch(registerType)
		{
		case "email":
			result = true;
			break;
		case "phone":
			result = true;
			break;
		default:
			result = false;
			break;
		}
		return result;
	}
	
	/**
	 * 判断用户邀请码是否真实，若不存在则设为空
	 * @param inviter
	 * @return
	 */
	private boolean isInviterExist(String inviter) {
		if(inviter.matches("[0-9]{8}")){
			Query query = new Query(Criteria.where("m_invitationCode").is(inviter));
			List<UserInfoTable> userClientTables = mongoTemplate.find(query, UserInfoTable.class,"UserInfoTable");
			if(userClientTables.size() == 1){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取新的TraderID
	 * 
	 * @param type
	 * @return
	 */
	public String GetNewTraderID(int type)
	{
		String value = redisTemplate.opsForList().leftPop(REDIS_KEY_NEWTRADERID_POOL);
		String newTraderID = "";
		if (type == 0)
		{
			newTraderID = "EA" + value;
		}
		else if (type == 1)
		{
			newTraderID = "PA" + value;
		}
		redisTemplate.opsForList().rightPush(REDIS_KEY_USEDTRADERID_POOL, newTraderID);
		return newTraderID;
	}
}
