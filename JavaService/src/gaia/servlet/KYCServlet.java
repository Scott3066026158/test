package gaia.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import gaia.data.LoginInfo;
import gaia.data.UserClientTable;
import gaia.util.Datacenter;

public class KYCServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String traderID = req.getParameter("traderID");
		Datacenter.update();
		ArrayList<LoginInfo> list = Datacenter.getLoginInfos();
		int userID = 0;
		for(LoginInfo loginInfo : list){
			if(loginInfo.m_traderID.equals(traderID)){
				req.setAttribute("loginInfo", loginInfo);
				userID = loginInfo.m_userID;
			}
		}
		String path = "C:/javaIDE/javaeeProject/upload/" + userID;
		File file = new File(path);
		if(file.exists() && file.isDirectory()){
			for(File file2 : file.listFiles()){
				String fString = file2.getName();
				if(fString.contains("imgFront")){
					String frontPath = userID + "/" + file2.getName();
					req.setAttribute("frontPath", frontPath);
					continue;
				}else if(fString.contains("imgBack")){
					String backPath = userID + "/" + file2.getName();
					req.setAttribute("backPath", backPath);
					continue;
				}else {
					Map<String, String> map = parseFile(file2);
					req.setAttribute("map", map);
					continue;
				}
			}
		}
		req.getRequestDispatcher("KYC.jsp").forward(req, resp);
	}
	
	private Map<String, String> parseFile(File file2) {
		Map<String, String> map = new HashMap<>();
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(
										new InputStreamReader( new FileInputStream(file2),"utf-8") );
			String line = null;
			while((line = bReader.readLine()) != null){
				String[] str = line.split("=");
				map.put(str[0].trim(), str[1].trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(bReader != null){
				try {
					bReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
