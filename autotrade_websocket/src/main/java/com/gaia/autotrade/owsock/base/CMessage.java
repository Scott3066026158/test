package com.gaia.autotrade.owsock.base;

/**
 * 消息结构
 * 
 */
public class CMessage {
	/**
	 * 创建消息
	 * 
	 */
	public CMessage() {

	}

	/**
	 * 创建消息
	 * 
	 * @param groupID      组ID
	 * @param serviceID    服务ID
	 * @param functionID   功能ID
	 * @param sessionID    登录ID
	 * @param requestID    请求ID
	 * @param socketID     连接ID
	 * @param state        状态
	 * @param compressType 压缩类型
	 * @param bodyLength   包体长度
	 * @param body         包体
	 */
	public CMessage(int groupID, int serviceID, int functionID, int sessionID, int requestID, int socketID, int state,
			int compressType, int bodyLength, byte[] body) {
		m_groupID = groupID;
		m_serviceID = serviceID;
		m_functionID = functionID;
		m_sessionID = sessionID;
		m_requestID = requestID;
		m_socketID = socketID;
		m_state = state;
		m_compressType = compressType;
		m_bodyLength = bodyLength;
		m_body = body;
	}

	/**
	 * 组ID
	 * 
	 */
	public int m_groupID;

	/**
	 * 服务ID
	 * 
	 */
	public int m_serviceID;

	/**
	 * 功能ID
	 * 
	 */
	public int m_functionID;

	/**
	 * 登录ID
	 * 
	 */
	public int m_sessionID;

	/**
	 * 请求ID
	 * 
	 */
	public int m_requestID;

	/**
	 * 连接ID
	 * 
	 */
	public int m_socketID;

	/**
	 * 状态
	 * 
	 */
	public int m_state;

	/**
	 * 压缩类型
	 * 
	 */
	public int m_compressType;

	/**
	 * 包体长度
	 * 
	 */
	public int m_bodyLength;

	/**
	 * 包体
	 * 
	 */
	public byte[] m_body;

	/**
	 * 复制数据
	 * 
	 * @param message 消息
	 */
	public final void Copy(CMessage message) {
		m_groupID = message.m_groupID;
		m_serviceID = message.m_serviceID;
		m_functionID = message.m_functionID;
		m_sessionID = message.m_sessionID;
		m_requestID = message.m_requestID;
		m_socketID = message.m_socketID;
		m_state = message.m_state;
		m_compressType = message.m_compressType;
		m_bodyLength = message.m_bodyLength;
		// m_body = message.m_body;
		m_body = new byte[m_bodyLength];
		System.arraycopy(message.m_body, 0, m_body, 0, m_bodyLength);
	}
}