package com.kunyi.bitamexJava.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.UserLoginService;
import com.kunyi.bitamexJava.model.LoginInfo;
import com.kunyi.bitamexJava.model.UserClientTable;
import com.kunyi.bitamexJava.model.UserInfoTable;
import com.kunyi.bitamexJava.util.GoogleAuthenticator;
import com.kunyi.bitamexJava.util.QEncodeUtil;

@Service
public class UserLoginServiceImpl implements UserLoginService{
	//客户端的参数异常
	private final String INVALID_PARAM_EXCEPTION= "-1";
	//用户不存在
	private final String USER_NOFOUND = "-2";
	//用户被锁定
	private final String USER_LOCKED = "-4";
	//用户登录错误次数超过最大值
	private final String ERROR_COUNT_EXCEED = "-5";
	//用户密码输入错误
	private final String PASSWORD_ERROR = "-6";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public String userLogin(String username, String password, String logintype) {
		if(username == null || "".equals(username.trim())){
			return USER_NOFOUND;
		}
		if(password == null || "".equals(password.trim())){
			return USER_NOFOUND;
		}
		if(logintype == null || "".equals(logintype.trim())){
			return USER_NOFOUND;
		}
		HashMap<String, List> map = isUserExist(username);
		if(map == null)
		{
			return INVALID_PARAM_EXCEPTION;
		}
		if(!isRegisterType(logintype))
		{
			return USER_NOFOUND;
		}
		List<UserInfoTable> loginInfos = map.get("logininfos");
		UserInfoTable loginInfo = loginInfos.get(0);
		//用户是否处于锁定状态
		if(loginInfo.getM_userPwdType() == 2 || loginInfo.getM_loginlimit() == 0)
		{
			return USER_LOCKED;
		}
		//用户登录错误是否超过最大值
		if (loginInfo.getM_maxErrorCount() <= loginInfo.getM_userPwdLimit())
		{
			Query query = new Query(Criteria.where("_id").is(loginInfo.get_id()));
			Update update = new Update().set("m_userPwdType", 2);
			mongoTemplate.updateFirst(query, update, UserInfoTable.class,"UserInfoTable");
			return ERROR_COUNT_EXCEED;
		}
		//用户密码是否正确
		String decodePwd = QEncodeUtil.AesDecrypt(loginInfo.getM_userPassword());
		if (!decodePwd.equals(password))
		{
			Query query = new Query(Criteria.where("_id").is(loginInfo.get_id()));
			Update update = new Update().set("m_userPwdLimit", loginInfo.getM_userPwdLimit() + 1);
			mongoTemplate.updateFirst(query, update, UserInfoTable.class,"UserInfoTable");
			return PASSWORD_ERROR;
		}
		Query query = new Query(Criteria.where("_id").is(loginInfo.get_id()));
		Update update = new Update().set("m_userPwdLimit", 0);
		mongoTemplate.updateFirst(query, update, UserInfoTable.class,"UserInfoTable");
		String result = loginInfo.getM_traderID() + "|" + loginInfo.getM_userID() + 
				"|" + loginInfo.getM_userNickName() + "|" + loginInfo.getM_userClientType() + 
				"|" + loginInfo.getM_userAreaCode() + loginInfo.getM_userInfoPhone() + "|" + loginInfo.getM_isGoogleAuthened()
				+ "|" + loginInfo.getM_invitationCode() + "|" + loginInfo.getM_isSMSAuthened();
		return result;
	}

	/**
	 * 判断用户是否存在
	 * @param username 用户名
	 * @return 若不存在返回null,存在返回对象
	 */
	private HashMap<String, List> isUserExist(String username) {
		//构建返回参数
		HashMap<String, List> result = new HashMap<String, List>();
		
		Query query1 = new Query(Criteria.where("m_traderName").is(username));
		List<UserInfoTable> logininfos = mongoTemplate.find(query1, UserInfoTable.class,"UserInfoTable");
		if(logininfos == null || logininfos.size() == 0){
			return null;
		}else{
			result.put("logininfos", logininfos);
		}
		return result;
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

	@Override
	public String smsAuthened(String userid, String password) {
		if(userid == null || password == null || "".equals(userid.trim()) || "".equals(password.trim())){
			return "failed";
		}
		Query query = new Query(Criteria.where("m_userID").is(userid));
		UserInfoTable loginInfo = mongoTemplate.findOne(query, UserInfoTable.class, "UserInfoTable");
		if(loginInfo == null){
			return "failed";
		}
		String decodePwd = QEncodeUtil.AesDecrypt(loginInfo.getM_userPassword());
		if(!decodePwd.equals(password)){
			return "failed";
		}
		Update update = new Update();
		if(loginInfo.getM_isSMSAuthened()){
			//之前是开启状态就关闭
			update.set("m_isSMSAuthened", false);
		}else {
			//之前是关闭状态就开启
			update.set("m_isSMSAuthened", true);
		}
		mongoTemplate.updateFirst(query, update, UserInfoTable.class, "UserInfoTable");
		return "success";
	}

	@Override
	public String fetchgooglesecretkey(String userid) {
		String secretKey = GoogleAuthenticator.generateSecretKey();
		GoogleAuthenticator.googleSecretKey.put(userid, secretKey);
		return secretKey;
	}

	@Override
	public String googleAuthened(Integer userid, String password, Integer googlecode) {
		//判断参数是否正常
		if(userid == null || googlecode == null || password == null || "".equals(password.trim())){
			return "failed";
		}
		Query query1 = new Query(Criteria.where("m_userID").is(userid));
		UserInfoTable loginInfo = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if(loginInfo == null){
			return "failed";
		}
		String decodePwd = QEncodeUtil.AesDecrypt(loginInfo.getM_userPassword());
		if(!password.equals(decodePwd)){
			//密码不正确
			return "failed1";
		}
		String secretKey = GoogleAuthenticator.googleSecretKey.get(userid+"");
		boolean result = GoogleAuthenticator.check_code(secretKey, googlecode, System.currentTimeMillis());
		if(!result){
			//谷歌验证码不正确
			return "failed2";
		}
		Update update1 = new Update();
		
		if(loginInfo.getM_isGoogleAuthened()){
			update1.set("m_isGoogleAuthened", false);
			update1.set("m_googleSecretKey", "");
		}else{
			update1.set("m_isGoogleAuthened", true);
			update1.set("m_googleSecretKey", secretKey);
		}
		mongoTemplate.updateFirst(query1, update1, UserInfoTable.class,"UserInfoTable");
		return "success";
	}

	@Override
	public String checkGoogleCode(Integer googlecode, Integer userid) {
		if(googlecode == 0)
		{
			return "failed";
		}
		String secretKey = GoogleAuthenticator.googleSecretKey.get(userid);
		if(secretKey == null || "".equals(secretKey)){
			Query query1 = new Query(Criteria.where("m_userID").is(userid));
			UserInfoTable loginInfo = mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
			if(loginInfo == null){
				return "failed";
			}
			secretKey = loginInfo.getM_googleSecretKey();
		}
		boolean ret = GoogleAuthenticator.check_code(secretKey, googlecode, System.currentTimeMillis());
		return ret ? "success" : "failed";
	}
}
