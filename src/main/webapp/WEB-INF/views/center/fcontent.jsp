<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>        
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
    
<h1>File Notice Content</h1>

<table id="notice">

    <tr>
	  	<th class="twrite">글번호</th>
	  	<td class="left" width="160">${board.num}</td>
	  	<th class="twrite">조회수</th>
	  	<td class="left" width="160">${board.readcount}</td>
    </tr>
    <tr>
	  	<th class="twrite">작성자명</th>
	  	<td class="left">${board.username}</td>
	  	<th class="twrite">작성일자</th>
	  	<td class="left"><fmt:formatDate value="${board.regDate}" pattern="yyyy년 MM월 dd일 hh시 mm분 ss초"/> </td>
    </tr>
    <tr>
	  	<th class="twrite">글제목</th>
	  	<td class="left" colspan="3">${board.subject}</td>
    </tr>
    <tr>
	  	<th class="twrite">파일</th>
	  	<td class="left" colspan="3">
	  		<c:forEach var="attach" items="${attachList}">
	  			<c:choose>
					<c:when test="${attach.filetype eq 'I'}"><%-- 이미지 타입 파일 --%>
						<a href="upload/${attach.filename}">
			  				<img src="upload/${attach.filename}" width="50" height="50"/>
			  			</a>				
					</c:when>	  			
	  				<c:otherwise><%-- 이미지가 아닌 일반 타입 파일 --%>
	  					<a href="upload/${attach.filename}">
			  				${attach.filename}
			  			</a><br>
	  				</c:otherwise>
	  			</c:choose>
	  		</c:forEach>
	  	</td>
    </tr>
    <tr>
	  	<th class="twrite">글내용</th>
	  	<td class="left" colspan="3"><pre>${board.content}</pre></td>
    </tr>
</table>

<div id="table_search">
	<c:if test="${not empty id and id eq board.username}">
		<input type="button" value="글수정" class="btn" onclick="location.href='fupdateForm.do?num=${board.num}&pageNum=${pageNum}';"/>
		<input type="button" value="글삭제" class="btn" onclick="checkDelete();"/>
	</c:if>
	<c:if test="${not empty id}">
		<input type="button" value="답글쓰기" class="btn" onclick="location.href='reWrite.do?reRef=${board.reRef}&reLev=${board.reLev}&reSeq=${board.reSeq}';"/>	
	</c:if>
		<input type="button" value="목록보기" class="btn" onclick="location.href='fnotice.do?pageNum=${pageNum}';"/>
</div>

</article>

     <div class="clear"></div>
    
    <!-- 푸터 영역 -->
    <jsp:include page="../include/footer.jsp" />
</div>

<script>
	function checkDelete() {
		var result = confirm('${board.num}번 글을 정말로 삭제하시겠습니까?');
		
		if (result == true) {
			location.href='fdelete.do?num=${board.num}&pageNum=${pageNum}';
		}
		
	}
</script>
</body>
</html>   

