<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Conecta Hogar - Panel Técnico</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">    
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

        <%-- Ruta a tu archivo CSS externo para el técnico --%>
        <link href="${pageContext.request.contextPath}/static/css/panelTecnico.css" rel="stylesheet" type="text/css"/>

    </head>
    <body class="d-flex flex-column">

        <div id="panel-tecnico-page" class="d-flex flex-column flex-md-row">

            <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
                <h1 class="navbar-brand p-3">
                    <i class="bi bi-person-workspace"></i> Panel Técnico
                </h1>
                
                <ul class="navbar-nav flex-column"> 
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-section="inicio" id="nav-inicio">
                            <i class="bi bi-house-door-fill"></i> Inicio
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-section="trabajos" id="nav-trabajos">
                            <i class="bi bi-briefcase-fill"></i> Trabajos Disponibles
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-section="historial" id="nav-historial">
                            <i class="bi bi-clock-history"></i> Mis Trabajos Asignados
                        </a>
                    </li>
                    <li class="nav-item">
                        <form action="${pageContext.request.contextPath}/login" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="logout"/>
                            <button type="submit" class="nav-link nav-link-button">
                                <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                            </button>
                        </form>
                    </li>
                </ul>
            </nav>

            <main class="flex-grow-1 p-4" id="main">
                <c:set var="tecnicoActual" value="${sessionScope.usuario}"/>
                <c:if test="${empty tecnicoActual}">
                    <c:redirect url="${pageContext.request.contextPath}/login"/>
                </c:if>

                <%-- Mensajes de éxito/error --%>
                <c:if test="${not empty mensajeExito}">
                    <div class="alert alert-success mt-3 alert-dismissible fade show" role="alert">
                        <c:out value="${mensajeExito}"/>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <c:if test="${not empty mensajeError}">
                    <div class="alert alert-danger mt-3 alert-dismissible fade show" role="alert">
                        <c:out value="${mensajeError}"/>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <%-- Contenido que se mantiene fijo en la parte superior --%>
                <h2 class="mb-4 welcome-heading">Bienvenido, <c:out value="${tecnicoActual.nombre}"/> <c:out value="${tecnicoActual.apellido}"/> (Técnico)</h2>
                <p class="welcome-subheading">Correo: <c:out value="${tecnicoActual.correoElectronico}"/></p>

                <%-- Contenido Principal - Secciones con la clase content-section (estas cambian) --%>
                <%-- La sección 'inicio' es la que se muestra por defecto al cargar la página --%>
                <div id="inicio-section" class="content-section">
                    <div class="card p-3"> 
                        <h2><i class="bi bi-person-circle"></i> Datos de tu Perfil</h2>
                        <div class="mb-3">
                            <label>ID de Usuario:</label>
                            <p><c:out value="${tecnicoActual.id_Usuario}"/></p>
                        </div>
                        <div class="mb-3">
                            <label>Nombre Completo:</label>
                            <p><c:out value="${tecnicoActual.nombre}"/> <c:out value="${tecnicoActual.apellido}"/></p>
                        </div>
                        <div class="mb-3">
                            <label>Email:</label>
                            <p><c:out value="${tecnicoActual.correoElectronico}"/></p>
                        </div>
                        <div class="mb-3">
                            <label>Teléfono:</label>
                            <p><c:out value="${tecnicoActual.telefono != null && !tecnicoActual.telefono.isEmpty() ? tecnicoActual.telefono : 'N/A'}"/></p>
                        </div>
                        <div class="mb-3">
                            <label>Dirección:</label>
                            <p><c:out value="${tecnicoActual.direccion != null && !tecnicoActual.direccion.isEmpty() ? tecnicoActual.direccion : 'N/A'}"/></p>
                        </div>
                        <div class="mb-3">
                            <label>DNI:</label>
                            <p><c:out value="${tecnicoActual.dni != null && !tecnicoActual.dni.isEmpty() ? tecnicoActual.dni : 'N/A'}"/></p>
                        </div>
                        <div class="mb-3">
                            <label>Fecha de Registro:</label>
                            <p><fmt:formatDate value="${tecnicoActual.fechaRegistro}" pattern="dd/MM/yyyy HH:mm"/></p>
                        </div>
                    </div>
                </div>

                <div id="trabajos-section" class="content-section">
                    <h2><i class="bi bi-briefcase-fill"></i> Trabajos Disponibles</h2>
                    <p>Aquí verás los trabajos que puedes tomar.</p>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th><th>Servicio</th><th>Descripción</th><th>Precio Sugerido</th><th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="solicitud" items="${solicitudesPendientes}">
                                <tr>
                                    <td><c:out value="${solicitud.id}"/></td>
                                    <td><c:out value="${solicitud.servicio}"/></td>
                                    <td><c:out value="${solicitud.descripcion}"/></td>
                                    <td>S/. <fmt:formatNumber value="${solicitud.precioSugerido}" type="number" minFractionDigits="2"/></td>
                                    <td><button class="btn btn-primary">Aceptar</button></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty solicitudesPendientes}">
                                <tr><td colspan="5">No hay trabajos disponibles por el momento.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <div id="historial-section" class="content-section">
                    <h2><i class="bi bi-clock-history"></i> Mis Trabajos Asignados</h2>
                    <p>Lista de trabajos que has aceptado.</p>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th><th>Servicio</th><th>Descripción</th><th>Estado</th><th>Precio Final</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="solicitud" items="${solicitudesAsignadas}">
                                <tr>
                                    <td><c:out value="${solicitud.id}"/></td>
                                    <td><c:out value="${solicitud.servicio}"/></td>
                                    <td><c:out value="${solicitud.descripcion}"/></td>
                                    <td>
                                        <span class="badge 
                                            <c:choose>
                                                <c:when test="${solicitud.estado == 'PENDIENTE'}">bg-warning</c:when>
                                                <c:when test="${solicitud.estado == 'ACEPTADA'}">bg-primary</c:when>
                                                <c:when test="${solicitud.estado == 'FINALIZADO'}">bg-success</c:when>
                                                <c:when test="${solicitud.estado == 'CANCELADA'}">bg-danger</c:when>
                                                <c:otherwise>bg-secondary</c:otherwise>
                                            </c:choose>
                                        ">
                                            <c:out value="${solicitud.estado}"/>
                                        </span>
                                    </td>
                                    <td>S/. <fmt:formatNumber value="${solicitud.precioFinal}" type="number" minFractionDigits="2"/></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty solicitudesAsignadas}">
                                <tr><td colspan="5">No tienes trabajos asignados.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

            </main>
        </div>

        <%-- Scripts de Bootstrap (Popper.js es requerido por Bootstrap 5) --%>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gybF5kXkgL9x0+M5pL5dM2FvVXxePHJ6NfX4QgJv6h5UuUe2hD" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js" integrity="sha384-pzjw8f+ua7Kw1TIq0tp1vS8l0g4mB6vFbcF6cv6FjcLddc5ts9p6oAZf/3S6v9t8" crossorigin="anonymous"></script>
        
        <%-- ¡IMPORTANTE! Referencia a tu archivo JavaScript externo (panelTecnico.js) --%>
        <script src="${pageContext.request.contextPath}/static/js/panelTecnico.js" type="text/javascript"></script>

    </body>
</html>