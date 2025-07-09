<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro - Paso 1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        .role-card {
            border: 2px solid transparent;
            transition: all 0.3s ease;
        }
        .role-card:hover {
            border-color: #0d6efd;
            transform: translateY(-5px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body class="bg-light d-flex align-items-center justify-content-center vh-100">
    <div class="container text-center">
        <i class="bi bi-house-door-fill text-primary" style="font-size: 3rem;"></i>
        <h2 class="mb-3">Bienvenido a ConectaHogar</h2>
        <p class="lead text-muted mb-4">Para comenzar, dinos qué tipo de cuenta quieres crear.</p>
        <div class="row justify-content-center g-4">
            <div class="col-md-5 col-lg-4">
                <a href="${pageContext.request.contextPath}/register?role=cliente" class="text-decoration-none text-dark">
                    <div class="card h-100 role-card">
                        <div class="card-body p-5">
                            <i class="bi bi-person-heart" style="font-size: 4rem;"></i>
                            <h3 class="h4 mt-3">Soy Cliente</h3>
                            <p class="text-muted">Busco un profesional para solucionar problemas en mi hogar.</p>
                        </div>
                    </div>
                </a>
            </div>
            <div class="col-md-5 col-lg-4">
                <a href="${pageContext.request.contextPath}/register?role=tecnico" class="text-decoration-none text-dark">
                    <div class="card h-100 role-card">
                        <div class="card-body p-5">
                            <i class="bi bi-tools" style="font-size: 4rem;"></i>
                            <h3 class="h4 mt-3">Soy Técnico</h3>
                            <p class="text-muted">Ofrezco mis servicios profesionales a la comunidad.</p>
                        </div>
                    </div>
                </a>
            </div>
        </div>
        <div class="mt-4">
            <p>¿Ya tienes una cuenta? <a href="${pageContext.request.contextPath}/login">Inicia Sesión</a></p>
        </div>
    </div>
</body>
</html>