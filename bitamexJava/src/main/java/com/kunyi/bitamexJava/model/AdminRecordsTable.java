package com.kunyi.bitamexJava.model;

/**
 * 管理员操作记录表
 *
 * @author GAIA
 * @create 2019-05-14-17:30
 */
public class AdminRecordsTable {

    /**
     * 管理员姓名
     */
    private String m_name;

    /**
     * 管理员角色ID，与角色表关联
     */
    private String m_roleID;

    /**
     * 操作时间
     */
    private Long m_operateTime;

    /**
     * 具体操作说明
     */
    private String m_operation;

    public String ConvertObjectToJson(){
        if(m_name == null || m_operateTime == 0 || m_operation == null){
            return null;
        }
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("{\"name\":\"");
        sBuilder.append(m_name);
        sBuilder.append("\",\"roleID\":\"");
        sBuilder.append(m_roleID);
        sBuilder.append("\",\"operateTime\":");
        sBuilder.append(m_operateTime);
        sBuilder.append(",\"operation\":\"");
        sBuilder.append(m_operation);
        sBuilder.append("\"}");
        return sBuilder.toString();
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_roleID() {
		return m_roleID;
	}

	public void setM_roleID(String m_roleID) {
		this.m_roleID = m_roleID;
	}

	public Long getM_operateTime() {
        return m_operateTime;
    }

    public void setM_operateTime(Long m_operateTime) {
        this.m_operateTime = m_operateTime;
    }

    public String getM_operation() {
        return m_operation;
    }

    public void setM_operation(String m_operation) {
        this.m_operation = m_operation;
    }
}

