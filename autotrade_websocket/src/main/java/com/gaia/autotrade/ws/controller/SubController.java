package com.gaia.autotrade.ws.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.ws.bean.ResponseMsg;
import com.gaia.autotrade.ws.bean.WebSocketServletRequest;
import com.gaia.autotrade.ws.bean.WebSocketServletResponse;
import com.gaia.autotrade.ws.global.PublicField;
import com.gaia.autotrade.ws.manager.WebSocketControllerManager;
import com.gaia.autotrade.ws.manager.WebSocketServiceManager;
import com.gaia.autotrade.ws.service.MarketBaseService;

@Component
public class SubController extends WebSocketController {

	// 服务管理器
	private WebSocketServiceManager m_wsSerManager;

	public SubController() {
		setControllerKey("sub");
	}

	// 获取服务管理器
	@Autowired
	private void setWebSocketServiceManager(WebSocketServiceManager wsSerManager) {
		m_wsSerManager = wsSerManager;
	}

	// 在控制管理器中注册
	@Autowired
	private void setWebSocketControllerManager(WebSocketControllerManager wsConManager) {
		wsConManager.addController(this);
	}

	@Override
	public ResponseMsg onReceive(JSONObject msg) {
		String sub = (String) msg.get("sub");
		try {
			String id = (String) msg.get("id");
			List<String> list = Arrays.asList(sub.split("[.]"));
			if (list.size() < 3) {
				return getResponseErrorMsg("参数错误:" + sub, id);
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			String serviceName = list.get(0);
			String pair = list.get(1);
			String serviceKey = list.get(2);
			if (!list.get(0).equals("market")) {
				return getResponseErrorMsg("参数错误:" + sub, id);
			}
			if (list.size() >= 4) {
				String param = list.get(3);
				map.put("param", param);
			}
			map.put("pair", pair);
			map.put("serviceKey", serviceKey);
			MarketBaseService service = m_wsSerManager.getService(serviceKey);

			WebSocketServletRequest request = new WebSocketServletRequest();
			request.setParamID(id);
			request.setServiceName(serviceName);
			request.setTopic(sub);
			request.setParams(map);
			request.setSid(msg.getString("sid"));

			WebSocketServletResponse response = new WebSocketServletResponse();
			response.setTimestamp(System.currentTimeMillis());
			response.setParamID(id);
			response.setRequestParms(sub);
			response.setSid(msg.getString("sid"));
			service.RevWsSub(request, response);

			return convertWebSocketServletResponse(response);

		} catch (ClassCastException e) {
			return getResponseErrorMsg("id转换错误:", "");
		} catch (Exception e) {
			return getResponseErrorMsg("未知错误:" + sub, (String) msg.get("id"));
		}
	}

	// 生成错误信息对象
	private ResponseMsg getResponseErrorMsg(String errMsg, String id) {
		ResponseMsg resp = new ResponseMsg();
		resp.setErrmsg(errMsg);
		resp.setErrcode("bad-request");
		resp.setTs(System.currentTimeMillis());
		resp.setStatus(PublicField.FAIL_STATUS);
		resp.setId(id);
		return resp;
	}

	// 将WebSocketServletResponse对象转换成ResponseMsgInterfact
	public ResponseMsg convertWebSocketServletResponse(WebSocketServletResponse response) {
		if (response.getStatus() == PublicField.SUCCESSFUL_STATUS) {
			ResponseMsg msg = new ResponseMsg();
			msg.setSubbed(response.getRequestParms());
			msg.setTs(response.getTimestamp());
			msg.setId(response.getParamID());
			msg.setStatus(response.getStatus());
			return msg;
		} else {
			ResponseMsg msg = new ResponseMsg();
			msg.setId(response.getParamID());
			msg.setStatus(response.getStatus());
			msg.setErrmsg(response.getMsg());
			msg.setErrcode("bad-request");
			msg.setTs(response.getTimestamp());
			return msg;
		}
	}

}
