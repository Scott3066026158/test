package gaia.flag;

public class BusinessIDs  {

	/**
     * 下面是暂且用不到的FundtionID
     */
    public static final int REQ_QRY_MARGINRATE = 45;
    public static final int REQ_QRY_INSTRUMENTINFO = 46;
    public static final int REQ_QRY_TRADINGACCOUNT = 53;
    public static final int REQ_QRY_TRADINGCODES = 58;
    public static final int RSP_QRY_MARGINRATES = 145;
    public static final int RSP_QRY_INSTRUMENTINFO = 146;
    public static final int RSP_QRY_TRADINGACCOUNT = 153;
    public static final int RSP_QRY_TRADINGCODES = 158;
    public static final int RTN_ACCOUNT = 170;
    public static final int RTN_COMMISSIONRATE = 171;
    public static final int RTN_MARGINRATE = 172;
    public static final int REQ_COMMISSIONRATE = 44;            //手续费请求
    public static final int RSP_COMMISSIONRATES = 144;          //手续费回报
    /**
     * 上面是暂且用不到的FundtionID
     */

    /**
     * 下面是行情的FunctionID
     */
    public static final int QUOTE_LOGOUT = 32;                  //行情登出请求
    public static final int REQ_QUOTE_LOGIN = 31;               //行情登录请求
    public static final int RSP_QUOTE_LOGIN = 131;              //行情登录回报

    public static final int REQ_QUOTE_CODES = 33;               //行情码表请求(纯交易对子)
    public static final int RSP_QUOTE_CODES = 133;              //行情码表回报(纯交易对子)
    
    public static final int RSP_RECOMMEND_CODES = 134;			//推荐股票代码回报
    /**
     * 上面是行情的FunctionID
     */

    /**
     * 下面是交易的FunctionID
     */
    public static final int REQ_HISTORY_ORDERS_IN_NATIVE = 47;  //历史委托请求(走交易服务本地)
    public static final int RSP_HISTORY_ORDERS_IN_NATIVE = 147;	//历史委托回报(走交易服务本地)
    public static final int REP_HISTORY_TRADES_IN_NATIVE = 54;	//历史成交请求(走交易服务本地)
    public static final int RSP_HISTORY_TRADES_IN_NATIVE = 154;	//历史成交回报(走交易服务本地)
    
    public static final int TRADE_OPENBUY = 21;                 //委托请求
    public static final int TRADE_OPENSELL = 22;                //委托请求
    public static final int RTN_ORDERAPPLY = 408;               //委托申报回报
    public static final int RTN_ORDERINFO = 173;                //委托回报
    
    public static final int REQ_OPENORDERS = 407;     			//单用户最近挂单请求
    public static final int RSP_OPENORDERS = 407;     			//挂单回报
    
    public static final int RTN_TRADERECORD = 176;              //成交回报

    public static final int REQ_TRADER_LOGIN = 42;              //交易登录请求
    public static final int RSP_TRADER_LOGIN = 142;              //交易登录回报
    
    public static final int REQ_TRADER_REGISTER = 43;              //交易登录请求
    public static final int RSP_TRADER_REGISTER = 143;              //交易登录回报
    
    public static final int REQ_LOGIN_HISTORY = 82;   			//登录历史请求
    public static final int REQ_USERSECURITY = 83;      		//自选股请求

    public static final int REQ_TRADE_CODES = 37;               //前端码表请求(附带币种名)
    public static final int RSP_TRADE_CODES = 137;              //前端码表回报(附带币种名)
    
    public static final int REQ_TRADE_CODES_AND_POSITION = 38;  //前端资金请求(附带币种名和用户资金)
    public static final int RSP_TRADE_CODES_AND_POSITION = 138; //前端资金回报(附带币种名和用户资金)
    
    public static final int REQ_POSITIONS = 48;                 //TP资金请求
    public static final int RSP_POSITIONS = 148;                //TP资金回报
    public static final int RTN_POSITIONS = 174;                //TP资金变化回报

    public static final int REQ_HISTORY_ORDERCANCEL = 204;      //历史撤单请求
    public static final int RSP_HISTORY_ORDERCANCEL = 524;      //历史撤单回报

