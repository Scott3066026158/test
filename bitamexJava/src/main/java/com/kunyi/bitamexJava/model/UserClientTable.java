package com.kunyi.bitamexJava.model;

/**
 * 用户详情表
 *
 * @author GAIA
 * @create 2019-05-14-17:41
 */
public class UserClientTable {

    /**
     * ID
     */
    private String _id = "";

    /**
     *终端登陆用户名
     * 如用户名、手机号、微信或第三方登陆号、邮箱地址
     */
    private String m_userClientID = "";

    /**
     0:邮箱 1:手机(手机+邮箱) 2:身份认证(审核) 3.身份认证(通过) 4.vip 5.至尊vip
     */
    private Integer m_userClientType = 0;

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
     *交易账户主显名称，默认以SystemName+BrokerName+TraderID，用户可修改
     */
    private String m_traderName = "";

    /**
     *用户昵称
     */
    private String m_userNickName = "";

    /**
     *用户实名
     */
    private String m_userClientName = "";

    /**
     *用户实名认证证件号码
     */
    private String m_userInfoCID = "";

    /**
     *存放用户身份证照片的路径，
     */
    private String m_userCIDPhotoPath = "";

    /**
     *用户性别 1：男 0：女
     */
    private Integer m_userSex = 0;

    /**
     *交易代理编号
     * GaiaLP+4位字母（标识经纪商）+4位数字序号
     * 例如:GaiaLPSWHY0001
     */
    private String m_lineProxyID = "";

    /**
     *用户组编号，用于绑定多个用户为同一个用户组
     */
    private String m_groupID = "";

    /**
     *通道号
     */
    private String m_brokerID = "";

    /**
     *通道名称
     */
    private String m_brokerName = "";

    /**
     *通道账户，用户最终下单所用的账户
     * 类型为1和2的账户为实际交易账户或衍生子账户对应的TraderID
     * 类型为3的账户为自身所对应的母账户
     */

    //系统外账号
    private String m_investorID = "";

    /**
     *系统编号，AB01(数字+字母）
     */
    private String m_systemNo = "";

    /**
     *系统名称
     */

    private String m_systemName = "";

    /**
     *用户手机号
     */
    private String m_userInfoPhone = "";

    /**
     *用户邮箱地址
     */
    private String m_userInfoMail = "";

    /**
     *用户微信号
     */
    private String m_userInfoWechat = "";

    /**
     *8~20位（SSL加密）
     */
    private String m_userPassword = "";

    /**
     *密码状态
     * 0：初始状态，首次登陆需修改密码
     * 1：正常状态
     * 2：锁定状态，无法登陆
     */
    private Integer m_userPwdType = 0;

    /**
     *密码错误次数
     * 连续输入错误超过设置值
     * 锁定账户
     */
    private Integer m_userPwdLimit = 0;

    /**
     *同时连接数
     */
    private Integer m_userConnectNo = 0;

    /**
     *密保方式
     * 1：手机短信
     * 2：邮箱
     * 3：微信
     */
    private Integer m_userPwdSecurity = 0;

    /**
     *用户注册时间
     * 年/月/日/时:分:秒
     */
    private Long m_userRegistTime = 0L;

    /**
     *数据变动标记，时间戳（自动生成，不可修改）
     */
    private Long m_timestamp = 0L;

    /**
     *用户到期时间
     * 年/月/日
     */
    private Long m_userExpTime = 0L;

    /**
     *删除标记
     * 0：该条数据有效
     * 1：该条数据已被删除，留痕
     */
    private Integer m_deleteFlag = 0;
    /**
     *  Object转为Document对象
     */

    /**
     * 登陆时谷歌验证密钥
     */
    private String m_googleSecretKey;

    /**
     * 被邀请人
     * 	注：通过谁注册的，没有则为空
     */
    private String m_inviter;

    /**
     * 邀请码
     * 	邀请别人用的，用户注册时自动生成
     */
    private String m_invitationCode;

