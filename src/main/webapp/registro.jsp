<%--
  Created by IntelliJ IDEA.
  User: Ancort
  Date: 08-07-2024
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Startup</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

</head>
<body>

    <div class="container mt-5">
        <h2 class="text-center">Registro de Usuario</h2>
        <form action="RegisterServlet" method="post">

            <label for="correo">Correo</label>
            <input type="email" class="form-control" id="correo" name="correo" required>

            <label for="nick">Nick</label>
            <input type="text" class="form-control" id="nick" name="nick" required>

            <label for="nombre">Nombre</label>
            <input type="text" class="form-control" id="nombre" name="nombre" required>

            <label for="password">Contraseña</label>
            <input type="password" class="form-control" id="password" name="password" required>

            <label for="peso">Peso</label>
            <input type="number" step="0.01" class="form-control" id="peso" name="peso">

            <button type="submit" class="btn btn-primary">Registrar</button>
        </form>
    </div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
