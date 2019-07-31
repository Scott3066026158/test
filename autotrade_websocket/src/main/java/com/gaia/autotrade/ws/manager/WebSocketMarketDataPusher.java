package com.gaia.autotrade.ws.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.MarketDepthData;
import com.gaia.autotrade.owsock.market_bean.MarketTickDetailData;
import com.gaia.autotrade.ws.base.MarketWebSocket;
import com.gaia.autotrade.ws.bean.DepthPushTick;
import com.gaia.autotrade.ws.bean.ResponseMsg;
import com.gaia.autotrade.ws.bean.SubDataBean;
import com.gaia.autotrade.ws.bean.TickPushTick;

@Component
public class WebSocketMarketDataPusher {

	// 深度数据订阅推送线程
	private DepthPushThread m_depthPushThread = new DepthPushThread(this);

	// Tick数据订阅推送线程
	private TickPushThread m_tickPushThread = new TickPushThread(this);

	// 深度数据推送列表
	private List<MarketDepthData> m_depthPushList = new ArrayList<MarketDepthData>();

	// Tick数据推送列表
	private List<MarketTickDetailData> m_tickPushList = new ArrayList<MarketTickDetailData>();

	// 订阅者管理器
	protected WebSocketSubManager m_subDataManager;

	// Ws会话管理器
	protected WebSocketSessionManager m_wsSenManager;


	// 推送深度数据
	public synchronized void addDepthPushPair(MarketDepthData pushDepthData) {
		m_depthPushList.add(pushDepthData);
	}

	// 获取需要推送的深度数据
	protected synchronized ArrayList<MarketDepthData> getDepthPushPair() {
		ArrayList<MarketDepthData> list = new ArrayList<MarketDepthData>();
		list.addAll(m_depthPushList);
		m_depthPushList.clear();
		return list;
	}

	// 推送Tick数据
	public synchronized void addTickPushPair(MarketTickDetailData pushPair) {
		m_tickPushList.add(pushPair);
		synchronized (m_tickPushThread) {
			m_tickPushThread.notify();
		}
	}

	// 获取需要推送的Tick数据
	protected synchronized ArrayList<MarketTickDetailData> getTickPushPair() {
		ArrayList<MarketTickDetailData> list = new ArrayList<MarketTickDetailData>();
		list.addAll(m_tickPushList);
		m_tickPushList.clear();
		return list;
	}

	// 构造函数
	public WebSocketMarketDataPusher() {
		m_depthPushThread.start();
		m_tickPushThread.start();
		MarketDataManager.getInstance().setWebSocketMarketDataPusher(this);
	}

	@Autowired
	public void setWebSocketSubManager(WebSocketSubManager subDataManager) {
		this.m_subDataManager = subDataManager;
	}

	@Autowired
	public void setWebSocketSubManager(WebSocketSessionManager wsSenManager) {
		this.m_wsSenManager = wsSenManager;
	}
}

// 推送深度数据线程
class DepthPushThread extends Thread {

	private WebSocketMarketDataPusher pusher;

	// 推送次数
	private long pushCount;

	public DepthPushThread(WebSocketMarketDataPusher pusher) {
		this.pusher = pusher;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 获取推送的深度数据
				List<MarketDepthData> depthDataList = pusher.getDepthPushPair();
				if(depthDataList.size() == 0) {
					Thread.sleep(1000);
					continue;
				}
				for (MarketDepthData data : depthDataList) {
					Map<String, SubDataBean> subMap = pusher.m_subDataManager.getAllCallBackDepthByNoCopy(data.m_lowCode);
					if(subMap == null){
						System.out.println("交易对子:" + data.m_code + "暂时无人订阅Depth数据!");
						continue;
					}
					Iterator<Map.Entry<String, SubDataBean>> iter = subMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry<String, SubDataBean> entry = iter.next();
						SubDataBean bean = entry.getValue();
						MarketWebSocket sen = pusher.m_wsSenManager.getWebSocket(bean.getSid());
						ResponseMsg msg = generateResponseMsg(data, bean);
						String result = sen.sendMsg(msg);
						if (result == null) {
							System.out.println(WebSocketMarketDataPusher.class.getName() + "Depth数据推送失败");
						}else {
							System.out.println("Depth数据推送成功:" + result);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
		}
	}

	// 此方法需要优化，对于每一笔行情，此对象只需要一个
	public ResponseMsg generateResponseMsg(MarketDepthData data, SubDataBean bean) {
		long timestamp = System.currentTimeMillis();
		long timesecond = timestamp / 1000;

		DepthPushTick tick = new DepthPushTick();
		tick.setId(timesecond);
		tick.setMrid(pushCount++);
		tick.setVersion(timesecond);
		tick.setCh(bean.getTopic());
		tick.setTs(timestamp);
		List<ArrayList<Double>> bids = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < data.m_bidPriceCount && i < data.m_bidVolumeCount; i++) {
			Double price = data.m_bidPriceList.get(i);
			Double volume = data.m_bidVolumeList.get(i);
			ArrayList<Double> list = new ArrayList<Double>();
			list.add(price);
			list.add(volume);
			bids.add(list);
		}

		List<ArrayList<Double>> asks = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < data.m_askPriceCount && i < data.m_askVolumeCount; i++) {
			Double price = data.m_askPriceList.get(i);
			Double volume = data.m_askVolumeList.get(i);
			ArrayList<Double> list = new ArrayList<Double>();
			list.add(price);
			list.add(volume);
			asks.add(list);
		}
		tick.setBids(bids);
		tick.setAsks(asks);

		ResponseMsg msg = new ResponseMsg();
		msg.setCh(bean.getTopic());
		msg.setTs(timestamp);
		msg.setTick(tick);
		return msg;
	}

}

//推送Tick数据线程
class TickPushThread extends Thread {

	private WebSocketMarketDataPusher pusher;

	// 推送次数
	private long pushCount;
	
	public TickPushThread(WebSocketMarketDataPusher pusher) {
		this.pusher = pusher;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 获取推送的深度数据
				List<MarketTickDetailData> depthTickList = pusher.getTickPushPair();
				if(depthTickList.size() == 0) {
					Thread.sleep(1000);
					continue;
				}
				for (MarketTickDetailData data : depthTickList) {
					Map<String, SubDataBean> subMap = pusher.m_subDataManager.getAllCallBackDepthByNoCopy(data.m_code);
					if(subMap == null){
						System.out.println("交易对子:" + data.m_code + "暂时无人订阅Tick数据!");
						continue;
					}
					Iterator<Map.Entry<String, SubDataBean>> iter = subMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry<String, SubDataBean> entry = iter.next();
						SubDataBean bean = entry.getValue();
						MarketWebSocket sen = pusher.m_wsSenManager.getWebSocket(bean.getSid());
						ResponseMsg msg = generateResponseMsg(data, bean);
						String result = sen.sendMsg(msg);
						if (result == null) {
							System.out.println(WebSocketMarketDataPusher.class.getName() + "Depth数据推送失败");
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	// 此方法需要优化，对于每一笔行情，此对象只需要一个
		public ResponseMsg generateResponseMsg(MarketTickDetailData data, SubDataBean bean) {
			long timestamp = System.currentTimeMillis();
			long timesecond = timestamp / 1000;

			TickPushTick tick = new TickPushTick();
			tick.setId(timesecond);
			tick.setMrid(pushCount++);

			ResponseMsg msg = new ResponseMsg();
			msg.setCh(bean.getTopic());
			msg.setTs(timestamp);
			return msg;
		}


}
