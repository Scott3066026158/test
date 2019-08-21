package gaia.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.conversions.Bson;

import gaia.data.LoginInfo;
import gaia.data.UserClientTable;
import gaia.mongo.MongoService;
import gaia.util.Datacenter;
import lord.common.mongo.Expression;
import lord.common.mongo.MongoExpression;

public class KYCSuccessServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String traderID = req.getParameter("traderID");
		String result = req.getParameter("result");
		MongoService mongoService = Datacenter.getM_mongoService();
		if("success".equals(result)) {
			for(UserClientTable userClientTable : Datacenter.getUserClientTables()){
				if(traderID.equals(userClientTable.getM_traderID())){
					userClientTable.setM_userClientType(3);
					List<Expression> expressions = new ArrayList<Expression>();
					expressions.add(Expression.Eq("m_traderID", userClientTable.m_traderID));
					Bson bson = MongoExpression.ExecuteParseExpressioin(expressions);
					mongoService.Update("UserClientTable", userClientTable, bson);
					break;
				}
			}
			for(LoginInfo loginInfo : Datacenter.getLoginInfos()){
				if(traderID.equals(loginInfo.m_traderID)){
					loginInfo.setM_type(3);
					List<Expression> expressions = new ArrayList<Expression>();
					expressions.add(Expression.Eq("m_traderID", loginInfo.m_traderID));
					Bson bson = MongoExpression.ExecuteParseExpressioin(expressions);
					mongoService.Update("LoginInfo", loginInfo, bson);
					break;
				}
			}
			Datacenter.update();
			req.getRequestDispatcher("success.jsp").forward(req, resp);
		}
		if("failed".equals(result)) {
			//审核不通过，将文件夹更名，数据库中字段改回
			for(UserClientTable userClientTable : Datacenter.getUserClientTables()){
				if(traderID.equals(userClientTable.getM_traderID())){
					if("".equals(userClientTable.m_userInfoPhone)) {
						userClientTable.setM_userClientType(0);
					}else {
						userClientTable.setM_userClientType(1);
					}
					List<Expression> expressions = new ArrayList<Expression>();
					expressions.add(Expression.Eq("m_traderID", userClientTable.m_traderID));
					Bson bson = MongoExpression.ExecuteParseExpressioin(expressions);
					mongoService.Update("UserClientTable", userClientTable, bson);
					break;
				}
			}
			for(LoginInfo loginInfo : Datacenter.getLoginInfos()){
				if(traderID.equals(loginInfo.m_traderID)){
					if(loginInfo.m_userName.contains("@")) {
						loginInfo.setM_type(0);
					}else {
						loginInfo.setM_type(1);
					}
					List<Expression> expressions = new ArrayList<Expression>();
					expressions.add(Expression.Eq("m_traderID", loginInfo.m_traderID));
					Bson bson = MongoExpression.ExecuteParseExpressioin(expressions);
					mongoService.Update("LoginInfo", loginInfo, bson);
					break;
				}
			}
			Datacenter.update();
			req.getRequestDispatcher("success.jsp").forward(req, resp);
		}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
