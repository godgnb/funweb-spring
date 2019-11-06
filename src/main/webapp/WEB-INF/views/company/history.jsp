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
  <div id="sub_img"></div>
  <div class="clear"></div>

	<!-- nav 영역 -->
	<jsp:include page="../include/nav_company.jsp" />
	  
<article>
    
    <h1>History</h1>
    <div class="y2011"> 
    <h3> 2011 </h3>
    <dl>
        <dt>may</dt>
        <dd>Lorem ipsum dolor sit amet, consectetur adipiscing elit</dd>
        <dd>Duis eu ipsum nisl. Duis posuere fringilla nunc quis </dd>
        <dd>Donec mollis dapibus risus volutpat mattis. </dd>
     </dl>
     <dl>  
        <dt>apr</dt>
        <dd>Cras felis lectus, gravida ac tincidunt eget</dd>
        <dd>Duis eu ipsum nisl. Duis posuere fringilla nunc quis </dd>
        <dd>Donec mollis dapibus risus volutpat mattis. </dd>
     </dl>
     <dl class="dot_none">   
        <dt>jan</dt>
        <dd>Fusce scelerisque dictum magna eget viverra.</dd>
    </dl>
    </dl>
    <div class="clear"></div>
    </div>
    
        <div class="y2010"> 
    <h3> 2010 </h3>
    <dl>
        <dt>dec</dt>
        <dd>Lorem ipsum dolor sit amet, consectetur adipiscing elit</dd>
        <dd>Duis eu ipsum nisl. Duis posuere fringilla nunc quis </dd>
        <dd>Donec mollis dapibus risus volutpat mattis. </dd>
     </dl>
     <dl>  
        <dt>nov</dt>
        <dd>Cras felis lectus, gravida ac tincidunt eget</dd>
        <dd>Duis eu ipsum nisl. Duis posuere fringilla nunc quis </dd>
        <dd>Donec mollis dapibus risus volutpat mattis. </dd>
     </dl>
     <dl class="dot_none">   
        <dt>jan</dt>
        <dd>Fusce scelerisque dictum magna eget viverra.</dd>
    </dl>
    </dl>
    <div class="clear"></div>
    </div>
    
        <div class="y2009"> 
    <h3> 2009 </h3>
    <dl>
        <dt>Jul</dt>
        <dd>Lorem ipsum dolor sit amet, consectetur adipiscing elit</dd>
        <dd>Duis eu ipsum nisl. Duis posuere fringilla nunc quis </dd>
        <dd>Donec mollis dapibus risus volutpat mattis. </dd>
     </dl>
     <dl>  
        <dt>jun</dt>
        <dd>Cras felis lectus, gravida ac tincidunt eget</dd>
        <dd>Duis eu ipsum nisl. Duis posuere fringilla nunc quis </dd>
        <dd>Donec mollis dapibus risus volutpat mattis. </dd>
     </dl>
     <dl class="dot_none">   
        <dt>jan</dt>
        <dd>Fusce scelerisque dictum magna eget viverra.</dd>
    </dl>
    </dl>
    <div class="clear"></div>
    </div>
    
 </article>
    
     <div class="clear"></div>
    
    <!-- 푸터 영역 -->
    <jsp:include page="../include/footer.jsp" />
    
    
</div>

</body>
</html>   

