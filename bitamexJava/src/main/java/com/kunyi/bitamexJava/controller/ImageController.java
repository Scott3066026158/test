package com.kunyi.bitamexJava.controller;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kunyi.bitamexJava.dao.ImageService;
import com.kunyi.bitamexJava.model.JsonMessage;

import springfox.documentation.annotations.ApiIgnore;


@RestController
@ApiIgnore
@RequestMapping("/image")
public class ImageController {
	@Autowired
	private ImageService imageService;
	
	@RequestMapping(value = "/get1")
	public String getPhoto(@RequestParam("phone")String phone,@RequestParam("img")String img){
		String userId = imageService.getUserId(phone);
		if("".equals(userId))
			return null;
		String fileUrl = System.getProperty("user.dir") + "\\upload\\" + userId;
		File file = new File(fileUrl);
		if(!file.exists() || !file.isDirectory())
			return null;
		
		File[] files = file.listFiles();
		for (File f : files) {
			String name = f.getName();
			if(name.contains(".")){
				name.substring(0, name.lastIndexOf("."));
			}
			if(name.contains(img)){
				JsonMessage jMessage = new JsonMessage();
				jMessage.setCode(200);
				jMessage.setMsg("成功");
				jMessage.setData("{\"url\":\"http://192.168.86.205:8081/image/get/" + userId + "/" + name + "\"}");
				String result = jMessage.toJson();
				return result;
			}
		}
		return null;
	}
	
	
	
	@RequestMapping(value = "/get/{userId}/{img}",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImage(@PathVariable Integer userId,@PathVariable String img){
		if("".equals(userId))
			return null;
		String fileUrl = System.getProperty("user.dir") + "\\upload\\" + userId;
		File file = new File(fileUrl);
		if(!file.exists() || !file.isDirectory())
			return null;
		
		File[] files = file.listFiles();
		for (File f : files) {
			String name = f.getName();
			if(name.contains(img)){
				try {
					FileInputStream in = new FileInputStream(f);
					byte[] bytes = new byte[in.available()];
					in.read(bytes, 0, in.available());
					return bytes;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
