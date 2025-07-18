<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>ConectaHogar - Panel del Técnico</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link href="${pageContext.request.contextPath}/static/css/panel.css" rel="stylesheet">
    </head>
    <body>
        <c:set var="tecnico" value="${sessionScope.usuario}"/>

        <div class="d-flex">
            <nav class="sidebar">
                <h1 class="sidebar-brand"><i class="bi bi-tools"></i> ConectaHogar</h1>
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" href="#" data-section="inicio">
                            <i class="bi bi-person-circle"></i> Mi Perfil
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-section="trabajosDisponibles">
                            <i class="bi bi-briefcase-fill"></i> Trabajos Disponibles
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-section="misTrabajos">
                            <i class="bi bi-list-check"></i> Mis Trabajos
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
                <h2 class="mb-1">Panel de Técnico: <c:out value="${tecnico.nombre}"/></h2>
                <p class="text-muted mb-4">Gestiona tu perfil y los trabajos disponibles.</p>

                <c:if test="${not empty sessionScope.mensajeExito}"><div class="alert alert-success">${sessionScope.mensajeExito}</div><c:remove var="mensajeExito" scope="session"/></c:if>
                <c:if test="${not empty sessionScope.mensajeError}"><div class="alert alert-danger">${sessionScope.mensajeError}</div><c:remove var="mensajeError" scope="session"/></c:if>
                <c:if test="${not empty requestScope.mensajeAdvertencia}"><div class="alert alert-warning">${requestScope.mensajeAdvertencia}</div></c:if>

                    <div id="inicio-section" class="content-section">
                        <h3><i class="bi bi-person-vcard"></i> Mi Perfil Profesional</h3>
                        <div id="perfil-display" class="card card-body mb-4">
                            <h5 class="card-title">Información de Contacto</h5>
                            <p><strong>Nombre:</strong> <c:out value="${tecnico.nombre} ${tecnico.apellido}"/></p>
                        <p><strong>Correo:</strong> <c:out value="${tecnico.correoElectronico}"/></p>
                        <p><strong>Teléfono:</strong> <c:out value="${tecnico.telefono}"/></p>
                        <hr>
                        <h5 class="card-title mt-2">Información Profesional</h5>
                        <p><strong>Especialidad:</strong> <c:out value="${tecnico.especialidad}" default="No definida"/></p>
                        <p><strong>Disponibilidad:</strong> <c:out value="${tecnico.disponibilidad}" default="No definida"/></p>
                        <button id="btn-editar-perfil" class="btn btn-secondary mt-3 align-self-start"><i class="bi bi-pencil-square"></i> Editar Perfil</button>
                    </div>
                    <div id="perfil-edit-form" class="card card-body d-none">
                        <h5 class="card-title">Actualiza tu Estado</h5>
                        <form action="${pageContext.request.contextPath}/panelTecnico" method="post" onsubmit="return confirm('¿Estás seguro de que deseas guardar los cambios?');">
                            <input type="hidden" name="accion" value="actualizarPerfil">
                            <div class="mb-3">
                                <label for="especialidad" class="form-label">Mi Especialidad:</label>
                                <select class="form-select" id="especialidad" name="especialidad" required>
                                    <option value="">-- Selecciona una especialidad --</option>
                                    <c:forEach var="servicio" items="${serviciosDisponibles}">
                                        <option value="${servicio.name()}" ${tecnico.especialidad == servicio.name() ? 'selected' : ''}><c:out value="${servicio}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="disponibilidad" class="form-label">Mi Disponibilidad:</label>
                                <select class="form-select" id="disponibilidad" name="disponibilidad" required>
                                    <option value="Disponible" ${tecnico.disponibilidad == 'Disponible' ? 'selected' : ''}>Disponible</option>
                                    <option value="No Disponible" ${tecnico.disponibilidad == 'No Disponible' ? 'selected' : ''}>No Disponible</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary"><i class="bi bi-check-circle"></i> Guardar Cambios</button>
                            <button type="button" id="btn-cancelar-edicion" class="btn btn-light ms-2">Cancelar</button>
                        </form>
                    </div>
                </div>

                <div id="trabajosDisponibles-section" class="content-section d-none">
                    <h3><i class="bi bi-briefcase-fill"></i> Trabajos Disponibles para Cotizar</h3>
                    <div class="card card-body">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Servicio</th>
                                        <th scope="col">Descripción del Cliente</th>
                                        <th scope="col" style="min-width: 240px;">Enviar Mi Oferta</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${solicitudesPendientes}" var="solicitud">
                                        <tr>
                                            <td>#<c:out value="${solicitud.id}"/></td>
                                            <td><c:out value="${solicitud.servicio}"/></td>
                                            <td><c:out value="${solicitud.descripcion}"/></td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/contraoferta" method="post" class="m-0">
                                                    <input type="hidden" name="idSolicitud" value="${solicitud.id}">
                                                    <div class="input-group input-group-sm">
                                                        <span class="input-group-text">S/.</span>
                                                        <input type="number" name="nuevoPrecio" class="form-control" placeholder="Tu cotización" required step="0.01" min="0">
                                                        <button type="submit" class="btn btn-primary">Enviar Oferta</button>
                                                    </div>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty solicitudesPendientes}">
                                        <tr><td colspan="4" class="text-center text-muted">No hay trabajos nuevos por el momento.</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div id="misTrabajos-section" class="content-section d-none">
                    <h3><i class="bi bi-list-check"></i> Mis Trabajos Asignados</h3>
                    <div class="card card-body">
                        <div class="table-responsive">
                            <table class="table table-hover align-middle">
                                <thead>
                                    <tr><th>ID</th><th>Servicio</th><th>Descripción</th><th>Estado</th><th>Precio Acordado</th></tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${solicitudesAsignadas}" var="solicitud">
                                        <tr>
                                            <td>#<c:out value="${solicitud.id}"/></td>
                                            <td><c:out value="${solicitud.servicio}"/></td>
                                            <td><c:out value="${solicitud.descripcion}"/></td>
                                            <td><span class="badge rounded-pill bg-info"><c:out value="${solicitud.estado}"/></span></td>
                                            <td>S/. <fmt:formatNumber value="${solicitud.precioFinal}" type="number" minFractionDigits="2"/></td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty solicitudesAsignadas}">
                                        <tr><td colspan="5" class="text-center text-muted">Aún no tienes trabajos asignados.</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </main>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/panelTecnico.js"></script>
    </body>
</html>