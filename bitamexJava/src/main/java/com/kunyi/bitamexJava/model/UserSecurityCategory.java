package com.kunyi.bitamexJava.model;

/**
 * 用户
 *
 * @author GAIA
 * @create 2019-05-14-17:46
 */
public class UserSecurityCategory {


    private String Id;
    /**
     * 类别ID
     */
    private String m_categoryID = "";

    /**
     * 股票代码
     */
    private String m_codes = "";

    /**
     * 类别名称
     */
    private String m_name = "";

    /**
     * 列表顺序
     */
    private Integer m_orderNum;

    /**
     * 类别
     */
    private Integer m_type;

    /**
     * 用户ID
     */
    private Integer m_userID;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getM_categoryID() {
        return m_categoryID;
    }

    public void setM_categoryID(String m_categoryID) {
        this.m_categoryID = m_categoryID;
    }

    public String getM_codes() {
        return m_codes;
    }

    public void setM_codes(String m_codes) {
        this.m_codes = m_codes;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public Integer getM_orderNum() {
        return m_orderNum;
    }

    public void setM_orderNum(Integer m_orderNum) {
        this.m_orderNum = m_orderNum;
    }

    public Integer getM_type() {
        return m_type;
    }

    public void setM_type(Integer m_type) {
        this.m_type = m_type;
    }

    public Integer getM_userID() {
        return m_userID;
    }

    public void setM_userID(Integer m_userID) {
        this.m_userID = m_userID;
    }
}
