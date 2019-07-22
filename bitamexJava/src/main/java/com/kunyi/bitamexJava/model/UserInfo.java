package com.kunyi.bitamexJava.model;

/**
 * 中间对象
 *
 * @author GAIA
 * @create 2019-05-14-17:44
 */
public class UserInfo {
    /**
     * 用户姓名
     */
    public String name;

    /**
     * 手机区号
     */
    public String areaCode;
    /**
     * 手机号
     */
    public String phone;

    /**
     * 用户昵称
     */
    public String nickName;

    /**
     * 身份证号
     */
    public String IDcard;

    /**
     * 角色
     */
    public String role;

    /**
     * 认证等级
     */
    public String range;

    /**
     * 审核状态
     */
    public String authstatus;

    /**
     * 注册时间
     */
    public String registerTime;

    /**
     * 用户来源
     */
    public String userSource;

    /**
     * 账户状态 默认为1,关闭为0
     */
    public int accountStatus = 1;

    public String ConvertObjectToJson(){
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{\"name\":\"");
        sBuilder.append(name);
        sBuilder.append("\",\"phone\":\"");
        sBuilder.append(phone);
        sBuilder.append("\",\"nickName\":\"");
        sBuilder.append(nickName);
        sBuilder.append("\",\"idcard\":\"");
        sBuilder.append(IDcard);
        sBuilder.append("\",\"role\":\"");
        sBuilder.append(role);
        sBuilder.append("\",\"range\":\"");
        sBuilder.append(range);
        sBuilder.append("\",\"authstatus\":\"");
        sBuilder.append(authstatus);
        sBuilder.append("\",\"registerTime\":\"");
        sBuilder.append(registerTime);
        sBuilder.append("\",\"accountstatus\":\"");
        sBuilder.append(accountStatus);
        sBuilder.append("\",\"usersource\":\"");
        sBuilder.append(userSource);
        sBuilder.append("\",");
        sBuilder.append("\"areacode\":\"");
        sBuilder.append(areaCode);
        sBuilder.append("\"}");
        return sBuilder.toString();
    }
}
