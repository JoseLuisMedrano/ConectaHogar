<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ConectaHogar - Registro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/auth.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-7">
                <div class="card shadow-sm">
                    <div class="card-body p-4 p-md-5">
                        <div class="text-center mb-4">
                            <i class="fas fa-home fa-3x text-primary mb-3"></i>
                            <h2 class="h3 mb-2">ConectaHogar</h2>
                            <p class="text-muted">Crea tu cuenta para empezar</p>
                        </div>

                        <c:if test="${not empty mensajeError}"><div class="alert alert-danger">${mensajeError}</div></c:if>

                        <form id="registerForm" action="${pageContext.request.contextPath}/register" method="post" class="needs-validation" novalidate>
                            <div class="row g-3">
                                <div class="col-12">
                                    <label class="form-label">Quiero registrarme como:</label>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="tipoUsuario" id="cliente" value="CLIENTE" required>
                                        <label class="form-check-label" for="cliente">Cliente</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="tipoUsuario" id="tecnico" value="TECNICO" required>
                                        <label class="form-check-label" for="tecnico">Técnico</label>
                                    </div>
                                </div>
                                </div>
                                <div class="col-12 mt-4">
                                    <button id="submitButton" type="submit" class="btn btn-primary w-100">Registrarme</button>
                                </div>
                            </div>
                        </form>
                        <div class="text-center mt-4"><p>¿Ya tienes una cuenta? <a href="${pageContext.request.contextPath}/login">Inicia sesión</a></p></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/auth.js"></script>
</body>
</html>