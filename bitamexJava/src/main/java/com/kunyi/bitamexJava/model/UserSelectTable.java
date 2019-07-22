package com.kunyi.bitamexJava.model;

/**
 * 用户自选股
 *
 * @author GAIA
 * @create 2019-05-14-17:48
 */
public class UserSelectTable {
    private String _id;
    //股票代码
    private String m_codes;
    // 列表顺序
    private Integer m_orderNum;
    //类别
    private Integer m_type;
    //用户ID
    private String m_traderID;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getM_codes() {
        return m_codes;
    }

    public void setM_codes(String m_codes) {
        this.m_codes = m_codes;
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

    public String getM_traderID() {
        return m_traderID;
    }

    public void setM_traderID(String m_traderID) {
        this.m_traderID = m_traderID;
    }
}
