<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- jstl에서는 \n을 직접적으로 사용할 수 없기 때문에 사용 -->
<%pageContext.setAttribute("newline", "\n"); %>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>JSTL Test: forEach</h4>

	<c:set var="count" value="${fn:length(list) }"/>
	
	<c:forEach items="${list }" var="vo" varStatus="status">
		<h4>[${count - status.index}] (${status.count}:${status.index}) ${vo.id }:${vo.name }</h4>
	</c:forEach>
	
	<p>
		${fn:replace(contents, newline, "<br>") }
	</p>
</body>
</html>