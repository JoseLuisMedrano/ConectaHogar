<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/fragments/header.jsp">
    <jsp:param name="title" value="Conecta con Profesionales Técnicos"/>
</jsp:include>

    <!-- Hero Section -->
    <header class="hero-bg d-flex align-items-center justify-content-center text-center text-white">
        <div class="hero-content">
            <h1 class="display-4 fw-bold mb-4">
                ConectaHogar: los mejores técnicos y trabajadores a tu alcance
            </h1>
            <p class="lead mb-4">
                Solicita servicios de confianza en segundos con ConectaHogar.
            </p>
            <div class="d-flex justify-content-center flex-wrap gap-2">
                <button class="btn btn-success btn-lg px-4 py-3 fw-bold" id="openRoleModalBtn">
                    <i class="fas fa-user-plus me-2"></i>Registrarse
                </button>
            </div>
        </div>
    </header>

    <!-- Sección de categorías -->
    <section class="py-5 bg-light" id="categorias">
        <div class="container px-4 px-lg-5">
            <div class="text-center mb-5">
                <h1 class="fw-bold">Categorías populares</h1>
                <p class="lead text-muted">
                    Encuentra al profesional ideal para tu hogar
                </p>
            </div>
            <div class="row g-4 justify-content-center">
                <!-- Jardineros -->
                <div class="col-md-4 col-6">
                    <div class="card category-card border-0 shadow-sm overflow-hidden h-100">
                        <div class="category-img-container">
                            <img src="${pageContext.request.contextPath}/images/jardineria.jpg" class="card-img-top" alt="Jardinero">
                            <div class="category-overlay"></div>
                        </div>
                        <div class="card-body text-center">
                            <h5 class="card-title">Jardineros</h5>
                            <p class="card-text">
                                Diseño, mantenimiento y cuidado de jardines y áreas verdes.
                            </p>
                            <a href="${pageContext.request.contextPath}/servicios/jardineria" class="btn btn-outline-primary">Ver perfiles</a>
                        </div>
                    </div>
                </div>

                <!-- Electricistas -->
                <div class="col-md-4 col-6">
                    <div class="card category-card border-0 shadow-sm overflow-hidden h-100">
                        <div class="category-img-container">
                            <img src="${pageContext.request.contextPath}/images/electricista.jpg" class="card-img-top" alt="Electricista">
                            <div class="category-overlay"></div>
                        </div>
                        <div class="card-body text-center">
                            <h5 class="card-title">Electricistas</h5>
                            <p class="card-text">
                                Soluciona fallas eléctricas con profesionales certificados.
                            </p>
                            <a href="${pageContext.request.contextPath}/servicios/electricistas" class="btn btn-outline-primary">Ver perfiles</a>
                        </div>
                    </div>
                </div>

                <!-- Gasfiteros -->
                <div class="col-md-4 col-6">
                    <div class="card category-card border-0 shadow-sm overflow-hidden h-100">
                        <div class="category-img-container">
                            <img src="${pageContext.request.contextPath}/images/gasfitero.jpg" class="card-img-top" alt="Gasfitero">
                            <div class="category-overlay"></div>
                        </div>
                        <div class="card-body text-center">
                            <h5 class="card-title">Gasfiteros</h5>
                            <p class="card-text">
                                Instalación y reparación de tuberías y sanitarios.
                            </p>
                            <a href="${pageContext.request.contextPath}/servicios/gasfiteros" class="btn btn-outline-primary">Ver perfiles</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Opiniones de Clientes -->
    <section class="py-5 bg-white">
        <div class="container">
            <h2 class="text-center mb-5 fw-bold">Lo que dicen nuestras clientas de Molina</h2>
            <div class="row justify-content-center">
                <div class="col-md-10">
                    <div class="row g-4">
                        <c:forEach items="${testimonios}" var="testimonio" varStatus="loop">
                            <div class="col-md-6">
                                <div class="testimonio p-4 h-100">
                                    <div class="rating mb-3 text-warning">
                                        <c:forEach begin="1" end="${testimonio.calificacion}">
                                            <i class="fas fa-star"></i>
                                        </c:forEach>
                                    </div>
                                    <p class="mb-3 fst-italic">"${testimonio.comentario}"</p>
                                    <div class="d-flex align-items-center">
                                        <img src="${pageContext.request.contextPath}/images/usuarios/${testimonio.imagen}" 
                                             alt="${testimonio.nombre}" 
                                             class="rounded-circle me-3" width="50">
                                        <div>
                                            <h6 class="mb-0 fw-bold">${testimonio.nombre}</h6>
                                            <small class="text-muted">${testimonio.ubicacion}</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Sección Sobre Nosotros -->
    <section class="py-5 bg-light" id="nosotros">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6 mb-4 mb-lg-0">
                    <div class="position-relative" style="min-height: 350px;">
                        <img src="${pageContext.request.contextPath}/images/sobre-nosotros.jpg" 
                             alt="Equipo ConectaHogar" 
                             class="img-fluid rounded shadow h-100 w-100"
                             style="object-fit: cover;">
                    </div>
                </div>
                <div class="col-lg-6">
                    <h2 class="fw-bold mb-4 text-center text-lg-start">SOBRE NOSOTROS</h2>
                    <div class="bg-white p-4 rounded shadow-sm">
                        <p class="fst-italic">${aboutUs.descripcion}</p>
                        
                        <p>${aboutUs.serviciosIntro}</p>
                        
                        <ul class="list-unstyled">
                            <c:forEach items="${aboutUs.servicios}" var="servicio">
                                <li class="mb-2"><i class="fas ${servicio.icono} text-success me-2"></i> ${servicio.nombre}</li>
                            </c:forEach>
                        </ul>
                        
                        <p>${aboutUs.conclusion}</p>
                        
                        <div class="text-center text-lg-start mt-4">
                            <a href="#" class="btn btn-success px-4 me-2">
                                <i class="fab fa-whatsapp me-2"></i> Contáctanos
                            </a>
                            <a href="#" class="btn btn-outline-secondary px-4">
                                <i class="fas fa-users me-2"></i> Conoce al equipo
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Modal de selección de rol -->
    <div class="modal fade" id="roleModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content bg-dark text-white">
                <div class="modal-header border-0">
                    <h2 class="modal-title fw-bold text-center w-100">¿Qué panel desea ingresar?</h2>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row g-4">
                        <!-- Opción Cliente -->
                        <div class="col-md-6">
                            <div class="role-option p-4 rounded-3 text-center h-100" 
                                 data-role="cliente" 
                                 tabindex="0">
                                <i class="fas fa-home fa-3x mb-3 text-success"></i>
                                <h3>Cliente</h3>
                                <p class="mb-0">Busca profesionales para tus necesidades del hogar</p>
                            </div>
                        </div>
                        
                        <!-- Opción Trabajador -->
                        <div class="col-md-6">
                            <div class="role-option p-4 rounded-3 text-center h-100" 
                                 data-role="trabajador" 
                                 tabindex="0">
                                <i class="fas fa-tools fa-3x mb-3 text-success"></i>
                                <h3>Trabajador</h3>
                                <p class="mb-0">Ofrece tus servicios y conecta con clientes</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer border-0 justify-content-center">
                    <button type="button" class="btn btn-success px-4 py-2 fw-bold" id="confirmRoleBtn" disabled style="min-width: 150px;">
                        Continuar <i class="fas fa-arrow-right ms-2"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>

<jsp:include page="/WEB-INF/fragments/footer.jsp"/>