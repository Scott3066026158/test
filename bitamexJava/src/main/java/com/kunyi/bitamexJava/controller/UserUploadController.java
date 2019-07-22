package com.kunyi.bitamexJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kunyi.bitamexJava.dao.UserUploadService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
@RequestMapping("/JavaService")
public class UserUploadController {
	@Autowired
	private UserUploadService uploadService;
	
	@PostMapping("/uploadservice")
	public String uploadPhoto(@RequestParam("userId")String userID,
 				              @RequestParam("id")String id,
				              @RequestParam("name")String name,
				              @RequestParam("imgFront")MultipartFile imgFront,
				              @RequestParam("imgBack")MultipartFile imgBack){
		boolean ret1 = uploadService.saveUserInfo(userID,id,name);
		
		boolean ret2 = uploadService.saveFile(userID,imgFront,imgBack);
		boolean result = ret1 && ret2;
		if(result){
			result = uploadService.modifyUserStatus(userID);
		}
		return (result) ? "true" : "false";
	}
}
