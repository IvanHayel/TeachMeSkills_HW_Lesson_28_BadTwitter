<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar navbar-expand-lg sticky-top navbar-light bg-light">
    <div class="container-fluid">
        <a href="/" class="navbar-brand">
            <img src="../../images/bt-logo.png" alt="Bad Twitter" height="70vh" width="auto"/>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/">Home</a>
                </li>
                <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/sign-up">Sign up</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/sign-in">Sign in</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/tweet">Tweet</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/my-tweets">My Tweets</a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user != null && sessionScope.accessLevel != null && sessionScope.accessLevel > 0}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin-panel">Admin panel</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <ul class="nav justify-content-end">
            <c:if test="${sessionScope.user != null}">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/logout"
                       style="color:gray">Logout</a>
                </li>
            </c:if>
        </ul>
    </div>
</header>