package com.kunyi.bitamexJava.model;

/**
 * 用户登录信息表
 *
 * @author GAIA
 * @create 2019-05-14-17:37
 */
public class LoginInfo {

    /**
     * ID
     */
    private String _id = "";

    /**
     *用户ID
     */
    private Integer m_userID = 0;

    /**
     *用户名称
     */
    private String m_userName = "";

    /**
     *系统内部对应唯一用户交易账户
     * 类型为0的账户定义：UA******(6位数字+字母)，如UAABC001，每位用户通过在注册之后，自动生成一个UA账户，该类型账户无法绑定InvestorID
     * 类型为1和2的账户定义规则：TA******(6位数字+字母)，如TAABC001
     * 类型为3的账户定义规则：V..母账户..***(3位数字+字母)，“..”为连接符。如VTAABC001001
     * 类型为97的账户定义规则：GA******(6位数字+字母)，如GAABC001
     * 类型为98和99账户定义规则：AA******(6位数字+字母)，如AA000001
     * TraderID为所有系统唯一，所有使用GAIA平台的用户的每一个以上类型的账号都有一个独立的TraderID，公司此表将维护所有使用我们平台的用户，无论他使用哪个合作机构的平台
     * 代替原有CorpUserID
     */
    private String m_traderID = "";

    /**
     *用户昵称
     */
    private String m_nickName = "";

    /**
     *最大用户数
     */
    private Integer m_maxUsers = 0;

    /**
     *密码
     */
    private String m_passWord = "";

    /**
     *SessionID
     */
    private Integer m_sessionID = 0;

    /**
     *状态
     */
    private Integer m_state = 0;

    /**
     *类型
     */
    private Integer m_type = 0;

    /**
     *最大错误次数
     */
    private Integer m_maxErrorCount = 0;

    /**
     *appid
     */
    private String m_appID = "";

    /**
     * 数据变动标记，时间戳（自动生成，不可修改）
     */
    private Long m_timeStamp = 0L;

    /**
     * 登录是否需要谷歌验证
     */
    private Boolean m_isGoogleAuthened = false;

    /**
     * 登录是否需要短信验证
     */
    private Boolean m_isSMSAuthened = false;

    /**
     * 根据用户类型获取最大错误数量
     * @param type 用户类型
     * @return 最大错误数量
     */
    public int getMaxErrorCount(int type)
    {
        int result;
        switch(type){
            case 0:
                result = 10;
                break;
            case 1:
                result = 10;
                break;
            case 2:
                result = 10;
                break;
            case 3:
                result = 10;
                break;
            case 4:
                result = 10;
                break;
            case 5:
                result = 10;
                break;
            case 10:
                result = 10;
                break;
            default:
                result = 10;
        }
        return result;
    }


    /**
     * 根据用户类型获取最大用户数量
     * @param type 用户类型
     * @return 最大用户数量
     */
    public int getMaxUsers(int type)
    {
        int result;
        switch(type){
            case 0:
                result = 5;
                break;
            case 1:
                result = 5;
                break;
            case 2:
                result = 5;
                break;
            case 3:
                result = 5;
                break;
            case 4:
                result = 5;
                break;
            case 5:
                result = 5;
                break;
            case 10:
                result = 5;
                break;
            default:
                result = 5;
        }
        return result;
    }

    /**
     * 根据用户类型获取SeesionID
     * @param type 用户类型
     * @return SeesionID
     */
    public int getSessionID(int type)
    {
        int result;
        switch(type){
            case 0:
                result = 3600;
                break;
            case 1:
                result = 3600;
                break;
            case 2:
                result = 3600;
                break;
            case 3:
                result = 3600;
                break;
            case 4:
                result = 3600;
                break;
            case 5:
                result = 3600;
                break;
            case 10:
                result = 3600;
                break;
            default:
                result = 3600;
        }
        return result;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getM_userID() {
        return m_userID;
    }

    public void setM_userID(Integer m_userID) {
        this.m_userID = m_userID;
    }

    public String getM_userName() {
        return m_userName;
    }

    public void setM_userName(String m_userName) {
        this.m_userName = m_userName;
    }

    public String getM_traderID() {
        return m_traderID;
    }

    public void setM_traderID(String m_traderID) {
        this.m_traderID = m_traderID;
    }

    public String getM_nickName() {
        return m_nickName;
    }

    public void setM_nickName(String m_nickName) {
        this.m_nickName = m_nickName;
    }

    public Integer getM_maxUsers() {
        return m_maxUsers;
    }

    public void setM_maxUsers(Integer m_maxUsers) {
        this.m_maxUsers = m_maxUsers;
    }

    public String getM_passWord() {
        return m_passWord;
    }

    public void setM_passWord(String m_passWord) {
        this.m_passWord = m_passWord;
    }

    public Integer getM_sessionID() {
        return m_sessionID;
    }

    public void setM_sessionID(Integer m_sessionID) {
        this.m_sessionID = m_sessionID;
    }

    public Integer getM_state() {
        return m_state;
    }

    public void setM_state(Integer m_state) {
        this.m_state = m_state;
    }

    public Integer getM_type() {
        return m_type;
    }

    public void setM_type(Integer m_type) {
        this.m_type = m_type;
    }

    public Integer getM_maxErrorCount() {
        return m_maxErrorCount;
    }

    public void setM_maxErrorCount(Integer m_maxErrorCount) {
        this.m_maxErrorCount = m_maxErrorCount;
    }

    public String getM_appID() {
        return m_appID;
    }

    public void setM_appID(String m_appID) {
        this.m_appID = m_appID;
    }

    public Long getM_timeStamp() {
        return m_timeStamp;
    }

    public void setM_timeStamp(Long m_timeStamp) {
        this.m_timeStamp = m_timeStamp;
    }

    public Boolean getM_isGoogleAuthened() {
        return m_isGoogleAuthened;
    }

    public void setM_isGoogleAuthened(Boolean m_isGoogleAuthened) {
        this.m_isGoogleAuthened = m_isGoogleAuthened;
    }

    public Boolean getM_isSMSAuthened() {
        return m_isSMSAuthened;
    }

    public void setM_isSMSAuthened(Boolean m_isSMSAuthened) {
        this.m_isSMSAuthened = m_isSMSAuthened;
    }
}
