<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ConectaHogar - Completar Registro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow-sm">
                <div class="card-body p-4 p-md-5">
                    <div class="text-center mb-4">
                        <i class="bi bi-person-plus-fill text-primary" style="font-size: 3rem;"></i>
                        <h2 class="h3 mb-2">Completa tus Datos</h2>
                        <p class="text-muted">Estás a un paso de unirte como <strong><c:out value="${param.role}"/></strong>.</p>
                    </div>

                    <c:if test="${not empty mensajeError}">
                        <div class="alert alert-danger"><c:out value="${mensajeError}"/></div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/register" method="post">
                        <input type="hidden" name="tipoUsuario" value="${param.role}">

                        <div class="row g-3">
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" name="nombre" class="form-control" id="nombre" placeholder="Nombre" required>
                                    <label for="nombre">Nombre</label>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-floating">
                                    <input type="text" name="apellido" class="form-control" id="apellido" placeholder="Apellido" required>
                                    <label for="apellido">Apellido</label>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-floating">
                                    <input type="email" name="correo" class="form-control" id="correo" placeholder="Correo Electrónico" required>
                                    <label for="correo">Correo Electrónico</label>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-floating">
                                    <input type="password" name="contrasena" class="form-control" id="contrasena" placeholder="Contraseña" required>
                                    <label for="contrasena">Contraseña</label>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-floating">
                                    <input type="tel" name="telefono" class="form-control" id="telefono" placeholder="Teléfono" required>
                                    <label for="telefono">Teléfono</label>
                                </div>
                            </div>
                            <div class="col-12">
                                 <div class="form-floating">
                                    <input type="text" name="direccion" class="form-control" id="direccion" placeholder="Dirección" required>
                                    <label for="direccion">Dirección</label>
                                 </div>
                            </div>
                            <div class="col-12">
                                 <div class="form-floating">
                                    <input type="text" name="dni" class="form-control" id="dni" placeholder="DNI" required maxlength="8">
                                    <label for="dni">DNI</label>
                                 </div>
                            </div>
                        </div>

                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary btn-lg">Registrarme</button>
                        </div>
                    </form>

                    <div class="text-center mt-3">
                         <a href="${pageContext.request.contextPath}/register" class="text-decoration-none">&larr; Volver a elegir rol</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>