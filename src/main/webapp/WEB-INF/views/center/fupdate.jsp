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
    
<h1>File Notice Update</h1>

<form action="fupdate.do" method="post" name="frm" onsubmit="return check();" enctype="multipart/form-data">
<%-- 수정할 글번호는 눈에 안보이는 hidden 타입 입력요소 사용 --%>
<input type="hidden" name="pageNum" value="${pageNum}"/>
<input type="hidden" name="num" value="${num}"/>
<table id="notice">
	<tr>
	  	<th class="twrite">아이디</th>
	  	<td class="left" width="300">
	  		<input type="text" name="username" value="${id}"  readonly>
	  	</td>
 	</tr>
	<tr>
		<th class="twrite">제목</th>
		<td class="left">
			<input type="text" name="subject" value="${board.subject}">
		</td>
	</tr>
	<tr>
		<th class="twrite">파일</th>
		<td class="left">
			<c:if test="${not empty attachList}">
				<ul>
					<c:forEach var="attach" items="${attachList}">
						<li>
							<div class="attach-item">
								${attach.filename}
								<span class="del" style="color: red; font-weight: bold;">X</span>
							</div>
							<input type="hidden" name="oldFiles" value="${attach.uuid}_${attach.filename}" />
						</li>
					</c:forEach>
				</ul>
			</c:if>
			
			<button type="button" id ="btn">새로 업로드</button>
			<div id="newFilesContainer"></div>
		</td>
	</tr>
    <tr>
  	<th class="twrite">내용</th>
  	<td class="left">
		<textarea name="content" cols="40" rows="13">${board.content}</textarea>
  	</td>
  </tr>
</table>

<div id="table_search">
	<input type="submit" value="글수정" class="btn" />
	<input type="reset" value="다시작성" class="btn"/>
	<input type="button" value="목록보기" class="btn" onclick="location.href='fnotice.do?pageNum=${pageNum}';"/>
</div>
</form>
</article>

     <div class="clear"></div>
    
    <!-- 푸터 영역 -->
    <jsp:include page="../include/footer.jsp" />
    
    
    
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
function check() {	
	// 수정 의도 확인
	var result = confirm('${num}번 글을 정말로 수정하시겠습니까?');
	if (result == false) {
		return false;
	}
} // function check


// id가 btn인 버튼에 클릭이벤트 연결
let num = 1;
$('#btn').on('click', function () {
	let str = '<input type="file" name="newFile' + num + '"><br>';
	$('#newFilesContainer').append(str); // 뒤에 추가
	num ++;
});


// class명이 del인 span태그에 클릭이벤트 연결하기
// quertSelectorAll로 리턴되는 객체는 NodeList 타입임.
/*
let delList = document.quertSelectorAll('span.del'); // quertSelectorAll => 해당 요소를 모두 가져온다
for (let i = 0; i < delList.length; i++) {
	let spanElem = delList.item(i);
	// span요소에 이벤트 연결하기
	spanElem.onclick = function(event) {
		// 이벤트객체의 target은 이벤트가 발생된 객체를 의미함.
		let liElem = event.target.closest('li'); // => 가장가까운 상위요소를 찾는다.
		// childNodes는 현재 요소의 자식요소들을 NodeList 타입으로 가져옴.
		let ndList = liElem.childNodes;
		
		let divElem = ndList.item(0);
		let inputElem = ndList.item(1);
		
		//inputElem.setAttribute('name', 'delFiles'); // 속성값 바꾸기(안됨)
		divElem.remove(); // 삭제
	};
}
*/

// class명이 del인 span태그에 클릭이벤트 연결하기 - jQuery방식
$('span.del').on('click', function () {
	var $li = $(this).closest('li');
	
	$li.children('input[type="hidden"]').attr('name', 'delFiles');
	$li.children('div.attach-item').remove();
});

</script>
</body>
</html>   

