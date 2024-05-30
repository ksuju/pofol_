<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
    <!--================================
            START SIGNUP AREA
    =================================-->
    <section class="signup_area section--padding2">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <div class="container">
            <div class="row">
                <div class="col-lg-6 offset-lg-3">
                    <form action="joinPage.do" method="POST">
                        <div class="cardify signup_form">
                            <div class="login--header">
                                <h3>Create Your Account</h3>
                                <p>Please fill the following fields with appropriate information to register a new MartPlace
                                    account.
                                </p>
                            </div>
                            <!-- end .login_header -->

                            <div class="login--form">

                                <div class="form-group">
                                    <label for="urname">Your Name</label>
                                    <input id="urname" type="text" class="text_field" name="memberNM" placeholder="Enter your Name">
                                </div>

                                <div class="form-group">
                                    <label for="email_ad">Email Address</label>
                                    <input id="email_ad" type="text" class="text_field" name="email" placeholder="Enter your email address">
                                </div>

                                <div class="form-group">
                                    <label for="user_name">UserID</label>
                                    <input id="user_name" type="text" class="text_field" name="memberID" placeholder="Enter your username...">
                                </div>

                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input id="password" type="text" class="text_field" name="passwd" placeholder="Enter your password...">
                                </div>

                                <div class="form-group">
                                    <label for="con_pass">Confirm Password</label>
                                    <input id="con_pass" type="text" class="text_field" placeholder="Confirm password">
                                </div>

                                <button class="btn btn--md btn--round register_btn" type="submit">Register Now</button>

                                <div class="login_assist">
                                    <p>Already have an account?
                                        <a href="<c:url value='/auth/loginPage.do'/>">Login</a>
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
        <!-- 회원가입 성공 확인 알람 script -->
	    let msg = "<c:out value='${msg}'/>";
		    if (msg != '') {
		        alert(msg);
		        //알람 후 리다이렉트 why? 안하면 계속 회원가입됨
		        window.location.href = 'loginPage.do';
		    }
		<!-- 회원가입 확인 알람 script 끝 -->
		<!-- 중복아이디확인 script -->
        window.onload = function() {
            let memberID = document.querySelector("#user_name");
            let register = document.querySelector(".register_btn");
            let url = 'join.do';
            
            register.addEventListener('click', function(event) {
                event.preventDefault(); // 기본 이벤트 제거 (폼 submit 방지)
                
                // form 태그 내의 데이터
                var formData = {
                    memberNM: $("input[name='memberNM']").val(),
                    email: $("input[name='email']").val(),
                    memberID: $("input[name='memberID']").val(),
                    passwd: $("input[name='passwd']").val(),
                    con_pass: $("#con_pass").val(),
                    errorMessage: $("#errorMessage").text(), // errorMessage 가져오기
                    successMessage: $("#successMessage").text() // successMessage 가져오기
                };
                
                // AJAX 요청 보내기
                $.ajax({
                    type: "POST",
                    url: url,
                    data: formData,
                    success: function(response) {
                        if(response === 1) {
                            // 회원가입 성공 알림 표시
                            alert("회원가입에 성공하셨습니다!");
                            // 회원가입 성공 시 리다이렉트
                            window.location.href = 'loginPage.do';
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
                        if(response === 1) {
                            // "중복된 아이디입니다" 문자열 생성
                            var errorMessage = $("<span></span>").text("중복된 아이디입니다.");
                            // 빨간색 스타일 적용
                            errorMessage.css("color", "red");
                         	// id 설정
                            errorMessage.attr("id", "errorMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#user_name").next().remove(); // 기존 메시지 삭제
                            $("#user_name").after(errorMessage);
                        } else {
                            // "사용 가능한 아이디입니다" 문자열 생성
                            var successMessage = $("<span></span>").text("사용 가능한 아이디입니다.");
                            // 초록색 스타일 적용
                            successMessage.css("color", "green");
                            // id 설정
                            successMessage.attr("id", "successMessage");
                            // label 요소 다음에 새로운 요소를 추가
                            $("#user_name").next().remove(); // 기존 메시지 삭제
                            $("#user_name").after(successMessage);
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
            
            
        }
		<!-- 중복아이디확인 script 끝 -->
        </script>
        
    </section>
    <!--================================
            END SIGNUP AREA
    =================================-->
</html>