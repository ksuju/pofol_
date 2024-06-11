<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!--================================
            START SIGNUP AREA
    =================================-->
<section class="pass_recover_area section--padding2">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 offset-lg-3">
				<form action="<c:url value='/auth/convertPw.do'/>" method="POST">
					<div class="cardify recover_pass">
						<div class="login--header">
							<c:if test="${not empty alert}">
								<h5 style="color: red; text-align:center;">${alert}</h5>
							</c:if>
							<p>계정의 이메일 주소를 입력하세요. 인증 코드가 발송됩니다.</p>
							<p>인증 코드를 받으면 계정의 새 비밀번호를 설정할 수 있습니다.</p>
						</div>
						<!-- end .login_header -->

						<div class="login--form">
							<div class="form-group">
								<label for="email_ad">Email Address</label> <input name="email"
									id="email_ad" type="text" class="text_field"
									placeholder="Enter your email address">
							</div>

							<button class="btn btn--md btn--round register_btn" type="submit">Register
								Now</button>
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
</section>
<!--================================
            END SIGNUP AREA
    =================================-->
</html>