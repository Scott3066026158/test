package com.kunyi.bitamexJava.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kunyi.bitamexJava.dao.UserSelectService;
import com.kunyi.bitamexJava.model.UserSelectTable;
import com.mongodb.client.result.UpdateResult;

@Service
public class UserSelectServiceImpl implements UserSelectService {

	//用户不存在
	private final String USER_NOFOUND = "2";
	//服务端未出现异常,成功返回
	private final String SUCCESS= "1";
	//协议解析异常
	private final String PROTOCOL_PARSE_EXCEPTION= "-1";
	//客户端的参数异常
	private final String INVALID_PARAM_EXCEPTION= "-2";
	//用户数据异常
	private final String USER_DATA_EXCEPTION = "-3";
	//用户自选股记录创建失败
	private final String USER_CREATE_FAILD = "-4";
	//自选股数据解析异常
	private final String USER_SELECT_DATA_EXCEPTION = "-5";
	//更新数据库失败
	private final String UPDATE_DB_FAILD = "-6";
	//每个自选股的分隔符
	public  static final String USERSELECT_SEPARATOR = ",";
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public String getUserData(String traderId) {
		if(traderId == null){
			return "result=" + INVALID_PARAM_EXCEPTION +"&codes=null";
		}
		Query query = new Query(Criteria.where("traderid").is(traderId));
		List<UserSelectTable> userSelects = mongoTemplate.find(query, UserSelectTable.class, "UserSelectTable");
		if(userSelects.size() == 0)
		{
			return "result=" + USER_NOFOUND +"&codes=null";
		}
		
		//判断用户数据是否正常
		if(userSelects.size() > 1)
		{
			return "result=" + USER_DATA_EXCEPTION +"&codes=null";
		}
		//判断自选股数据是否正常
		String codes = parse(userSelects.get(0).getM_codes(), USERSELECT_SEPARATOR);
		if(codes == null)
		{
			return "result=" + USER_SELECT_DATA_EXCEPTION +"&codes=null";
		}
		return "result=" + SUCCESS +"&codes=" + codes;
	}

	@Override
	public String addUserData(String traderId, String code) {
		if(traderId == null || code == null){
			return "result=" + INVALID_PARAM_EXCEPTION +"&codes=null";
		}
		Query query = new Query(Criteria.where("traderid").is(traderId));
		List<UserSelectTable> userSelects = mongoTemplate.find(query, UserSelectTable.class, "UserSelectTable");
		//判断用户数据是否正常
		if(userSelects.size() > 1)
		{
			return "result=" + USER_DATA_EXCEPTION +"&codes=null";
		}
		if(userSelects.size() == 0)
		{
			UserSelectTable userSelect = createUser(traderId);
			if(userSelect == null)
			{
				return "result=" + USER_CREATE_FAILD +"&codes=null";
			}
			userSelects.add(userSelect);
		}
		//添加自选股
		boolean addSelect = addUserSelect(userSelects.get(0), code);
		if(!addSelect)
		{
			return "result=" + UPDATE_DB_FAILD +"&codes=null";
		}
		//更新数据库
		boolean flag = updateUserSelectToDB(userSelects.get(0));
		if(!flag)
		{
			return "result=" + UPDATE_DB_FAILD +"&codes=null";
		}
		//判断自选股数据是否正常
		String codes = parse(userSelects.get(0).getM_codes(), USERSELECT_SEPARATOR);
		if(codes == null)
		{
			return "result=" + USER_SELECT_DATA_EXCEPTION +"&codes=null";
		}
		return "result=" + SUCCESS +"&codes=" + codes;
	}

