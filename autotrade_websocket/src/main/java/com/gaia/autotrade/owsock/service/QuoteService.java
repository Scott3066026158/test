package com.gaia.autotrade.owsock.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.gaia.autotrade.owsock.base.BaseService;
import com.gaia.autotrade.owsock.base.Binary;
import com.gaia.autotrade.owsock.base.CMessage;
import com.gaia.autotrade.owsock.global.BusinessIDs;
import com.gaia.autotrade.owsock.global.DataCenter;
import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.CoinNewData;
import com.gaia.autotrade.owsock.market_bean.HistoryDataInfo;
import com.gaia.autotrade.owsock.market_bean.LatestDataInfo;
import com.gaia.autotrade.owsock.market_bean.MarketDepthData;
import com.gaia.autotrade.owsock.market_bean.MarketTickData;
import com.gaia.autotrade.owsock.market_bean.MarketUserInfo;
import com.gaia.autotrade.owsock.market_bean.SecurityInfo;
import com.gaia.autotrade.owsock.market_bean.SecurityLatestDataTiny;

public class QuoteService extends BaseService {

	//唯一对象
	private static QuoteService m_self;

	// 获取行情数据管理类
	private MarketDataManager m_marketDataManager = MarketDataManager.getInstance();
	// 获取行情用户信息
	private MarketUserInfo m_userInfo = m_marketDataManager.getMarketUserInfo();
	// 为了快速，先定死requestID
	private int m_requestID = 123456;

	public static final int FUNCTIONID_MD_PUSHDATA = 0;
	public static final int FUNCTIONID_MD_PUSHDATATINY = 1;
	public static final int FUNCTIONID_MD_STOPPUSHDATATINY = 2;
	public static final int FUNCTIONID_MD_STOPPUSHDATA = 4;
	public static final int FUNCTIONID_MD_GETHISTORYDATA = 6;
	public static final int SUBSCRIBEID = 38;
	public static final int UNSUBSCRIBEID = 39;
	public static final int SERVICEID_MD = 0;

	
	public QuoteService() {
		this("");
	}

	public QuoteService(String serverName) {
		super(serverName);
		SetSocketID(-1);
		SetCompressType(BaseService.COMPRESSTYPE_NONE);
		SetServiceID(SERVICEID_MD);
	}
	// 连接行情服务
	public int ConnectServer(String url) {
		int socketID = ConnectToServer(url);
		SetSocketID(socketID);

		if (socketID > 0) {
			ReqLogin();
			ReqSecurityInfo();
			ReqPairs();
			m_connected = true;
		}
		return socketID;
	}
	
	//单例
	public static QuoteService getInstance() {
		if (m_self == null) {
			synchronized (QuoteService.class) {
				if (m_self == null) {
					m_self = new QuoteService();
				}
			}
		}
		return m_self;
	}

	public void OnReceive(CMessage message) {
		System.out.println("收到行情数据!functionID:" + message.m_functionID);
		Binary binary = new Binary();
		binary.Write(message.m_body, message.m_bodyLength);
		switch (message.m_functionID) {
		// 登录回调
		case BusinessIDs.RSP_QUOTE_LOGIN:
			RevLogin(binary);
			break;
		// 交易对子信息回调
		case BusinessIDs.RSP_QRY_COININFO:
			RevSecurityInfo(binary);
			break;
		// 交易对子回调
		case BusinessIDs.RSP_QUOTE_CODES:
			RevPairInfo(binary);
			break;
		// 深度行情回调(包括Depth与Tick)
		case FUNCTIONID_MD_PUSHDATA:
			RevPushData(binary);
			break;
		// 简易行情回调(只有Tiny)
		case FUNCTIONID_MD_PUSHDATATINY:
			RevPushDataTiny(binary);
			break;
		// K线数据订阅
		case FUNCTIONID_MD_GETHISTORYDATA:
			RevPushKLine(binary);
			break;
		default:
			break;
		}
	}

	private void RevPushKLine(Binary binary) {

	}

