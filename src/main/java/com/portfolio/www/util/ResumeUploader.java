package com.portfolio.www.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResumeUploader {

    public static void main(String[] args) {
        // 데이터베이스 연결 정보 설정
        String dbUrl = "jdbc:mysql://localhost:33306/forum?serverTimezone=Asia/Seoul";
        String dbUser = "admin";
        String dbPassword = "a123456789";
        
        // 업로드할 파일 경로 설정
        String filePath = "C:\\dev\\workspace\\pofol_\\WebContent\\assest\\resume\\resume.pdf";

        // 데이터베이스에 연결하고 PDF 파일을 업로드
        try {
            // 데이터베이스에 연결
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // PDF 파일 업로드
            uploadPDF(connection, filePath);

            // 연결 종료
            connection.close();

        } catch (SQLException e) {
            System.err.println("SQL 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // PDF 파일을 데이터베이스에 업로드하는 메서드
    public static void uploadPDF(Connection connection, String filePath) {
        File pdfFile = new File(filePath);
        
        if (!pdfFile.exists()) {
            System.err.println("파일이 존재하지 않습니다: " + filePath);
            return;
        }
        
        try {
            // SQL 쿼리 준비
            String sql = "INSERT INTO forum.resume (save_path, file_type, org_file_nm, file_data) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // 파일 정보 설정
            String savePath = filePath; // 파일이 실제로 저장된 경로
            String fileType = "application/pdf"; // MIME 타입
            String orgFileName = pdfFile.getName(); // 원래 파일 이름

            // 파일을 FileInputStream으로 읽어 들임
            FileInputStream inputStream = new FileInputStream(pdfFile);

            // SQL 파라미터 설정
            statement.setString(1, savePath);
            statement.setString(2, fileType);
            statement.setString(3, orgFileName);
            statement.setBinaryStream(4, inputStream, (int) pdfFile.length());

            // 쿼리 실행
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("PDF 파일이 성공적으로 업로드되었습니다!");
            }

            // 리소스 정리
            statement.close();
            inputStream.close();

        } catch (SQLException | IOException e) {
            System.err.println("오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}