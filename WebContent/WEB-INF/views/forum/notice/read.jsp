<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String ctx = request.getContextPath();
%>
<link rel="stylesheet"
	href="<%=ctx%>/assest/template/css/trumbowyg.min.css">
<script src="<%=ctx%>/assest/template/js/vendor/trumbowyg.min.js"></script>
<script src="<%=ctx%>/assest/template/js/vendor/trumbowyg/ko.js"></script>
<script type="text/javascript">
	    $('#trumbowyg-demo').trumbowyg({
	        lang: 'kr'
	    });
	</script>
<!--================================
            START DASHBOARD AREA
    =================================-->
<section class="support_threads_area section--padding2">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="forum_detail_area ">
					<div class="cardify forum--issue">
						<div class="title_vote clearfix">
							<h3>${title}</h3>

							<div class="vote">
								<a href="#"> <span class="lnr lnr-thumbs-up"></span>
								</a> <a href="#"> <span class="lnr lnr-thumbs-down"></span>
								</a>
							</div>
							<!-- end .vote -->
						</div>
						<!-- end .title_vote -->
						<div class="suppot_query_tag">
							<img class="poster_avatar"
								src="<%=ctx%>/assest/template/images/support_avat1.png"
								alt="Support Avatar"> ${memberId} <span>${regDtm}</span>
						</div>
						<p style="margin-bottom: 0; margin-top: 19px;">${content}</p>
						<br> <br>
						<h1>${attachSeq}</h1>
						<c:if test="${fileList!=null}">
							<c:forEach var="entry" items="${fileList}">
								<c:if test="${not empty entry}">
									<a href='download.do?attachSeq=${entry.attachSeq}'> <!-- <a href='/forum/notice/download.do'> -->
										fileName="${entry.orgFileNm}"&nbsp;&nbsp;fileSize="${entry.fileSize}"
									</a>
								</c:if>
								<br>
							</c:forEach>

							<!-- 조건 : 파일이 하나만 첨부되어 있거나 첨부되어 있지 않을때 a태그 출력 xxxxx -->
							<c:if test="${fn:length(fileList)>1}">
								<a
									href="<c:url value='downloadAll.do?boardSeq=${boardSeq}&boardTypeSeq=${boardTypeSeq}'/>"
									id="downAllButton">전체다운로드</a>
							</c:if>

						</c:if>
					</div>
					<!-- end .forum_issue -->


					<div class="forum--replays cardify">
						<div class="area_title">
							<h4>1 Replies</h4>
						</div>
						<c:forEach items="${comments}" var="comment" varStatus="status">
							<!-- end .area_title -->
							<div class="forum_single_reply">
								<div class="reply_content">
									<div class="name_vote" style="display: flex; justify-content: space-between; align-items: center;">
									    <div class="pull-left" style="flex: 1;">
									        <h4>${comment.memberNm}
									            <!-- <span>staff</span> -->
									        </h4>
									        <p>${comment.regDtm}</p>
									        <c:if test="${not empty comment.updateDtm}">
									            <p>수정된 날짜 : ${comment.updateDtm}</p>
									        </c:if>
									    </div>
									    <!-- end .pull-left -->
									
									    <c:if test="${logInUser eq comment.memberNm}">
									        <div class="delete-button" style="margin-right: 10px;">
									            <form action="deleteComment.do" method="post">
													<input type="hidden" name="logInUser" value="${logInUser}"/>
													<input type="hidden" name="boardTypeSeq" value="${boardTypeSeq}"/>
													<input type="hidden" name="boardSeq" value="${boardSeq}"/>
													<input type="hidden" name="commentSeq" value="${comment.commentSeq}"/>
													
									                <button
									                    class="btn btn--round btn--bordered btn-sm btn-secondary">
									                    <input type="submit" value="삭제"
									                    style="background-color: rgba(0, 0, 0, 0); border: none"/>
									                </button>
									            </form>
									        </div>
									    </c:if>
									
									    <div class="vote" style="flex: 0 0 auto; text-align: right;">
									        <a href="#" class="active">
									            <span class="lnr lnr-thumbs-up"></span>
									        </a>
									        <a href="#" class="">
									            <span class="lnr lnr-thumbs-down"></span>
									        </a>
									    </div>
									</div>
									<!-- end .vote -->
									<p>${comment.content}</p>
								</div>
								<!-- end .reply_content -->
							</div>
							<!-- 댓글 수정내용 입력칸 -->
							<c:if test="${logInUser eq comment.memberNm}">
								<form action="updateComment.do" method="post" style="display:flex; border:1px solid rgba(0, 0, 0, 0.2)">
									<input type="hidden" name="logInUser" value="${logInUser}"/>
									<input type="hidden" name="boardTypeSeq" value="${boardTypeSeq}"/>
									<input type="hidden" name="boardSeq" value="${boardSeq}"/>
									<input type="hidden" name="commentSeq" value="${comment.commentSeq}"/>
									<div style="width:90%">
										<input type="text" name="content" placeholder="수정할 댓글 내용을 입력해주세요."/>
									</div>
									<div style="width:10%">
										<input type="submit" value="수정" style="width:100%"/>
									</div>
								</form>
							</c:if>
							<!-- 댓글 수정내용 입력칸 끝 -->
							<!-- end .forum_single_reply -->
						</c:forEach>

						<div class="comment-form-area">
							<h4>Leave a comment</h4>
							<!-- comment reply -->
							<div class="media comment-form support__comment">
								<div class="media-left">
									<a href="#"> <img class="media-object"
										src="<%=ctx%>/assest/template/images/m7.png"
										alt="Commentator Avatar">
									</a>
								</div>
								<div class="media-body">
									<form action="javascript:void(0)" class="comment-reply-form">
										<div id="trumbowyg-demo"></div>
										<button class="btn btn--sm btn--round"
											onClick='addComment(${boardSeq}, ${boardTypeSeq});'>Post
											Comment</button>
									</form>
								</div>
							</div>
							<!-- comment reply -->
						</div>
					</div>
					<!-- end .forum_replays -->
				</div>
				<!-- end .forum_detail_area -->
			</div>
			<!-- end .col-md-12 -->
		</div>
		<!-- end .row -->
	</div>
	<!-- end .container -->
</section>
<script>
	    function addComment(boardSeq, boardTypeSeq) {
	    	let url = '<c:url value="/forum/notice/reply.do"/>';
	    	$.ajax({        
	    		type : 'POST',
	    		url : url,
	    		headers : {
	    			"Content-Type" : "application/json"
	    		},
	    		dataType : 'JSON',
	    		data : JSON.stringify ({
	    			boardSeq : boardSeq,
	    			boardTypeSeq : boardTypeSeq,
	    			content: $('#trumbowyg-demo').trumbowyg('html')
	    		}),
	    		success : function(result) {
	    			if(result == 1) {
	    				alert('댓글작성완료');
	    				window.location.reload();
	    			}
	    			else {
	      			alert('실패!');
	    			}
	    		},
	    		error : function(request, status, error) {
	    			console.log(error)
	    		}
	    	});
	    }
    </script>
<!--================================
            END DASHBOARD AREA
    =================================-->