	private void RevPushDataTiny(Binary binary) {
		LatestDataInfo dataInfo = new LatestDataInfo();
		ArrayList<SecurityLatestDataTiny> datas = new ArrayList<>();
		QuoteService.GetLatestDatasTiny(dataInfo, datas, binary);
		int size = datas.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				SecurityLatestDataTiny data = datas.get(i);
				System.out.println(data);
			}
		}
	}

	private void RevPushData(Binary binary) {
		LatestDataInfo dataInfo = new LatestDataInfo();
		ArrayList<CoinNewData> datas = new ArrayList<CoinNewData>();
		QuoteService.GetLatestDatas(dataInfo, datas, binary);
		int size = datas.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				CoinNewData data = datas.get(i);
				// Depth Market Data
				if (data.m_type == 117) {
					RevDepthData(data);
					// Tick Market Data
				} else if (data.m_type == 116) {
					RevTickData(data);
				} else {
					System.out.println("行情接收到了未知数据");
				}
			}
		}
	}

	private void RevTickData(CoinNewData data) {
		MarketTickData tickData = new MarketTickData();
		tickData.coinNewDataToMarketTickData(data);
		m_marketDataManager.putTickData(tickData);
	}

	private void RevDepthData(CoinNewData data) {
		MarketDepthData depthData = new MarketDepthData();
		depthData.coinNewDataToMarketDepthData(data);
		m_marketDataManager.putDepthData(depthData);
		if(depthData.m_lowCode.equals("btcusdt")) {
		}
	}

	private void RevLogin(Binary binary) {
		try {
			int status = binary.ReadInt();
			int sessionID = binary.ReadInt();
			SetSessionID(sessionID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int GenerateReqID() {
		return 0;
	}

	public void RevSecurityInfo(Binary binary) {
		ArrayList<SecurityInfo> vec = new ArrayList<SecurityInfo>();
		ReadSecurityInfos(binary, vec);
		m_marketDataManager.putSecurityList(vec);
	}

	public void RevPairInfo(Binary binary) {
		try {
			StringBuilder sb = new StringBuilder();
			ArrayList<String> codes = new ArrayList<String>();
			int size = binary.ReadInt();
			for (int i = 0; i < size; i++) {
				String code = binary.ReadString();
				codes.add(code);
				if (i == size) {
					sb.append(code);
				} else {
					sb.append(code + ",");
				}
			}
			if (!sb.toString().equals("")) {
				SubMarketTickData(sb.toString());
				SubMarketDepthData(sb.toString());
			}
			m_marketDataManager.putTradePairList(codes);
			// 因为行情没有获取所有币种接口所以通过交易对子获取币种
			RevCoinCode(codes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 通过交易对子析出所有币种
	public void RevCoinCode(List<String> pairList) {
		for (String pair : pairList) {
			String[] codes = pair.split("[/]");
			for (String code : codes) {
				m_marketDataManager.putCoinCode(code);
			}
		}
	}

	public int PushLatestDatas(int requestID, LatestDataInfo dataInfo) {
		return Send(FUNCTIONID_MD_PUSHDATA, requestID, GetSocketID(), dataInfo) > 0 ? 1 : 0;
	}

	public int PushLatestDatasTiny(int requestID, LatestDataInfo dataInfo) {
		return Send(FUNCTIONID_MD_PUSHDATATINY, requestID, GetSocketID(), dataInfo) > 0 ? 1 : 0;
	}

	// 请求历史数据
	public int GetHistoryDatas(int requestID, HistoryDataInfo dataInfo) {
		return Send(FUNCTIONID_MD_GETHISTORYDATA, requestID, GetSocketID(), dataInfo) > 0 ? 1 : 0;
	}

	/**
	 * 发送数据
	 * 
	 * @param functionID
	 * @param binary
	 * @return
	 */
	private int RealSend(int functionID, Binary binary) {
		byte[] ps = binary.GetBytes();
		CMessage message = new CMessage(GetGroupID(), GetServiceID(), functionID, GetSessionID(), GenerateReqID(),
				GetSocketID(), 0, GetCompressType(), ps.length, ps);
		return Send(message);
	}

	public void ReadSecurityInfos(Binary binary, ArrayList<SecurityInfo> vec) {
		try {
			int size = binary.ReadInt();
			for (int i = 0; i < size; i++) {
				SecurityInfo security = new SecurityInfo();
				security.m_code = binary.ReadString();
				security.m_exchangeID = binary.ReadString();
				security.m_isTrading = binary.ReadInt() == 1 ? true : false;
				security.m_name = binary.ReadString();
				security.m_pingyin = binary.ReadString();
				security.m_priceTick = binary.ReadDouble();
				security.m_amountTick = binary.ReadDouble();
				security.m_status = binary.ReadInt();
				security.m_type = binary.ReadInt();
				security.m_digit = binary.ReadInt();
				security.m_firstLeg = binary.ReadString();
				security.m_secondLeg = binary.ReadString();
				vec.add(security);
			}
		} catch (Exception e) {
		}
	}

	public void ReqLogin() {
		Binary binary = new Binary();
		try {
			binary.WriteString(m_userInfo.getUserName());
			binary.WriteString(m_userInfo.getPassWord());
			RealSend(BusinessIDs.REQ_QUOTE_LOGIN, binary);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ReqLogout(String traderID) {
		Binary binary = new Binary();
		try {
			binary.WriteString(traderID);
			RealSend(BusinessIDs.QUOTE_LOGOUT, binary);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ReqSecurityInfo() {
		try {
			Binary binary = new Binary();
			binary.WriteString(m_userInfo.getUserName());
			binary.WriteInt(GenerateReqID());
			RealSend(BusinessIDs.REQ_QRY_INSTRUMENTINFO, binary);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ReqPairs() {
		try {
			Binary binary = new Binary();
			binary.WriteString(m_userInfo.getUserName());
			binary.WriteInt(GenerateReqID());
			RealSend(BusinessIDs.REQ_QUOTE_CODES, binary);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送历史数据请求
	 * 
	 * @param functionID 功能ID
	 * @param requestID  请求ID
	 * @param socketID   连接ID
	 * @param dataInfo   数据信息
	 * @return 状态
	 */
	public int Send(int functionID, int requestID, int socketID, HistoryDataInfo dataInfo) {
		int ret = -1;
		try {
			Binary bw = new Binary();
			bw.WriteString(dataInfo.m_securityCode);
			bw.WriteChar((char) dataInfo.m_type);
			bw.WriteInt(dataInfo.m_size);
			bw.WriteInt(dataInfo.m_cycle);
			bw.WriteInt(dataInfo.m_subscription);
			bw.WriteDouble(dataInfo.m_startDate);
			bw.WriteDouble(dataInfo.m_endDate);
			byte[] bytes = bw.GetBytes();
			ret = Send(new CMessage(GetGroupID(), GetServiceID(), functionID, GetSessionID(), requestID, socketID, 0,
					GetCompressType(), bytes.length, bytes));
			bw.Close();
		} catch (Exception ex) {

		}
		return ret;
	}

	/**
	 * 获取最新数据
	 * 
	 * @param dataInfo
	 * @param datas
	 * @param body
	 * @param bodyLength
	 * @return
	 */
	public static int GetLatestDatas(LatestDataInfo dataInfo, List<CoinNewData> datas, Binary binary) {
		try {
			int size = binary.ReadInt();
			for (int i = 0; i < size; i++) {
				CoinNewData latestData = new CoinNewData();
				latestData.m_code = binary.ReadString();
				latestData.m_open = binary.ReadDouble();
				latestData.m_preClose = binary.ReadDouble();
				latestData.m_close = binary.ReadDouble();
				latestData.m_high = binary.ReadDouble();
				latestData.m_low = binary.ReadDouble();
				latestData.m_volume = (int) binary.ReadDouble();
				latestData.m_turnover = binary.ReadDouble();
				latestData.m_innerVol = binary.ReadInt();
				latestData.m_outerVol = binary.ReadInt();
				latestData.m_turnoverRate = binary.ReadDouble();
				latestData.m_openInterest = binary.ReadDouble();
				latestData.m_settlementPrice = binary.ReadDouble();
				latestData.m_preSettlementPrice = binary.ReadDouble();
				latestData.m_date = binary.ReadDouble();
				latestData.m_dVolume = (int) binary.ReadDouble();

				latestData.m_askPriceCount = binary.ReadInt();
				for (int idx = 0; idx < latestData.m_askPriceCount; idx++) {
					latestData.m_askPrices.add(binary.ReadDouble());
				}
				latestData.m_askVolCount = binary.ReadInt();
				for (int idx = 0; idx < latestData.m_askVolCount; idx++) {
					latestData.m_askVols.add(binary.ReadDouble());
				}
				latestData.m_bidPriceCount = binary.ReadInt();
				for (int idx = 0; idx < latestData.m_bidPriceCount; idx++) {
					latestData.m_bidPrices.add(binary.ReadDouble());
				}
				latestData.m_bidVolCount = binary.ReadInt();
				for (int idx = 0; idx < latestData.m_bidVolCount; idx++) {
					latestData.m_bidVols.add(binary.ReadDouble());
				}
				latestData.m_type = binary.ReadInt();

				latestData.m_assessment = binary.ReadDouble();
				latestData.m_rate = binary.ReadDouble();
				latestData.m_thirdPartPrice = binary.ReadDouble();
				datas.add(latestData);
			}
			binary.Close();
		} catch (Exception ex) {

		}
		return 1;
	}

	public static int GetLatestDatasTiny(LatestDataInfo dataInfo, List<SecurityLatestDataTiny> datas, Binary binary) {
		try {
			int size = binary.ReadInt();
			for (int i = 0; i < size; i++) {
				SecurityLatestDataTiny latestData = new SecurityLatestDataTiny();
				latestData.m_assessment = binary.ReadDouble();
				latestData.m_close = binary.ReadDouble();
				latestData.m_code = binary.ReadString();
				latestData.m_preclose = binary.ReadDouble();
				latestData.m_rate = binary.ReadDouble();
				latestData.m_vol = binary.ReadDouble();
				datas.add(latestData);
			}
			binary.Close();
		} catch (Exception ex) {

		}
		return 1;
	}

	public static int GetLatestDatas(LatestDataInfo dataInfo, List<CoinNewData> datas, byte[] body, int bodyLength) {
		try {
			Binary binary = new Binary();
			binary.Write(body, bodyLength);
			int size = binary.ReadInt();
			for (int i = 0; i < size; i++) {
				CoinNewData latestData = new CoinNewData();
				latestData.m_code = binary.ReadString();
				latestData.m_open = binary.ReadDouble();
				latestData.m_preClose = binary.ReadDouble();
				latestData.m_close = binary.ReadDouble();
				latestData.m_high = binary.ReadDouble();
				latestData.m_low = binary.ReadDouble();
				latestData.m_volume = (int) binary.ReadDouble();
				latestData.m_turnover = binary.ReadDouble();
				latestData.m_innerVol = binary.ReadInt();
				latestData.m_outerVol = binary.ReadInt();
				latestData.m_turnoverRate = binary.ReadDouble();
				latestData.m_openInterest = binary.ReadDouble();
				latestData.m_settlementPrice = binary.ReadDouble();
				latestData.m_preSettlementPrice = binary.ReadDouble();
				latestData.m_date = binary.ReadDouble();
				latestData.m_dVolume = (int) binary.ReadDouble();

				latestData.m_askPriceCount = binary.ReadInt();
				for (int idx = 0; idx < latestData.m_askPriceCount; idx++) {
					latestData.m_askPrices.add(binary.ReadDouble());
				}
				latestData.m_askVolCount = binary.ReadInt();
				for (int idx = 0; idx < latestData.m_askVolCount; idx++) {
					latestData.m_askVols.add(binary.ReadDouble());
				}
				latestData.m_bidPriceCount = binary.ReadInt();
				for (int idx = 0; idx < latestData.m_bidPriceCount; idx++) {
					latestData.m_bidPrices.add(binary.ReadDouble());
				}
				latestData.m_bidVolCount = binary.ReadInt();
				for (int idx = 0; idx < latestData.m_bidVolCount; idx++) {
					latestData.m_bidVols.add(binary.ReadDouble());
				}
				latestData.m_type = binary.ReadInt();

				latestData.m_assessment = binary.ReadDouble();
				latestData.m_rate = binary.ReadDouble();
				latestData.m_thirdPartPrice = binary.ReadDouble();
				datas.add(latestData);
			}
			binary.Close();
		} catch (Exception ex) {

		}
		return 1;
	}

	public static int GetLatestDatasTiny(LatestDataInfo dataInfo, List<SecurityLatestDataTiny> datas, byte[] body,
			int bodyLength) {
		try {
			Binary binary = new Binary();
			binary.Write(body, bodyLength);
			int size = binary.ReadInt();
			for (int i = 0; i < size; i++) {
				SecurityLatestDataTiny latestData = new SecurityLatestDataTiny();
				latestData.m_assessment = binary.ReadDouble();
				latestData.m_close = binary.ReadDouble();
				latestData.m_code = binary.ReadString();
				latestData.m_preclose = binary.ReadDouble();
				latestData.m_rate = binary.ReadDouble();
				latestData.m_vol = binary.ReadDouble();
				datas.add(latestData);
			}
			binary.Close();
		} catch (Exception ex) {

		}
		return 1;
	}

	/**
	 * 发送最新数据请求
	 * 
	 * @param functionID
	 * @param requestID
	 * @param socketID
	 * @param dataInfo
	 * @return
	 */
	public int Send(int functionID, int requestID, int socketID, LatestDataInfo dataInfo) {
		int ret = -1;
		try {
			Binary bw = new Binary();
			bw.WriteString(dataInfo.m_codes);
			bw.WriteInt(dataInfo.m_size);
			bw.WriteInt(dataInfo.m_format);
			byte[] bytes = bw.GetBytes();
			ret = Send(new CMessage(GetGroupID(), GetServiceID(), functionID, GetSessionID(), requestID, socketID, 0,
					GetCompressType(), bytes.length, bytes));
			bw.Close();
		} catch (Exception ex) {

		}
		return ret;
	}

	/**
	 * 停止推送最新数据
	 * 
	 * @param requestID
	 * @return
	 */
	public int StopPushLatestDepthDatas(int requestID) {
		byte[] bytes = "1".getBytes(Charset.forName("UTF-8"));
		return Send(new CMessage(GetGroupID(), GetServiceID(), FUNCTIONID_MD_STOPPUSHDATA, GetSessionID(), requestID,
				GetSocketID(), 0, GetCompressType(), bytes.length, bytes)) > 0 ? 1 : 0;
	}

	/**
	 * 停止推送最新数据
	 * 
	 * @param requestID
	 * @return
	 */
	public int StopPushLatestTinyDatas(int requestID) {
		byte[] bytes = "1".getBytes(Charset.forName("UTF-8"));
		return Send(new CMessage(GetGroupID(), GetServiceID(), FUNCTIONID_MD_STOPPUSHDATATINY, GetSessionID(),
				requestID, GetSocketID(), 0, GetCompressType(), bytes.length, bytes)) > 0 ? 1 : 0;
	}

	/**
	 * 订阅Depth数据
	 * 
	 * @param code
	 */
	public void SubMarketDepthData(String codes) {
		LatestDataInfo dataInfo = new LatestDataInfo();
		dataInfo.m_codes = codes;
		dataInfo.m_format = 1;
		DataCenter.GetMDService().StopPushLatestDepthDatas(m_requestID);
		DataCenter.GetMDService().PushLatestDatas(m_requestID, dataInfo);
	}

	/**
	 * 订阅Tick数据
	 */
	public void SubMarketTickData(String codes) {
		LatestDataInfo dataInfo = new LatestDataInfo();
		dataInfo.m_codes = codes;
		dataInfo.m_format = 0;
		DataCenter.GetMDService().StopPushLatestDepthDatas(m_requestID);
		DataCenter.GetMDService().PushLatestDatas(m_requestID, dataInfo);
	}

	/**
	 * 取消订阅Depth数据
	 * 
	 * @param codes
	 * @param reqID
	 */
	public void UnSubMarketDepthData(String codes) {
		LatestDataInfo dataInfo = new LatestDataInfo();
		dataInfo.m_codes = codes;
		dataInfo.m_format = 1;
		DataCenter.GetMDService().StopPushLatestDepthDatas(m_requestID);
	}

	/**
	 * 取消订阅Tick数据
	 * 
	 * @param codes
	 * @param reqID
	 */
	public void UnSubMarketTickData(String codes) {
		LatestDataInfo dataInfo = new LatestDataInfo();
		dataInfo.m_codes = codes;
		dataInfo.m_format = 0;
		DataCenter.GetMDService().StopPushLatestDepthDatas(m_requestID);
	}

}
