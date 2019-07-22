package com.gaia.autotrade.ws.controller;

import com.alibaba.fastjson.JSONObject;
import com.gaia.autotrade.ws.bean.ResponseErrorMsg;
import com.gaia.autotrade.ws.bean.ResponseMsgInterfact;

public class ErrorController {

	// 单例
	private static ErrorController m_errController;

	// 私有化
	private ErrorController() {
	}

	// 接口
	public static ErrorController getInstance() {
		if (null == m_errController) {
			synchronized (ErrorController.class) {
				if (null == m_errController) {
					m_errController = new ErrorController();
				}
			}
		}
		return m_errController;
	}

	public ResponseMsgInterfact onReceive(JSONObject msg, Exception e) {
		ResponseErrorMsg resp = new ResponseErrorMsg();
		resp.setErrmsg(e.getMessage());
		resp.setTs(System.currentTimeMillis());
		resp.setStatus("error");
		try {
			String id = (String) msg.get("id");
			resp.setId(id);
		} catch (ClassCastException ex) {
			resp.setId("");
		} finally {
			resp.setId("");
		}
		return resp;
	}

	public ResponseMsgInterfact onReceive(String msg, Exception e) {
		ResponseErrorMsg resp = new ResponseErrorMsg();
		resp.setErrmsg(e.getMessage());
		resp.setTs(System.currentTimeMillis());
		resp.setStatus("error");
		resp.setId("");
		return resp;
	}

	public ResponseMsgInterfact onReceive(JSONObject msg, String errMsg) {
		ResponseErrorMsg resp = new ResponseErrorMsg();
		resp.setErrmsg(errMsg);
		resp.setTs(System.currentTimeMillis());
		resp.setStatus("error");
		resp.setId("");
		return resp;
	}

}
