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
							<h5 class="title" style="color:red;">${alert}</h5>
						</c:if>
						<h1 class="title">인증코드 입력</h1>
					</div>
					<c:choose>
					    <%-- 비밀번호 찾기할때 --%>
					    <c:when test="${empty idAuth}">
					        <!-- form -->
					        <form id='resetPwForm' action="/pf/auth/findpswd.do" method="POST">
					            <div class="wrapper">
					                <!-- ### add class "error" -->
					                <label id="input_pw_label" for="input_pw" class="label_input">
					                    <input type="text" id="input_pw" name="authNum"
					                    placeholder="인증번호 6자리를 입력하세요." />
					                    <input type="hidden" name="email" value="${email}"/>
					                    <input type="hidden" name="token"
					                    value="96ee3e81f61574a3f61af14fb979f06758f34d7d1870da54046fa5cc0f482f384f59830c34d86b29f594ff7cdcfff0fc80ac35679a2bb0f003b01b38bb4896add54eb8f2637d91927bcca15597419bef0ecdc1d709f63ac5a31eaaefd638e897" />
					                </label> <span id='error_msg' class="error_msg" style="display: block"></span>
					            </div>
					            <input type="submit" id="submit" name="submit" class="btn_submit active" value="인증하기" />
					        </form>
					    </c:when>
					    
					    <%-- 아이디 인증할때 --%>
					    <c:otherwise>
					        <!-- form -->
					        <form id='resetPwForm' action="/pf/auth/idAuth.do" method="POST">
					            <div class="wrapper">
					                <!-- ### add class "error" -->
					                <label id="input_pw_label" for="input_pw" class="label_input">
					                    <input type="text" id="input_pw" name="authNum"
					                    placeholder="인증번호 6자리를 입력하세요." />
					                    <input type="hidden" name="email" value="${email}"/>
					                    <input type="hidden" name="token"
					                    value="96ee3e81f61574a3f61af14fb979f06758f34d7d1870da54046fa5cc0f482f384f59830c34d86b29f594ff7cdcfff0fc80ac35679a2bb0f003b01b38bb4896add54eb8f2637d91927bcca15597419bef0ecdc1d709f63ac5a31eaaefd638e897" />
					                </label> <span id='error_msg' class="error_msg" style="display: block"></span>
					            </div>
					            <input type="submit" id="submit" name="submit" class="btn_submit active" value="인증하기" />
					        </form>
					    </c:otherwise>
					</c:choose>
				</div>
			</div>
		</section>
	</div>
