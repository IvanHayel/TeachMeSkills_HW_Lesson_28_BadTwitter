<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="user" value="${sessionScope.user}"/>
<c:set var="userName" value="${sessionScope.user.name}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sweater</title>
    <jsp:include page="common/_head.jsp"/>
</head>
<body>
<jsp:include page="common/_header.jsp"/>
<div class="container position-absolute top-50 start-50 translate-middle">
    <p class="text-center fs-1 fw-bold">
        <c:choose>
            <c:when test="${user != null}">
                ${userName}, glad to see you!
            </c:when>
            <c:otherwise>
                Welcome to Sweater!
            </c:otherwise>
        </c:choose>
    </p>
</div>
<jsp:include page="common/_footer.jsp"/>
</body>
</html>