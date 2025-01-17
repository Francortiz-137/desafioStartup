<%--
  Created by IntelliJ IDEA.
  User: Ancort
  Date: 08-07-2024
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

</head>
<body>

    <section class="container">
        <h1>Bienvenido</h1>
        <p>Usuario: ${user}</p>
        <p>Correo: ${email}</p>

    </section>

    <c:if test="${userRole == 'admin'}">

        <section class="container">
            <form action="userServlet?action=register" method="post">

                <label for="email">Correo</label>
                <input type="email" class="form-control" id="email" name="email" required>
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                        out.println("<p style='color:red;'>" + error + "</p>");
                    }
                %>
                <label for="nick">Nick</label>
                <input type="text" class="form-control" id="nick" name="nick" required>

                <label for="name">Nombre</label>
                <input type="text" class="form-control" id="name" name="name" required>

                <label for="password">Contraseña</label>
                <input type="password" class="form-control" id="password" name="password" required>

                <label for="weight">Peso</label>
                <input type="number" step="0.01" class="form-control" id="weight" name="weight">

                <button type="submit" class="btn btn-primary">Registrar</button>
            </form>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Correo</th>
                    <th>Nick</th>
                    <th>Nombre</th>
                    <th>Password</th>
                    <th>Peso</th>
                    <th>Creado en</th>
                    <th>Actualizado en</th>
                    <th>Vehiculos</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.email}</td>
                        <td>${user.nick}</td>
                        <td>${user.name}</td>
                        <td>${user.password}</td>
                        <td>${user.weight}</td>
                        <td>${user.createdAt}</td>
                        <td>${user.updatedAt}</td>
                        <td><a href="userServlet?action=showVehicles&userId=${user.id}" class="btn btn-primary">Vehiculos</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
    </c:if>
    <c:if test="${userRole != 'admin'}">
        <p class="text-danger text-center">Error de permisos. No eres Administrador. Para visualizar el listado de usuarios por favor ingresa con un cuenta de administrador.</p>
    </c:if>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