    /**
     * 用于存储用户认证视频
     */
    private String m_userKYCVideo;

    /**
     * 用户资金密码
     */
    private String m_fundPwd;
	/**
     * 根据用户类型获取输入密码最大错误次数
     * @param userType 用户类型
     * @return SeesionID
     */
    public int getUserPwdLimit(int userType)
    {
        int result;
        switch(userType){
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
     * 根据用户类型获取最大连接数
     * @param userType 用户类型
     * @return SeesionID
     */
    public int getUserConnectNo(int userType)
    {
        int result;
        switch(userType){
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getM_userClientID() {
        return m_userClientID;
    }

    public void setM_userClientID(String m_userClientID) {
        this.m_userClientID = m_userClientID;
    }

    public Integer getM_userClientType() {
        return m_userClientType;
    }

    public void setM_userClientType(Integer m_userClientType) {
        this.m_userClientType = m_userClientType;
    }

    public String getM_traderID() {
        return m_traderID;
    }

    public void setM_traderID(String m_traderID) {
        this.m_traderID = m_traderID;
    }

    public String getM_traderName() {
        return m_traderName;
    }

    public void setM_traderName(String m_traderName) {
        this.m_traderName = m_traderName;
    }

    public String getM_userNickName() {
        return m_userNickName;
    }

    public void setM_userNickName(String m_userNickName) {
        this.m_userNickName = m_userNickName;
    }

    public String getM_userClientName() {
        return m_userClientName;
    }

    public void setM_userClientName(String m_userClientName) {
        this.m_userClientName = m_userClientName;
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

    public Integer getM_userSex() {
        return m_userSex;
    }

    public void setM_userSex(Integer m_userSex) {
        this.m_userSex = m_userSex;
    }

    public String getM_lineProxyID() {
        return m_lineProxyID;
    }

    public void setM_lineProxyID(String m_lineProxyID) {
        this.m_lineProxyID = m_lineProxyID;
    }

    public String getM_groupID() {
        return m_groupID;
    }

    public void setM_groupID(String m_groupID) {
        this.m_groupID = m_groupID;
    }

    public String getM_brokerID() {
        return m_brokerID;
    }

    public void setM_brokerID(String m_brokerID) {
        this.m_brokerID = m_brokerID;
    }

    public String getM_brokerName() {
        return m_brokerName;
    }

    public void setM_brokerName(String m_brokerName) {
        this.m_brokerName = m_brokerName;
    }

    public String getM_investorID() {
        return m_investorID;
    }

    public void setM_investorID(String m_investorID) {
        this.m_investorID = m_investorID;
    }

    public String getM_systemNo() {
        return m_systemNo;
    }

    public void setM_systemNo(String m_systemNo) {
        this.m_systemNo = m_systemNo;
    }

    public String getM_systemName() {
        return m_systemName;
    }

    public void setM_systemName(String m_systemName) {
        this.m_systemName = m_systemName;
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

    public Integer getM_userConnectNo() {
        return m_userConnectNo;
    }

    public void setM_userConnectNo(Integer m_userConnectNo) {
        this.m_userConnectNo = m_userConnectNo;
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

    public Long getM_userExpTime() {
        return m_userExpTime;
    }

    public void setM_userExpTime(Long m_userExpTime) {
        this.m_userExpTime = m_userExpTime;
    }

    public Integer getM_deleteFlag() {
        return m_deleteFlag;
    }

    public void setM_deleteFlag(Integer m_deleteFlag) {
        this.m_deleteFlag = m_deleteFlag;
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
    
    public String getM_userKYCVideo() {
		return m_userKYCVideo;
	}

	public void setM_userKYCVideo(String m_userKYCVideo) {
		this.m_userKYCVideo = m_userKYCVideo;
	}

	public String getM_fundPwd() {
		return m_fundPwd;
	}

	public void setM_fundPwd(String m_fundPwd) {
		this.m_fundPwd = m_fundPwd;
	}
}
