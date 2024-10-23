package com.portfolio.www.dao.mybatis;

import org.apache.ibatis.annotations.Param;

import com.portfolio.www.dto.ResumeDto;

public interface ResumeRepository {
	
	// 이력서 가져오기 이용 기록
	public int resumeRec(@Param("name")String name,
			@Param("email")String email);
	
	// 이력서 가져오기
	public ResumeDto resume();

}
