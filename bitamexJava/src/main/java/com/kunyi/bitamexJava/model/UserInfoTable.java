package com.kunyi.bitamexJava.model;

/**
 * 交易所用户信息表
 * @author GAIA
 *
 */
public class UserInfoTable {
    /**
     * ID
     */
    private String _id = "";
    
    /**
     * 系统内部对应唯一用户交易账户
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
     * 用户ID
     */
    private Integer m_userID = 0;
    
    /**
     * 用户昵称
     */
    private String m_userNickName = "";
    
    /**
     * 交易账户主显名称，默认以SystemName+BrokerName+TraderID，用户可修改
     */
    private String m_traderName = "";
    
    /**
     * 用户实名
     */
    private String m_userName = "";
    
    /**
     * 用户实名认证证件号码
     */
    private String m_userInfoCID = "";
    
    /**
     * 存放用户身份证照片的路径，
     */
    private String m_userCIDPhotoPath = "";

    /**
     * 0:邮箱 1:手机(手机+邮箱) 2:身份认证(审核) 3.身份认证(通过) 4.vip 5.至尊vip
     */
    private Integer m_userClientType = 0;

    /**
     * 用户性别 1：男 0：女
     */
    private Integer m_userSex = 0;

    /**
     * 用户手机区号
     */
    private String m_userAreaCode;
    
    /**
     * 用户手机号
     */
    private String m_userInfoPhone = "";

    /**
     * 用户邮箱地址
     */
    private String m_userInfoMail = "";

    /**
     * 用户微信号
     */
    private String m_userInfoWechat = "";

    /**
     * 同时连接数，默认为5次
     */
    private Integer m_userConnectNo = 5;
    
    /**
     * 8~20位（SSL加密）
     */
    private String m_userPassword = "";

    /**
     * 密码状态
     * 0：初始状态，首次登陆需修改密码
     * 1：正常状态
     * 2：锁定状态，无法登陆
     */
    private Integer m_userPwdType = 0;

    /**
     * 密码错误次数
     * 连续输入错误超过设置值
     * 锁定账户
     */
    private Integer m_userPwdLimit = 0;

    /**
     * 最大错误次数
     * 默认为10次
     */
    private Integer m_maxErrorCount = 10;
    
    /**
     * 密保方式
     * 1：手机短信
     * 2：邮箱
     * 3：微信
     */
    private Integer m_userPwdSecurity = 0;

    /**
     * 用户注册时间
     * 年/月/日/时:分:秒
     */
    private Long m_userRegistTime = 0L;

    /**
     * 数据变动标记，时间戳（自动生成，不可修改）
     */
    private Long m_timestamp = 0L;

    /**
     * 登录限制
     * 0，为限制登录
     * 1，为正常
     */
    private Integer m_loginlimit = 1;

    /**
     * 登陆时谷歌验证密钥
     */
    private String m_googleSecretKey;
    
    /**
     * 登录是否需要谷歌验证
     */
    private Boolean m_isGoogleAuthened = false;

    /**
     * 登录是否需要短信验证
     */
    private Boolean m_isSMSAuthened = false;

    /**
     *  被邀请人
     * 	注：通过谁注册的，没有则为空
     */
    private String m_inviter;

    /**
     *  邀请码
     * 	邀请别人用的，用户注册时自动生成
     */
    private String m_invitationCode;   
    
	public Integer getM_userID() {
		return m_userID;
	}

	public void setM_userID(Integer m_userID) {
		this.m_userID = m_userID;
	}

	public Integer getM_maxErrorCount() {
		return m_maxErrorCount;
	}

