<%@page import="com.exam.repository.MemberDao"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String id = request.getParameter("id");

// DAO 객체 준비
MemberDao memberDao = MemberDao.getInstance();
// 아이디 중복확인 메소드 호출
boolean isIdDup = memberDao.isIdDuplicated(id);

// JSON 응답 자료형
// 객체일 경우: []리스트	{}맵
// 기본자료형일 경우: 숫자	 불리언	 문자열

%>
<%=isIdDup %>