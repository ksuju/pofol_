package com.portfolio.www.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.www.dao.mybatis.NoticeRepository;
import com.portfolio.www.dto.BoardAttachDto;
import com.portfolio.www.dto.BoardDto;
import com.portfolio.www.util.FileUtil;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	FileUtil fileUtil;

	// attach_seq로 첨부파일 정보 가져오기
	public BoardAttachDto getDownloadFileInfo(int attachSeq) {
		return noticeRepository.getAttachInfo(attachSeq);
	}

	// 게시글 수정
	public int updateBoard(HashMap<String, Object> params, ServletRequest request) {

		System.out.println("======= NoticeService > updateBoard =======");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String now = sdf.format(nowDate);

		params.put("now", now);

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		String logInUser = (String) session.getAttribute("logInUser");

		int memberSeq = noticeRepository.getMemberSeq((String) params.get("memberId"));

		params.put("memberSeq", memberSeq);

		if (logInUser.isEmpty()) {
			return 0;
		} else {
			return noticeRepository.updateBoard(params);
		}

	}

	// 게시글 읽어오기
	public HashMap<String, Object> getReadBoard(int boardSeq, int boardTypeSeq) {
		System.out.println("============getReadBoard > boardInfo=================");
		HashMap<String, Object> boardInfo = new HashMap<String, Object>();

		boardInfo = selectBoard(boardSeq, boardTypeSeq);

		Integer regMemberSeq = (Integer) boardInfo.get("reg_member_seq");

		String memberId = noticeRepository.selectMemberId(regMemberSeq).get("member_id");

		// 저장된 파일 리스트 가져오기
		List<BoardAttachDto> fileList = noticeRepository.selectAllFile(boardTypeSeq, boardSeq);
		
        // 파일이름 저장할 List 생성
        List<String> fileNames = new ArrayList<>();
        // 파일이 현재 저장된 경로를 저장한 List 생성
        List<String> filePaths = new ArrayList<>();

        // fileList에서 파일 이름과 경로 추출하여 리스트에 추가
        for (BoardAttachDto attachDto : fileList) {
            fileNames.add(attachDto.getOrgFileNm());
            filePaths.add(attachDto.getSavePath());
        }

		boardInfo.put("memberId", memberId);
		boardInfo.put("fileList", fileList);
		// 전체다운로드 할 때 사용할 것
		boardInfo.put("fileNames", fileNames);
		boardInfo.put("filePaths", filePaths);

		return boardInfo;
	}

	// 게시글 하나만 가져오기
	public HashMap<String, Object> selectBoard(int boardSeq, int boardTypeSeq) {
		return noticeRepository.selectBoard(boardSeq, boardTypeSeq);
	}

	// 게시글 작성
	@Transactional
	public boolean boardCreate(HashMap<String, Object> params, ServletRequest request, MultipartFile[] attFiles) {
		System.out.println("=========================== service > boardCreate ===========================");

		File destFile = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String now = sdf.format(nowDate);

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		String logInUser = (String) session.getAttribute("logInUser");

		if (logInUser.isEmpty()) {
			return false;
		} else {
			int memberSeq = noticeRepository.getMemberSeq(logInUser);

			params.put("memberSeq", memberSeq);
			params.put("now", now);

			noticeRepository.boardCreate(params);

			int boardTypeSeq = Integer.parseInt((String) params.get("boardTypeSeq"));
			int boardSeq = (int) params.get("boardSeq");

			for (MultipartFile mf : attFiles) {
				// 물리적 파일 저장
				destFile = fileUtil.saveFile(mf);

//				String unqFileNm = UUID.randomUUID().toString().replaceAll("-", "");
				BoardAttachDto boardAttachDto = new BoardAttachDto();

				boardAttachDto.setBoardTypeSeq(boardTypeSeq);
				boardAttachDto.setBoardSeq(boardSeq);

				boardAttachDto.setOrgFileNm(mf.getOriginalFilename());
				boardAttachDto.setChngFileNm(destFile.getName());
				boardAttachDto.setFileType(mf.getContentType());
				boardAttachDto.setFileSize(mf.getSize());
				boardAttachDto.setSavePath(destFile.getPath());

				noticeRepository.insertBoardAttach(boardAttachDto);
			}
			return true;
		}
	}

	// 게시글 삭제하기
	public int boardDelete(String memberId, int boardTypeSeq, int boardSeq) {
		System.out.println("=========================== service > boardDelete ===========================");
		noticeRepository.deleteBoardAttach(boardTypeSeq, boardSeq);
		return noticeRepository.boardDelete(memberId, boardTypeSeq, boardSeq);

	}

	// 게시판 내 게시글 불러오기
	public List<BoardDto> getList(HashMap<String, Integer> params) {

		return noticeRepository.getList(params);
	}

	// 게시판 내 총 게시글 수
	public int totalCnt(int bdTypeSeq) {
		return noticeRepository.totalCnt(bdTypeSeq);
	}
}
