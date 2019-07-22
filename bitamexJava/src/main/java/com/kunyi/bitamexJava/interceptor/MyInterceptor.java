package com.kunyi.bitamexJava.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kunyi.bitamexJava.dao.AdminRecordsService;
import com.kunyi.bitamexJava.model.AdminRecordsTable;
import com.kunyi.bitamexJava.model.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截管理员所有的增删改操作
 * 并将其插入管理员操作记录表
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
	@Autowired
	private AdminRecordsService adminRecordsDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	//在controller方法后执行,记录操作
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HttpSession session = request.getSession(false);
		if(session == null){
			//如果session为空，则不做记录
			logger.error("Interceptor 中Session为空");
			HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		}else{
			String result = (String)request.getAttribute("response");
			if(result == null){
				addAdminRecord(request,false);
				logger.error("result is null");
			}else if(result.contains("200")){
				addAdminRecord(request,true);
			}else {
				addAdminRecord(request,false);
				logger.error(result);
			}
		}
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * 记录管理员操作
	 * 插入管理员操作记录表
	 * @param request
	 * @param result	操作成功为true，失败为false
	 */
	private void addAdminRecord(HttpServletRequest request, boolean result) {
		String requestURI = request.getRequestURI();
		if(requestURI == null || !result)
			return;
		HttpSession session = request.getSession(false);
		String name = (String)session.getAttribute("name");
		String roleID = (String) session.getAttribute("roleID");
		if(name == null){
			logger.info("没有拿到管理员姓名");
			return;
		}
		AdminRecordsTable aRecordsTable = new AdminRecordsTable();
		aRecordsTable.setM_name(name);
		aRecordsTable.setM_roleID(roleID);
		aRecordsTable.setM_operateTime(System.currentTimeMillis());
		String operation = null;
		switch (requestURI){
			case Constants.ADD_ADMIN_URL :
				String adminname = request.getParameter("name");
				String adminroleID = request.getParameter("roleid");
				operation = String.format(Constants.ADD_ADMIN,adminname,adminroleID);
				break;
			case Constants.DEL_ADMIN_URL :
				operation = String.format(Constants.DEL_ADMIN,request.getParameter("account"));
				break;
			case Constants.MODIFY_ADMIN_STATUS_URL :
				operation = String.format(Constants.MODIFY_ADMIN_STATUS,request.getParameter("account"));
				break;
			case Constants.MODIFY_ADMIN_URL :
				operation = String.format(Constants.MODIFY_ADMIN,request.getParameter("account"));
				break;
			case Constants.ADD_ADMIN_RIGHT_URL :
				operation = String.format(Constants.ADD_ADMIN_RIGHT,request.getParameter("name"));
				break;
			case Constants.ADD_ADMIN_ROLE_URL :
				operation = String.format(Constants.ADD_ADMIN_ROLE,request.getParameter("name"),request.getParameter("rights"));
				break;
			case Constants.MODIFY_USER_INFO_URL :
				operation = String.format(Constants.MODIFY_USER_INFO,request.getParameter("name"));
				break;
			case Constants.ADD_USER_RIGHT_URL :
				operation = String.format(Constants.ADD_USER_RIGHT,request.getParameter("name"));
				break;
			case Constants.ADD_USER_ROLE_URL :
				operation = String.format(Constants.ADD_USER_ROLE,request.getParameter("name"),request.getParameter("rights"));
				break;
		}
		aRecordsTable.setM_operation(operation);
		adminRecordsDao.addAdminRecords(aRecordsTable);
		logger.info("新增操作记录成功" + aRecordsTable.ConvertObjectToJson());
	}
}
