package com.kunyi.bitamexJava.model;

import java.util.Arrays;
import java.util.List;

/**
 * 管理员角色表
 *
 * @author GAIA
 * @create 2019-05-14-17:33
 */
public class AdminRoleTable {

    /**
     * 角色名称
     */
    private String m_name;

    /**
     * 角色ID
     */
    private Integer m_roleID;

    /**
     * 角色创建者
     */
    private String m_founder;

    /**
     * 创建时间
     */
    private Long m_createTime;

    /**
     * 状态，默认为1开启，0为关闭
     */
    private Integer m_status = 1;

    /**
     * 权限集合与权限表关联
     */
    private String m_rights;

    public String ConvertObjectToJson() {
        if(m_name == null || m_founder == null || m_createTime == 0){
            return null;
        }
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{\"name\":\"");
        sBuilder.append(m_name);
        sBuilder.append("\",\"founder\":\"");
        sBuilder.append(m_founder);
        sBuilder.append("\",\"roleID\":");
        sBuilder.append(m_roleID);
        sBuilder.append(",\"createTime\":\"");
        sBuilder.append(m_createTime);
        sBuilder.append("\",\"status\":");
        sBuilder.append(m_status);
        sBuilder.append(",\"rights\":\"");
        sBuilder.append(m_rights);
        sBuilder.append("\"}");

        return sBuilder.toString();
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public Integer getM_roleID() {
        return m_roleID;
    }

    public void setM_roleID(Integer m_roleID) {
        this.m_roleID = m_roleID;
    }

    public String getM_founder() {
        return m_founder;
    }

    public void setM_founder(String m_founder) {
        this.m_founder = m_founder;
    }

    public Long getM_createTime() {
        return m_createTime;
    }

    public void setM_createTime(Long m_createTime) {
        this.m_createTime = m_createTime;
    }

    public Integer getM_status() {
        return m_status;
    }

    public void setM_status(Integer m_status) {
        this.m_status = m_status;
    }

	public String getM_rights() {
		return m_rights;
	}

	public void setM_rights(String m_rights) {
		this.m_rights = m_rights;
	}
}
