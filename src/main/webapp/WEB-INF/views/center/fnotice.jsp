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
	<div id="sub_img_center"></div>
	<div class="clear"></div>

 	<!-- nav 영역 -->
 	<jsp:include page="../include/nav_center.jsp" />
 	
<article>
    
<h1>File Notice [전체글개수 : ${pageInfoMap.count}]</h1>
    
<table id="notice">
	<tr>
	  <th scope="col" class="tno">no.</th>
	  <th scope="col" class="ttitle">title</th>
	  <th scope="col" class="twrite">writer</th>
	  <th scope="col" class="tdate">date</th>
	  <th scope="col" class="tread">read</th>
	</tr>
	  <c:choose>
  	<c:when test="${pageInfoMap.count gt 0}"><%-- ${not empty boardList} --%>
  		
  		<c:forEach var="board" items="${boardList}">
  			<tr onclick="location.href='fcontent.do?num=${board.num}&pageNum=${pageNum}';">
		  	<td>${board.num}</td>
		  	<td class="left">
		  		<c:if test="${board.reLev gt 0}"><%--답글일때 --%>
		  			<c:set var="level" value="${board.reLev * 10}" />
		  			
		  			<img src="images/center/level.gif" width="${level}" height="13" />
		  			<img src="images/center/icon_re.gif" />
		  		</c:if>
  				${board.subject}
		  	</td>
		  	<td>${board.username}</td>
		  	<td><fmt:formatDate value="${board.regDate}" pattern="yyyy.MM.dd"/></td>
		  	<td>${board.readcount}</td>
		  </tr>
  		</c:forEach>
  	
  	</c:when>
  	<c:otherwise>
  		<tr>
	  		<td colspan="5">게시판 글이 없습니다.</td>
	  	</tr>
  	</c:otherwise>
  </c:choose>
</table>

<c:if test="${not empty id}">
	<div id="table_search">
		<input type="button" value="글쓰기" class="btn" onclick="location.href='fwriteForm.do';"/>
	</div>
</c:if>
<form action="fnotice.do" method="get">
	<div id="table_search">
		<input type="text" name="search" value="${search}" class="input_box">
		<input type="submit" value="제목검색" class="btn">
	</div>
</form>

 <div class="clear"></div>
 
<div id="page_control">
<c:if test="${pageInfoMap.count gt 0}">
	<%--[이전] 출력 --%>
	<c:if test="${pageInfoMap.startPage gt pageInfoMap.pageBlock}">
		<a href="fnotice.do?pageNum=${pageInfoMap.startPage - pageInfoMap.pageBlock}&search=${search}">[이전]</a>
	</c:if>
	
	<%--페이지블록 페이지5개 출력 --%>
	<c:forEach var="i" begin="${pageInfoMap.startPage }" end="${pageInfoMap.endPage}" step="1">
		<a href="fnotice.do?pageNum=${i}&search=${search}">
		<c:choose>
			<c:when test="${i eq pageNum}">
				<span style="font-weight: bold;">[${i}]</span>				
			</c:when>
			<c:otherwise>
				<span>${i}</span>
			</c:otherwise>
		</c:choose>
		</a>
	</c:forEach>
	
	<%--[다음] 출력 --%>
	<c:if test="${pageInfoMap.endPage lt pageInfoMap.pageCount}">
		<a href="fnotice.do?pageNum=${pageInfoMap.startPage + pageInfoMap.pageBlock}&search=${search}">[다음]</a>
	</c:if>
	
</c:if>
</div>	    
</article>
    
     <div class="clear"></div>
    
    <!-- 푸터 영역 -->
    <jsp:include page="../include/footer.jsp" />
    
    
    
</div>

</body>
</html>   

