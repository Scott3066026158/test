package com.gaia.autotrade.ws.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaia.autotrade.owsock.manager.MarketDataManager;
import com.gaia.autotrade.owsock.market_bean.MarketDepthData;
import com.gaia.autotrade.owsock.market_bean.MarketKLineData;
import com.gaia.autotrade.owsock.market_bean.MarketTickDetailData;
import com.gaia.autotrade.owsock.market_bean.MarketTradeDetailData;
import com.gaia.autotrade.owsock.service.QuoteService;
import com.gaia.autotrade.ws.base.MarketWebSocket;
import com.gaia.autotrade.ws.bean.DepthPushTick;
import com.gaia.autotrade.ws.bean.KLinePushTick;
import com.gaia.autotrade.ws.bean.ResponseMsg;
import com.gaia.autotrade.ws.bean.SubDataBean;
import com.gaia.autotrade.ws.bean.SubKLineData;
import com.gaia.autotrade.ws.bean.TickPushTick;
import com.gaia.autotrade.ws.bean.TradePushTick;

@Component
public class WebSocketMarketDataPusher {

	// Depth数据订阅推送线程
	private DepthPushThread m_depthPushThread = new DepthPushThread(this);

	// Tick数据订阅推送线程
	private TickPushThread m_tickPushThread = new TickPushThread(this);

	// Trade数据订阅推送线程
	private TickPushThread m_tradePushThread = new TickPushThread(this);
	
	// KLine数据订阅推送线程
	private KLinePushThread m_klinePushThread = new KLinePushThread(this);

	// Depth数据推送列表
	private List<MarketDepthData> m_depthPushList = new ArrayList<MarketDepthData>();

	// Tick数据推送列表
	private List<MarketTickDetailData> m_tickPushList = new ArrayList<MarketTickDetailData>();
	
	// Trade数据推送列表
	private List<MarketTradeDetailData> m_tradePushList = new ArrayList<MarketTradeDetailData>();
	
	// KLine数据推送列表
	private List<MarketKLineData> m_klinePushList = new ArrayList<MarketKLineData>();

	// 订阅者管理器
	protected WebSocketSubManager m_subDataManager;

	// Ws会话管理器
	protected WebSocketSessionManager m_wsSenManager;
	
	// 行情服务
	protected QuoteService m_quoteService;
	
	@Autowired
	private void setQuoteService(QuoteService quoteService) {
		m_quoteService = quoteService;
	}

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
	}

	// 获取需要推送的Tick数据
	protected synchronized ArrayList<MarketTickDetailData> getTickPushPair() {
		ArrayList<MarketTickDetailData> list = new ArrayList<MarketTickDetailData>();
		list.addAll(m_tickPushList);
		m_tickPushList.clear();
		return list;
	}
	
	// 推送Trade数据
	public synchronized void addTradePushPair(MarketTradeDetailData pushPair) {
		m_tradePushList.add(pushPair);
	}

	// 获取需要推送的Trade数据
	protected synchronized ArrayList<MarketTradeDetailData> getTradePushPair() {
		ArrayList<MarketTradeDetailData> list = new ArrayList<MarketTradeDetailData>();
		list.addAll(m_tradePushList);
		m_tradePushList.clear();
		return list;
	}

	// 推送深度数据
	public synchronized void addKLinePushPair(MarketKLineData pushDepthData) {
		m_klinePushList.add(pushDepthData);
	}

	// 获取需要推送的深度数据
	protected synchronized ArrayList<MarketKLineData> getKLinePushPair() {
		ArrayList<MarketKLineData> list = new ArrayList<MarketKLineData>();
		list.addAll(m_klinePushList);
		m_klinePushList.clear();
		return list;
	}

	// 构造函数
	public WebSocketMarketDataPusher() {
		m_depthPushThread.start();
		m_tickPushThread.start();
		m_klinePushThread.start();
		m_tradePushThread.start();
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
				if (depthDataList.size() == 0) {
					Thread.sleep(1000);
					continue;
				}
				for (MarketDepthData data : depthDataList) {
					Map<String, SubDataBean> subMap = pusher.m_subDataManager
							.getAllCallBackDepthByNoCopy(data.m_lowCode);
					if (subMap == null) {
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
						} else {
							System.out.println("Depth数据推送成功:" + result);
						}
					}
					pushCount++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}
	}

	// 对于每一笔行情，此对象只需要一个
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
				// 获取推送的Tick数据
				List<MarketTickDetailData> depthTickList = pusher.getTickPushPair();
				if (depthTickList.size() == 0) {
					Thread.sleep(1000);
					continue;
				}
				for (MarketTickDetailData data : depthTickList) {
					Map<String, SubDataBean> subMap = pusher.m_subDataManager.getAllCallBackTickByNoCopy(data.m_lowCode);
					if (subMap == null) {
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
							System.out.println(WebSocketMarketDataPusher.class.getName() + "Tick数据推送失败");
						}
					}
					pushCount++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	// 对于每一笔行情，此对象只需要一个
	public ResponseMsg generateResponseMsg(MarketTickDetailData data, SubDataBean bean) {
		long timestamp = System.currentTimeMillis();
		long timesecond = timestamp / 1000;

		TickPushTick tick = new TickPushTick();
		tick.setId(timesecond);
		tick.setMrid(pushCount++);
		tick.setAmount(data.m_tradeVolIn24Hour);
		tick.setClose(data.m_close);
		tick.setCount(1);
		tick.setHigh(data.m_topPriceIn24Hour);
		tick.setId(timesecond);
		tick.setLow(data.m_floorPriceIn24Hour);
		//24小时开盘价
		tick.setOpen(data.m_preClose);
		tick.setVol(data.m_tradeAmountIn24Hour);
		ResponseMsg msg = new ResponseMsg();
		msg.setCh(bean.getTopic());
		msg.setTs(timestamp);
		msg.setTick(tick);
		return msg;
	}
}

