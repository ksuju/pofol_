package com.portfolio.www.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.EtcRepository;
import com.portfolio.www.dao.mybatis.NoticeRepository;

@Service("indexService")
public class IndexService {
	
	private final EtcRepository etcRepository;
	
	@Autowired
	public IndexService(EtcRepository etcRepository) {
		this.etcRepository = etcRepository;
	}

	// 특정 카테고리 식사메뉴 가져오기
	public String getMenu(String category) {
		
		List<String> menu = etcRepository.menu(category);
		double random = Math.random();
		int num = (int)Math.round(random * (menu.size()-1));
		
		String menuName = menu.get(num);
		
		return menuName;
	}
	
	// 모든 식사메뉴 카테고리 가져오기
	public List<String> menuCategory() {
		return etcRepository.menuCategory();
	}
	
	// 모든 식사메뉴 가져오기
	public String allMenu() {
		
		List<String> allMenu = etcRepository.allMenu();

		Collections.shuffle(allMenu);
		
		double random = Math.random();
		int num = (int)Math.round(random * (allMenu.size()-1));
		
		String menuName = allMenu.get(num);
		
		return menuName;
	}
}
