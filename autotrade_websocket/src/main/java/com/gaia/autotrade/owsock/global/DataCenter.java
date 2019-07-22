package com.gaia.autotrade.owsock.global;


import com.gaia.autotrade.owsock.base.BaseService;
import com.gaia.autotrade.owsock.service.QuoteService;

public class DataCenter
{
	//是否是第一次连接标记
	private static boolean m_firstConnect = true;
	
	//连接状态
	private static int m_connectState = 0;

	/**
	 获取登陆服务器连接状态
	 */
	public static int GetConnectState()
	{
		return DataCenter.m_connectState;
	}

	public static boolean m_isDisconnected = false;

	//获取是否断开连接
	public static boolean IsDisconnected()
	{
		return m_isDisconnected;
	}


	public static String m_mdServiceName = "MDService";
	private static QuoteService m_mdService = new QuoteService(m_mdServiceName);
	
	/**
	 获取行情服务
	 */
	public static QuoteService GetMDService()
	{
		return DataCenter.m_mdService;
	}

	/**
	 * @param reConnectFlag 是否是第一次连接的标志(重连标志)
	 * @return 连接状态
	 */
	public static void Connect()
	{
		int quoteSocket = GetMDService().ConnectServer("ss.gaiafintech.com:13334");
		if(quoteSocket < 0)
		{
			System.out.println("连接失败!");
		}
		else
		{
			System.out.println("连接成功!");
		}
		m_connectState = 1;
		//当第一次连接时,启动心跳线程和连接检查线程
		if(m_firstConnect)
		{
			new Thread() {
				public void run()
				{
					OnHeartBeatThread();
				}
			}.start();
			new Thread() {
				public void run()
				{
					OnReconnectThread();
				}
			}.start();
			m_firstConnect = false;
		}
	}

	/**
	 取消连接
	 @return 状态
	 */
	public static int DisConnect()
	{
		m_connectState = 0;
		m_isDisconnected = true;
		return 1;
	}


	//启动心跳线程
	private static void OnHeartBeatThread()
	{
		//每隔3秒向TradeService,QuoteService发送一个无用数据
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("心跳线程已启动!");
				while(true)
				{
					if(!m_isDisconnected)
					{
						//3秒一心跳
						m_mdService.KeepAlive(m_mdService.GetSocketID());
					}
					try
					{
						Thread.sleep(3000);
					}
					catch (Exception e )
					{

					}
				}
			}
		});
		t.start();
	}
	
	//重连线程(5秒后启动,当连接正常,5秒检查一次,当连接断开,3秒重连一次)
	private static void OnReconnectThread()
	{
		System.out.println("重连线程已启动!");
		try
		{
			Thread.sleep(5000);
		}
		catch (Exception e )
		{

		}
		while (true)
		{
			if (m_isDisconnected)
			{
				while (true)
				{
					if(Reconnect())
					{
						m_isDisconnected = false;
						break;
					}
					try
					{
						Thread.sleep(3000);
					}
					catch (Exception ex)
					{

					}
				}
			}
			int timeout = 1000;
			while(timeout > 0 && !m_isDisconnected)
			{
				timeout--;
				try
				{
					Thread.sleep(5000);
				}
				catch (Exception ex)
				{

				}
			}
		}
	}
	
	/**
	 * 重连方法
	 */
	public static boolean Reconnect()
	{
		if(!m_mdService.m_connected)
		{
			int quoteConnected = GetMDService().ConnectServer("ss.gaiafintech.com:13334");
			if(quoteConnected < 0) {
				return false;
			}
		}
		System.out.println("重连成功!");
		return true;
	}

	/**
	 开启服务
	 */
	public static void StartService()
	{
		BaseService.AddService(m_mdService);
	}
}