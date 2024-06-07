<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page import="com.portfolio.www.message.MessageEnum"%>
<!--================================
            START SIGNUP AREA
    =================================-->
<section class="signup_area section--padding2">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<div class="container">
		<div class="row">
			<div class="col-lg-6 offset-lg-3">
				<form action="#" method="POST">
					<div class="cardify signup_form">
						<div class="login--header">
							<h3>Create Your Account</h3>
							<p>Please fill the following fields with appropriate
								information to register a new MartPlace account.</p>
						</div>
						<!-- end .login_header -->

						<div class="login--form">

							<div class="form-group">
								<label for="urname">Your Name</label> <input id="urname"
									type="text" class="text_field" name="memberNM"
									placeholder="Enter your Name">
							</div>

							<div class="form-group">
								<label for="email_ad">Email Address</label> <input id="email_ad"
									type="text" class="text_field" name="email"
									placeholder="Enter your email address">
							</div>

							<div class="form-group">
								<label for="user_name">UserID</label> <input id="user_name"
									type="text" class="text_field" name="memberID"
									placeholder="Enter your username...">
							</div>

							<div class="form-group">
								<label for="password">Password</label> <input id="password"
									type="password" class="text_field" name="passwd"
									placeholder="Enter your password...">
							</div>

							<div class="form-group">
								<label for="con_pass">Confirm Password</label> <input
									id="con_pass" type="password" class="text_field"
									placeholder="Confirm password">
							</div>

							<button class="btn btn--md btn--round register_btn" type="submit">Register
								Now</button>

							<div class="login_assist">
								<p>
									Already have an account? <a
										href="<c:url value='/auth/loginPage.do'/>">Login</a>
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
            let memberID = document.querySelector("#user_name");
            let password = document.querySelector("#password");
            let con_pass = document.querySelector("#con_pass");
            
            let register = document.querySelector(".register_btn");
            let url = 'join.do';
            
            <!-- 회원가입 script -->
            register.addEventListener('click', function(event) {
                event.preventDefault(); // 기본 이벤트 제거 (폼 submit 방지)
                
                // form 태그 내의 데이터
                var formData = {
                    memberNM: $("input[name='memberNM']").val(),
                    email: $("input[name='email']").val(),
                    memberID: $("input[name='memberID']").val(),
                    passwd: $("input[name='passwd']").val(),
                    con_pass: $("#con_pass").val(),
                };
                
                // AJAX 요청 보내기
                $.ajax({
                    type: "POST",
                    url: url,
                    data: formData,
                    success: function(response) {
                        const messageEnum = response;
                        const alertLocation = document.querySelector(".login_assist");
                        const errorMessage = document.createElement('h4');
                        // 글자색은 빨간색
                        errorMessage.style.color = 'red';
                        
                        // 기존 h4 태그 제거
                        const errorMessageRemove = alertLocation.querySelector('h4');
                        if (errorMessageRemove) {
                        	errorMessageRemove.remove();
                        }
                        
                        switch (messageEnum) {
                            case "0000":
                                alert("회원가입에 성공하셨습니다! " + messageEnum);
                                window.location.href = 'loginPage.do';
                                break;
                            case "0001":
                                errorMessage.textContent = "회원가입에 실패했습니다. ";
                                alertLocation.insertAdjacentElement('afterbegin', errorMessage);
                                break;
                            case "1100":
                                errorMessage.textContent = "유효하지 않은 아이디입니다. ";
                                alertLocation.insertAdjacentElement('afterbegin', errorMessage);
                                break;
                            case "1101":
                                errorMessage.textContent = "유효하지 않은 패스워드입니다. ";
                                alertLocation.insertAdjacentElement('afterbegin', errorMessage);
                                break;
                            case "2001":
                                errorMessage.textContent = "비밀번호가 일치하지 않습니다. ";
                                alertLocation.insertAdjacentElement('afterbegin', errorMessage);
                                break;
                            case "3000":
                                errorMessage.textContent = "이미 가입된 이메일 주소입니다. ";
                                alertLocation.insertAdjacentElement('afterbegin', errorMessage);
                                break;
                            default:
                                errorMessage.textContent = "회원가입에 실패했습니다. ";
                            	alertLocation.insertAdjacentElement('afterbegin', errorMessage);
                                break;
                        }
                    },
                    error: function(xhr, status, error) {
                        // 에러 처리 - enum 활용할것
                    	// 회원가입 실패 알림 표시
                        alert("회원가입에 실패하셨습니다!");
                        // 회원가입 실패시 리다이렉트
                        window.location.href = 'joinPage.do';
                    }
                });
            })
            <!-- 회원가입 script 끝 -->
            
            <!-- 중복아이디확인 script -->
            memberID.addEventListener('input', function() {
                let idCheck = memberID.value;
                let url = 'idCheck.do';
                
                $.ajax({
                    type: 'GET',
                    url: url,
    	            dataType: 'JSON',
    	            data: {
    	                idCheck: idCheck
    	            },
                    success: function(response) {
                        // console.log("성공");
                        // console.log("서버 응답 데이터:", response);
                        if(response === <%=MessageEnum.DUPL_ID.getCode()%>) {
                            // "중복된 아이디입니다" 문자열 생성
                            var errorMessage = $("<span></span>").text("<%=MessageEnum.DUPL_ID.getDescription()%>");
                            // 빨간색 스타일 적용
                            errorMessage.css("color", "red");
                         	// id 설정
                            errorMessage.attr("id", "errorMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#user_name").next().remove(); // 기존 메시지 삭제
                            $("#user_name").after(errorMessage);
                        } else if (response === <%=MessageEnum.NO_DUPL_ID.getCode()%>) {
                            // "사용 가능한 아이디입니다" 문자열 생성
                            var successMessage = $("<span></span>").text("<%=MessageEnum.NO_DUPL_ID.getDescription()%>");
                            // 초록색 스타일 적용
                            successMessage.css("color", "green");
                            // id 설정
                            successMessage.attr("id", "successMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#user_name").next().remove(); // 기존 메시지 삭제
                            $("#user_name").after(successMessage);
                        } else {
                            // "아이디는 공백 또는 빈 칸일 수 없고 4~20자의 영어 소문자, 숫자만 사용 가능합니다." 문자열 생성
                            var validMessage = $("<span></span>").text("아이디는 공백 또는 빈 칸일 수 없고 4~20자의 영어 소문자, 숫자만 사용 가능합니다.");
                            // 빨간색 스타일 적용
                            validMessage.css("color", "red");
                            // id 설정
                            validMessage.attr("id", "validMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#user_name").next().remove(); // 기존 메시지 삭제
                            $("#user_name").after(validMessage);
                        }
                        
                        // 아이디란이 공백이면 메세지 삭제
                        if(idCheck === '') {
                        	$("#user_name").next().remove(); // 기존 메시지 삭제
                        }
                    },
                    error: function(request, status, error) {
                        console.log(error);
                    }
                });
            });
            <!-- 중복아이디확인 script 끝 -->
            
            <!-- 비밀번호 유효성 체크 -->
            password.addEventListener('input', function() {
                let passwordCheck = password.value;
                let url = 'validPasswd.do';
                
                $.ajax({
                    type: 'POST',
                    url: url,
    	            dataType: 'JSON',
    	            data: {
    	            	passwordCheck: passwordCheck
    	            },
                    success: function(response) {
                        // console.log("성공");
                        // console.log("서버 응답 데이터:", response);
                        if (response === <%=MessageEnum.VALLID_PASSWD.getCode()%>) {
                            // "비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다." 문자열 생성
                            var validPassMessage = $("<span></span>").text("비밀번호는 8~16자의 영문 대/소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.");
                            // 빨간색 스타일 적용
                            validPassMessage.css("color", "red");
                            // id 설정
                            validPassMessage.attr("id", "validPassMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#password").next().remove(); // 기존 메시지 삭제
                            $("#password").after(validPassMessage);
                        } else {
                            // "사용가능한 비밀번호 입니다." 문자열 생성
                            var PassPassMessage = $("<span></span>").text("사용가능한 비밀번호 입니다.");
                            // 빨간색 스타일 적용
                            PassPassMessage.css("color", "green");
                            // id 설정
                            PassPassMessage.attr("id", "PassPassMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#password").next().remove(); // 기존 메시지 삭제
                            $("#password").after(PassPassMessage);
                        }
                        
                        // 비밀번호확인란이 공백이면 메세지 삭제
                        if(passwordCheck === '') {
                        	$("#password").next().remove(); // 기존 메시지 삭제
                        }
                    },
                    error: function(request, status, error) {
                        console.log(error);
                    }
                });
            });
            
            <!-- 비밀번호, 비밀번호확인 일치여부 검사 -->
            con_pass.addEventListener('input', function() {
                let conPassCheck = con_pass.value;
                let passwordCheck = password.value;
                let url = 'passCheck.do';
                
                $.ajax({
                    type: 'POST',
                    url: url,
    	            dataType: 'JSON',
    	            data: {
    	            	conPassCheck: conPassCheck,
    	            	passwordCheck: passwordCheck
    	            },
                    success: function(response) {
                        // console.log("성공");
                        // console.log("서버 응답 데이터:", response);
                        if(response === <%=MessageEnum.NOT_EQUAL_PASSWD.getCode()%>) {
                            // "비밀번호가 일치하지 않습니다." 문자열 생성
                            var passErrorMessage = $("<span></span>").text("<%=MessageEnum.NOT_EQUAL_PASSWD.getDescription()%>");
                            // 빨간색 스타일 적용
                            passErrorMessage.css("color", "red");
                         	// id 설정
                            passErrorMessage.attr("id", "passErrorMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#con_pass").next().remove(); // 기존 메시지 삭제
                            $("#con_pass").after(passErrorMessage);
                        } else if (response === <%=MessageEnum.EQUAL_PASSWD.getCode()%>) {
                            // "비밀번호가 일치합니다." 문자열 생성
                            var passMessage = $("<span></span>").text("<%=MessageEnum.EQUAL_PASSWD.getDescription()%>");
                            // 초록색 스타일 적용
                            passMessage.css("color", "green");
                         	// id 설정
                            passMessage.attr("id", "passMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#con_pass").next().remove(); // 기존 메시지 삭제
                            $("#con_pass").after(passMessage);
                        }
                        
                        // 비밀번호확인란이 공백이면 메세지 삭제
                        if(conPassCheck === '') {
                        	$("#con_pass").next().remove(); // 기존 메시지 삭제
                        }
                    },
                    error: function(request, status, error) {
                        console.log(error);
                    }
                });
            });
            <!-- 비밀번호, 비밀번호확인 일치여부 검사 -->
        }
        </script>

</section>
<!--================================
            END SIGNUP AREA
    =================================-->
