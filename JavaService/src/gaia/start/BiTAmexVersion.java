package gaia.start;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import gaia.servlet.UpdateServlet;

@WebListener
public class BiTAmexVersion implements ServletContextListener
{

	public static String UPDATE_PATH = null;

	public void contextDestroyed(ServletContextEvent arg0)
	{

	}

	public void contextInitialized(ServletContextEvent event)
	{
		UPDATE_PATH = event.getServletContext().getRealPath("/WEB-INF/update/");
		ServletContext context = event.getServletContext();
		//创建文件夹
		File updateDirectory = new File(UPDATE_PATH);
		String maxVersion = "";
		if(!updateDirectory.isDirectory())
		{
			updateDirectory.mkdirs();
			maxVersion = "1.0.0";
		}
		else
		{
			maxVersion = ReadVersionInfo(updateDirectory);
		}
		context.setAttribute("maxversion", maxVersion);
	}

	
	
	

	/**
	 * 
	 * @param verArr
	 * @return
	 */
	public static File MaxVersion(File []verArr)
	{
        File maxVer = verArr[0];//定义最大值为该数组的第一个数
        for (int i = 1; i < verArr.length; i++) {
        	String []maxIndex = maxVer.getName().split("[.]");
        	String []verIndex = verArr[i].getName().split("[.]");
        	boolean flag = false;
        	int j = 0;
        	for(; j < maxIndex.length && j < verIndex.length; j++)
        	{
        		if(Integer.parseInt(maxIndex[j]) < Integer.parseInt(verIndex[j]))
    			{
        			flag = true;
        			break;
    			}else if(Integer.parseInt(maxIndex[j]) > Integer.parseInt(verIndex[j]))
    			{
    				flag = false;
    				break;
    			}else
    			{
    				if(maxIndex.length > verIndex.length)
    				{
    					if(j == verIndex.length - 1)
    					{
    						flag = false;
    					}
    				}
    				else if(maxIndex.length < verIndex.length)
    				{
    					if(j == maxIndex.length - 1)
    					{
    						flag = true;
    					}
    				}
    			}
        	}
        	if(flag){
            	maxVer = verArr[i];
            }
        }
        System.out.println("最大版本是：" + maxVer);
        return maxVer;
	}
	
	public static String ReadVersionInfo(File updateDirectory)
	{
		if(updateDirectory.listFiles().length == 0)
		{
			System.out.println("暂无历史版本!");
			return "1.0.0";
		}
		File maxVersion = MaxVersion(updateDirectory.listFiles());
		File []versionFiles = maxVersion.listFiles();
		for(File file : versionFiles)
		{
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
				String versionContent = br.readLine();
				String fileName = file.getName();
				switch(fileName)
				{
					case "iosen.txt":
						UpdateServlet.IOS_EN = versionContent;
						break;
					case "ioscn.txt":
						UpdateServlet.IOS_CN = versionContent;
						break;
					case "androiden.txt":
						UpdateServlet.ANDROID_EN = versionContent;
						break;
					case "androidcn.txt":
						UpdateServlet.ANDROID_CN = versionContent;
						break;
					default:
						System.out.println("BiTAmexVersion.contextInitialized:文件不存在!");
				}
				br.close();
			}catch(Exception e)
			{
				System.out.println("BiTAmexVersion.contextInitialized:读取版本文件出现异常!");
				e.printStackTrace();
				
			}
		}
		return maxVersion.getName();
	}
}
