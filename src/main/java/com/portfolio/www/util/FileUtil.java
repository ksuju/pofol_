package com.portfolio.www.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
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
    // SAVE_PATH 캡슐화할것
	private String SAVE_PATH = "C:/dev/tmp/"+nowTime;
	
	public File saveFile(MultipartFile mf) {
		System.out.println("==================== FileUtil>saveFile 진입 ====================");
		File destFile = new File(SAVE_PATH);
		try {
			if( !destFile.exists() ) {
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