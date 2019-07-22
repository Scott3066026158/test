package com.kunyi.bitamexJava.model;

/**
 * 管理员信息表
 *
 * @author GAIA
 * @create 2019-05-14-17:27
 */
public class AdminInfoTable {

    /**
     * 管理员角色姓名
     */
    private String m_name;

    /**
     * 手机号
     */
    private String m_tel;

    /**
     * 角色名，与角色表关联
     */
    private String m_roleID;

    /**
     * 账户
     */
    private String m_account;

    /**
     * 密码
     */
    private String m_pwd;

    /**
     * 账户创建时间
     */
    private Long m_createTime;

    /**
     * 最新登录时间
     */
    private Long m_loginTime;

    /**
     * 账户状态,0为关闭，1为开启，默认开启状态
     */
    private Integer m_status = 1;

    public String ConvertObjectToJson(){
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{");
        sBuilder.append("\"name\":\"");
        sBuilder.append(m_name);
        sBuilder.append("\",");
        sBuilder.append("\"phone\":\"");
        sBuilder.append(m_tel);
        sBuilder.append("\",");
        sBuilder.append("\"roleID\":\"");
        sBuilder.append(m_roleID);
        sBuilder.append("\",");
        sBuilder.append("\"account\":\"");
        sBuilder.append(m_account);
        sBuilder.append("\",");
        sBuilder.append("\"password\":\"");
        sBuilder.append(m_pwd);
        sBuilder.append("\",");
        sBuilder.append("\"createTime\":\"");
        sBuilder.append(m_createTime);
        sBuilder.append("\",");
        sBuilder.append("\"loginTime\":\"");
        sBuilder.append(m_loginTime);
        sBuilder.append("\",");
        sBuilder.append("\"status\":\"");
        sBuilder.append(m_status);
        sBuilder.append("\"}");
        return sBuilder.toString();
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_tel() {
        return m_tel;
    }

    public void setM_tel(String m_tel) {
        this.m_tel = m_tel;
    }

    public String getM_roleID() {
		return m_roleID;
	}

	public void setM_roleID(String m_roleID) {
		this.m_roleID = m_roleID;
	}

	public String getM_account() {
        return m_account;
    }

    public void setM_account(String m_account) {
        this.m_account = m_account;
    }

    public String getM_pwd() {
        return m_pwd;
    }

    public void setM_pwd(String m_pwd) {
        this.m_pwd = m_pwd;
    }

    public Long getM_createTime() {
        return m_createTime;
    }

    public void setM_createTime(Long m_createTime) {
        this.m_createTime = m_createTime;
    }

    public Long getM_loginTime() {
        return m_loginTime;
    }

    public void setM_loginTime(Long m_loginTime) {
        this.m_loginTime = m_loginTime;
    }

    public Integer getM_status() {
        return m_status;
    }

    public void setM_status(Integer m_status) {
        this.m_status = m_status;
    }
}

