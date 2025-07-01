<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>ConectaHogar - Panel Técnico</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link href="${pageContext.request.contextPath}/static/css/panelTecnico.css" rel="stylesheet">
    </head>
    <body>

        <div id="panel-tecnico-page">
            <nav class="sidebar">
                <h1 class="navbar-brand"><i class="bi bi-person-workspace"></i> Panel Técnico</h1>
                <ul class="nav flex-column">
                    <li class="nav-item"><a class="nav-link" href="#" data-section="inicio"><i class="bi bi-person-circle"></i> Mi Perfil</a></li>
                    <li class="nav-item"><a class="nav-link" href="#" data-section="trabajos"><i class="bi bi-briefcase-fill"></i> Trabajos Disponibles</a></li>
                    <li class="nav-item"><a class="nav-link" href="#" data-section="historial"><i class="bi bi-clock-history"></i> Mis Trabajos</a></li>
                    <li class="nav-item mt-auto">
                        <form action="${pageContext.request.contextPath}/logout" method="post">
                            <button type="submit" class="nav-link nav-link-button">
                                <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                            </button>
                        </form>
                    </li>
                </ul>
            </nav>

            <main class="main-content">
                <h2 class="mb-1">Bienvenido, <c:out value="${sessionScope.usuario.nombre}"/></h2>
                <p class="text-muted mb-4">Revisa las solicitudes de servicio disponibles y gestiona tus trabajos.</p>

                <c:if test="${not empty mensajeExito}"><div class="alert alert-success">${mensajeExito}</div></c:if>
                <c:if test="${not empty mensajeError}"><div class="alert alert-danger">${mensajeError}</div></c:if>

                    <div id="inicio-section" class="content-section"> ... </div>

                    <div id="trabajos-section" class="content-section">
                        <h3><i class="bi bi-briefcase-fill"></i> Trabajos Disponibles en tu Especialidad</h3>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead><tr><th>ID</th><th>Servicio</th><th>Descripción</th><th>Presupuesto</th><th>Acción</th></tr></thead>
                                <tbody>
                                <c:forEach var="solicitud" items="${solicitudesPendientes}">
                                    <tr>
                                        <td>#<c:out value="${solicitud.id}"/></td>
                                        <td><c:out value="${solicitud.servicio.name()}"/></td>
                                        <td><c:out value="${solicitud.descripcion}"/></td>
                                        <td>S/. <fmt:formatNumber value="${solicitud.precioSugerido}" type="number" minFractionDigits="2"/></td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/aceptar-solicitud" method="post">
                                                <input type="hidden" name="idSolicitud" value="${solicitud.id}"/>
                                                <input type="hidden" name="precioSugerido" value="${solicitud.precioSugerido}"/>
                                                <button type="submit" class="btn btn-sm btn-success">Aceptar</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty solicitudesPendientes}">
                                    <tr><td colspan="5" class="text-center text-muted">No hay trabajos disponibles en tu especialidad por el momento.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div id="historial-section" class="content-section">
                    <h3><i class="bi bi-clock-history"></i> Mis Trabajos Asignados</h3>
                    <div class="table-responsive">
                        <table class="table table-hover">
                            <thead><tr><th>ID</th><th>Servicio</th><th>Descripción</th><th>Precio Final</th><th>Estado</th></tr></thead>
                            <tbody>
                                <c:forEach var="solicitud" items="${solicitudesAsignadas}">
                                    <tr>
                                        <td>#<c:out value="${solicitud.id}"/></td>
                                        <td><c:out value="${solicitud.servicio.name()}"/></td>
                                        <td><c:out value="${solicitud.descripcion}"/></td>
                                        <td>S/. <fmt:formatNumber value="${solicitud.precioFinal}" type="number" minFractionDigits="2"/></td>
                                        <td>
                                            <span class="badge rounded-pill
                                                  <c:choose>
                                                      <c:when test='${solicitud.estado == "ACEPTADA"}'>bg-primary</c:when>
                                                      <c:when test='${solicitud.estado == "EN_PROCESO"}'>bg-info text-dark</c:when>
                                                      <c:when test='${solicitud.estado == "COMPLETADA"}'>bg-success</c:when>
                                                  </c:choose>">
                                                <c:out value="${solicitud.estado.name()}"/>
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty solicitudesAsignadas}">
                                    <tr><td colspan="5" class="text-center text-muted">Aún no has aceptado ningún trabajo.</td></tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </main>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/panelTecnico.js"></script>
    </body>
</html>