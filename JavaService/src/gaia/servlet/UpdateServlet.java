package gaia.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import gaia.start.BiTAmexVersion;
import gaia.update.VersionInfo;
import gaia.util.PublicMethod;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet
{
	/**
	 * 注册服务特殊标识符表
	 */
	//服务端未出现异常,成功返回
	private static final String SUCCESS= "1";
	//客户端的参数异常
	private static final String INVALID_PARAM_EXCEPTION= "-1";
	//平台类型非法(除ios/android之外的平台)
	private static final String PLATFORM_EXCEPTION = "-2";
	//语言类型非法(除en/cn之外的语言)
	private static final String LANGEUAGE_EXCEPTION = "-3";
	//获取指定版本内容失败
	private static final String VERINFO_GET_FAILD = "-4";
	
	//服务中文名(打印Log时使用)
	private static final String SERVICE_NAME = "版本更新信息服务:";

	//更新本地和内存更新内容的锁对象
	public static final Object LOCK = new Object();
	//版本更新内容存放地址
	private static String UPDATE_PATH = BiTAmexVersion.UPDATE_PATH;
	
	public static String IOS_CN = "";
	public static String IOS_EN = "";
	public static String ANDROID_CN = "";
	public static String ANDROID_EN = "";
	
	public UpdateServlet()
	{
		super();
	}

	/**
	 * 用于打印并返回内容
	 * @param response 接收者对象
	 * @param content  错误内容
	 * @throws ServletException  
	 * @throws IOException
	 */
	protected void respPostContent(HttpServletResponse response, String content) throws ServletException, IOException
	{
		JSONObject object = new JSONObject();
		object.put("result", content);
		response.setContentType("application/json");
		response.getWriter().write(object.toJSONString());
		response.getWriter().flush();
		System.out.println(SERVICE_NAME + content);
	}
	
	/**
	 * 用于打印并返回内容
	 * @param response 接收者对象
	 * @param content  错误内容
	 * @throws ServletException  
	 * @throws IOException
	 */
	protected void respGetContent(HttpServletResponse response, String content) throws ServletException, IOException
	{
		response.setHeader("Content-type", "text/html;charset=UTF8");
		response.getWriter().write(content);
		response.getWriter().flush();
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	/**
	 * 接收Post请求
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String functionID = request.getParameter("functionid");
		if(PublicMethod.IsEmpty(functionID))
		{
			respGetContent(response, INVALID_PARAM_EXCEPTION);
			System.out.println(SERVICE_NAME + "functionID=" +functionID + ",functionID为空!");
			return ;
		}
		switch(functionID) {
		case "set":
			setVersionInfo(request, response);
			break;
		case "get":
			getVersionInfo(request, response);
			break;
		case "getversion":
			String maxversion = (String)getServletContext().getAttribute("maxversion");
			response.getWriter().write(maxversion);
			break;
		default:
			respGetContent(response, INVALID_PARAM_EXCEPTION);
			System.out.println(SERVICE_NAME + "functionID=" +functionID + ",functionID异常!");
			break;
		}
	}
	
	private void getVersionInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//平台(ios/android)
		String platform = request.getParameter("platform");
		//语言(en/cn)
		String language = request.getParameter("lang");
		//版本号
		String version = request.getParameter("version");
		//判断参数是否正常
		if(PublicMethod.IsEmpty(platform, language, version))
		{
			respGetContent(response, INVALID_PARAM_EXCEPTION);
			System.out.println(SERVICE_NAME + "platform=" + platform + ",language=" + language + ",version=" + version + ",参数异常!");
			return;
		}
		
		//检查平台的合法性
		if(!(platform.equalsIgnoreCase("ios") || platform.equalsIgnoreCase("android")))
		{
			respGetContent(response, PLATFORM_EXCEPTION);
			System.out.println(SERVICE_NAME + "platform=" + platform + ",language=" + language + ",platform非法!");
			return; 
		}
		
		//检查语言的合法性
		if(!(language.equalsIgnoreCase("en") || language.equalsIgnoreCase("cn")))
		{
			respGetContent(response, LANGEUAGE_EXCEPTION);
			System.out.println(SERVICE_NAME + "platform=" + platform + ",language=" + language + ",version=" + version + ",language非法!");
			return;
		}
		
		//检查是否成功获取版本信息
		String verInfo = GetVersionInfo(platform, language, version);
		if(verInfo == null)
		{
			respGetContent(response, VERINFO_GET_FAILD);
			System.out.println(SERVICE_NAME + "platform=" + platform + ",language=" + language + ",version=" + version + ",版本信息获取失败!");
			return; 
		}
		
		respGetContent(response, verInfo);
		System.out.println(SERVICE_NAME + "platform=" + platform + ",language=" + language + ",version=" + version + ",版本信息获取成功!");
		return; 
	}
	
	/**
	 * 获取指定的版本信息
	 * @param platform 平台
	 * @param language 语言
	 * @param version 版本号
	 * @return 成功返回指定的版本信息,失败返回null
	 */
	private String GetVersionInfo(String platform, String language, String version)
	{
		//判断是否获取最新版本信息
		if(version.equals("max"))
		{
			String maxVerInfo = GetMaxVersionInfo(platform, language);
			return maxVerInfo;
		}
		
		//判断是否成功获取文件路径
		String verFilepath = getVersionPath(platform, language, version);
		if(verFilepath == null)
		{
			return null;
		}
		
		//判断是否成功获取版本内容
		String verInfo = readFileInfo(new File(verFilepath));
		if(verInfo == null)
		{
			return null;
		}
		return verInfo;
	}
	
	/**
	 * 获取指定版本信息的文件路径
	 * @param version 
	 * @return 成功返回文件路径,失败返回null
	 */
	private String getVersionPath(String platform, String language,String version)
	{
		try
		{
			//判断更新文件夹是否存在
			File updateDirectory = new File(UPDATE_PATH);
			if(!updateDirectory.isDirectory())
			{
				return null;
			}
			
			//是否成功获取指定版本信息文件路径标记,null表示失败,成功path获取路径
			String path = null;
			one:
			for(File verDir : updateDirectory.listFiles())
			{
				if(verDir.getName().equals(version))
				{
					File[] verInfoFiles = verDir.listFiles();
					for(File file : verInfoFiles)
					{
						if(!file.isFile())
						{
							continue;
						}
						if(!file.getName().equals(platform + language + ".txt"))
						{
							continue;
						}
						path = file.getAbsolutePath();
					}
				}
			}
			
			if(path == null)
			{
				return null;
			}
			
			return path;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 读取指定文件内容
	 * @param path 文件所在路径
	 * @return 读取承成功返回文件内容,失败返回null
	 */
	private String readFileInfo(File path)
	{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "gbk"));
			StringBuilder sb = new StringBuilder();
			String content = null;
			if((content = br.readLine()) != null)
			{
				sb.append(content).append(System.lineSeparator());                                                                                                         
			}
			br.close();
			return sb.toString();
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取最新的版本信息 
	 * @param platform 平台
	 * @param language 语言
	 * @return 成功返回最新版本信息,失败返回null
	 */
	public String GetMaxVersionInfo(String platform, String language)
	{
		String result;
		synchronized (LOCK)
		{
			switch((platform + language).toLowerCase()){
				case "iosen":
					result = IOS_EN;
					break;
				case "ioscn":
					result = IOS_CN;
					break;
				case "androiden":
					result = ANDROID_EN;
					break;
				case "androidcn":
					result = ANDROID_CN;
					break;
				default:
					result = null;
					break;
			}
		}
		return result;
	}
	
	
	
	private void setVersionInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//判断内容是否指定类型
		String contentType = request.getHeader("Content-Type");
		if (contentType == null || !contentType.equals("application/json"))
		{
			respGetContent(response, "ContentType=" + contentType + ",上传的更新ContentType不符合要求,必须为application/json!");
			return;
		}
		
		//判断读取内容
		String content = readContent(request);
		if(content == null)
		{
			respGetContent(response, "读取上传内容出错!");
			return;
		}
		
		//判断更新本地文件结果
		VersionInfo info = JSON.parseObject(content, VersionInfo.class);
		boolean isSuccess = UpdateLocalFile(info, content);
		if(!isSuccess)
		{
			respGetContent(response, "更新本地历史版本信息文件出错!");
			return;
		}
		
		//判断更新内存版本内容结果
		String updateContent = UpdateMemoryData(info, content);
		if(!updateContent.equals("success"))
		{
			respGetContent(response, "更新内存最新版本信息出错!");
			return;
		}
		
		respGetContent(response, "更新成功!");
		return ;
	}
	/**
	 * 读取上传的内容
	 * @param req 请求者对象
	 * @return 读取成功返回读取内容,失败返回null
	 */
	private String readContent(HttpServletRequest req)
	{
		try
		{
			//设置请求内容的解码格式
			req.setCharacterEncoding("UTF-8");
			BufferedReader reader = req.getReader();
			StringBuilder sbBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				sbBuilder.append(line).append(System.lineSeparator());
			}
			if (sbBuilder.length() <= 0)
			{
				return null;
			}
			return sbBuilder.toString();
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	//更新本地文件
	private static boolean UpdateLocalFile(VersionInfo info,String content)
	{
		try
		{
			//若更新内容已经存在,则覆盖,若不存在则创建,文件夹名为版本号
			File versionDirectory = new File(UPDATE_PATH +  "/" + info.versionString);
			if(!versionDirectory.isDirectory())
			{
				versionDirectory.mkdirs();
			}
			File updateContentFile = new File(versionDirectory + "/" + info.platform + info.language + ".txt");
			if(!updateContentFile.isFile())
			{
				updateContentFile.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(updateContentFile), "gbk"));
			bw.write(content);
			
			bw.flush();
			bw.close();
			return true;
		}catch(Exception e)
		{
			System.out.println("更新文件创建异常:BiTAmexVersion.Update");
			e.printStackTrace();
			return false;
		}
	}
	
	//更新内存数据
	private static String UpdateMemoryData(VersionInfo info, String content)
	{
		//检查参数的合法性
		if(!(info.platform.equalsIgnoreCase("ios") || info.platform.equalsIgnoreCase("android")))
		{
			return "unknow platform"; 
		}
		if(!(info.language.equalsIgnoreCase("en") || info.language.equalsIgnoreCase("cn")))
		{
			return "unknow langeuage"; 
		}
		synchronized (LOCK)
		{
			switch((info.platform + info.language).toLowerCase()){
				case "iosen":
					IOS_EN = content;
					break;
				case "ioscn":
					IOS_CN = content;
					break;
				case "androiden":
					ANDROID_EN = content;
					break;
				case "androidcn":
					ANDROID_CN = content;
					break;
				default:
					break;
			}
			return "success";
		}
	}

}