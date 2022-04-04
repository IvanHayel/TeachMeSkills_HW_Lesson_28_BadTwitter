<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>My Tweets</title>
    <jsp:include page="common/_head.jsp"/>
</head>
<body>
<jsp:include page="common/_header.jsp"/>
<table class="table table-striped table-bordered align-middle">
    <caption></caption>
    <thead>
    <tr>
        <th scope="col" class="text-center">Post ID</th>
        <th scope="col" class="text-center">Owner</th>
        <th scope="col" class="text-center">Content</th>
        <th scope="col" class="text-center">Comments</th>
        <th scope="col" class="text-center">Likes</th>
        <th scope="col" class="text-center">Edit</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="post" items="${requestScope.userPosts}">
        <tr>
            <th scope="row" class="text-center">${post.id}</th>
            <td class="text-center">${post.owner}</td>
            <td>${post.content}</td>
            <td class="text-center">
                <a href="my-tweets/comments?post-id=${post.id}" class="link-success">Comments</a>
            </td>
            <td class="text-center">
                <div class="dropdown">
                    <a class="btn btn-primary w-100 dropdown-toggle" role="button" id="dropdownMenuLink"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        likes
                        <span class="position-absolute top-0 start-0 translate-middle badge rounded-pill bg-success bg-secondary me-4">
                            ${post.likesCount}
                            <span class="visually-hidden">Hey, here a lot of likes!</span>
                        </span>
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <c:forEach var="like" items="${post.likes}">
                            <li>${like}</li>
                        </c:forEach>
                    </ul>
                </div>
            </td>
            <td class="d-flex justify-content-center">
                <form action="my-tweets/edit" method="get" class="mx-auto">
                    <input type='hidden' name='post-id' value='${post.id}'/>
                    <button type="submit" class="btn btn-warning">Edit</button>
                </form>
                <form action="my-tweets/delete" method="get" class="mx-auto">
                    <input type='hidden' name='post-id' value='${post.id}'/>
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="common/_footer.jsp"/>
</body>
</html>