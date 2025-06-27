<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ConectaHogar - Iniciar Sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/css/auth.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-sm">
                    <div class="card-body p-4 p-md-5">
                        <div class="text-center mb-4">
                            <i class="fas fa-home fa-3x text-primary mb-3"></i>
                            <h2 class="h3 mb-2">ConectaHogar</h2>
                            <p class="text-muted">Bienvenido de vuelta</p>
                        </div>

                        <c:if test="${not empty mensajeError}">
                            <div class="alert alert-danger d-flex align-items-center">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                <div>${mensajeError}</div>
                            </div>
                        </c:if>

                        <c:if test="${not empty mensajeExito}">
                            <div class="alert alert-success d-flex align-items-center">
                                <i class="fas fa-check-circle me-2"></i>
                                <div>${mensajeExito}</div>
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="mb-3">
                                <div class="form-floating">
                                    <input type="email" class="form-control" id="correo" name="correo" 
                                           placeholder="Correo Electrónico" value="${param.correo}" required>
                                    <label for="correo">Correo Electrónico</label>
                                </div>
                            </div>

                            <div class="mb-3">
                                <div class="form-floating">
                                    <input type="password" class="form-control" id="contrasena" name="contrasena" 
                                           placeholder="Contraseña" required>
                                    <label for="contrasena">Contraseña</label>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 py-2 mb-3">
                                <i class="fas fa-sign-in-alt me-2"></i>
                                Iniciar Sesión
                            </button>
                            
                            <div class="text-center">
                                <a href="#" class="text-decoration-none">¿Olvidaste tu contraseña?</a>
                            </div>
                        </form>

                        <div class="text-center mt-4 pt-3 border-top">
                            <p class="mb-0">¿No tienes cuenta? <a href="${pageContext.request.contextPath}/register" class="text-decoration-none">Regístrate aquí</a></p>
                        </div>
                        
                        <div class="mt-4 pt-3 border-top">
                            <p class="small text-muted mb-2"><strong>Credenciales de prueba:</strong></p>
                            <p class="small text-muted mb-1">Admin: admin@conectahogar.com / admin123</p>
                            <p class="small text-muted mb-1">Cliente: cliente@test.com / 123456</p>
                            <p class="small text-muted">Técnico: tecnico@test.com / 123456</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        setTimeout(() => {
            document.querySelectorAll('.alert').forEach(alert => {
                alert.style.transition = 'opacity 0.5s';
                alert.style.opacity = '0';
                setTimeout(() => alert.remove(), 500);
            });
        }, 5000);
    </script>
</body>
</html>