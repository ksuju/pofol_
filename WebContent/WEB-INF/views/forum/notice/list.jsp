<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String ctx = request.getContextPath();
%>
<section class="section--padding2">
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="">
					<div class="modules__content">
						<div class="withdraw_module withdraw_history">
							<div class="withdraw_table_header">
								<h3>공지사항</h3>
							</div>
							<div class="table-responsive">
								<table class="table withdraw__table">
									<thead>
										<tr>
											<th>No</th>
											<th>제목</th>
											<th></th>
											<th></th>
											<th>Date</th>
											<th>작성자</th>
										</tr>
									</thead>

									<tbody>
										<c:forEach items="${list}" var="i" varStatus="status">
											<tr>
												<td>${i.boardSeq}</td>
												<td><a
													href="<c:url value='/forum/notice/readPage.do?boardSeq=${i.boardSeq}&boardTypeSeq=${i.boardTypeSeq}'/>">
														${i.title} </a> <%-- &nbsp;<c:if test="${commentCounts[i.boardSeq] != 0}"><a>(${commentCounts[i.boardSeq]})</a></c:if> --%>
												</td>

												<td><c:if test="${loginMember eq i.memberId}">
														<button
															onclick="confirmUpdate('${i.memberId}', '${i.boardTypeSeq}', '${i.boardSeq}', '${i.memberId}')"
															class="btn btn--round btn--bordered btn-sm btn-secondary">수정</button>
													</c:if></td>

												<td><c:if test="${loginMember eq i.memberId}">
														<button
															onclick="confirmDelete('${i.memberId}', '${i.boardTypeSeq}', '${i.boardSeq}')"
															class="btn btn--round btn--bordered btn-sm btn-secondary">삭제</button>
													</c:if></td>

												<td>${i.regDtm}</td>
												<td>${i.memberId}</td>
												<%-- <td><a id="deleteBoard" href="<c:url value='/forum/deleteBoard.do?boardSeq=${i.boardSeq}&boardTypeSeq=${i.boardTypeSeq}'/>" onclick="confirmDelete()">삭제</a></td> --%>
											</tr>
										</c:forEach>
									</tbody>

								</table>
								<div
									style="display: inline-block; margin: 0 5px; float: right; padding-right: 10px;">
									<a
										href="<c:url value='/forum/notice/writePage.do?boardTypeSeq=${bdTypeSeq}'/>">
										<button
											class="btn btn--round btn--bordered btn-sm btn-secondary">작성</button>
									</a>
								</div>
								<div class="pagination-area" style="padding-top: 45px;">
									<nav class="navigation pagination" role="navigation">
										<div class="nav-links">
											<!-- 이전 페이지 -->
											<c:if test="${hasPrev}">
												<a class="prev page-numbers"
													href="<c:url value='/forum/notice/listPage.do?bdTypeSeq=${bdTypeSeq}&page=${startPage - 10}&size=10'/>">
													<span class="lnr lnr-arrow-left"></span>
												</a>
											</c:if>
											<!-- 페이지 링크 -->
											<c:forEach begin="${startPage}" end="${endPage}"
												var="pageNum">
												<c:if test="${pageNum eq currentPage}">
													<a class="page-numbers current" href="#"> ${pageNum} </a>
												</c:if>
												<c:if test="${pageNum ne currentPage}">
													<a class="page-numbers"
														href="<c:url value='/forum/notice/listPage.do?bdTypeSeq=${bdTypeSeq}&page=${pageNum}&size=10'/>">
														${pageNum} </a>
												</c:if>
											</c:forEach>
											<!-- 다음 페이지 -->
											<c:if test="${hasNext}">
												<a class="next page-numbers"
													href="<c:url value='/forum/notice/listPage.do?bdTypeSeq=${bdTypeSeq}&page=${startPage + 10}&size=10'/>">
													<span class="lnr lnr-arrow-right"></span>
												</a>
											</c:if>
										</div>
									</nav>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end .col-md-6 -->
		</div>
		<!-- end .row -->
	</div>
	<!-- end .container -->

	<script>
		//게시글 삭제
		function confirmDelete(memberId, boardTypeSeq, boardSeq) {
			if (confirm("게시글을 삭제하시겠습니까?")) {
				let url = '<c:url value="/forum/notice/deleteBoard.do"/>';
				url += '?memberId=' + encodeURIComponent(memberId);
				url += '&boardTypeSeq=' + encodeURIComponent(boardTypeSeq);
				url += '&boardSeq=' + encodeURIComponent(boardSeq);

				window.location.href = url;
			}
		}
		// 게시글 수정
		function confirmUpdate(memberId, boardTypeSeq, boardSeq, memberId) {
			if (confirm("게시글을 수정하시겠습니까?")) {
				let url = '<c:url value="/forum/notice/updatePage.do"/>';
				url += '?memberId=' + encodeURIComponent(memberId);
				url += '&boardTypeSeq=' + encodeURIComponent(boardTypeSeq);
				url += '&boardSeq=' + encodeURIComponent(boardSeq);
				url += '&memberId=' + encodeURIComponent(memberId);

				window.location.href = url;
			}
		}

		window.onload = function() {
			let errorMsg = '<c:out value="${errorMsg}" />';

			if (errorMsg.trim() === '') {
				console.log("에러메세지 없음");
			} else {
				alert(errorMsg);
			}
		}
	</script>
</section>

