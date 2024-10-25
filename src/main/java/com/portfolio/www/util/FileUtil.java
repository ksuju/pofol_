package com.portfolio.www.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {
	
	// 날짜출력형식 지정
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	// 지정한 형식을 바탕으로 날짜 출력
    Date now = new Date();
    String nowTime = sdf.format(now);
	
    //@Value("#conis('file.save.path'")
	private String SAVE_PATH = "/home/ec2-user/files/"+nowTime;
	
	public File saveFile(MultipartFile mf) {
		System.out.println("==================== FileUtil>saveFile 진입 ====================");
		
		File destFile = new File(SAVE_PATH);
		
		try {
			// 업로드된 파일이 없거나 비어 있는 경우 예외를 던져서 빈 파일이 저장되지 않도록 방지
		    if(mf == null || mf.isEmpty()) {
		        System.out.println("업로드된 파일이 없거나 비어 있습니다.");
		        throw new IOException();
		    }
		    
		    // 파일이 저장될 디렉토리가 없으면 생성
			if(!destFile.exists()) {
				destFile.mkdirs();
			}
			
			destFile = new File(SAVE_PATH, UUID.randomUUID().toString().replaceAll("-", ""));
			
			mf.transferTo(destFile);
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destFile;
	}
}