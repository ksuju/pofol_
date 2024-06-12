<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!--================================
            START LOGIN AREA
    =================================-->
<section class="login_area section--padding2">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 offset-lg-3">
				<form action="<c:url value='/auth/login.do'/>" method="POST">
					<div class="cardify login">
						<div class="login--header">
							<c:if test="${not empty alert}">
								<h5 style="color: red;">${alert}</h5>
							</c:if>
							<h3>환영합니다!</h3>
							<p>아이디와 비밀번호를 입력해주세요.</p>
						</div>
						<!-- end .login_header -->

						<div class="login--form">
							<div class="form-group">
							    <label for="user_name">아이디</label>
							    <input id="user_name" name="memberID" type="text" class="text_field"
							           placeholder="Enter your username..."
							           <c:if test="${not empty saveId}">
							               value="${saveId}"
							           </c:if> />
							</div>

							<div class="form-group">
								<label for="pass">비밀번호</label> <input id="pass" name="passwd"
									type="text" class="text_field"
									placeholder="Enter your password...">
							</div>
							<!-- 로그인 실패 시 메시지 표시 -->
							<c:if test="${not empty errorMessage}">
								<div style="color: red;">
									<c:out value="${errorMessage}" />
								</div>
							</c:if>
							<div class="form-group">
								<div class="custom_checkbox">
									<input name="checkBox" type="checkbox" id="ch2"> <label for="ch2">
										<span class="shadow_checkbox"></span> <span class="label_text">아이디저장</span>
									</label>
								</div>
							</div>

							<button id="loginButton" class="btn btn--md btn--round"
								type="submit">Login Now</button>

							<div class="login_assist">
								<p class="recover">
									Lost your <a href="pass-recovery.html">username</a> or <a
										href="resetPw.do">password</a>?
								</p>
								<p class="signup">
									Don't have an <a href="<c:url value='/auth/joinPage.do'/>">account</a>?
								</p>
							</div>
						</div>
						<!-- end .login--form -->
					</div>
					<!-- end .cardify -->
				</form>
			</div>
			<!-- end .col-md-6 -->
		</div>
		<!-- end .row -->
	</div>
	<!-- end .container -->
	<script>
	    window.onload = function() {
	        var saveId = "${saveId}"; // saveId 속성을 가져옴
	        if (saveId) { // saveId 값이 있으면
	            document.getElementById("ch2").checked = true; // 체크박스를 체크함
	        }
	    };
	</script>
</section>
<!--================================
            END LOGIN AREA
    =================================-->
