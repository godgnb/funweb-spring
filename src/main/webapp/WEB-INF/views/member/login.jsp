<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Welcome to Fun Web</title>
<link href="css/default.css" rel="stylesheet" type="text/css" media="all">
<link href="css/subpage.css" rel="stylesheet" type="text/css"  media="all">
<link href="css/print.css" rel="stylesheet" type="text/css"  media="print">
<link href="css/iphone.css" rel="stylesheet" type="text/css" media="screen">
<!--[if lt IE 9]>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js" type="text/javascript"></script>
<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/ie7-squish.js" type="text/javascript"></script>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js" type="text/javascript"></script>
<![endif]-->


<!--[if IE 6]>
 <script src="../script/DD_belatedPNG.js"></script>
 <script>
   /* EXAMPLE */
   DD_belatedPNG.fix('#wrap');
   DD_belatedPNG.fix('#sub_img');   
   DD_belatedPNG.fix('#sub_img_center'); 
   DD_belatedPNG.fix('#sub_img_member'); 

 </script>
 <![endif]--> 

</head>

<body>
<div id="wrap">
  <!-- 헤더 영역 -->
  <jsp:include page="../include/header.jsp" />
  
  
  <div class="clear"></div>
  <div id="sub_img_member"></div>
  <div class="clear"></div>
  
	<!-- nav 영역 -->
	<jsp:include page="../include/nav_member.jsp" />

  
	<article>
		<h1>Login</h1>
		<form action="memberLogin.do" method="post" id="join">
			<fieldset>
				<legend>Login Info</legend>
				<label>User ID</label>
				<input type="text" name="id"><br>
				<label>Password</label>
				<input type="password" name="passwd"><br>
				<label>Remember Me</label>
				<input type="checkbox" name="rememberMe" value="true"/>
			</fieldset>
			
			<div class="clear"></div>
			<div id="buttons">
				<input type="submit" value="로그인" class="submit">
				<input type="reset" value="초기화" class="cancel">
			</div>
		</form>
	</article>
	
	
    <div class="clear"></div>
    
    <!-- 푸터 영역 -->
    <jsp:include page="../include/footer.jsp" />
    
    
</div>

</body>
</html>   

