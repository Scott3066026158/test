package gaia.data;

import org.bson.Document;

import gaia.util.DocumentUtil;

public class LoginInfo  extends MongoBaseData
{
    /**
    * ID
    */
    public String _id = "";

    /**
    *用户ID
    */
    public int m_userID = 0; 

    /**
    *用户名称
    */
    public String m_userName = "";

    /**
    *系统内部对应唯一用户交易账户
     * 类型�?0的账户定义：UA******(6位数�?+字母)，如UAABC001，每位用户�?�过在注册之后，自动生成�?个UA账户，该类型账户无法绑定InvestorID
     * 类型�?1�?2的账户定义规则：TA******(6位数�?+字母)，如TAABC001
     * 类型�?3的账户定义规则：V..母账�?..***(3位数�?+字母)，�??..”为连接符�?�如VTAABC001001
     * 类型�?97的账户定义规则：GA******(6位数�?+字母)，如GAABC001
     * 类型�?98�?99账户定义规则：AA******(6位数�?+字母)，如AA000001
     * TraderID为所有系统唯�?，所有使用GAIA平台的用户的每一个以上类型的账号都有�?个独立的TraderID，公司此表将维护�?有使用我们平台的用户，无论他使用哪个合作机构的平�?
     * 代替原有CorpUserID
    */
    public String m_traderID = "";

    /**
    *用户昵称
    */
    public String m_nickName = "";

    /**
    *�?大用户数
    */
    public int m_maxUsers = 0; 

    /**
    *密码
    */
    public String m_passWord = "";

    /**
    *SessionID
    */
    public int m_sessionID = 0; 

    /**
    *状�??
    */
    public int m_state = 0; 

    /**
    *类型
    */
    public int m_type = 0; 

    /**
    *�?大错误次�?
    */
    public int m_maxErrorCount = 0; 

    /**
    *appid
    */
    public String m_appID = "";

    /**
    * 数据变动标记，时间戳（自动生成，不可修改�?
    */
    public long m_timeStamp = 0; 
    /**
    *  Object转为Document对象
    */
    
    
    @Override
    public Document ConvertObjectToDocument()
    {
        return new Document(DocumentUtil.Object2Map(this));
    }
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public int getM_userID() {
		return m_userID;
	}
	public void setM_userID(int m_userID) {
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
	public int getM_maxUsers() {
		return m_maxUsers;
	}
	public void setM_maxUsers(int m_maxUsers) {
		this.m_maxUsers = m_maxUsers;
	}
	public String getM_passWord() {
		return m_passWord;
	}
	public void setM_passWord(String m_passWord) {
		this.m_passWord = m_passWord;
	}
	public int getM_sessionID() {
		return m_sessionID;
	}
	public void setM_sessionID(int m_sessionID) {
		this.m_sessionID = m_sessionID;
	}
	public int getM_state() {
		return m_state;
	}
	public void setM_state(int m_state) {
		this.m_state = m_state;
	}
	public int getM_type() {
		return m_type;
	}
	public void setM_type(int m_type) {
		this.m_type = m_type;
	}
	public int getM_maxErrorCount() {
		return m_maxErrorCount;
	}
	public void setM_maxErrorCount(int m_maxErrorCount) {
		this.m_maxErrorCount = m_maxErrorCount;
	}
	public String getM_appID() {
		return m_appID;
	}
	public void setM_appID(String m_appID) {
		this.m_appID = m_appID;
	}
	public long getM_timeStamp() {
		return m_timeStamp;
	}
	public void setM_timeStamp(long m_timeStamp) {
		this.m_timeStamp = m_timeStamp;
	}
}