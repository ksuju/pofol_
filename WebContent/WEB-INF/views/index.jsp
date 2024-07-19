<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
String ctx = request.getContextPath();
%>
	<!--================================
	    START FEATURE AREA
	=================================-->
	<script type="text/javascript">
	    $('#trumbowyg-demo').trumbowyg({
	        lang: 'kr'
	    });
	</script>
	<style>
		.feature__img img {
			width: 112px;
            height: 140px;
		}
		
		.content_01 {
		    display: inline-block;
		    text-align: left !important;
		}
		
		.content_02 {
		    display: inline-block;
		    text-align : left !important;
		}
		.content_02 p {
			margin-top : 15px;
		}
		
		#asd {
			flex : 0 0 66.666666% !important;
			width : 66.666666% !important;
			max-width: 66.666666% !important;
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
                </div>
                <!-- end /.col-lg-4 col-md-6 -->
                
                <!-- start search-area -->
                <div class="col-lg-4 col-md-6" id="asd">
                    <div class="feature">
                        <div class="feature__title">
                            <h3>매일 능동적으로 발전하는 백엔드 개발자가 되겠습니다.</h3>
                        </div>
                        <div class="feature__desc">
                        <h3>경력 / 학력</h3>
                        	<div class="content_02">
	                        	<br>
	                        	<h6>태성텍㈜ – 3D 모델링 프로그래밍</h6>
	                        	<p>
	                        		- 22년 8월 ~ 23년 12월 (1년 5개월)
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
    <!--================================
	    END FEATURE AREA
	=================================-->
