<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.teachmeskills.sweater.constant.SweaterWebConstants" %>
<c:set var="user" value="${sessionScope.user}"/>
<c:set var="accessLevel" value="${sessionScope.accessLevel}"/>
<c:url var="homeUrl" value="${SweaterWebConstants.PATH_HOME}"/>
<c:url var="signUpUrl" value="${SweaterWebConstants.PATH_SIGN_UP}"/>
<c:url var="signInUrl" value="${SweaterWebConstants.PATH_SIGN_IN}"/>
<c:url var="newTweetUrl" value="${SweaterWebConstants.PATH_NEW_TWEET}"/>
<c:url var="myTweetsUrl" value="${SweaterWebConstants.PATH_MY_TWEETS}"/>
<c:url var="allTweetsUrl" value="${SweaterWebConstants.PATH_ALL_TWEETS}"/>
<c:url var="adminPanelUrl" value="${SweaterWebConstants.PATH_ADMIN_PANEL}"/>
<c:url var="logoutUrl" value="${SweaterWebConstants.PATH_LOGOUT}"/>
<c:set var="adminAccessLevelValue" value="${SweaterWebConstants.USER_ACCESS_LEVEL_ADMIN}"/>
<header class="navbar navbar-expand-lg sticky-top navbar-light bg-light">
    <div class="container-fluid">
        <a href="/" class="navbar-brand">
            <img src="../../images/sweater-logo.png" alt="Sweater" height="70vh" width="auto"/>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${homeUrl}">Home</a>
                </li>
                <c:if test="${user == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${signUpUrl}">Sign up</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${signInUrl}">Sign in</a>
                    </li>
                </c:if>
                <c:if test="${user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${newTweetUrl}">Tweet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${myTweetsUrl}">My Tweets</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${allTweetsUrl}">All Tweets</a>
                    </li>
                </c:if>
                <c:if test="${user != null && accessLevel != null && accessLevel >= adminAccessLevelValue}">
                    <li class="nav-item">
                        <a class="nav-link" href="${adminPanelUrl}">Admin panel</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <ul class="nav justify-content-end">
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="${logoutUrl}" style="color:gray">Logout</a>
                </li>
            </c:if>
        </ul>
    </div>
</header>