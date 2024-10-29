<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
String ctx = request.getContextPath();
%>
<script src="<%=ctx%>/assest/template/js/vendor/jquery/jquery-1.12.3.js"></script>
<link rel="stylesheet"
	href="<%=ctx%>/assest/template/css/trumbowyg.min.css">
<script src="<%=ctx%>/assest/template/js/vendor/trumbowyg.min.js"></script>
<script src="<%=ctx%>/assest/template/js/vendor/trumbowyg/ko.js"></script>
	<!--================================
	    START FEATURE AREA
	=================================-->

	<style>
		.feature__img img {
			width: 112px;
            height: 140px;
		}
		
		.content_01 {
		    display: inline-block;
		    text-align: left !important;
		    width: 70%;
		}
		
		.content_02 {
		    display: inline-block;
		    text-align : left !important;
		}
		.content_02 p {
			margin-top : 15px;
		}
		
		#introduction {
			flex : 0 0 66.666666% !important;
			width : 66.666666% !important;
			max-width: 66.666666% !important;
		}
		
		
		.post-container {
		    display: grid;
		    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
		    gap: 20px;
		    margin: 20px;
		}
		
		.post-card {
		    background-color: #f9f9f9;
		    border: 1px solid #ddd;
		    border-radius: 8px;
		    padding: 15px;
		    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
		    height: 230px; /* 고정된 높이 설정 */
		    display: flex;
		    flex-direction: column;
		    justify-content: space-between;
		    transition: transform 0.3s, box-shadow 0.3s;
		}
		
		.post-title {
		    font-size: 1.1em;
		    color: #333;
		    margin: 0 0 10px;
		    white-space: nowrap; /* 한 줄로 고정 */
		    overflow: hidden;
		    text-overflow: ellipsis;
		}
		
		.post-excerpt {
		    margin: 10px 0;
		    font-size: 0.95em;
		    line-height: 1.4em;
		    max-height: 2.8em; /* 최대 두 줄로 표시 */
		    overflow: hidden;
		    text-overflow: ellipsis;
		    display: -webkit-box; 
		    -webkit-line-clamp: 2; /* 두 줄로 자르기 */
		    -webkit-box-orient: vertical;
		    white-space: normal;
		}
		
		.post-meta {
		    font-size: 0.9em;
		    color: #777;
		    margin-bottom: 10px;
		}
		
		.read-more {
		    display: inline-block;
		    padding: 8px 12px;
		    background-color: #007bff;
		    color: white;
		    text-decoration: none;
		    border-radius: 4px;
		    align-self: flex-end; /* 링크를 아래쪽에 고정 */
		    margin : 0 auto;
		}
		
		.read-more:hover {
		    background-color: #0056b3;
		    color: white;
		}
		
	</style>

    <section class="features section--padding">
        <!-- start container -->
        <div class="container">
            <!-- start row -->
            <div class="row">
                <!-- start search-area -->
                <div class="col-lg-4 col-md-6">
                    <div class="feature">
                        <div class="feature__img">
                            <img src="<c:url value='/assest/template/images/ksuju.jpg'/>" alt="feature" />
                        </div>
                        <div class="feature__title">
                            <h3>프로필 / Profile</h3>
                        </div>
                        <div class="feature__desc">
                        	<div class="content_01">
                        		<p>이름 : 강성준</p>
                        		<p>생년월일 : 1995-07-22</p>
                            	<p>전화번호 : 010-4172-8036</p>
                            	<p>이메일 : kyd547@naver.com</p>
                        	</div>
                        </div>
                    </div>
                    <!-- end /.feature -->
                    
					<div class="feature">
						<div class="feature__title">
	                    	<h3>기술 / Skill</h3>
	                    </div>
	                    <div class="feature__desc">
                        	<div class="content_01">
                        		<p>백엔드 : JAVA, Spring</p>
								<p>프론트엔드 : HTML, CSS, JavaScript, JSP</p>
								<p>데이터베이스 : MySQL, MyBatis</p>
								<p>클라우드 : AWS(EC2, RDS)</p>
								<p>서버 : Nginx</p>
								<p>기타 : Git, GitHub</p>
                        	</div>
                        </div>
	                </div>
                </div>
                <!-- end /.col-lg-4 col-md-6 -->
                
                <!-- start search-area -->
                <div class="col-lg-4 col-md-6" id="introduction">
                    <div class="feature">
                        <div class="feature__title">
                            <h3>Ksuju 최신 포스트</h3>
                        </div>
                        <div class="feature__desc">
                        	<!-- <div class="content_02"> -->
                        	<div class="post-container">
                        	    <!-- Ksuju 최신 포스트 -->
							    <c:forEach items="${rssItems}" var="item" varStatus="status">
							        <c:if test="${status.index < 6}">
							            <div class="post-card">
							                <h3 class="post-title">
							                	<a href="${item.link}" target="_blank">${item.title}</a>
							                </h3>
							                
							                <p class="post-meta">${item.category} / ${item.pubDate}</p>
							                
											<p class="post-excerpt"><c:out value="${item.description}"/></p>
											
							                <a href="${item.link}" class="read-more" target="_blank">자세히 보기</a>
							            </div>
							        </c:if>
							    </c:forEach>
                        	
                        		<!-- 
	                        	<br>
	                        	<h6>태성텍㈜ – 3D 모델링 프로그래밍</h6>
	                        	<p>
	                        		- 21년 8월 ~ 22년 12월 (1년 5개월)
	                        		<br>
		                        	- 3D모델링 제작 & 수정 (CAD, HYPERMILL)
		                        	<br>
		                        	- 3D모델링 교육, 재고관리 및 납기관리
	                        	</p>
	                        	<br>
	                        	<h6>정석코딩 – 백엔드 웹개발자 취업과정 오프라인 교육</h6>
	                        	<p>
	                        		- 23년 10월 ~ 24년 3월 (5개월)
	                        		<br>
	                        		- Java, 객체지향개념과 설계, TDD, HTML & CSS, DB모델링, git
	                        		<br>
	                        		- Spring + MyBatis, SQL기본(MySQL), JavaScript기본, Web기본, 팀 프로젝트(8주)
	                        	</p>
	                        	<br>
	                        	<h6>오프라인 그룹 스터디 – 개인 프로젝트 배포 및 관리</h6>
	                        	<p>
	                        		- 회원가입, 이메일인증
	                        		<br>
	                        		- 로그인/세션 처리/메세지 처리
	                        		<br>
	                        		- 게시판 : 좋아요/싫어요, 첨부파일, 댓글
	                        		<br>
	                        		- AWS 구성, 배포, Junit Test, nginx proxy server, maven deploy, Jenkins 배포
	                        		<br>
	                        		- MySQL 통계 쿼리
	                        	</p>
	                        	<br>
	                        	<h6>건양대학교 사회복지학 학사</h6>
	                        	<p>
	                        		- 20년 2월 졸업
	                        	</p>
	                        	 -->
                        	</div>
                        </div>
                    </div>
                    <!-- end /.feature -->
                </div>
                <!-- end /.col-lg-4 col-md-6 -->
            </div>
            <!-- end /.row -->
        </div>
        <!-- end /.container -->
    </section>
    
    <script type="text/javascript">
	    $('#trumbowyg-demo').trumbowyg({
	        lang: 'kr'
	    });
	    
	    $(document).ready(function() {
	        // ${alert}가 존재하면 alert를 띄운다
	        var alertMessage = "${alert}";
	        if (alertMessage && alertMessage.trim().length > 0) {
	            alert(alertMessage);
	        }
	    });
	</script>
	
	</html>
    <!--================================
	    END FEATURE AREA
	=================================-->
