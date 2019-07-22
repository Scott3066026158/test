package com.kunyi.bitamexJava.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量类
 *
 * @author GAIA
 * @create 2019-05-23-12:51
 */
public final class Constants  {
    private Constants() {}

    //管理员操作信息模板
    public static final String ADD_ADMIN = "新增一个姓名是%s,角色名是%s的管理员";
    public static final String DEL_ADMIN = "删除账户为%s的管理员";
    public static final String MODIFY_ADMIN_STATUS = "修改账户是%s的登录状态信息";
    public static final String MODIFY_ADMIN = "修改账户是%s信息";
    public static final String ADD_ADMIN_RIGHT = "新增管理员权限，权限名为%s";
    public static final String ADD_ADMIN_ROLE = "新增管理员角色，角色名为%s,对应的权限为%s";
    public static final String MODIFY_USER_INFO = "修改用户名为%s的用户信息";
    public static final String ADD_USER_RIGHT = "新增用户权限，权限名为%s";
    public static final String ADD_USER_ROLE = "新增用户角色，角色名为%s,对应的权限为%s";

    //需要拦截的请求路径
    public static final String ADD_ADMIN_URL = "/admininfoservice/addadmin";
    public static final String DEL_ADMIN_URL = "/admininfoservice/deleteadmin";
    public static final String MODIFY_ADMIN_STATUS_URL = "/admininfoservice/modifyadminstatus";
    public static final String MODIFY_ADMIN_URL = "/admininfoservice/modifyadmin";
    public static final String ADD_ADMIN_RIGHT_URL = "/adminrightservice/addright";
    public static final String ADD_ADMIN_ROLE_URL = "/adminroleservice/addrole";
    public static final String MODIFY_USER_INFO_URL = "/userinfoservice/modifyuserinfo";
    public static final String ADD_USER_RIGHT_URL = "/userrightservice/addright";
    public static final String ADD_USER_ROLE_URL = "/userroleservice/addrole";
    
    
    //需要管理员登录过滤的路径
    public static final List<String> LOGIN_FILTER_URL_LIST = new ArrayList<>();
    
    static{
    	LOGIN_FILTER_URL_LIST.add(ADD_ADMIN_URL);
    	LOGIN_FILTER_URL_LIST.add(DEL_ADMIN_URL);
    	LOGIN_FILTER_URL_LIST.add(MODIFY_ADMIN_STATUS_URL);
    	LOGIN_FILTER_URL_LIST.add(MODIFY_ADMIN_URL);
    	LOGIN_FILTER_URL_LIST.add(ADD_ADMIN_RIGHT_URL);
    	LOGIN_FILTER_URL_LIST.add(ADD_ADMIN_ROLE_URL);
    	LOGIN_FILTER_URL_LIST.add(MODIFY_USER_INFO_URL);
    	LOGIN_FILTER_URL_LIST.add(ADD_USER_RIGHT_URL);
    	LOGIN_FILTER_URL_LIST.add(ADD_USER_ROLE_URL);
    	LOGIN_FILTER_URL_LIST.add("/admininfoservice/queryadmin");
    	LOGIN_FILTER_URL_LIST.add("/adminrecordservice/queryadminrecords");
    	LOGIN_FILTER_URL_LIST.add("/adminrightservice/queryright");
    	LOGIN_FILTER_URL_LIST.add("/adminroleservice/queryrole");
    	LOGIN_FILTER_URL_LIST.add("/userinfoservice/queryuserinfo");
    	LOGIN_FILTER_URL_LIST.add("/userpermissionservice/audituser");
    	LOGIN_FILTER_URL_LIST.add("/userrightservice/queryright");
    	LOGIN_FILTER_URL_LIST.add("/userroleservice/queryrole");
    }
}
