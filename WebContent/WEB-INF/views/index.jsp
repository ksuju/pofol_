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
		.feature__img {
            position: relative;
            width: 130px;
            height: 120px;
            margin: 0 auto; /* 수평 가운데 정렬 */
		}
		.todayMenu-background {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-image: url('<c:url value="/assest/template/images/food.png"/>');
            background-size: cover;
            background-position: center;
            opacity: 0.6;
		}
		.todayMenu {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            opacity: 0.5;
		}
		
		.turn {
			transition: all ease 1s;
		}
		.turn:hover {
			transform: rotate( 720deg );
		}
		
		.menuButton {
			width: 100%;
			background-color: white;
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
                        <div class="feature__img turn">
                         	<div class="todayMenu-background"></div>
                            <img class="todayMenu" src="<c:url value='/assest/template/images/tasty.png'/>" alt="feature" />
                        </div>
                        <div class="feature__title">
                            <h3>오늘의 추천메뉴</h3>
                        </div>
                        <div class="feature__desc">
                        	<form action="#" onsubmit="recomMenu(event)">
                        		<select id="selectCat" name="category">
	                        		<c:forEach var="i" items="${category}">
	                        			<option>
	                        				<c:out value="${i}"/>
										</option>
	                        		</c:forEach>
                        		</select>
                        		<input class="menuButton" value="추천메뉴는?" type="submit"/>
                        	</form>
                        	<br>
                        	<c:if test="${not empty menu}"><h4 id="menu">${menu}</h4></c:if>
                        </div>
                    </div>
                    <!-- end /.feature -->
                </div>
                <!-- end /.col-lg-4 col-md-6 -->
                <!-- start search-area -->
                <div class="col-lg-4 col-md-6">
                    <div class="feature">
                        <div class="feature__img">
                            <img src="<c:url value='/assest/template/images/feature2.png'/>" alt="feature" />
                        </div>
                        <div class="feature__title">
                            <h3>Fully Responsive</h3>
                        </div>
                        <div class="feature__desc">
                            <p>Nunc placerat mi id nisi interdum mollis. Praesent pharetra, justo ut scelerisque the mattis,
                                leo quam aliquet diam congue is laoreet elit metus.</p>
                        </div>
                    </div>
                    <!-- end /.feature -->
                </div>
                <!-- end /.col-lg-4 col-md-6 -->

                <!-- start search-area -->
                <div class="col-lg-4 col-md-6">
                    <div class="feature">
                        <div class="feature__img">                        
                            <img src="<c:url value='/assest/template/images/feature3.png'/>" alt="feature" />
                        </div>
                        <div class="feature__title">
                            <h3>Buy & Sell Easily</h3>
                        </div>
                        <div class="feature__desc">
                            <p>Nunc placerat mi id nisi interdum mollis. Praesent pharetra, justo ut scelerisque the mattis,
                                leo quam aliquet diam congue is laoreet elit metus.</p>
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
    <script>
    	function recomMenu() {
    		event.preventDefault();
        	let category = document.querySelector("#selectCat").value;
        	let url = '<%=ctx%>/recomMenu.do';
	    	$.ajax({
	    		// 타입 (get, post, put 등등)    
	    		type : 'GET',           
	    		// 요청할 서버url
	    		url : url,
	    		// Http header
	    		headers : {
	    			"Content-Type" : "application/json; charset=UTF-8"
	    		},
	    		dataType : 'text',
	    		data : {category:category},
	    		// 결과 성공 콜백함수 
	    		success : function(result) {
	    			console.log(result)
	    			let menu = document.querySelector("#menu");
	    			menu.innerHTML = result;
	    		},
	    		// 결과 에러 콜백함수
	    		error : function(request, status, error) {
	    			console.log(error)
	    		}
	    	});
    	}
    </script>
    <!--================================
	    END FEATURE AREA
	=================================-->
