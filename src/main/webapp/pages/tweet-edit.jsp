<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="postId" value="${requestScope.post.id}"/>
<c:set var="postAuthor" value="${requestScope.post.author}"/>
<c:set var="postContent" value="${requestScope.post.content}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Edit Tweet</title>
    <jsp:include page="common/_head.jsp"/>
</head>
<body>
<jsp:include page="common/_header.jsp"/>
<form class="position-absolute w-50 h-50 p-3 top-50 start-50 translate-middle" method="post">
    <h1 class="h3 fw-normal">Edit tweet №${postId}. Author: ${postAuthor}</h1>
    <input type='hidden' name='post-id' value='${postId}'/>
    <div class="form-floating mt-3 h-50">
        <textarea class="form-control h-100" name="post-content"
                  placeholder="You can change everything!"
                  id="floatingTextarea" maxlength="2048" required style="resize: none">${postContent}</textarea>
        <label for="floatingTextarea">You can change everything!</label>
    </div>
    <div class="row mt-3 justify-content-md-center">
        <button class="col-md-auto btn btn-lg btn-success" type="submit">Save</button>
        <span class="col-2"></span>
        <button class="col-md-auto btn btn-lg btn-secondary" type="reset">Reset</button>
    </div>
</form>
<jsp:include page="common/_footer.jsp"/>
</body>
</html>
