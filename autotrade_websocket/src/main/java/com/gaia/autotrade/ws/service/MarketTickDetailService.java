package com.gaia.autotrade.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.MarketTickDetailData;
import com.gaia.autotrade.ws.bean.SubDataBean;
import com.gaia.autotrade.ws.bean.TickDetailPushTick;
import com.gaia.autotrade.ws.bean.WebSocketServletRequest;
import com.gaia.autotrade.ws.bean.WebSocketServletResponse;
import com.gaia.autotrade.ws.global.PublicField;
import com.gaia.autotrade.ws.manager.WebSocketServiceManager;
import com.gaia.autotrade.ws.manager.WebSocketSubManager;

@Component
public class MarketTickDetailService extends MarketBaseService {

	// 行情数据管理器
	private MarketDataManager m_mkDataManager = MarketDataManager.getInstance();
	// 订阅管理器
	private WebSocketSubManager m_subDataManager;

	// 设置服务Key
	public MarketTickDetailService() {
		setServiceKey("detail");
	}

	// 在服务管理器中注册
	@Autowired
	private void setWebSocketServiceManager(WebSocketServiceManager wsSerManager) {
		wsSerManager.addService(this);
	}
	
	// 在订阅管理器中注册
	@Autowired
	private void setWebSocketSubManager(WebSocketSubManager subDataManager) {
		m_subDataManager = subDataManager;
	}

	@Override
	public int RevWsSub(WebSocketServletRequest request, WebSocketServletResponse response) {
		String pair = (String) request.getParams().get("pair");
		if (!m_mkDataManager.isExistPair(pair)) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("Pair：" + pair + ",不是一个合法的Pair"); // 对子
			return -1;
		}
		SubDataBean bean = new SubDataBean();
		bean.setPair(pair);
		bean.setSid(request.getSid());
		bean.setTopic(request.getTopic());
		m_subDataManager.putCallBackTick(bean);
		response.setStatus(PublicField.SUCCESSFUL_STATUS);
		response.setRequestParms(request.getTopic());
		return 0;
	}

	@Override
	public int RevWsReq(WebSocketServletRequest request, WebSocketServletResponse response) {
		String pair = (String) request.getParams().get("pair");
		String sid = (String) request.getParams().get("sid");
		if (!m_mkDataManager.isExistPair(pair)) {
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("Pair：" + pair + ",不是一个合法的Pair"); // 对子
			return -1;
		}
		// 推送一笔最新数据
		MarketTickDetailData data = m_mkDataManager.getTickData(pair);
		TickDetailPushTick tick = data.copyToTickDetail();
		response.setStatus(PublicField.SUCCESSFUL_STATUS);
		response.setRequestParms(request.getTopic());
		response.setData(tick);
		return 0;
	}
}
