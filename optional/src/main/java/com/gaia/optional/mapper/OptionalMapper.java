package com.gaia.optional.mapper;

import java.util.List;

import com.gaia.optional.entity.OptionalStock;

public interface OptionalMapper {
	
	boolean add(OptionalStock stock);
	
	List<OptionalStock> query(String condition);

	boolean delete(String key);
		

}
