<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="postId" value="${requestScope.post.id}"/>
<c:set var="postAuthor" value="${requestScope.post.author}"/>
<c:set var="postContent" value="${requestScope.post.content}"/>
<c:set var="postDate" value="${requestScope.post.timestampFormatted}"/>
<c:set var="postLikesCount" value="${requestScope.post.likesCount}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Tweet ${postId}</title>
    <jsp:include page="common/_head.jsp"/>
</head>
<body>
<jsp:include page="common/_header.jsp"/>
<div class="container mt-3 position-relative">
    <div class="card">
        <h3 class="card-title text-center">Tweet ${postId} from ${postAuthor}</h3>
        <div class="card-body">
            ${postContent}
        </div>
        <span class="position-absolute bottom-0 end-0 fs-6 fw-lighter text-muted me-2">
            ${postDate}
        </span>
        <form method="post">
            <input type='hidden' name='post-id' value='${postId}'/>
            <input type='hidden' name='like' value='true'/>
            <button type="submit" class="btn btn-outline-success position-absolute top-0 end-0">
                Like
                <span class="position-absolute top-0 start-0 translate-middle badge rounded-pill bg-success bg-secondary me-4">
                    ${postLikesCount}
                    <span class="visually-hidden">Hey, here a lot of likes!</span>
                </span>
            </button>
        </form>
    </div>
</div>
<div class="accordion mx-5 mt-3" id="accordionExample">
    <c:forEach var="comment" items="${requestScope.post.comments}">
        <div class="accordion-item">
            <h2 class="accordion-header" id="heading${comment.id}">
                <button class="accordion-button" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapse${comment.id}" aria-expanded="true"
                        aria-controls="collapse${comment.id}">
                    Comment ${comment.id} from ${comment.author}. ${comment.timestampFormatted}
                </button>
            </h2>
            <div id="collapse${comment.id}" class="accordion-collapse collapse" aria-labelledby="heading${comment.id}"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                        ${comment.content}
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<div class="container mt-1">
    <form class="w-100 h-50 p-3" method="post">
        <div class="form-floating mt-3">
        <textarea class="form-control" name="comment-content"
                  placeholder="What do you think of this tweet?"
                  id="floatingTextarea" maxlength="2048" required style="resize: none; height: 15vh;"></textarea>
            <label for="floatingTextarea">What do you think of this tweet?</label>
        </div>
        <div class="row mt-3 justify-content-md-center">
            <button class="col-md-auto btn btn-lg btn-success" type="submit">Comment</button>
            <span class="col-2"></span>
            <button class="col-md-auto btn btn-lg btn-secondary" type="reset">Clear</button>
        </div>
    </form>
</div>
<jsp:include page="common/_footer.jsp"/>
</body>
</html>