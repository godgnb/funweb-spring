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
</head>
<body>
<div id="wrap">
  	<!-- 헤더 영역 -->
  	<jsp:include page="../include/header.jsp" />
  
  
  	<div class="clear"></div>
 	<div id="sub_img_center"></div>
 	<div class="clear"></div>
 	
 	<!-- nav 영역 -->
 	<jsp:include page="../include/nav_center.jsp" />
	
<article>
    
<h1>Notice Delete</h1>

<form action="delete.do" method="post" name="frm" onsubmit="return check();">
	<%-- 수정할 글번호는 눈에 안보이는 hidden 타입 입력요소 사용 --%>
	<input type="hidden" name="pageNum" value="${pageNum}"/>
	<input type="hidden" name="num" value="${num}"/>
	
	<table id="notice">
		<tr>
			<th class="twrite">패스워드</th>
			<td><input type="password" name="passwd" /></td>
		</tr>
	</table>
	
	<div id="table_search">
		<input type="submit" value="글삭제" class="btn" />
		<input type="reset" value="다시작성" class="btn"/>
		<input type="button" value="목록보기" class="btn" onclick="location.href='notice.jsp?pageNum=${pageNum}';"/>
	</div>
</form>

</article>

     <div class="clear"></div>
    
    <!-- 푸터 영역 -->
    <jsp:include page="../include/footer.jsp" />
  
</div>
<script>
function check() {
	var result = confirm('${num}번 글을 정말로 삭제하시겠습니까?');
	
	if (result == false) {
		return false;
	}
	
	var strPasswd = document.frm.passwd.value.trim();
		if (strPasswd.value.length == 0) {
			alert('게시글 패스워드는 필수 입력사항입니다.');
			frm.Passwd.focus();
			return false;
	}

	return true;
}
</script>

</body>
</html>   

