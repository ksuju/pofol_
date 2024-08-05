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
				<form action="<c:url value='/auth/sendID.do'/>" method="POST">
					<div class="cardify recover_pass">
						<div class="login--header">
							<c:if test="${not empty alert}">
								<h5 style="color: red; text-align:center;">${alert}</h5>
							</c:if>
							
							<c:if test="${not empty findID}">
								<p>귀하의 아이디는</p>
								<h2>${findID}입니다.</h2>
							</c:if>
							
						</div>
						<!-- end .login_header -->
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