package gaia.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import gaia.data.LoginInfo;
import gaia.data.SimpleData;
import gaia.data.UserClientTable;
import gaia.util.Datacenter;

public class ManagementServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page = req.getParameter("page");
		if(page == null){
			req.setAttribute("page", 500);
			req.getRequestDispatcher("usermanagement.jsp").forward(req, resp);
		}else{
			if("All".equals(page)){
				req.setAttribute("page", 1);
			}else if("KYC".equals(page)){
				req.setAttribute("page", 0);
			}
			
			Datacenter.update();
			ArrayList<SimpleData> simpleList = TurnToSimpleData(Datacenter.getUserClientTables(),Datacenter.getLoginInfos());
			req.setAttribute("UserInfo", simpleList);
			
			req.getRequestDispatcher("usermanagement.jsp").forward(req, resp);
		}
	}
	
	private ArrayList<SimpleData> TurnToSimpleData(ArrayList<UserClientTable> list, ArrayList<LoginInfo> loginInfos) {
		ArrayList<SimpleData> sArrayList = new ArrayList<>();
		for(UserClientTable userClientTable : list){
			SimpleData simpleData = new SimpleData();
			simpleData.m_traderID = userClientTable.m_traderID;
			simpleData.m_traderName = userClientTable.m_traderName;
			simpleData.m_userClientType = userClientTable.m_userClientType;
			if(!"".equals(userClientTable.m_userInfoPhone)){
				simpleData.m_userInfoPhone = userClientTable.m_userInfoPhone.substring(2);
			}
			if(userClientTable.m_userNickName.contains("@")){
				simpleData.m_registerType = "邮箱";
			}else{
				simpleData.m_registerType = "手机";
			}
			for(LoginInfo loginInfo : loginInfos){
				if(loginInfo.m_traderID.equals(simpleData.m_traderID)){
					simpleData.m_userID = loginInfo.m_userID;
					break;
				}
			}
			sArrayList.add(simpleData);
		}
		
		return sArrayList;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
