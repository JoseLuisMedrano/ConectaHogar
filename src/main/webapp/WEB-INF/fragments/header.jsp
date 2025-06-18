<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>ConectaHogar | ${param.title}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/index.css">
        
    </head>
    <body>
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
                    <img src="${pageContext.request.contextPath}/static/images/fotos_index/logo-conecta-hogar.jpeg" 
                         alt="Logo ConectaHogar" 
                         width="75" height="75" 
                         class="bg-white rounded-circle p-1">

                </a>
                <button class="navbar-toggler navbar-dark" type="button" data-bs-toggle="collapse" data-bs-target="#menu">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="menu">
                    <ul class="navbar-nav ms-auto align-items-lg-center">
                        <li class="nav-item me-lg-3">
                            <a class="nav-link contratar-btn" href="#quiero-contratar" id="contratarBtn">
                                <i class="fas fa-home me-1"></i> Quiero Contratar
                                <div class="hover-panel"></div>
                            </a>
                        </li>
                        <li class="nav-item me-lg-3">
                            <a class="nav-link trabajar-btn" href="#quiero-trabajar" id="trabajarBtn">
                                <i class="fas fa-tools me-1"></i> Quiero Trabajar
                                <div class="hover-panel"></div>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link btn btn-outline-light ms-lg-2" href="${pageContext.request.contextPath}/loginServlet">
                                <i class="fas fa-sign-in-alt me-1"></i> Ingresar
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>