package com.kunyi.bitamexJava.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kunyi.bitamexJava.dao.UserUploadService;
import com.kunyi.bitamexJava.model.LoginInfo;
import com.kunyi.bitamexJava.model.UserClientTable;
import com.kunyi.bitamexJava.model.UserInfoTable;

@Service
public class UserUploadServiceImpl implements UserUploadService{

	private static String savePath = System.getProperty("user.dir") + "\\upload";
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public boolean saveUserInfo(String userID, String id, String name) {	
	    File file = new File(savePath);
	    //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
        String fileDir = savePath + "\\" + userID;
        File userFile = new File(fileDir);
        if(!userFile.exists() && !userFile.isDirectory()){
        	userFile.mkdirs();
        }
        try{
            FileOutputStream out = new FileOutputStream(new File(fileDir + "\\" + userID + ".txt"));
            out.write(("userId = " + userID +"\r\n").getBytes("UTF-8"));
            out.write(("name = " + name + "\r\n").getBytes("UTF-8"));
            out.write(("id = " + id + "\r\n").getBytes("UTF-8"));
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
		return true;
	}

	@Override
	public boolean saveFile(String userID,MultipartFile imgFront, MultipartFile imgBack) {
		String path = savePath + "\\" + userID;
		File file = new File(path);
		 //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(path + "目录不存在，需要创建");
            //创建目录
            file.mkdir();
        }
		File[] filelist = file.listFiles();
		 //如果文件存在則刪除
        if(filelist != null){
            for(File f : filelist){
            	String fileName = f.getName();
                if(fileName.contains("imgFront") || fileName.contains("imgBack")){
                    f.delete();
                }
            }
        }
        String frontOriginalFilename = imgFront.getOriginalFilename();
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
        	//存身份证前的照片
        	inputStream = imgFront.getInputStream();
        	outputStream = new FileOutputStream(
        			path + "\\imgFront" + frontOriginalFilename.substring(frontOriginalFilename.lastIndexOf(".")));
        	//创建一个缓冲区
            byte buffer[] = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int len = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while((len=inputStream.read(buffer))>0){
                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
            	outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
          //存身份证后的照片
            String backOriginalFilename = imgBack.getOriginalFilename();
            inputStream = imgBack.getInputStream();
        	outputStream = new FileOutputStream(
        			path + "\\imgBack" + backOriginalFilename.substring(backOriginalFilename.lastIndexOf(".")));
        	//创建一个缓冲区
            byte buf[] = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int lenngth = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while((lenngth = inputStream.read(buf))>0){
                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
            	outputStream.write(buf, 0, lenngth);
            }
            inputStream.close();
            outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean modifyUserStatus(String userID) {
		if(userID == null){
			return false;
		}
		Query query1 = new Query(Criteria.where("m_userID").is(userID));
		UserInfoTable logininfo =  mongoTemplate.findOne(query1, UserInfoTable.class,"UserInfoTable");
		if(logininfo == null){
			return false;
		}
		
		Update update1 = new Update().set("m_userClientType", "2");
		mongoTemplate.updateFirst(query1,update1, UserInfoTable.class, "UserInfoTable");
		return true;
	}

	
}
