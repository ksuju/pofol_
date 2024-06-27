package com.portfolio.www.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.portfolio.www.dto.BoardAttachDto;
import com.portfolio.www.dto.BoardCommentDto;
import com.portfolio.www.dto.BoardDto;
import com.portfolio.www.dto.BoardLikeDto;
import com.portfolio.www.dto.CommentLikeDto;
import com.portfolio.www.dto.EmailAuthDto;
import com.portfolio.www.dto.MemberAuthDto;
import com.portfolio.www.dto.ResumeDto;

public interface NoticeRepository {
	
	List<Map<String, Object>> selectIsLikeList(@Param("memberSeq") Integer memberSeq,
			@Param("boardSeqs") List<Integer> boardSeqs, @Param("boardTypeSeq") int boardTypeSeq);
	
	// 특정 카테고리 메뉴 가져오기
	public List<String> menu(@Param("category")String category);
	
	// 모든 메뉴 카테고리 가져오기
	public List<String> menuCategory();
	
	// 모든 카테고리에서 메뉴 가져오기
	public List<String> allMenu();
	
	// 첨부파일 개수 가져오기	
    List<Map<String, Object>> bringFileCnt(@Param("boardSeqs") List<Integer> boardSeqs, @Param("boardTypeSeq") int boardTypeSeq);
	// 댓글 개수 가져오기
    List<Map<String, Object>> bringCmtCnt(@Param("boardSeqs") List<Integer> boardSeqs, @Param("boardTypeSeq") int boardTypeSeq);

	// 이력서 가져오기 이용 기록
	public int resumeRec(@Param("name")String name,
			@Param("email")String email);
	
	// 이력서 가져오기
	public ResumeDto resume();
	
	// 댓글 좋아요 Y or N 셀렉트
	public List<HashMap<String, Object>> commentIsLike(@Param("memberSeq")int memberSeq,
			@Param("boardSeq")int boardSeq,
			@Param("boardTypeSeq")int boardTypeSeq);
	
	// 댓글 좋아요 Y or N
	public int commentUpDownCvt(CommentLikeDto commentLikeDto);
	
	// 댓글 좋아요
	public int commentUpDown(CommentLikeDto commentLikeDto);
	
	// 게시글 좋아요 Y or N 셀렉트
	public String selectIsLike(@Param("memberSeq")int memberSeq,
			@Param("boardSeq")int boardSeq,
			@Param("boardTypeSeq")int boardTypeSeq);
	
	// 게시글 좋아요 Y or N
	public int thumbUpDownCvt(BoardLikeDto boardLikeDto);
	
	// 게시글 좋아요
	public int thumbUpDown(BoardLikeDto boardLikeDto);
	
	// 댓글 전부 삭제하기 (게시글 지울때 활용)
	public int deleteAllComment(@Param("boardTypeSeq")int boardTypeSeq,
			@Param("boardSeq")int boardSeq);
	
	// 댓글 삭제하기
	public int deleteComment(HashMap<String,Object> params);
	
	// 댓글 수정하기
	public int updateComments(HashMap<String,Object> params);
	
	// 댓글 목록 가져오기
	public List<BoardCommentDto> selectComments(@Param("boardSeq")int boardSeq, @Param("boardTypeSeq")int boardTypeSeq);
	
	// insert comment
	public int insertComment(BoardCommentDto dto);
	
	// 수정페이지 파일 개별 삭제
	public boolean deleteFile(@Param("attachSeq")int attachSeq, @Param("boardSeq")int boardSeq, @Param("boardTypeSeq")int boardTypeSeq);
	
	// attach_seq로 첨부파일 정보 가져오기
	public BoardAttachDto getAttachInfo(int attachSeq);
	
	// 저장되어 있는 모든 파일 가져오기
	public List<BoardAttachDto> selectAllFile(@Param("boardTypeSeq") int boardTypeSeq,
			@Param("boardSeq") int boardSeq);
	
	// boardAttach delete
	public int deleteBoardAttach(@Param("boardTypeSeq") int boardTypeSeq,
			@Param("boardSeq") int boardSeq);
	
	// 파일첨부 > boardAttach insert
	public int insertBoardAttach(BoardAttachDto boardAttachDto);
	
	// reg_member_seq로 member_id 가져오기
	public HashMap<String, String> selectMemberId(int regMemberSeq);
	
	// 게시글 수정하기
	public int updateBoard(HashMap<String, Object> params);
	
	// 특정 게시글 가져오기
	public HashMap<String, Object> selectBoard(@Param("boardSeq") int boardSeq,
			@Param("boardTypeSeq") int boardTypeSeq);
	
	// 게시글 작성
	public int boardCreate(HashMap<String, Object> params);
	
	// 게시글 삭제
	public int boardDelete(@Param("memberId") String memberId,
			@Param("boardTypeSeq") int boardTypeSeq,
			@Param("boardSeq") int boardSeq);
	
	// 게시판 내에 있는 게시글 모두 가져오기
	public List<BoardDto> getList(HashMap<String, Integer> params);
	public List<Integer> getSeq(HashMap<String, Integer> params);
	
	// 게시판 내에 있는 전체 게시글의 수
	public int totalCnt(int boardTypeSeq);
	
	// 이메일 유무 확인
	public int emailCount(String email);
	
	// 계정인증여부 가져오기
	public String getAuthYN(String memberID);
	
	// 인증만료시간 가져오기
	public long getExpireDtm(String email);
	
	// 비밀번호 변경
	public int changePasswd(HashMap<String, String> params);
	
	// authNumSelect db에 저장된 authNum 조회
	public Integer authNumSelect(String email);
	
	// auth_num update to null
	public int updateAuthNumToNull(String email);
	
	// auth_num update
	public int updateAuthNum(HashMap<String, String> params);

	//회원가입
	public int joinMember(HashMap<String, String> params);
	
	//아이디 중복검사
	public List<String> memberSelectAll();
	
	//email 중복검사
	public List<String> emailSelectAll();
	
	//로그인
	public String loginCheak(String memberID);
	
	//멤버 seq 가져오기
	public int getMemberSeq(String memberID);
	
	//멤버지우기
	public int deleteMember(int memberSeq);
	
	//addAuthInfo
	public int addAuthInfo(MemberAuthDto memberAuthDto);
	
	//authURI가져오기
	public List<HashMap<String, Object>> authURI();
	
	//updateAuth
	public int updateAuth(EmailAuthDto emailAuthDto);
	
	//updateMemAuth
	public int updateMemAuth(@Param("memberSeq") int memberSeq);
}
