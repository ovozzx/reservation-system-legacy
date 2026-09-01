<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/css/common.css" />
<div class="common-header">
    <c:if test="${not empty sessionScope.userId}">
        <span>${sessionScope.userId}</span>
        <a href="/logout">로그아웃</a>
    </c:if>
    <a href="/">돌아가기</a>
</div>