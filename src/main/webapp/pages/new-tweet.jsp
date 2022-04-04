<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>New Tweet</title>
    <jsp:include page="common/_head.jsp"/>
</head>
<body>
<jsp:include page="common/_header.jsp"/>
<form class="position-absolute w-50 h-50 p-3 top-50 start-50 translate-middle" method="post">
    <div class="form-floating mt-3 h-50">
        <textarea class="form-control h-100" name="post-content"
                  placeholder="Share smart thoughts with the world:"
                  id="floatingTextarea" maxlength="2048" required style="resize: none"></textarea>
        <label for="floatingTextarea">Share smart thoughts with the world!</label>
    </div>
    <div class="row mt-3 justify-content-md-center">
        <button class="col-md-auto btn btn-lg btn-success" type="submit">Tweet</button>
        <span class="col-2"></span>
        <button class="col-md-auto btn btn-lg btn-secondary" type="reset">Clear</button>
    </div>
</form>
<jsp:include page="common/_footer.jsp"/>
</body>
</html>