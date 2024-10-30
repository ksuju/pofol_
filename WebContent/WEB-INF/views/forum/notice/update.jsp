<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/scripts.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet"
	href="<%=ctx%>/assest/template/css/trumbowyg.min.css">
<script src="<%=ctx%>/assest/template/js/vendor/trumbowyg.min.js"></script>
<script src="<%=ctx%>/assest/template/js/vendor/trumbowyg/ko.js"></script>
<script type="text/javascript">
	$('#trumbowyg-demo').trumbowyg({
		lang : 'kr'
	});

	// 파일삭제여부 확인하는 js코드
	function confirmDelete() {
		event.preventDefault();

		let deleteFile = document.querySelector("#deleteFile");
		let confirmed = confirm("정말로 삭제하시겠습니까?");

		if (confirmed) {
			alert("파일이 삭제되었습니다.");
			window.location.href = deleteFile.href;
		} else {
			alert("삭제가 취소되었습니다.");
		}
	}
</script>
<!--================================
            START DASHBOARD AREA
    =================================-->
<section class="support_threads_area section--padding2">
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="question-form cardify p-4">
					<form action="updateBoard.do" method="post"
						enctype="multipart/form-data">
						<input type="hidden" name="boardTypeSeq" value="${boardTypeSeq}" />
						<input type="hidden" name="boardSeq" value="${boardSeq}" /> <input
							type="hidden" name="memberId" value="${memberId}" />
						<div class="form-group">
							<c:if test="${not empty errorMsg}">
								<h5 style="color: red;">${errorMsg}</h5>
							</c:if>
							<label>제목</label> <input value="${title}" type="text"
								name="title" placeholder="Enter title here" required>
						</div>
						<div class="form-group">
							<label>Description</label>
							<div id="trumbowyg-demo">${content}</div>
						</div>
						<div class="form-group">
							<c:if test="${fileList!=null}">
								<c:forEach var="entry" items="${fileList}">
									<c:if test="${not empty entry}">
										<a href='javascript:void(0)'>
											fileName="${entry.orgFileNm}"&nbsp;&nbsp;fileSize="${entry.fileSize}"
										</a>
										&nbsp;&nbsp;&nbsp;
										<a id="deleteFile"
											href="deleteFile.do?attachSeq=${entry.attachSeq}
										&boardSeq=${boardSeq}&boardTypeSeq=${boardTypeSeq}&memberId=${memberId}"
											onclick="confirmDelete()">파일삭제</a>
									</c:if>
									<br>
								</c:forEach>
							</c:if>
						</div>
						<div class="form-group">
							<!-- 목록 길이를 저장 -->
							<c:set var="listLength" value="${fn:length(fileList)}" />
							<!-- 목록길이별로 파일첨부칸 추가 -->								
								<c:choose>
								    <c:when test="${listLength == 0}">
								        <c:forEach var="i" begin="0" end="2">
								        	<div class="form-group">
				                                <div class="attachments">
				                                    <label>Attachments</label>
				                                    <label>
				                                        <span class="lnr lnr-paperclip"></span> Add File
				                                        <span>or Drop Files Here</span>
				                                        <input type="file" name ="attFile" style="display:inline-block;">
				                                    </label>
				                                </div>	
			                                </div>
								        </c:forEach>
								    </c:when>
								    <c:when test="${listLength == 1}">
								        <c:forEach var="i" begin="0" end="1">
								        	<div class="form-group">
				                                <div class="attachments">
				                                    <label>Attachments</label>
				                                    <label>
				                                        <span class="lnr lnr-paperclip"></span> Add File
				                                        <span>or Drop Files Here</span>
				                                        <input type="file" name ="attFile" style="display:inline-block;">
				                                    </label>
				                                </div>	
			                                </div>
								        </c:forEach>
								    </c:when>
								    <c:when test="${listLength == 2}">
								        <c:forEach var="i" begin="0" end="0">
								        	<div class="form-group">
				                                <div class="attachments">
				                                    <label>Attachments</label>
				                                    <label>
				                                        <span class="lnr lnr-paperclip"></span> Add File
				                                        <span>or Drop Files Here</span>
				                                        <input type="file" name ="attFile" style="display:inline-block;">
				                                    </label>
				                                </div>	
			                                </div>
								        </c:forEach>
								    </c:when>
								    <c:when test="${listLength == 3}">
								    </c:when>
								    <c:otherwise>
								    </c:otherwise>
								</c:choose>
								<!-- 목록길이별로 파일첨부칸 추가 끝-->
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn--md btn-primary">Submit
								Request</button>
							<a
								href="<c:url value='/forum/notice/listPage.do?bdTypeSeq=${boardTypeSeq}'/>"
								class="btn btn--md btn-light">Cancel</a>
						</div>
					</form>
				</div>
				<!-- ends: .question-form -->
			</div>
			<!-- end .col-md-12 -->
		</div>
		<!-- end .row -->
	</div>
	<!-- end .container -->
</section>
<!--================================
            END DASHBOARD AREA
    =================================-->
