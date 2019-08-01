package com.gaia.autotrade.ws.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.SubKLineData;
import com.gaia.autotrade.owsock.service.QuoteService;
import com.gaia.autotrade.ws.bean.SubDataBean;
import com.gaia.autotrade.ws.bean.WebSocketServletRequest;
import com.gaia.autotrade.ws.bean.WebSocketServletResponse;
import com.gaia.autotrade.ws.global.PublicField;
import com.gaia.autotrade.ws.manager.WebSocketServiceManager;
import com.gaia.autotrade.ws.manager.WebSocketSubManager;

/**
 * 处理K线数据
 * 
 * @author GAIA
 *
 */
@Component
public class MarketKLineService extends MarketBaseService {

	// 行情服务
	private QuoteService m_quoteService;
	// 行情数据管理器
	private MarketDataManager m_mkDataManager = MarketDataManager.getInstance();

	// 订阅管理器
	private WebSocketSubManager m_subDataManager;

	// 设置服务Key
	public MarketKLineService() {
		setServiceKey("kline");
	}

	// 在服务管理器中注册
	@Autowired
	private void setWebSocketServiceManager(WebSocketServiceManager wsSerManager) {
		wsSerManager.addService(this);
	}

	@Autowired
	private void setWebSocketSubManager(WebSocketSubManager subDataManager) {
		m_subDataManager = subDataManager;
	}

	@Override
	public int RevWsSub(WebSocketServletRequest request, WebSocketServletResponse response) {
		Map<String, String> params = request.getParams();
		String pair = params.get("pair");
		String param = params.get("param");
		if (!m_mkDataManager.isExistPair(pair)) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("Pair：" + pair + ",不是一个合法的Pair");
			return -1;
		}
		int cycle = checkParam(param);
		if(cycle == -1){
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("Param：" + param + ",不是一个支持的周期参数");
			return -1;
		}
		SubDataBean bean = new SubDataBean();
		bean.setPair(pair);
		bean.setSid(request.getSid());
		bean.setTopic(request.getTopic());
		m_subDataManager.putCallBackKLine(bean);
		if(ReqMarketKLineData(pair, cycle)) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("KLine数据请求失败");
			return -1;
		}
		response.setStatus(PublicField.SUCCESSFUL_STATUS);
		response.setRequestParms(request.getTopic());
		return 0;
	}

	@Override
	public int RevWsReq(WebSocketServletRequest request, WebSocketServletResponse response) {
		revNoProvideReq(request, response);
		return 0;
	}

	public boolean ReqMarketKLineData(String pair, int cycle) {
		SubKLineData info = new SubKLineData();
		info.m_cycle = cycle;
		info.m_size = 1;
		info.m_code = m_mkDataManager.getTradePair(pair);
		return m_quoteService.GetHistoryDatas(info) > 0 ? true :false ;
	}
	
	//检查周期参数
	private int checkParam(String param) {
		switch(param) {
			case "1min":
				return 1;
			case "5min":
				return 5;
			case "15min":
				return 15;
			case "30min":
				return 30;
			case "60min":
				return 60;
			case "1day":
				return 24 * 60;
			case "1week":
				return 7 * 24 * 60;
			case "1mon":
				return 30 * 24 * 60;
			case "1year":
				return 365 * 24 * 60;
			default:
				return -1;
		}
	}
}
