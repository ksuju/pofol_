package com.portfolio.www.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.www.dao.mybatis.NoticeRepository;
import com.portfolio.www.dto.BoardAttachDto;
import com.portfolio.www.dto.BoardCommentDto;
import com.portfolio.www.dto.BoardDto;
import com.portfolio.www.dto.BoardLikeDto;
import com.portfolio.www.dto.CommentLikeDto;
import com.portfolio.www.dto.EmailDto;
import com.portfolio.www.message.MessageEnum;
import com.portfolio.www.util.EmailUtil;
import com.portfolio.www.util.FileUtil;

@Service
public class NoticeService {

	private final NoticeRepository noticeRepository;

	private final FileUtil fileUtil;
	
	private final EmailUtil emailutil;
	
	@Autowired
	public NoticeService(NoticeRepository noticeRepository, FileUtil fileUtil, EmailUtil emailutil) {
		this.noticeRepository = noticeRepository;
		this.fileUtil = fileUtil;
		this.emailutil = emailutil;
	}

	// 아이디로 email 가져오기
	public String getEmail(String memberID) {
		return noticeRepository.getEmail(memberID);
	}
	
	// 아이디 인증번호 생성 및 인증메일 발송
	public int updateAuthNum(HashMap<String, String> params) {
		
		String memberID = params.get("logInUser");
		String dbEmail = noticeRepository.getEmail(memberID);
		params.put("email", dbEmail);
		
		// 6자리의 인증번호 생성
		int authNum = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;
		
		String putAuthNum = authNum+"";
		
		params.put("authNum", putAuthNum);
		
		System.out.println("alkdnsalkdnasd====>"+params);
		
		int cnt = noticeRepository.updateAuthNum(params);
		int emailExist = noticeRepository.emailCount(params.get("email"));
		
		if (emailExist == 0) {
			return Integer.parseInt(MessageEnum.VALLID_EMAIL.getCode());
		}
		
		if (cnt == 1) {
			// 인증 메일 발송
			EmailDto email = new EmailDto();
			email.setReceiver(params.get("email"));
			email.setSubject("아이디 인증 메일입니다.");
			
			String auth = "<p>" + params.get("authNum") + "</p>";
					
			email.setText(auth);

			emailutil.sendMail(email, true);
			return cnt;
		}
		return cnt;
	}
	
	// 아이디 인증여부 가져오기
		public String getAuthYN(String memberID) {
			
			if(memberID.equals("guest")) {
				return "guest";
			} else {
				return noticeRepository.getAuthYN(memberID);
			}
		}
		
		
	// 좋아요 수 가져오기
	public List<Integer> like(List<BoardDto> list, HashMap<String, String> params) {
	    Integer bdTypeSeq = Integer.parseInt(params.get("bdTypeSeq"));
	    List<Integer> likes = new ArrayList<>();
	    
	    for (BoardDto board : list) {
	        Integer boardSeq = board.getBoardSeq(); // BoardDto의 getter 메서드 사용
	        Integer likeCount = noticeRepository.like(boardSeq, bdTypeSeq);
	        likes.add(likeCount);
	    }
	    return likes;
	}
	
	// 좋아요 여부 가져오기
    public Map<Integer, String> getIsLikeMap(Integer memberSeq, List<Integer> boardSeqs, int boardTypeSeq) {
        List<Map<String, Object>> isLikeList = noticeRepository.selectIsLikeList(memberSeq, boardSeqs, boardTypeSeq);

        // Map 변환
        Map<Integer, String> isLikeMap = isLikeList.stream()
                .collect(Collectors.toMap(
                        map -> (Integer) map.get("boardSeq"),
                        map -> (String) map.get("isLike")
                ));

        return isLikeMap;
    }
	
	// 댓글 좋아요 Y or N 셀렉트
	public List<HashMap<String, Object>> commentIsLike(int memberSeq, int boardSeq, int boardTypeSeq) {
		return noticeRepository.commentIsLike(memberSeq, boardSeq, boardTypeSeq);
	}
	
	// 댓글 좋아요 Y or N
	public int commentUpDownCvt(CommentLikeDto commentLikeDto) {
		return noticeRepository.commentUpDownCvt(commentLikeDto);
	}
	
	// 댓글 좋아요
	public int commentUpDown(CommentLikeDto commentLikeDto) {
		return noticeRepository.commentUpDown(commentLikeDto);
	}
	