	public void setM_maxErrorCount(Integer m_maxErrorCount) {
		this.m_maxErrorCount = m_maxErrorCount;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getM_traderID() {
		return m_traderID;
	}

	public void setM_traderID(String m_traderID) {
		this.m_traderID = m_traderID;
	}

	public String getM_userNickName() {
		return m_userNickName;
	}

	public void setM_userNickName(String m_userNickName) {
		this.m_userNickName = m_userNickName;
	}

	public String getM_traderName() {
		return m_traderName;
	}

	public void setM_traderName(String m_traderName) {
		this.m_traderName = m_traderName;
	}

	public String getM_userName() {
		return m_userName;
	}

	public void setM_userName(String m_userName) {
		this.m_userName = m_userName;
	}

	public String getM_userInfoCID() {
		return m_userInfoCID;
	}

	public void setM_userInfoCID(String m_userInfoCID) {
		this.m_userInfoCID = m_userInfoCID;
	}

	public String getM_userCIDPhotoPath() {
		return m_userCIDPhotoPath;
	}

	public void setM_userCIDPhotoPath(String m_userCIDPhotoPath) {
		this.m_userCIDPhotoPath = m_userCIDPhotoPath;
	}

	public Integer getM_userClientType() {
		return m_userClientType;
	}

	public void setM_userClientType(Integer m_userClientType) {
		this.m_userClientType = m_userClientType;
	}

	public Integer getM_userSex() {
		return m_userSex;
	}

	public void setM_userSex(Integer m_userSex) {
		this.m_userSex = m_userSex;
	}

	public String getM_userInfoPhone() {
		return m_userInfoPhone;
	}

	public void setM_userInfoPhone(String m_userInfoPhone) {
		this.m_userInfoPhone = m_userInfoPhone;
	}

	public String getM_userInfoMail() {
		return m_userInfoMail;
	}

	public void setM_userInfoMail(String m_userInfoMail) {
		this.m_userInfoMail = m_userInfoMail;
	}

	public String getM_userInfoWechat() {
		return m_userInfoWechat;
	}

	public void setM_userInfoWechat(String m_userInfoWechat) {
		this.m_userInfoWechat = m_userInfoWechat;
	}

	public Integer getM_userConnectNo() {
		return m_userConnectNo;
	}

	public void setM_userConnectNo(Integer m_userConnectNo) {
		this.m_userConnectNo = m_userConnectNo;
	}

	public String getM_userPassword() {
		return m_userPassword;
	}

	public void setM_userPassword(String m_userPassword) {
		this.m_userPassword = m_userPassword;
	}

	public Integer getM_userPwdType() {
		return m_userPwdType;
	}

	public void setM_userPwdType(Integer m_userPwdType) {
		this.m_userPwdType = m_userPwdType;
	}

	public Integer getM_userPwdLimit() {
		return m_userPwdLimit;
	}

	public void setM_userPwdLimit(Integer m_userPwdLimit) {
		this.m_userPwdLimit = m_userPwdLimit;
	}

	public Integer getM_userPwdSecurity() {
		return m_userPwdSecurity;
	}

	public void setM_userPwdSecurity(Integer m_userPwdSecurity) {
		this.m_userPwdSecurity = m_userPwdSecurity;
	}

	public Long getM_userRegistTime() {
		return m_userRegistTime;
	}

	public void setM_userRegistTime(Long m_userRegistTime) {
		this.m_userRegistTime = m_userRegistTime;
	}

	public Long getM_timestamp() {
		return m_timestamp;
	}

	public void setM_timestamp(Long m_timestamp) {
		this.m_timestamp = m_timestamp;
	}

	public Integer getM_loginlimit() {
		return m_loginlimit;
	}

	public void setM_loginlimit(Integer m_loginlimit) {
		this.m_loginlimit = m_loginlimit;
	}

	public String getM_googleSecretKey() {
		return m_googleSecretKey;
	}

	public void setM_googleSecretKey(String m_googleSecretKey) {
		this.m_googleSecretKey = m_googleSecretKey;
	}

	public String getM_inviter() {
		return m_inviter;
	}

	public void setM_inviter(String m_inviter) {
		this.m_inviter = m_inviter;
	}

	public String getM_invitationCode() {
		return m_invitationCode;
	}

	public void setM_invitationCode(String m_invitationCode) {
		this.m_invitationCode = m_invitationCode;
	}

	public String getM_userAreaCode() {
		return m_userAreaCode;
	}

	public void setM_userAreaCode(String m_userAreaCode) {
		this.m_userAreaCode = m_userAreaCode;
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