//推送Trade数据线程
class TradePushThread extends Thread {

	private WebSocketMarketDataPusher pusher;

	// 推送次数
	private long pushCount;

	public TradePushThread(WebSocketMarketDataPusher pusher) {
		this.pusher = pusher;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 获取推送的深度数据
				List<MarketTradeDetailData> depthTickList = pusher.getTradePushPair();
				if (depthTickList.size() == 0) {
					Thread.sleep(1000);
					continue;
				}
				for (MarketTradeDetailData data : depthTickList) {
					Map<String, SubDataBean> subMap = pusher.m_subDataManager.getAllCallBackTickByNoCopy(data.m_pair);
					if (subMap == null) {
						System.out.println("交易对子:" + data.m_pair + "暂时无人订阅Trade数据!");
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
							System.out.println(WebSocketMarketDataPusher.class.getName() + "Trade数据推送失败");
						}
					}
					pushCount++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	// 对于每一笔行情，此对象只需要一个
	public ResponseMsg generateResponseMsg(MarketTradeDetailData data, SubDataBean bean) {
		long timestamp = System.currentTimeMillis();
		long timesecond = timestamp / 1000;

		TradePushTick tick = new TradePushTick();
		tick.setAmount(data.m_amount);
		tick.setDirection("");
		tick.setId((int)pushCount);
		tick.setPrice(data.m_price);
		tick.setTs((int)timestamp);
		ResponseMsg msg = new ResponseMsg();
		msg.setCh(bean.getTopic());
		msg.setTs(timestamp);
		msg.setTick(tick);
		return msg;
	}

}

//推送KLine数据线程
class KLinePushThread extends Thread {

	private WebSocketMarketDataPusher pusher;

	// 推送次数
	private long pushCount;

	public KLinePushThread(WebSocketMarketDataPusher pusher) {
		this.pusher = pusher;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 获取推送的KLine数据
				List<MarketKLineData> klineDataList = pusher.getKLinePushPair();
				if (klineDataList.size() == 0) {
					Thread.sleep(1000);
					continue;
				}
				
				//重新请求订阅
				SubKLineData reqData = new SubKLineData();
				for (MarketKLineData data : klineDataList) {
					//当此字段为0时，标识此KLine是请求信息
					if(data.m_subKLineData.m_subscription == 0) {
						SubKLineData subdata = pusher.m_subDataManager.getCallBackKLineReq(data.m_subKLineData.hashCode());
						MarketWebSocket sen = pusher.m_wsSenManager.getWebSocket(subdata.m_sid);
						ResponseMsg msg = generateResponseMsg(data, subdata);
						String result = sen.sendMsg(msg);
						if (result == null) {
							System.out.println(WebSocketMarketDataPusher.class.getName() + "KLine数据推送失败");
						} else {
							System.out.println("KLine数据推送成功:" + result);
						}
						pusher.m_subDataManager.removeCallBackKLineReq(data.m_subKLineData.hashCode());
					}
					// 此字段不为0时则标识此为订阅请求
					else {
						Map<String, SubKLineData> subMap = pusher.m_subDataManager.getAllCallBackKLineByNoCopy(data.m_subKLineData.hashCode());
						if (subMap == null) {
							System.out.println("hashCode:" + data.m_subKLineData.hashCode() + "暂时无人订阅KLine数据!");
							continue;
						}
						Iterator<Map.Entry<String, SubKLineData>> iter = subMap.entrySet().iterator();
						while (iter.hasNext()) {
							Map.Entry<String, SubKLineData> entry = iter.next();
							SubKLineData subData = entry.getValue();
							MarketWebSocket sen = pusher.m_wsSenManager.getWebSocket(subData.m_sid);
							ResponseMsg msg = generateResponseMsg(data, subData);
							String result = sen.sendMsg(msg);
							if (result == null) {
								System.out.println(WebSocketMarketDataPusher.class.getName() + "KLine数据推送失败");
							} else {
								System.out.println("KLine数据推送成功:" + result);
							}
							reqData.copySubData(subData);
						}
					}
				}
				while(true) {
					if( pusher.m_quoteService.GetHistoryDatas(reqData) > 0){
						break;
					}else {
						Thread.sleep(3000);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	// 此方法需要优化，对于每一笔行情，此对象只需要一个
	public ResponseMsg generateResponseMsg(MarketKLineData data, SubKLineData bean) {
		long timestamp = System.currentTimeMillis();
		long timesecond = timestamp / 1000;

		KLinePushTick tick = new KLinePushTick();
		tick.setId(timesecond);
		tick.setOpen(data.m_open);
		tick.setClose(data.m_close);
		tick.setLow(data.m_low);
		tick.setHigh(data.m_high);
		tick.setAmount(data.m_amount);
		tick.setVol(data.m_volume);
		tick.setCount(1);
		ResponseMsg msg = new ResponseMsg();
		msg.setCh(bean.m_topic);
		msg.setTs(timestamp);
		msg.setTick(tick);
		return msg;
	}

}
