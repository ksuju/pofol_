<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

<!-- 	<section class="breadcrumb-area breadcrumb--center breadcrumb--smsbtl">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="page_title">
						<h3>문의게시판</h3>
						<p class="subtitle">무엇이든 물어보세요!</p>
					</div>
					<div class="breadcrumb">
						<ul>
							<li><a href="index.html">Home</a></li>
							<li class="active"><a href="#">고객센터</a></li>
						</ul>
					</div>
				</div>
				end /.col-md-12
			</div>
			end /.row
		</div>
		end /.container
	</section> -->
	<!--================================
        END BREADCRUMB AREA
    =================================-->

	<!--================================
        START AFFILIATE AREA
    =================================-->
	<section class="contact-area section--padding">
		<br>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="col-md-12">
							<div class="contact_form cardify">
								<div class="contact_form__title">
									<h3>
										<span style="color:#0674ec">이메일</span>을 적어주세요 <span style="color:#0674ec">이력서</span>를 보내드립니다!
									</h3>
									<c:choose>
										<c:when test="${not empty alert}">
											<br>
											<h3 style="color:red">${alert}</h3>
										</c:when>
										<c:when test="${not empty success}">
											<br>
											<h3 style="color:#0674ec">${success}</h3>
										</c:when>
									</c:choose>
								</div>

								<div class="row">
									<div class="col-md-8 offset-md-2">
										<div class="contact_form--wrapper">
											<form action="resume.do" method="POST">
												<div class="row">
													<div class="col-md-6">
														<div class="form-group">
															<input type="text" name="name" placeholder="이름(회사명)을 적어주세요.">
														</div>
													</div>

													<div class="col-md-6">
														<div class="form-group">
															<input type="text" name="email" placeholder="이력서를 받을 이메일 주소를 적어주세요.">
														</div>
													</div>
												</div>

												<div class="sub_btn">
													<button type="submit" class="btn btn--round btn--default">이력서 받기</button>
												</div>
											</form>
										</div>
									</div>
									<!-- end /.col-md-8 -->
								</div>
								<!-- end /.row -->
							</div>
							<!-- end /.contact_form -->
						</div>
						<!-- end /.col-md-12 -->
					</div>
					<!-- end /.row -->
				</div>
				<!-- end /.col-md-12 -->
			</div>
			<!-- end /.row -->
		</div>
		<!-- end /.container -->
	</section>

</body>
</html>