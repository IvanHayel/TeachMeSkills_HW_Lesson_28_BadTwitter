<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Admin panel</title>
    <jsp:include page="common/_head.jsp"/>
</head>
<body>
<jsp:include page="common/_header.jsp"/>
<table class="table table-striped table-bordered align-middle" style="table-layout: fixed">
    <caption></caption>
    <thead>
    <tr>
        <th scope="col" class="col-1 text-center">User ID</th>
        <th scope="col" class="col-2 text-center">Login</th>
        <th scope="col" class="col-2 text-center">Email</th>
        <th scope="col" class="col-1 text-center">Name</th>
        <th scope="col" class="col-1 text-center">Surname</th>
        <th scope="col" class="col-1 text-center">Roles</th>
        <th scope="col" class="col-2 text-center">Edit</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.allUsers}">
        <tr>
            <th scope="row" class="text-center">${user.id}</th>
            <td class="text-center">${user.login}</td>
            <td class="text-center">${user.email}</td>
            <td class="text-center">${user.name}</td>
            <td class="text-center">${user.surname}</td>
            <td class="text-center">
                <div class="dropdown">
                    <a class="btn btn-primary w-100 dropdown-toggle" role="button" id="dropdownMenuLink"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        Roles
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <c:forEach var="role" items="${user.roles}">
                            <li class="ms-2">${role.name}</li>
                        </c:forEach>
                    </ul>
                </div>
            </td>
            <td class="d-flex justify-content-center">
                <form action="${pageContext.request.contextPath}/delete-user" method="post" class="mx-auto">
                    <input type='hidden' name='user-id' value='${user.id}'/>
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
