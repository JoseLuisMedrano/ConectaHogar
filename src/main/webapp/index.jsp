<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/fragments/header.jsp">
    <jsp:param name="title" value="Técnicos de Confianza a tu Alcance"/>
</jsp:include>

<header class="hero-bg">
    <div class="hero-content">
        <h1 class="display-4 fw-bold mb-4">
            Los mejores técnicos y trabajadores a tu alcance
        </h1>
        <p class="lead mb-4">
            Solicita servicios de confianza en segundos con ConectaHogar.
        </p>
        <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-lg px-4 py-3 fw-bold">
            <i class="fas fa-user-plus me-2"></i> Crear Cuenta Gratis
        </a>
    </div>
</header>

<section class="py-5 bg-light" id="categorias">
    <div class="container px-4 px-lg-5">
        <div class="text-center mb-5">
            <h2 class="fw-bold">Categorías Populares</h2>
            <p class="lead text-muted">Encuentra al profesional ideal para tu hogar.</p>
        </div>
        <div class="row g-4 justify-content-center">
            <div class="col-md-4 col-sm-6">
                <div class="card category-card border-0 shadow-sm h-100">
                    <img src="${pageContext.request.contextPath}/static/images/fotos_index/jardineria.jpg" class="card-img-top" alt="Jardinero" style="height: 200px; object-fit: cover;">
                    <div class="card-body text-center">
                        <h5 class="card-title fw-bold">Jardineros</h5>
                        <p class="card-text">Diseño, mantenimiento y cuidado de jardines y áreas verdes.</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-sm-6">
                <div class="card category-card border-0 shadow-sm h-100">
                    <img src="${pageContext.request.contextPath}/static/images/fotos_index/electricista.jpg" class="card-img-top" alt="Electricista" style="height: 200px; object-fit: cover;">
                    <div class="card-body text-center">
                        <h5 class="card-title fw-bold">Electricistas</h5>
                        <p class="card-text">Soluciona fallas eléctricas con profesionales certificados.</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-sm-6">
                <div class="card category-card border-0 shadow-sm h-100">
                    <img src="${pageContext.request.contextPath}/static/images/fotos_index/gasfiteria.png" class="card-img-top" alt="Gasfitero" style="height: 200px; object-fit: cover;">
                    <div class="card-body text-center">
                        <h5 class="card-title fw-bold">Gasfiteros</h5>
                        <p class="card-text">Instalación y reparación de tuberías, grifos y sanitarios.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="py-5 bg-white" id="testimonios">
    <div class="container">
        <h2 class="text-center mb-5 fw-bold">Lo que dicen nuestras clientas</h2>
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-5 mb-4">
                <div class="testimonio p-4 h-100">
                    <div class="rating mb-3 text-warning">
                       <i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i>
                    </div>
                    <p class="mb-3 fst-italic">"¡Servicio rápido y muy profesional! El técnico electricista resolvió mi problema en menos de una hora. Totalmente recomendado."</p>
                    <div class="d-flex align-items-center">
                       <img src="https://i.pinimg.com/736x/55/22/b9/5522b9bc82edc96ec45e2497beaefd8a.jpg" width="50px" heigth="auto" alt="María" class="rounded-circle me-3">
                        <div>
                            <h6 class="mb-0 fw-bold">María Gonzales</h6>
                            <small class="text-muted">La Molina, Lima</small>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-5 mb-4">
                <div class="testimonio p-4 h-100">
                     <div class="rating mb-3 text-warning">
                       <i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star-half-alt"></i>
                    </div>
                    <p class="mb-3 fst-italic">"El jardinero dejó mi patio hermoso. Muy amable y con buenos precios. Usaré la plataforma de nuevo, sin duda."</p>
                     <div class="d-flex align-items-center">
                       <img src="https://i.pravatar.cc/50?img=2" alt="Carla" class="rounded-circle me-3">
                        <div>
                            <h6 class="mb-0 fw-bold">Carla Rodriguez</h6>
                            <small class="text-muted">Ate, Lima</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="py-5 bg-light" id="nosotros">
    <div class="container px-4 px-lg-5">
        <div class="row gx-5 align-items-center">

            <div class="col-lg-6 mb-4 mb-lg-0">
                <img class="img-fluid rounded-3 shadow" 
                     src="${pageContext.request.contextPath}/static/images/fotos_index/sobrenosotros.png" 
                     alt="Equipo de ConectaHogar trabajando">
            </div>

            <div class="col-lg-6">
                <h2 class="fw-bold mb-4">Nuestra Misión es tu Tranquilidad</h2>
                <p class="lead text-muted mb-4">
                    En ConectaHogar, nacimos con el objetivo de simplificar la vida de las personas, conectando hogares con técnicos y profesionales de confianza de manera rápida, segura y transparente.
                </p>
                
                <div class="mb-4">
                    <div class="d-flex mb-3">
                        <div class="flex-shrink-0">
                            <i class="fas fa-check-circle fa-2x text-primary"></i>
                        </div>
                        <div class="ms-3">
                            <h5 class="fw-bold mb-0">Confianza y Seguridad</h5>
                            <p class="text-muted mb-0">Verificamos a cada uno de nuestros profesionales para garantizar tu seguridad.</p>
                        </div>
                    </div>
                    <div class="d-flex">
                        <div class="flex-shrink-0">
                            <i class="fas fa-bolt fa-2x text-primary"></i>
                        </div>
                        <div class="ms-3">
                            <h5 class="fw-bold mb-0">Rapidez y Eficiencia</h5>
                            <p class="text-muted mb-0">Encuentra ayuda en minutos y resuelve tus problemas el mismo día.</p>
                        </div>
                    </div>
                </div>

                <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-lg px-4">
                    Únete a nuestra comunidad
                </a>
            </div>
            
        </div>
    </div>
</section>

<jsp:include page="/WEB-INF/fragments/footer.jsp"/>