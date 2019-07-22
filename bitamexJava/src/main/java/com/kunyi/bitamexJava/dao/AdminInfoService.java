package com.kunyi.bitamexJava.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.kunyi.bitamexJava.model.AdminInfoTable;

/**
 * 管理员信息操作接口
 *
 * @author GAIA
 * @create 2019-05-15-8:27
 */
public interface AdminInfoService {

    /**
     * 保存管理员信息到mongo
     * @param infoTable
     */
	boolean addAdminInfo(AdminInfoTable infoTable);
    
    /**
     * 根据管理员账户信息删除管理员信息
     * @param account
     */
    boolean deleteAdminInfo(String account); 

    /**
     * 更新管理员账户部分信息
     * @param infoTable
     */
    boolean updateAdminInfo(AdminInfoTable infoTable);
    
    /**
     * 修改管理员状态信息
     * @return
     */
    boolean updateAdminStatus(String account,Integer status);
    
    /**
     * 有条件的查询管理员信息
     * @param name
     * @param roleID
     * @param startTime
     * @param endTime 
     * @return
     */
    List<AdminInfoTable> queryAdminInfo(String name,String roleID,String startTime, String endTime);

    /**
     * 管理员登录
     * @param account
     * @param password
     * @return
     */
	String adminlogin(String account, String password,HttpSession session);
}
