package com.gaia.optional.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaia.optional.entity.OptionalStock;
import com.gaia.optional.mapper.OptionalMapper;
import com.gaia.optional.service.OptionalService;

@Service
public class OptionalServiceImpl implements OptionalService {

	@Autowired
	private OptionalMapper optionalMapper;
	
	@Override
	public List<String> add(String username, String code) {
		String key = username + code;
		List<OptionalStock> list =  optionalMapper.query(key);
		for(OptionalStock optional :list) {
			String value = optional.getKeyValue();
			if(key.equals(value)){
				return null;
			}
		}
		OptionalStock newStock = new OptionalStock();
		newStock.setKeyVaule(key);
		newStock.setOrderNum(0);
		newStock.setType(0);
		optionalMapper.add(newStock);
		
		List<OptionalStock> result = optionalMapper.query(username);
		return getOptionalList(result, username);
	}

	@Override
	public List<String> query(String username) {
		List<OptionalStock> list = optionalMapper.query(username);
		return getOptionalList(list, username);
	}

	@Override
	public List<String> delete(String username, String code) {
		String key = username + code;
		if(!optionalMapper.delete(key)) {
			return null;
		}
		List<OptionalStock> list = optionalMapper.query(username);
		return getOptionalList(list, username);
	}

	@Override
	public List<String> addList(String username, String[] codes) {
		for(String code : codes) {
			List<OptionalStock> list = optionalMapper.query(username + code);
			if(list.size() > 0) {
				continue;
			}
			OptionalStock newStock = new OptionalStock();
			newStock.setKeyVaule(username + code);
			newStock.setOrderNum(0);
			newStock.setType(0);
			optionalMapper.add(newStock);
		}
		List<OptionalStock> list = optionalMapper.query(username);
		return getOptionalList(list, username);
	}

	@Override
	public List<String> delList(String username, String[] codes) {
		for(String code : codes) {
			List<OptionalStock> list = optionalMapper.query(username + code);
			if(list.size() == 0) {
				continue;
			}
			optionalMapper.delete(username + code);
		}
		List<OptionalStock> list = optionalMapper.query(username);
		return getOptionalList(list, username);
	}
	
	 
	public List<String> getOptionalList(List<OptionalStock> list, String username){
		List<String> result = new ArrayList<String>();
		for(OptionalStock stock : list){
			String key = stock.getKeyValue();
			int index = key.indexOf(username);
			result.add(key.substring(index + username.length()));
		}
		return result;
	}
	

}
