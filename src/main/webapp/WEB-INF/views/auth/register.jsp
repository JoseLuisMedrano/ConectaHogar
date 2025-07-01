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

                        <c:if test="${not empty mensajeError}">
                            <div class="alert alert-danger d-flex align-items-center" role="alert">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                <div>${mensajeError}</div>
                            </div>
                        </c:if>

                        <form id="registerForm" action="${pageContext.request.contextPath}/register" method="post" class="needs-validation" novalidate>
                            <div class="row g-3">
                                
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required>
                                        <label for="nombre">Nombre</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="apellido" name="apellido" placeholder="Apellido" required>
                                        <label for="apellido">Apellido</label>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-floating">
                                        <input type="email" class="form-control" id="correo" name="correo" placeholder="Correo Electrónico" required>
                                        <label for="correo">Correo Electrónico</label>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="password" class="form-control" id="contrasena" name="contrasena" placeholder="Contraseña" required minlength="6">
                                        <label for="contrasena">Contraseña (mín. 6 caracteres)</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="password" class="form-control" id="confirmarContrasena" name="confirmarContrasena" placeholder="Confirmar Contraseña" required>
                                        <label for="confirmarContrasena">Confirmar Contraseña</label>
                                        <div class="invalid-feedback">Las contraseñas no coinciden.</div>
                                    </div>
                                </div>
                                
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="dni" name="dni" placeholder="DNI" required pattern="[0-9]{8}" title="El DNI debe tener 8 dígitos.">
                                        <label for="dni">DNI (8 dígitos)</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="tel" class="form-control" id="telefono" name="telefono" placeholder="+51987654321" required>
                                        <label for="telefono">Teléfono</label>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <div class="form-floating">
                                        <input type="text" class="form-control" id="direccion" name="direccion" placeholder="Dirección" required>
                                        <label for="direccion">Dirección</label>
                                    </div>
                                </div>
                                
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <input type="number" class="form-control" id="edad" name="edad" min="18" max="100" placeholder="Edad" required>
                                        <label for="edad">Edad (mín. 18 años)</label>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-floating">
                                        <select class="form-select" id="sexo" name="sexo" required>
                                            <option value="" disabled selected>Seleccione...</option>
                                            <option value="Masculino">Masculino</option>
                                            <option value="Femenino">Femenino</option>
                                            <option value="Otro">Prefiero no decir</option>
                                        </select>
                                        <label for="sexo">Sexo</label>
                                    </div>
                                </div>

                                <div class="col-12">
                                    <label class="form-label">Quiero registrarme como:</label>
                                    <div class="form-check mb-2">
                                        <input class="form-check-input" type="radio" name="tipoUsuario" id="cliente" value="CLIENTE" required>
                                        <label class="form-check-label fw-bold" for="cliente">
                                            <i class="fas fa-user me-2 text-primary"></i>Cliente
                                            <small class="d-block text-muted fw-normal">Para buscar y contratar servicios para mi hogar.</small>
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="tipoUsuario" id="tecnico" value="TECNICO" required>
                                        <label class="form-check-label fw-bold" for="tecnico">
                                            <i class="fas fa-tools me-2 text-primary"></i>Técnico
                                            <small class="d-block text-muted fw-normal">Para ofrecer mis servicios profesionales.</small>
                                        </label>
                                    </div>
                                </div>

                                <div class="col-12 mt-4">
                                    <button id="submitButton" type="submit" class="btn btn-primary w-100 py-2">
                                        <span id="buttonText">
                                            <i class="fas fa-user-plus me-2"></i>Registrarme
                                        </span>
                                        <span id="buttonSpinner" class="spinner-border spinner-border-sm d-none" role="status" aria-hidden="true"></span>
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div class="text-center mt-4 pt-3 border-top">
                            <p class="mb-0">¿Ya tienes una cuenta? <a href="${pageContext.request.contextPath}/login" class="text-decoration-none fw-bold">Inicia sesión</a></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/auth.js"></script>
</body>
</html>