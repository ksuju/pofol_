package com.portfolio.www.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.www.dao.mybatis.NoticeRepository;

@Service("indexService")
public class IndexService {
	
	private final NoticeRepository noticeRepository;
	
	@Autowired
	public IndexService(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	// 특정 카테고리 메뉴 가져오기
	public String getMenu(String category) {
		
		List<String> menu = noticeRepository.menu(category);
		double random = Math.random();
		int num = (int)Math.round(random * (menu.size()-1));
		
		String menuName = menu.get(num);
		
		return menuName;
	}
	
	// 모든 메뉴 카테고리 가져오기
	public List<String> menuCategory() {
		return noticeRepository.menuCategory();
	}
	
	// 모든 메뉴 가져오기
	public String allMenu() {
		
		List<String> allMenu = noticeRepository.allMenu();

		Collections.shuffle(allMenu);
		
		double random = Math.random();
		int num = (int)Math.round(random * (allMenu.size()-1));
		
		String menuName = allMenu.get(num);
		
		return menuName;
	}
}
