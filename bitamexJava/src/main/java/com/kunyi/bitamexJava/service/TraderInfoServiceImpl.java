package com.kunyi.bitamexJava.service;

import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.kunyi.bitamexJava.mapper.TraderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kunyi.bitamexJava.dao.TraderInfoService;
import com.kunyi.bitamexJava.model.TraderInfo;

@Service
public class TraderInfoServiceImpl implements TraderInfoService{

	@Autowired
	private TraderInfoMapper traderInfoMapper;
	@Override
	public String queryAll(String trader,String code,Integer direction) {
		List<TraderInfo> traderInfos = traderInfoMapper.getTraderInfos(trader, code, direction);
		String jsonString = JSONObject.toJSONString(traderInfos);
		return jsonString;
	}
}
