package com.gaia.autotrade.ws.service;

import com.gaia.autotrade.ws.bean.WebSocketServletRequest;
import com.gaia.autotrade.ws.bean.WebSocketServletResponse;
import com.gaia.autotrade.ws.global.PublicField;

public abstract class MarketBaseService {

	// 服务key，可在服务管理器中根据key获取到对应的服务
	private String serviceKey;

	public String getServiceKey() {
		return serviceKey;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	/**
	 * 当接收到订阅消息时调用此方法
	 * 
	 * @param request  请求数据
	 * @param response 回报数据
	 * @return 根据返回值确定处理结果
	 */
	public abstract int RevWsSub(WebSocketServletRequest request, WebSocketServletResponse response) throws Exception;

	/**
	 * 当接收到请求消息时调用此方法
	 * 
	 * @param request  请求数据
	 * @param response 回报数据
	 * @return 根据返回值确定处理结果
	 */
	public abstract int RevWsReq(WebSocketServletRequest request, WebSocketServletResponse response) throws Exception;

	/**
	 * Req服务未提供默认方法
	 * 
	 * @param request  请求参数
	 * @param response 返回参数
	 */
	public void revNoProvideReq(WebSocketServletRequest request, WebSocketServletResponse response) {
		response.setMsg(serviceKey + "数据请求接口暂未开放");
		response.setData(null);
		response.setParamID(request.getParamID());
		response.setStatus(PublicField.FAIL_STATUS);
		response.setTimestamp(System.currentTimeMillis());
		response.setRequestParms(request.getTopic());
	}

	/**
	 * Sub服务未提供默认方法
	 * 
	 * @param request  请求参数
	 * @param response 返回参数
	 */
	public void revNoProvideSub(WebSocketServletRequest request, WebSocketServletResponse response) {
		response.setMsg(serviceKey + "数据订阅接口暂未开放");
		response.setData(null);
		response.setParamID(request.getParamID());
		response.setStatus(PublicField.FAIL_STATUS);
		response.setTimestamp(System.currentTimeMillis());
		response.setRequestParms(request.getTopic());
	}
}
