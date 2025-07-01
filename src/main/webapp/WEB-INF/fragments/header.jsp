<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ConectaHogar | ${param.title}</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css">
</head>
<body>

    <nav class="navbar navbar-expand-lg fixed-top" id="mainNav">
        <div class="container">
            <a class="navbar-brand fw-bold text-white" href="${pageContext.request.contextPath}/">
                <i class="fas fa-home"></i> ConectaHogar
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menu" aria-controls="menu" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fas fa-bars text-white"></i>
            </button>
            <div class="collapse navbar-collapse" id="menu">
                <ul class="navbar-nav ms-auto align-items-lg-center">
                    <li class="nav-item"><a class="nav-link" href="#categorias">Categorías</a></li>
                    <li class="nav-item"><a class="nav-link" href="#testimonios">Opiniones</a></li>
                    <li class="nav-item"><a class="nav-link" href="#nosotros">Nosotros</a></li>
                    <li class="nav-item ms-lg-3">
                        <a class="nav-link btn btn-outline-light px-3 py-2" href="${pageContext.request.contextPath}/login">
                            <i class="fas fa-sign-in-alt me-1"></i> Ingresar
                        </a>
                    </li>
                    <li class="nav-item ms-lg-2">
                         <a class="nav-link btn btn-primary px-3 py-2" href="${pageContext.request.contextPath}/register">
                            <i class="fas fa-user-plus me-1"></i> Registrarse
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>