<%@ page import="by.teachmeskills.bad_twitter.entity.user.User" %>
<%@ page import="by.teachmeskills.bad_twitter.entity.user.ReadOnlyUser" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <c:when test="${sessionScope.user != null}">
                <% String currentUserFullName = new ReadOnlyUser((User) session.getAttribute("user")).getFullName(); %>
                <%= currentUserFullName %>, glad to see you!
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