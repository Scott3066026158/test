package gaia.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import gaia.data.ResultMapping;
import gaia.flag.BusinessIDs;
import gaia.util.PublicMethod;

@WebServlet(asyncSupported = true, name = "Recommend", urlPatterns = { "/Recommend" })
public class RecommendServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7547084291783035294L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String appid = req.getParameter("appid");
		if(appid.isEmpty())
		{
			System.out.println("appid为空!!!");
			return;
		}
		List<String> array = ReadRecommendStock(req);
		if (array.isEmpty()) {
			return;
		}
		if (appid.equals("web")) {
			System.out.println("web");
			ResultMapping<String> result = new ResultMapping<String>();
			result.m_data = array;
			result.m_functionID = BusinessIDs.RSP_RECOMMEND_CODES + "";
			String jsonString = JSON.toJSONString(result);
			resp.getWriter().write(jsonString);
		} else if (appid.equals("ios") || appid.equals("android")) {
			System.out.println("android/ios");
			StringBuffer sb = new StringBuffer();
			for(String r : array)
			{
				sb.append(r + ",");
			}
			String str = new String(sb);
			if(PublicMethod.IsEmpty(str))
			{
				//当推荐股未查询到,应当定义一些特殊的返回值,让客户端判别服务端出现的异常
				return;
			}
			else
			{
				str = str.substring(0, str.length()-1);
				resp.getWriter().write(str);
				return;
			}
		} else {
			System.out.println("没有此客户端");
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post");
	}

	/**
	 * 读取推荐股票文件
	 * 
	 * @param req
	 * @return 文件内容list
	 */
	private static List<String> ReadRecommendStock(HttpServletRequest req) {
		String filePath = req.getSession().getServletContext().getRealPath("/") + "/WEB-INF/RecommendedStock.txt";
		List<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String str = null;
			while ((str = br.readLine()) != null) {
				list.add(str);
			}
			br.close();
		} catch (Exception e) {
		}
		return list;
	}

}
