package com.portfolio.www.dao.mybatis;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portfolio.www.dto.BoardAttachDto;
import com.portfolio.www.dto.BoardDto;
import com.portfolio.www.dto.EmailAuthDto;
import com.portfolio.www.dto.MemberAuthDto;

public interface NoticeRepository {
	
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
