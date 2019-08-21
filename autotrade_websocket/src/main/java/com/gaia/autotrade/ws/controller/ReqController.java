package com.gaia.autotrade.ws.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
public class ReqController extends WebSocketController {

	// 服务管理器
	private WebSocketServiceManager m_wsSerManager;

	public ReqController() {
		setControllerKey("req");
	}

	// 在控制管理器中注册
	@Autowired
	private void setWebSocketControllerManager(WebSocketControllerManager wsConManager) {
		wsConManager.addController(this);
	}

	// 获取服务管理器
	@Autowired
	private void setWebSocketServiceManager(WebSocketServiceManager wsSerManager) {
		m_wsSerManager = wsSerManager;
	}

	@Override
	public ResponseMsg onReceive(JSONObject msg) {
		String req = (String) msg.get("req");
		try {
			String id = (String) msg.get("id");
			List<String> list = Arrays.asList(req.split("[.]"));
			if (list.size() < 3) {
				return getResponseErrorMsg("参数错误:" + req, id);
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			String serviceName = list.get(0);
			String pair = list.get(1);
			String serviceKey = list.get(2);
			if (!list.get(0).equals("market")) {
				return getResponseErrorMsg("参数错误:" + req, id);
			}
			if (list.size() == 4) {
				String param = list.get(3);
				map.put("param", param);
			}
			map.put("pair", pair);
			map.put("serviceKey", serviceKey);

			// 遍历取出其他参数
			Iterator<Entry<String, Object>> it = msg.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				map.put(entry.getKey(), (String) entry.getValue());
			}
			MarketBaseService service = m_wsSerManager.getService(serviceKey);
			WebSocketServletRequest request = new WebSocketServletRequest();
			request.setParamID(id);
			request.setServiceName(serviceName);
			request.setTopic(req);
			request.setParams(map);
			request.setSid(msg.getString("sid"));
			WebSocketServletResponse response = new WebSocketServletResponse();
			response.setTimestamp(getServerTime());
			response.setParamID(id);
			response.setRequestParms(req);
			response.setStatus(PublicField.FAIL_STATUS);
			response.setMsg("服务器未接收处理");
			response.setData(null);
			response.setSid(msg.getString("sid"));
			service.RevWsReq(request, response);

			return convertWebSocketServletResponse(response);

		} catch (ClassCastException e) {
			return getResponseErrorMsg("参数类型转换错误:", (String) msg.get("id"));
		} catch (Exception e) {
			return getResponseErrorMsg("未知错误:" + req, (String) msg.get("id"));
		}
	}

	// 获取当前服务器时间
	private long getServerTime() {
		return System.currentTimeMillis();
	}

	// 生成错误信息对象
	private ResponseMsg getResponseErrorMsg(String errMsg, String id) {
		ResponseMsg resp = new ResponseMsg();
		resp.setErrmsg(errMsg);
		resp.setTs(getServerTime());
		resp.setStatus("error");
		resp.setId(id);
		return resp;
	}

	// 将WebSocketServletResponse对象转换成ResponseMsgInterfact
	public ResponseMsg convertWebSocketServletResponse(WebSocketServletResponse response) {
		if (response.getStatus() == PublicField.SUCCESSFUL_STATUS) {
			ResponseMsg msg = new ResponseMsg();
			msg.setCh(response.getRequestParms());
			msg.setTick(response.getData());
			msg.setTs(response.getTimestamp());
			msg.setId(response.getParamID());
			msg.setStatus(response.getStatus());
			return msg;
		} else {
			ResponseMsg msg = new ResponseMsg();
			msg.setId(response.getParamID());
			msg.setStatus(response.getStatus());
			msg.setErrmsg(response.getMsg());
			msg.setTs(response.getTimestamp());
			return msg;
		}
	}
}
