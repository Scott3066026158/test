package com.kunyi.bitamexJava.mapper;

import java.util.List;

import com.kunyi.bitamexJava.model.TraderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderInfoMapper {
	/**
	 * 查询用户交易表
	 * @return
	 */
	List<TraderInfo> getTraderInfos(@Param("trader")String trader, @Param("code")String code, @Param("direction")Integer direction);
}
