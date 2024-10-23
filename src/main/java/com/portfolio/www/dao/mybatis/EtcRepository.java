package com.portfolio.www.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EtcRepository {
	
	// 특정 카테고리 식사메뉴 가져오기
	public List<String> menu(@Param("category")String category);
	
	// 모든 식사메뉴 카테고리 가져오기
	public List<String> menuCategory();
	
	// 모든 카테고리에서 식사메뉴 가져오기
	public List<String> allMenu();

}
