package com.portfolio.www.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.portfolio.www.dto.BoardCommentDto;
import com.portfolio.www.dto.CommentLikeDto;

public interface CommentRepository {
	
	// 댓글 전부 삭제하기 (게시글 지울때 활용) (notice)
	public int deleteAllComment(@Param("boardTypeSeq")int boardTypeSeq,
			@Param("boardSeq")int boardSeq);
	
	// 댓글 삭제하기 (notice)
	public int deleteComment(HashMap<String,Object> params);
	
	// 댓글 수정하기 (notice)
	public int updateComments(HashMap<String,Object> params);
	
	// 댓글 목록 가져오기 (notice)
	public List<BoardCommentDto> selectComments(@Param("boardSeq")int boardSeq, @Param("boardTypeSeq")int boardTypeSeq);
	
	// 댓글 작성 (notice)
	public int insertComment(BoardCommentDto dto);
	
	// 댓글 개수 가져오기 (notice)
    List<Map<String, Object>> bringCmtCnt(@Param("boardSeqs") List<Integer> boardSeqs, @Param("boardTypeSeq") int boardTypeSeq);

	// 댓글 좋아요 Y or N 셀렉트 (notice)
	public List<HashMap<String, Object>> commentIsLike(@Param("memberSeq")int memberSeq,
			@Param("boardSeq")int boardSeq,
			@Param("boardTypeSeq")int boardTypeSeq);
	
	// 댓글 좋아요 Y or N (notice)
	public int commentUpDownCvt(CommentLikeDto commentLikeDto);
	
	// 댓글 좋아요 (notice)
	public int commentUpDown(CommentLikeDto commentLikeDto);

}
