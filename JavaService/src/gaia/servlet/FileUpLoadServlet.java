package gaia.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import gaia.util.http.HttpGetService;

public class FileUpLoadServlet extends HttpServlet {

	//身份上传成功后返回新的用户权限
	public static String newUserPermission = "2";
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = "C:\\javaIDE\\javaeeProject\\upload";
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
        //消息提示
        String message = "";
        try{
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
             //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8"); 
            
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                return;
            }
            
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            String userId = null;
            String name = null;
            String id = null;
            for(FileItem item : list){
            	if(item.isFormField()){
            		String fieldName = item.getFieldName();
            		switch (fieldName) {
    				case "userId":
    					userId = item.getString("utf-8");
    					break;
    				case "name" :
    					name = item.getString("utf-8");
    					break;
    				case "id" :
    					id = item.getString("utf-8");
    					break;
    				default:
    					break;
            		}
				}
            }
            String fileDir = savePath + "\\" + userId;
            File userFile = new File(fileDir);
            ArrayList<File> filelist = new ArrayList<>();
            if(!userFile.exists() && !userFile.isDirectory()){
            	userFile.mkdirs();
            	FileOutputStream out = new FileOutputStream(new File(fileDir + "\\" + userId + ".txt"));
            	out.write(("userId = " + userId +"\r\n").getBytes("UTF-8"));
            	out.write(("name = " + name + "\r\n").getBytes("UTF-8"));
            	out.write(("id = " + id + "\r\n").getBytes("UTF-8"));
            	out.flush();
            	out.close();
            }else{
            	for(File f : userFile.listFiles()){
            		filelist.add(f);
            	}
            }
          //如果文件存在則刪除
            if(filelist != null){
            	for(File f : filelist){
                	String pr = f.getName().substring(0, f.getName().lastIndexOf("."));
                	if("imgFront".equals(pr) || "imgBack".equals(pr)){
                		f.delete();
                	}
                }
            }
            for(FileItem item : list){
                //如果fileitem中封装的不是普通输入项的数据
                if(!item.isFormField()){
                   //如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getFieldName() + item.getName().substring(item.getName().lastIndexOf("."));
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(fileDir + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传成功！";
                }
            }
            String result = HttpGetService.doGet("http://bitamex.io:13336/userpermissionservice?functionid=setuserpermission&userid="+ userId +"&userpermission=" + newUserPermission);
            switch(result)
            {
            case "-1":
            case "-2":
            case "-3":
            	response.getWriter().write("false");
            	break;
        	default:
        		response.getWriter().write(result);
        		break;
            }
        }catch (Exception e) {
            message= "文件上传失败！";
            e.printStackTrace();
            response.getWriter().write("false");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}