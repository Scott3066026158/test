package gaia.util;


import java.util.ArrayList;

import gaia.data.LoginInfo;
import gaia.data.UserClientTable;
import gaia.mongo.MongoService;

public class Datacenter {
	
	//���е�userClientTable����
	private static ArrayList<UserClientTable> userClientTables;
	
	//���е�LoginInfo����
	private static ArrayList<LoginInfo> loginInfos;
	
	
	
	//���ݿ����
	private static MongoService m_mongoService = new MongoService(
										"161.202.101.108",
										27017,
										"gaiaexchange",
										"gaiamongo123456",
										"gaiaexchange");

	public static MongoService getM_mongoService() {
		return m_mongoService;
	}

	public static ArrayList<UserClientTable> getUserClientTables() {
		return userClientTables;
	}

	public static void setUserClientTables(ArrayList<UserClientTable> userClientTables) {
		Datacenter.userClientTables = userClientTables;
	}

	public static ArrayList<LoginInfo> getLoginInfos() {
		return loginInfos;
	}

	public static void setLoginInfos(ArrayList<LoginInfo> loginInfos) {
		Datacenter.loginInfos = loginInfos;
	}
	
	public static void update() {
		ArrayList<UserClientTable> list = (ArrayList<UserClientTable>)Datacenter.getM_mongoService().
				QueryForList("UserClientTable", UserClientTable.class, null);
		Datacenter.setUserClientTables(list);
		
		ArrayList<LoginInfo> loginInfos = (ArrayList<LoginInfo>)Datacenter.getM_mongoService().
				QueryForList("LoginInfo", LoginInfo.class, null);
		Datacenter.setLoginInfos(loginInfos);
	}
	
		
}
