<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- ${cookie.쿠키이름.value}로 접근 --%>
<c:if test="${not empty cookie.id.value }">
	<c:set var="id" value="${cookie.id.value }" scope="session" />
</c:if>

<header>
	<div id="login">
		<c:choose>
			<c:when test="${empty id}"><%-- 세션값없음 --%>
				<a href="memberLoginForm.do">login</a>
			</c:when>
			<c:otherwise><%-- 세션값있음 --%>
				${id}님 <a href="memberLogout.do">logout</a>
			</c:otherwise>
		</c:choose>
		| <a href="memberJoinForm.do">Join</a>
	</div>

	<div class="clear"></div>
	<div id="logo">
		<a href="main.do"><img src="images/logo.gif" width="265"
			height="62" alt="Fun Web"></a>
	</div>
	<nav id="top_menu">
		<ul>
			<li><a href="main.do">HOME</a></li>
			<li><a href="welcome.do">COMPANY</a></li>
			<li><a href="#">SOLUTIONS</a></li>
			<li><a href="notice.do">CUSTOMER CENTER</a></li>
			<li><a href="#">CONTACT US</a></li>
		</ul>
	</nav>

</header>