	// 게시글 좋아요 Y or N 셀렉트
	public String selectIsLike(int memberSeq, int boardSeq, int boardTypeSeq) {
		return noticeRepository.selectIsLike(memberSeq, boardSeq, boardTypeSeq);
	}
	
	// 게시글 좋아요 Y or N
	public int thumbUpDownCvt(BoardLikeDto boardLikeDto) {
		return noticeRepository.thumbUpDownCvt(boardLikeDto);
	}
	
	// 게시글 좋아요
	public int thumbUpDown(BoardLikeDto boardLikeDto) {
		return noticeRepository.thumbUpDown(boardLikeDto);
	}
	
	// member_seq 가져오기
	public Integer getMemberSeq(String memberId) {
		return noticeRepository.getMemberSeq(memberId);
	}
	
	// 댓글 삭제하기
	public int deleteComment(HashMap<String,Object> params) {
		return noticeRepository.deleteComment(params);
	}
	
	// 댓글 수정하기
	public int updateComments(HashMap<String,Object> params) {
		return noticeRepository.updateComments(params);
	}
	
	// 댓글 작성하기
	public int addComment(BoardCommentDto dto,
			HttpServletRequest request) {
		
		System.out.println("======= NoticeService > addComment =======");
		HttpSession session = request.getSession();
		
		String memberId = (String) session.getAttribute("logInUser");
		
		if(noticeRepository.getMemberSeq(memberId) > 0) {
			dto.setMemberSeq(noticeRepository.getMemberSeq(memberId));
		} else {
			return -1;
		}
		
	    // parentCommentSeq가 null이거나 0이면 null로 설정
	    if (dto.getParentCommentSeq() == null || dto.getParentCommentSeq() == 0) {
	        dto.setParentCommentSeq(null);
	    }
		noticeRepository.insertComment(dto);
		
		return 1;
	}
	
	// 수정페이지 파일 개별 삭제
	public boolean deleteFile(@RequestParam HashMap<String, Object> params) {
		int attachSeq = Integer.parseInt((String)params.get("attachSeq"));
		int boardSeq = Integer.parseInt((String)params.get("boardSeq"));
		int boardTypeSeq = Integer.parseInt((String)params.get("boardTypeSeq"));
		
		return noticeRepository.deleteFile(attachSeq, boardSeq, boardTypeSeq);
	}

	// attach_seq로 첨부파일 정보 가져오기
	public BoardAttachDto getDownloadFileInfo(int attachSeq) {
		return noticeRepository.getAttachInfo(attachSeq);
	}

