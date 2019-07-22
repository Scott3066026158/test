package com.kunyi.bitamexJava.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.AdminInfoService;
import com.kunyi.bitamexJava.model.AdminInfoTable;
import com.kunyi.bitamexJava.model.JsonMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 主要负责接收管理员信息请求
 * 判断参数，处理交给dao层
 *
 * @author GAIA
 * @create 2019-05-15-8:33
 */
@CrossOrigin
@Api(value = "/admininfoservice",description = "管理员信息Api")
@RestController
@RequestMapping(value = "/admininfoservice")
public class AdminInfoController {
	@Autowired
	private AdminInfoService adminInfoDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 新增管理员信息
	 * @param name		姓名
	 * @param phone		手机号
	 * @param roleID	角色ID	
	 * @param account	管理员账号
	 * @param password	管理员登录密码
	 * @return
	 */
	@ApiOperation(value = "新增管理员")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "管理员姓名"),
		@ApiImplicitParam(name = "phone",paramType = "query",value = "手机号"),
		@ApiImplicitParam(name = "roleid",paramType = "query",value = "管理员所属角色"),
		@ApiImplicitParam(name = "account",paramType = "query",value = "管理员账户"),
		@ApiImplicitParam(name = "password",paramType = "query",value = "登录密码")
	})
	@ApiResponses({
		@ApiResponse(code = 200,message = "新增成功"),
		@ApiResponse(code = 250,message = "参数错误或者管理员已存在",response = JsonMessage.class)
	})
    @RequestMapping(value = "/addadmin",method = RequestMethod.POST)
    public String addAdminInfo(@RequestParam(value="name")String name,
    						   @RequestParam(value="phone")String phone,
    						   @RequestParam(value="roleid")String roleID,
    						   @RequestParam(value="account")String account,
    						   @RequestParam(value="password")String password){
		logger.info("请求url\"/addadmin\", 参数值: name = " + name + " , " + "phone = " + phone +  " , " +  "roleid = " + roleID  + " , " + "account = " + account  + " , " +  "password = " + password);
    	JsonMessage jsonMessage = new JsonMessage();
		if(roleID == null || phone == null || name == null || account == null || password == null){
			jsonMessage.setCode(250);
			jsonMessage.setMsg("参数错误");
			logger.error("新增管理员----->请求参数错误");
			return jsonMessage.toJson();
		}
		AdminInfoTable adminInfoTable = new AdminInfoTable();
		adminInfoTable.setM_name(name);
		adminInfoTable.setM_tel(phone);
		adminInfoTable.setM_roleID(roleID);
		adminInfoTable.setM_account(account);
		adminInfoTable.setM_pwd(password);
		if(!checkParamer(adminInfoTable)){
			jsonMessage.setCode(250);
			jsonMessage.setMsg("参数错误");
			logger.error("新增管理员----->请求参数错误");
			return jsonMessage.toJson();
		}
		adminInfoTable.setM_createTime(System.currentTimeMillis());
		adminInfoTable.setM_loginTime(adminInfoTable.getM_loginTime());
		boolean ret = adminInfoDao.addAdminInfo(adminInfoTable);
		jsonMessage.setCode(ret ? 200 : 250);
		jsonMessage.setMsg(ret ? "新增成功" : "管理员已存在");
        return jsonMessage.toJson();
    }
    
    /**
	 * 检查输入的参数是否有问题
	 * @param adminInfoTable
	 */
	private boolean checkParamer(AdminInfoTable adminInfoTable) {
		boolean isNormal;
		isNormal = (adminInfoTable.getM_name() == null || adminInfoTable.getM_account() == null ||
					adminInfoTable.getM_tel() == null || adminInfoTable.getM_pwd() == null) ? false : true;
		return isNormal;
	}
    
    /**
     * 删除管理员信息
     * @param account
     * @return
     */
	@ApiOperation(value = "删除管理员")
	@ApiImplicitParam(name = "account",paramType = "query",value = "要删除的管理员账户",required = true)
    @RequestMapping(value = "/deleteadmin",method = RequestMethod.GET)
    public String deleteAdmin(
    		@RequestParam(value="account")String account){
		logger.info("请求url\"/deleteadmin\", 请求参数: account = " + account);
    	JsonMessage jMessage = new JsonMessage();
    	if(account == null){
    		jMessage.setCode(250);
			jMessage.setMsg("参数错误");
			logger.error("删除管理员----->请求参数错误");
			return jMessage.toJson();
    	}
    	boolean ret = adminInfoDao.deleteAdminInfo(account);
		jMessage.setCode(ret ? 200 : 250);
		jMessage.setMsg(ret ? "删除成功" : "删除失败");
		return jMessage.toJson();
    }
    
    /**
     * 修改管理员状态信息
     * @param account
     * @param status
     * @return
     */
	@ApiOperation(value = "修改管理员可用状态信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account",paramType = "query",value = "要修改的管理员账户",required = true),
		@ApiImplicitParam(name = "status",paramType = "query",value = "0为关闭，1为开启",required = true)
	})
    @RequestMapping(value = "/modifyadminstatus",method = RequestMethod.GET)
    public String modifyAdminStatus(
    		@RequestParam(value = "account")String account,
    		@RequestParam(value = "status")Integer status){
		logger.info("请求url\"/modifyadminstatus\", 请求参数: account = " + account + " , " +  "status = " + status);
    	JsonMessage jMessage = new JsonMessage();
    	if(account == null || status == null){
    		jMessage.setCode(250);
			jMessage.setMsg("参数错误");
			logger.error("修改管理员可用状态信息---->请求参数错误");
			return jMessage.toJson();
    	}
    	boolean ret = adminInfoDao.updateAdminStatus(account, status);
    	jMessage.setCode(ret ? 200 : 250);
		jMessage.setMsg(ret ? "更新成功" : "更新失败");
    	return jMessage.toJson();
    }
    
    /**
     * 修改管理员信息
     * @param account
     * @param name
     * @param phone
     * @param roleID
     * @param password
     * @return
     */
	@ApiOperation(value = "修改管理员信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "管理员姓名",required = false),
		@ApiImplicitParam(name = "phone",paramType = "query",value = "手机号",required = false),
		@ApiImplicitParam(name = "roleid",paramType = "query",value = "管理员所属角色",required = false),
		@ApiImplicitParam(name = "account",paramType = "query",value = "管理员账户",required = true),
		@ApiImplicitParam(name = "password",paramType = "query",value = "登录密码",required = false)
	})
    @RequestMapping(value = "/modifyadmin",method = RequestMethod.GET)
    public String modifyAdmin(@RequestParam(value = "account")String account,
    						  @RequestParam(value = "name",required = false)String name,
    						  @RequestParam(value = "phone",required = false)String phone,
    						  @RequestParam(value = "roleid",required = false)String roleID,
    						  @RequestParam(value = "password",required = false)String password){
		logger.info("请求url\"/modifyadmin\", 参数值: account = " + account + " , " + "name = " + name  + " , " +  "phone = " + phone  + " , " +  "roleid = " + roleID  + " , " + "password = " + password);
		JsonMessage jMessage = new JsonMessage();
    	if(account == null){
    		jMessage.setCode(250);
    		jMessage.setMsg("参数错误");
    		logger.error("修改管理员信息---->请求参数错误");
    		return jMessage.toJson();
    	}
    	AdminInfoTable aTable = new AdminInfoTable();
    	aTable.setM_account(account);
    	aTable.setM_name(name);
    	aTable.setM_pwd(password);
    	aTable.setM_roleID(roleID);
    	aTable.setM_tel(phone);
    	boolean ret = adminInfoDao.updateAdminInfo(aTable);
    	jMessage.setCode(ret ? 200 : 250);
		jMessage.setMsg(ret ? "修改成功" : "管理员账户不存在");
		return jMessage.toJson();
    }
    
    /**
     * 查询管理员信息
     * @param name
     * @param roleID
     * @param startTime
	 * @param endTime
     * @return
     */
	@ApiOperation(value = "条件查询管理员信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "name",paramType = "query",value = "管理员姓名",required = false),
		@ApiImplicitParam(name = "role",paramType = "query",value = "管理员角色",required = false),
		@ApiImplicitParam(name = "startTime",paramType = "query",value = "范围查询的起始时间戳",required = false),
		@ApiImplicitParam(name = "endTime",paramType = "query",value = "范围查询的结束时间戳",required = false)
	})
    @RequestMapping(value = "/queryadmin",method = RequestMethod.GET)
    public String queryAdmin(@RequestParam(value = "name",required = false)String name,
    						 @RequestParam(value = "role",required = false)String roleID,
    						 @RequestParam(value = "startTime",required = false)String startTime,
    						 @RequestParam(value = "endTime",required = false)String endTime){
		logger.info("请求url:\"queryadmin\", 请求参数: name = " + name + " , " + "role = " + roleID + " , " + "startTime = " + startTime + " , " + "endTime = " + endTime);
    	JsonMessage jMessage = new JsonMessage();
    	List<AdminInfoTable> list = adminInfoDao.queryAdminInfo(name, roleID, startTime,endTime);
    	if(list == null){
    		jMessage.setCode(250);
    		jMessage.setMsg("没有符合条件的管理员");
    		logger.info("没有符合条件的管理员");
    		return jMessage.toJson();
    	}
    	jMessage.setCode(200);
		jMessage.setMsg("查询成功");
		jMessage.setData(convertAdminInfosToJson(list));
    	return jMessage.toJson();
    }
    
    /**
     * 管理员登录
     * @return
     */
	@ApiOperation(value = "管理员登录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "account",paramType = "query",value = "可以是账户，姓名，手机号三者之一",required = true),
		@ApiImplicitParam(name = "password",paramType = "query",value = "登录密码",required = true)
	})
    @RequestMapping(value = "/adminlogin",method = RequestMethod.GET)
    public String adminLogin(@RequestParam("account")String account,
    						 @RequestParam("password")String password,
    						 HttpServletRequest request){
    	logger.info("请求url:\"adminlogin\",请求参数: account = " + account + "," + "password = " + password);
    	HttpSession session = request.getSession();
    	return adminInfoDao.adminlogin(account,password,session);	
    }
    
    /**
	 *管理员登出，清除session内容
	 */
	@ApiOperation(value = "管理员登出",notes = "只作为服务端清除session的信号,不需要参数")
	@RequestMapping(value = "/adminloginout",method = RequestMethod.GET)
	public String adminLoginOut(HttpServletRequest request){
		logger.info("请求url:\"adminloginout\"");
		HttpSession session = request.getSession(false);
		JsonMessage json = new JsonMessage();
		if(session == null){
			json.setCode(200);
			json.setMsg("session不存在");
			logger.info("session已过期");
		}else {
			String name = (String)session.getAttribute("name");
			session.removeAttribute("name");
			session.removeAttribute("roleID");
			json.setCode(200);
			json.setMsg("session已清除");
			logger.info("清除name = " + name + "的session");
		}
		return json.toJson();
	}
    
    /**
	 * 将AdminInfoTable的链表转化成json字符串用于传输
	 * @param list
	 */
	private static String convertAdminInfosToJson(List<AdminInfoTable> list) {
		if(list == null || list.size() == 0){
			return null;
		}
		int length = list.size();
		StringBuilder json = new StringBuilder();
		//json.append("{\"admininfos\":[");
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
