package com.portfolio.www.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class ZipService {
	
	// 압축파일경로설정
	// 날짜출력형식 지정
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	// 지정한 형식을 바탕으로 날짜 출력
    Date now = new Date();
    String nowTime = sdf.format(now);
    
    private String CHECK_PATH = "C:/dev/tmp/" + nowTime;
	private String SAVE_PATH = "C:/dev/tmp/" + nowTime + "/downAll.zip";
	
	// fileNames는 원래 파일 이름 , filePaths는 파일이 저장되어있는 경로
    public void createZip(List<String> fileNames, List<String> filePaths) throws IOException {
    	
    	File destZip = new File(CHECK_PATH);
    	// 경로 없으면 만들어줌
        if (!destZip.exists()) {
        	destZip.mkdirs();
        }
    	
        try (FileOutputStream fout = new FileOutputStream(SAVE_PATH);
             ZipOutputStream zout = new ZipOutputStream(fout)) {

            for (int i = 0; i < filePaths.size(); i++) {
                String sourceFile = filePaths.get(i);
                String originalName = fileNames.get(i);

                File file = new File(sourceFile);
                if (file.exists()) {
                    try (FileInputStream fin = new FileInputStream(file)) {
                        // 압축 파일의 엔트리 이름을 원래 파일 이름으로 설정
                        ZipEntry zipEntry = new ZipEntry(originalName);
                        zout.putNextEntry(zipEntry);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fin.read(buffer)) > 0) {
                            zout.write(buffer, 0, length);
                        }
                        zout.closeEntry();
                    }
                } else {
                    System.err.println("File not found: " + sourceFile);
                }
            }
        }
    }

    public void downloadZip(HttpServletResponse response) throws IOException {
    	
    	//사용자가 전체다운로드 받았을때 다운받은 zip파일의 이름 설정
		/*
		 * String downloadFileName = "downAll";
		 * response.setContentType("application/zip");
		 * response.addHeader("Content-Disposition", "attachment; filename=" +
		 * downloadFileName + ".zip");
		 */
    	
    	// zip파일 다운로드
        try (FileInputStream fis = new FileInputStream(SAVE_PATH);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ServletOutputStream so = response.getOutputStream();
             BufferedOutputStream bos = new BufferedOutputStream(so)) {

            byte[] data = new byte[2048];
            int input;
            while ((input = bis.read(data)) != -1) {
                bos.write(data, 0, input);
                bos.flush();
            }
        }
    }
    
	    public void deleteZip() {
	        File file = new File(SAVE_PATH);
	        if (file.exists()) {
	            if (file.delete()) {
	                System.out.println("downAll.zip파일이 성공적으로 삭제되었습니다.");
	            } else {
	                System.err.println("downAll.zip파일 삭제 실패.");
	            }
	        } else {
	            System.err.println("downAll.zip파일이 존재하지 않습니다.");
	        }
	    }
	}