	// 게시글 수정
	public int updateBoard(HashMap<String, Object> params, ServletRequest request, MultipartFile[] attFiles) {

		System.out.println("======= NoticeService > updateBoard =======");
		File destFile = null;
		
		int boardSeq = Integer.parseInt((String)params.get("boardSeq"));
		int boardTypeSeq = Integer.parseInt((String)params.get("boardTypeSeq"));

		String memberId = (String) params.get("memberId");
		
		// 가져오기 끝
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String now = sdf.format(nowDate);

		params.put("now", now);

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		String logInUser = (String) session.getAttribute("logInUser");

		int memberSeq = noticeRepository.getMemberSeq(memberId);

		params.put("memberSeq", memberSeq);

		if (logInUser.isEmpty()) {
			return 0;
		} else {
			for (MultipartFile mf : attFiles) {
				// 물리적 파일 저장
				destFile = fileUtil.saveFile(mf);
				
//				String unqFileNm = UUID.randomUUID().toString().replaceAll("-", "");
				BoardAttachDto boardAttachDto = new BoardAttachDto();
				boardAttachDto.setOrgFileNm(mf.getOriginalFilename());
				boardAttachDto.setBoardTypeSeq(boardTypeSeq);
				boardAttachDto.setBoardSeq(boardSeq);
				boardAttachDto.setChngFileNm(destFile.getName());
				boardAttachDto.setFileType(mf.getContentType());
				boardAttachDto.setFileSize(mf.getSize());
				boardAttachDto.setSavePath(destFile.getPath());
				
				if(mf.getOriginalFilename().isEmpty()) {
					continue;
				} else {
					noticeRepository.insertBoardAttach(boardAttachDto);
				}
			}
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
        
        // 댓글 목록 가져오기
        List<BoardCommentDto> comments = noticeRepository.selectComments(boardSeq, boardTypeSeq);
        
        for (BoardCommentDto comment : comments) {
        	int memberSeq = comment.getMemberSeq();
            HashMap<String, String> memberInfo = noticeRepository.selectMemberId(memberSeq);
            
            String getMemberId = memberInfo.get("member_id");
            comment.setMemberNm(getMemberId);
        }
        
        boardInfo.put("comments", comments);
        
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
				boardAttachDto.setOrgFileNm(mf.getOriginalFilename());
				boardAttachDto.setBoardTypeSeq(boardTypeSeq);
				boardAttachDto.setBoardSeq(boardSeq);
				boardAttachDto.setChngFileNm(destFile.getName());
				boardAttachDto.setFileType(mf.getContentType());
				boardAttachDto.setFileSize(mf.getSize());
				boardAttachDto.setSavePath(destFile.getPath());
				
				if(mf.getOriginalFilename().isEmpty()) {
					continue;
				} else {
					noticeRepository.insertBoardAttach(boardAttachDto);
				}
			}
			return true;
		}
	}

	// 게시글 삭제하기
	public int boardDelete(String memberId, int boardTypeSeq, int boardSeq) {
		System.out.println("=========================== service > boardDelete ===========================");
		noticeRepository.deleteBoardAttach(boardTypeSeq, boardSeq); // 첨부파일 삭제
		noticeRepository.deleteAllComment(boardTypeSeq, boardSeq); // 댓글 삭제
		return noticeRepository.boardDelete(memberId, boardTypeSeq, boardSeq); // 게시글 삭제
	}

	
	// ---------------------------------------------------------------------------------------
	// 게시판 내 게시글 불러오기
	
	public List<BoardDto> getList(HashMap<String, Integer> params) {
	    List<BoardDto> getList = noticeRepository.getList(params);
	    int boardTypeSeq = params.get("bdTypeSeq");

	    // 모든 boardSeq 값을 추출
	    List<Integer> boardSeqs = getList.stream()
	                                     .map(BoardDto::getBoardSeq)
	                                     .collect(Collectors.toList());

	    if (boardSeqs.isEmpty()) {
	        return getList;
	    }

	    // 게시글의 파일 개수, 댓글 개수, 좋아요 개수를 한 번에 가져옴
	    List<Map<String, Object>> boardDetails = noticeRepository.getBoardDetails(boardSeqs, boardTypeSeq);

	    // 파일 개수와 댓글 개수를 Map으로 변환
	    Map<Integer, Integer> fileCountMap = boardDetails.stream()
	        .collect(Collectors.toMap(
	            map -> (Integer) map.get("boardSeq"),
	            map -> ((Long) map.get("fileCount")).intValue()
	        ));

	    Map<Integer, Integer> commentCountMap = boardDetails.stream()
	        .collect(Collectors.toMap(
	            map -> (Integer) map.get("boardSeq"),
	            map -> ((Long) map.get("commentCount")).intValue()
	        ));

	    Map<Integer, Integer> isLikeMap = boardDetails.stream()
	        .collect(Collectors.toMap(
	            map -> (Integer) map.get("boardSeq"),
	            map -> ((Long) map.get("isLike")).intValue()
	        ));

	    // 각 BoardDto에 파일 개수와 댓글 개수, 좋아요 개수를 설정
	    for (BoardDto boardDto : getList) {
	        int boardSeq = boardDto.getBoardSeq();
	        boardDto.setFileCount(fileCountMap.getOrDefault(boardSeq, 0));
	        boardDto.setCommentCount(commentCountMap.getOrDefault(boardSeq, 0));
	        boardDto.setIsLike(isLikeMap.getOrDefault(boardSeq, 0));
	    }

	    return getList;
	}
	
	// ---------------------------------------------------------------------------------------

	// 게시판 내 총 게시글 수
	public int totalCnt(int bdTypeSeq) {
		return noticeRepository.totalCnt(bdTypeSeq);
	}
	
	// 게시판 내 인기글 top5 가져오기
	public List<Map<String, Integer>> getLikeTopFive(int boardTypeSeq) {
		return noticeRepository.getLikeTopFive(boardTypeSeq);
	}
}