	@Override
	public String delUserData(String traderId, String code) {
		if(traderId == null || code == null){
			return INVALID_PARAM_EXCEPTION;
		}
		Query query = new Query(Criteria.where("traderid").is(traderId));
		List<UserSelectTable> userSelects = mongoTemplate.find(query, UserSelectTable.class, "UserSelectTable");
		if(userSelects.size() == 0)
		{
			return USER_NOFOUND;
		}else if(userSelects.size() > 1){
			return "result=" + USER_DATA_EXCEPTION +"&codes=null";
		}
		//删除自选股
		if("".equals(userSelects.get(0).getM_codes()))
		{
			return "result=" + UPDATE_DB_FAILD +"&codes=null";
		}
		userSelects.get(0).setM_codes(UpdateDelCode(code,userSelects.get(0).getM_codes())); 
		//更新数据库
		boolean flag = updateUserSelectToDB(userSelects.get(0));
		if(!flag)
		{
			return "result=" + UPDATE_DB_FAILD +"&codes=null";
		}
		return "result=" + SUCCESS +"&codes=" + userSelects.get(0).getM_codes();
	}

	
	/**
	 * 查询或创建一条用户自选股记录
	 * @param traderID 用户ID
	 * @return 当数据库存在此用户记录就返回此用户,不存在则创建并返回,失败返回null
	 */
	private UserSelectTable createUser(String traderID)
	{
		Query query = new Query(Criteria.where("traderid").is(traderID));
		UserSelectTable userSelects = mongoTemplate.findOne(query, UserSelectTable.class, "UserSelectTable");
		//如果存在则返回
		if(userSelects != null)
		{
			return userSelects;
		}
		userSelects = new UserSelectTable();
		userSelects.set_id(UUID.randomUUID().toString().replaceAll("-", ""));
		userSelects.setM_codes("");
		userSelects.setM_orderNum(0);
		userSelects.setM_traderID(traderID);
		userSelects.setM_type(0);
		mongoTemplate.save(userSelects,"UserSelectTable");
		
		userSelects = mongoTemplate.findOne(query, UserSelectTable.class, "UserSelectTable");
		if(userSelects == null)
		{
			return null;
		}
		return userSelects;
	}
	
	/**
	 * 获取指定用户删除后剩下的code
	 * @param code  需要删除的code
	 * @param oldcode 原本已存在的code
	 * @return
	 */
	public String UpdateDelCode(String code, String oldcode)
	{
		if(oldcode.equals("") || oldcode == null)
		{
			return "";
		}
		oldcode = "," + oldcode + ",";
		String[] delcodes = code.split(",");
		for(int i = 0; i < delcodes.length; i++)
		{
			String codeID = "," + delcodes[i] + ",";
			if(oldcode.indexOf(codeID) != -1)
			{
				oldcode = oldcode.replaceAll(codeID, ",");
			}
		}
		if(oldcode.length() <= 1) return "";
		return oldcode.substring(1, oldcode.length() - 1);
	}
	
	/**
	 * 向对象userSelect中添加一条自选股
	 * @param userSelect 需要添加自选股的对象
	 * @param code 添加的自选股
	 * @return 返回true
	 */
	private boolean addUserSelect(UserSelectTable userSelect, String code)
	{
		boolean result = false;
		if(userSelect.getM_codes().length() <= 0)
		{
			userSelect.setM_codes(code);
			result = true;
		}
		else
		{
			String oldcode = "," + userSelect.getM_codes() + ",";
			String[] addcodes = code.split(",");
			for(int i = 0; i < addcodes.length; i++)
			{
				if(addcodes[i].trim() == "")continue;
				String codeID = "," + addcodes[i] + ",";
				if(oldcode.indexOf(codeID) == -1)
				{
					userSelect.setM_codes(userSelect.getM_codes() + "," + addcodes[i]);
					result = true;
				}
				else
				{
					continue;
				}
			}
		}
		return result;
	}
	
	/**
	 * 更新指定用户的自选股数据
	 * @param userSelect 需要更新的数据
	 * @return 更新成功返回true,失败返回false
	 */
	private boolean updateUserSelectToDB(UserSelectTable userSelect)
	{
		Query query = new Query(Criteria.where("traderid").is(userSelect.getM_traderID()));
		UserSelectTable userSelects = mongoTemplate.findOne(query, UserSelectTable.class, "UserSelectTable");
		Update update = new Update().set("m_codes", userSelect.getM_codes());
		UpdateResult result = mongoTemplate.updateFirst(query,update,UserSelectTable.class,"UserSelectTable");
		return result== null ? false : true;
	}
	
	/**
	 * 将数组转换成字符串
	 * @param s 源字符串
	 * @param split 数组元素之间的分隔符
	 * @return 成功返回字符串,失败返回null
	 */
	private String parse(String a, String split)
	{
		if(a == null || "".equals(a))
		{
			return null;
		}
		String[] s = a.split(",");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; ; i++)
		{
			sb.append(s[i]);
			if(i == s.length - 1)
				return sb.toString();
			sb.append(split);
		}
	}
}
