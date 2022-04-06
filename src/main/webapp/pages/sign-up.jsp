<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign up</title>
    <jsp:include page="common/_head.jsp"/>
</head>
<body>
<jsp:include page="common/_header.jsp"/>
<form class="position-absolute top-50 start-50 translate-middle" method="post">
    <h1 class="h3 fw-normal">Registration</h1>
    <div class="form-floating mt-3">
        <input type="text" class="form-control" name="login" id="floatingLogin" placeholder="Login" required>
        <label for="floatingLogin">Login</label>
    </div>
    <div class="form-floating mt-3">
        <input type="password" class="form-control" name="password" id="floatingPassword" placeholder="Password"
               required>
        <label for="floatingPassword">Password</label>
    </div>
    <div class="form-floating mt-3">
        <input type="text" class="form-control" name="email" id="floatingEmail" placeholder="Email" required>
        <label for="floatingEmail">Email</label>
    </div>
    <div class="form-floating mt-3">
        <input type="text" class="form-control" name="name" id="floatingName" placeholder="Name" required>
        <label for="floatingName">Name</label>
    </div>
    <div class="form-floating mt-3">
        <input type="text" class="form-control" name="surname" id="floatingSurname" placeholder="Surname" required>
        <label for="floatingSurname">Surname</label>
    </div>
    <button class="w-100 btn btn-lg btn-primary mt-5" type="submit">Sign up</button>
</form>
<jsp:include page="common/_footer.jsp"/>
</body>
</html>