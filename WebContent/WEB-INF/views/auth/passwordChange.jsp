<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page import = "com.portfolio.www.message.MessageEnum" %>
<link rel="stylesheet" type="text/css"
	href="https://mm.realbyteapps.com/api/static/common/css/main.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script src="https://mm.realbyteapps.com/api/static/js/resetpw.js"></script>
	<div class="container">
		<section id="changePwSection">
			<div class="wrapper">
				<div class="card">
					<!-- title -->
					<div class="tit_wrapper">
						<c:if test="${not empty alert}">
							<h1 class="title" style="color:red;">${alert}</h1>
						</c:if>
						<h1 class="title">비밀번호 재설정</h1>
					</div>
					<!-- form -->
					<form id='resetPwForm' action="/pf/auth/resetPw.do" method="POST">
						<div class="wrapper">
							<!-- ### add class "error" -->
							<label id="input_pw_label" for="input_pw" class="label_input">
								<input type="password" id="input_pw" name="passwd"
								placeholder="비밀번호 (특수문자 포함 8자 이상)" />
								<input type="hidden" name="email" value="${email}"/> 
								<input type="hidden" name="token"
								value="96ee3e81f61574a3f61af14fb979f06758f34d7d1870da54046fa5cc0f482f384f59830c34d86b29f594ff7cdcfff0fc80ac35679a2bb0f003b01b38bb4896add54eb8f2637d91927bcca15597419bef0ecdc1d709f63ac5a31eaaefd638e897" />
							</label> <span id='error_msg' class="error_msg" style="display: block"></span>
						</div>
						<input type="submit" id="submit" name="submit" class="btn_submit active" value="재설정" />
					</form>
				</div>
				<span class="copyright">ⓒ Realbyte Inc</span>
			</div>
		</section>
		<section id="resultSection">
			<div class="wrapper">
				<div id="successCard" style="display: none" class="card">
					<div class="tit_wrapper">
						<h1 class="title">비밀번호 변경 완료</h1>
						<h4 class="sub">
							안녕하세요 <strong id='nickname'></strong>님! 비밀번호가 변경되었습니다.
						</h4>
					</div>
					<p class="text">
						<br>변경된 비밀번호로 다시 로그인 해주세요.<br>고객님의 소중한 개인정보 보안을 위해 최선을
						다하겠습니다.
					</p>
				</div>
				<div id="expireCard" style="display: none" class="card">
					<div class="tit_wrapper">
						<h1 class="title" id="errorTitle">유효하지 않은 요청입니다.</h1>
						<h4 class="sub" id="errorContent">
							비밀번호 재설정 유효시간이 만료되었거나, <br>올바르지 않은 요청입니다. <br> <br>다시
							한번 <strong>로그인 > 비밀번호 찾기</strong>를 통해 <br>비밀번호 재설정 메일을 요청
							부탁드립니다.
						</h4>
					</div>
				</div>
				<div id="errorCard" style="display: none" class="card">
					<!-- title -->
					<div class="tit_wrapper">
						<h1 class="title" id="errorTitle">작업도중 에러가 발생했습니다</h1>
						<h4 class="sub" id="errorContent">
							문의사항은 <a href="https://cafe.naver.com/cashbook">네이버 카페</a>나 <a
								href="mailto:help@realbyteapps.com">help@realbyteapps.com</a>으로
							연락 부탁드립니다.
						</h4>
					</div>
				</div>
				<span class="copyright">ⓒ Realbyte Inc</span>
			</div>
		</section>
	</div>
<script>
	var sign_email_password_same = "기존의 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.";
	var includeSpecialChar = "특수문자 포함 8자 이상으로 입력해주세요.";
</script>
