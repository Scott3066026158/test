package com.gaia.autotrade.ws.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.service.QuoteService;
import com.gaia.autotrade.ws.bean.SubDataBean;
import com.gaia.autotrade.ws.bean.SubKLineData;
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

	// 请求K线唯一标识
	private int m_count = 1;
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

	@Autowired
	private void setQuoteService(QuoteService quoteService) {
		m_quoteService = quoteService;
	}

	@Override
	public int RevWsSub(WebSocketServletRequest request, WebSocketServletResponse response) throws Exception {
		Map<String, Object> params = request.getParams();
		String pair = (String) params.get("pair");
		String param = (String) params.get("param");
		if (!m_mkDataManager.isExistPair(pair)) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("Pair：" + pair + ",不是一个合法的Pair");
			return -1;
		}
		int cycle = checkParam(param);
		if (cycle == -1) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("Param：" + param + ",不是一个支持的周期参数");
			return -1;
		}
		SubKLineData subParam = new SubKLineData();
		subParam.m_sid = request.getSid();
		subParam.m_topic = request.getTopic();
		subParam.m_cycle = cycle;
		subParam.m_size = 1;
		subParam.m_type = 116;
		subParam.m_subscription = 0;
		subParam.m_startDate = 0;
		subParam.m_endDate = 0;
		subParam.m_lowCode = pair;
		subParam.m_code = m_mkDataManager.getTradePair(pair);
		m_subDataManager.putCallBackKLine(subParam.hashCode(), subParam);
		if (m_quoteService.GetHistoryDatas(subParam) < 0) {
			m_subDataManager.removeCallBackKLineReq(subParam.hashCode());
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("KLine数据请求失败");
			return -1;
		}
		response.setStatus(PublicField.SUCCESSFUL_STATUS);
		response.setRequestParms(request.getTopic());
		return 0;
	}

	@Override
	public int RevWsReq(WebSocketServletRequest request, WebSocketServletResponse response) throws Exception {
		Map<String, Object> params = request.getParams();
		String pair = (String) params.get("pair");
		String param = (String) params.get("param");
		long from = 0;
		long to = 0;
		if (!params.containsKey("from")) {
			from = System.currentTimeMillis() / 1000 - 60;
		} else {
			from = (long) params.get("from");
		}

		if (!params.containsKey("to")) {
			to = System.currentTimeMillis() / 1000 - 60;
		} else {
			to = (long) params.get("to");
		}
		if (CheckTime(from, to)) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("请检查from与to参数");
			return -1;
		}
		if (!m_mkDataManager.isExistPair(pair)) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("Pair：" + pair + ",不是一个合法的Pair");
			return -1;
		}
		int cycle = checkParam(param);
		if (cycle == -1) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("Param：" + param + ",不是一个支持的周期参数");
			return -1;
		}

		SubKLineData subParam = new SubKLineData();
		subParam.m_sid = request.getSid();
		subParam.m_topic = request.getTopic();
		subParam.m_cycle = cycle;
		subParam.m_size = (int) ((from - to) / 60);
		subParam.m_type = 116;
		subParam.m_subscription = m_count;
		subParam.m_startDate = from;
		subParam.m_endDate = to;
		subParam.m_lowCode = pair;
		subParam.m_code = m_mkDataManager.getTradePair(pair);

		m_subDataManager.putCallBackKLineReq(subParam.hashCode(), subParam);

		if (m_quoteService.GetHistoryDatas(subParam) < 0) {
			m_subDataManager.removeCallBackKLineReq(subParam.hashCode());
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("KLine数据请求失败");
			return -1;
		}
		response.setStatus(PublicField.SUCCESSFUL_STATUS);
		response.setRequestParms(request.getTopic());
		return 0;
	}

	private boolean CheckTime(long from, long to) {
		if (to - from < 60)
			return false;
		return true;
	}

	// 检查周期参数
	private int checkParam(String param) {
		switch (param) {
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

	public boolean IsNull(String... params) {
		for (String param : params) {
			if (param == null || param.equals("")) {
				return true;
			}
		}
		return false;
	}
}
