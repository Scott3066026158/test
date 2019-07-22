package com.kunyi.bitamexJava.model;

/**
 * 管理员权限表
 *
 * @author GAIA
 * @create 2019-05-14-17:32
 */
public class AdminRightTable {

	/**
	 * 权限ID
	 */
	private Integer m_rightID;
	
    /**
     * 权限名称
     */
    private String m_name;

    /**
     * 创建人
     */
    private String m_founder;

    /**
     * 创建时间
     */
    private Long m_createTime;

    /**
     * 权限状态,默认为1开启，0为关闭
     */
    private Integer m_status = 1;

    public String ConvertObjectToJson(){
        if(m_name == null || m_founder == null || m_createTime == 0){
            return null;
        }
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{\"name\":\"");
        sBuilder.append(m_name);
        sBuilder.append("\",\"founder\":\"");
        sBuilder.append(m_founder);
        sBuilder.append("\",\"createTime\":");
        sBuilder.append(m_createTime);
        sBuilder.append(",\"status\":");
        sBuilder.append(m_status);
        sBuilder.append("}");
        return sBuilder.toString();
    }

    public Integer getM_rightID() {
		return m_rightID;
	}


	public void setM_rightID(Integer m_rightID) {
		this.m_rightID = m_rightID;
	}


	public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
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
}

