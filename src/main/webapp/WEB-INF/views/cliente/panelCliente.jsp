<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ConectaHogar - Panel del Cliente</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="${pageContext.request.contextPath}/static/css/panel.css" rel="stylesheet">
</head>
<body>
    <c:set var="cliente" value="${sessionScope.usuario}"/>

    <div class="d-flex">
        <nav class="sidebar">
            <h1 class="sidebar-brand">
                <i class="bi bi-house-door-fill"></i> ConectaHogar
            </h1>
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active" href="#" data-section="inicio">
                        <i class="bi bi-person-circle"></i> Mi Perfil
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" data-section="contraofertas">
                        <i class="bi bi-cash-coin"></i> Contraofertas
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" data-section="misSolicitudes">
                        <i class="bi bi-list-task"></i> Mis Solicitudes
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" data-section="crearSolicitud">
                        <i class="bi bi-plus-circle"></i> Nueva Solicitud
                    </a>
                </li>
                <li class="nav-item mt-auto">
                    <form action="${pageContext.request.contextPath}/logout" method="post" class="d-grid">
                        <button type="submit" class="btn btn-warning">
                            <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                        </button>
                    </form>
                </li>
            </ul>
        </nav>

        <main class="main-content">
            <h2 class="mb-1">Bienvenido, <c:out value="${cliente.nombre}"/></h2>
            <p class="text-muted mb-4">Desde aquí puedes gestionar todas tus solicitudes de servicio.</p>

            <c:if test="${not empty sessionScope.mensajeExito}">
                <div class="alert alert-success">${sessionScope.mensajeExito}</div>
                <c:remove var="mensajeExito" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.mensajeError}">
                <div class="alert alert-danger">${sessionScope.mensajeError}</div>
                <c:remove var="mensajeError" scope="session"/>
            </c:if>
            
            <div id="inicio-section" class="content-section">
                <h3><i class="bi bi-person-vcard"></i> Datos de tu Perfil</h3>
                <div class="card card-body">
                    <p><strong>Nombre:</strong> <c:out value="${cliente.nombre} ${cliente.apellido}"/></p>
                    <p><strong>Correo:</strong> <c:out value="${cliente.correoElectronico}"/></p>
                    <p><strong>Teléfono:</strong> <c:out value="${cliente.telefono}"/></p>
                    <p><strong>Dirección:</strong> <c:out value="${cliente.direccion}"/></p>
                    <p><strong>DNI:</strong> <c:out value="${cliente.dni}"/></p>
                </div>
            </div>
            
            <div id="contraofertas-section" class="content-section d-none">
                <h3><i class="bi bi-cash-coin"></i> Contraofertas Recibidas</h3>
                <div class="card card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID</th><th>Servicio</th><th>Tu Precio</th><th>Precio del Técnico</th><th>Acción</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${contraofertas}" var="solicitud">
                                    <tr>
                                        <td>#<c:out value="${solicitud.id}"/></td>
                                        <td><c:out value="${solicitud.servicio}"/></td>
                                        <td>S/. <fmt:formatNumber value="${solicitud.precioSugerido}" type="number" minFractionDigits="2"/></td>
                                        <td><strong>S/. <fmt:formatNumber value="${solicitud.precioFinal}" type="number" minFractionDigits="2"/></strong></td>
                                        <td>
                                            <div class="d-flex gap-2">
                                                <form action="${pageContext.request.contextPath}/aceptarContraoferta" method="post" class="m-0">
                                                    <input type="hidden" name="idSolicitud" value="${solicitud.id}">
                                                    <button type="submit" class="btn btn-primary btn-sm">Aceptar</button>
                                                </form>
                                                <form action="${pageContext.request.contextPath}/rechazarContraoferta" method="post" class="m-0">
                                                    <input type="hidden" name="idSolicitud" value="${solicitud.id}">
                                                    <button type="submit" class="btn btn-danger btn-sm">Rechazar</button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty contraofertas}">
                                    <tr><td colspan="5" class="text-center text-muted">No tienes contraofertas pendientes.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div id="crearSolicitud-section" class="content-section d-none">
                <h3><i class="bi bi-plus-circle"></i> Crear Nueva Solicitud</h3>
                <div class="card card-body">
                    <form action="${pageContext.request.contextPath}/panelCliente" method="post">
                        <input type="hidden" name="accion" value="crearSolicitud">
                        <div class="mb-3">
                            <label for="servicio" class="form-label">Tipo de Servicio:</label>
                            <select class="form-select" id="servicio" name="servicio" required>
                                <option value="" disabled selected>Selecciona un servicio...</option>
                                <c:forEach var="s" items="${serviciosDisponibles}">
                                    <option value="${s.name()}"><c:out value="${s}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="descripcion" class="form-label">Describe tu problema:</label>
                            <textarea class="form-control" id="descripcion" name="descripcion" rows="4" required placeholder="Ej: Fuga de agua en el lavadero de la cocina."></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="precioSugerido" class="form-label">Presupuesto Sugerido (S/.):</label>
                            <input type="number" class="form-control" id="precioSugerido" name="precioSugerido" step="0.01" min="0" required placeholder="Ej: 50.00">
                        </div>
                        <button type="submit" class="btn btn-primary"><i class="bi bi-send-fill"></i> Enviar Solicitud</button>
                    </form>
                </div>
            </div>

            <div id="misSolicitudes-section" class="content-section d-none">
                <h3><i class="bi bi-list-task"></i> Historial de Solicitudes</h3>
                <div class="card card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID</th><th>Servicio</th><th>Descripción</th><th>Fecha</th><th>Estado</th><th>Técnico Asignado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${solicitudes}" var="solicitud">
                                    <tr>
                                        <td>#<c:out value="${solicitud.id}"/></td>
                                        <td><c:out value="${solicitud.servicio}"/></td>
                                        <td><c:out value="${solicitud.descripcion}"/></td>
                                        <td><fmt:formatDate value="${solicitud.fechaCreacion}" pattern="dd/MM/yy"/></td>
                                        <td>
                                            <span class="badge rounded-pill 
                                                <c:choose>
                                                    <c:when test="${solicitud.estado == 'PENDIENTE'}">bg-warning text-dark</c:when>
                                                    <c:when test="${solicitud.estado == 'ASIGNADA' || solicitud.estado == 'CONTRAOFERTA'}">bg-primary</c:when>
                                                    <c:when test="${solicitud.estado == 'COMPLETADA'}">bg-secondary</c:when>
                                                    <c:when test="${solicitud.estado == 'CANCELADA'}">bg-danger</c:when>
                                                    <c:otherwise>bg-dark</c:otherwise>
                                                </c:choose>">
                                                <c:out value="${solicitud.estado}"/>
                                            </span>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty solicitud.nombreTecnico}">
                                                    <i class="bi bi-person-check-fill"></i> <c:out value="${solicitud.nombreTecnico}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">--</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty solicitudes}">
                                    <tr><td colspan="6" class="text-center text-muted">Aún no has creado ninguna solicitud.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/panelCliente.js"></script>
</body>
</html>