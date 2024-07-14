<%--
  Created by IntelliJ IDEA.
  User: Ancort
  Date: 13-07-2024
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Vehicles</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

</head>
<body>

<h2 class="text-center">Lista de vehiculos</h2>

<div class="container mt-5">
    <form action="userServlet?action=addVehicle" method="post">
        <div class="form-group">
            <label for="name">Nombre del Vehiculo</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="url">URL</label>
            <input type="text" class="form-control" id="url" name="url" required>
        </div>
        <input type="hidden" id="userId" name="userId" value="${userId}">
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
                out.println("<p class='text-danger'>" + error + "</p>");
            }
        %>
        <button type="submit" class="btn btn-primary">Add Vehicle</button>
    </form>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>URL</th>
            <th>User ID</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vehicles}" var="vehicle">
            <tr>
                <td>${vehicle.id}</td>
                <td>${vehicle.name}</td>
                <td><a href="${vehicle.url}" target="_blank">${vehicle.url}</a></td>
                <td>${vehicle.userId}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
