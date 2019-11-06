<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
/*
	// request 영역개체로부터 뷰(jsp)에서 사용할 데이터 가져오기
	Boolean isIdDup = (Boolean) request.getAttribute("isIdDup");
//	String userid = request.getParameter("userid");
	String userid = (String) request.getAttribute("userid");
*/	
%>

<c:choose>
	<c:when test="${isIdDup eq true}">
		아이디 중복, 사용중인 ID입니다.<br>
	</c:when>
	<c:otherwise>
		사용가능한 ID입니다.<br>
		<button type="button" onclick="useId();">사용</button>
	</c:otherwise>	
</c:choose>

<form action="joinIdDupCheck.do" method="get" name="wfrm">
	<input type="text" name="userid" value="${userid }" />
	<button type="submit">중복확인</button>

</form>

<script>
function useId() {
	// 현재창을 생성한 부모창 참조
	// (window.)opener
	opener.document.frm.id.value = /*window.document.*/wfrm.userid.value;
	// 현재 창 닫기 window.close();
	close();
}
</script>
</body>
</html>