    public static final int REQ_RECHARGED_ADDR = 207;           //充值地址请求
    public static final int RTN_RECHARGED_ADDR = 406;           //充值地址回报

    public static final int REQ_WITHDRAWAL = 209;               //提现请求
    public static final int RTN_WITHDRAWAL = 257;               //提现回报
    
    public static final int REQ_HISTORY_RECHARGED = 303;        //历史充值请求
    public static final int RSP_HISTORY_RECHARGED = 403;        //历史充值回报

    public static final int REQ_HISTORY_WITHDRAWALS = 304;      //历史提现请求
    public static final int RSP_HISTORY_WITHDRAWALS = 307;      //历史提现回报

    public static final int REQ_HISTORY_FUNDSRECORD = 305;      //历史资金变化请求
    public static final int RSP_HISTORY_FUNDSRECORD = 308;      //历史资金变化回报

    public static final int REQ_HISTORY_ORDERS = 300;            //历史委托请求
    public static final int RSP_HISTORY_ORDERS = 300;           //历史委托回报
    
    public static final int REQ_HISTORY_DEALS = 301;           //历史成交回报
    public static final int RSP_HISTORY_DEALS = 301;           //历史成交回报
    
    /**
     * 下面是还没有决定的FunctionID
     */
    public static final int REQ_TRADER_LOGOUT = 9999;           //交易登出请求
    public static final int RSP_TRADER_LOGOUT = 9998;           //交易登出回报
    
    public static final int REQ_ORDER_CANCEL = 9997;            //撤单请求
    public static final int RTN_ORDER_CANCEL = 9996;            //撤单回报
    /**
     * 上面是交易的FunctionID
     */
    
    
    /**
     * 委托的错误信息代码
     */
    public static final int ERROR_NOERROR			=	0;	//没有错误
    public static final int ERROR_PERMISSION_DENIED	=	1;	//权限不足
    public static final int ERROR_INCORRECT_PASSWD	=	2;	//密码错误
    public static final int ERROR_ACCOUNT_NOTFOUND	=	3;	//用户不存在
    public static final int ERROR_ACCOUNT_FROZEN	=	5;	//账户被冻结
    public static final int ERROR_ACCOUNT_POOR		=	6;	//用户资金不足
    public static final int ERROR_VOLUME_INCORRECT	=	7;	//委托的手数不合理
    public static final int ERROR_PRICE_INCORRECT	=	8;	//委托的价格不合理
    public static final int ERROR_INSPECTOR_REJECT	=	9;	//监视器拒绝
    public static final int ERROR_CODE_NOTFOUND		=	10;	//代码不存在
    
    
    /**
     * 下面是交易的标识(WebSocketTrade)
     */
    //此部分是回送前端的标识符含义
    //URL的参数错误
    public static final String RSP_URL_ERROR = "-1";
    //成功接收前端参数，但发送失败
    public static final String RSP_SEND_FAIL = "1";
    //成功接收前端参数，成功发送，但返回有误
    public static final String RSP_REV_ERROR = "2";
    //数据接收成功
    public static final String RSP_SUCCEES= "0";

    /**
     * 下面的标识用于历史登录记录
     */
    //表示此用户登录请求已经发送到java端,但不表示一定登录成功
    public static final String LOGIN_REQ= "0";
    //表示此用户登录已经成功
    public static final String LOGIN_SUCCESS= "1";
    
    /**
     * 自选股操作标识
     */
    //请求所有自选股
    public static final String REQ_ALL_USERSECURITY = "1";
    //表示用户添加自选股
    public static final String ADD_USERSECURITY = "2";
   //表示用户删除自选股
    public static final String DELEDE_USERSECURITY = "3";
    /**
     * 上面是交易的标识(WebSocketTrade)
     */
    
    /**
     * 下面是行情的标识(WebSocket)
     */
    public static final int PUSH_DEPTH_DATA = 600;
    public static final int PUSH_TINY_DATA = 601;
    
    public static final int STOP_DEPTH_DATA = 602;
    public static final int STOP_TINY_DATA = 603;
    
    public static final int GET_HISTORY_DATA = 604;
    
    //K线分钟数据推送
    public static final int PUSH_KLINE_MINUTE = 605;
    /**
     * 上面是行情的标识(WebSocket)
     */
    
    
    public BusinessIDs()
    {}
}
