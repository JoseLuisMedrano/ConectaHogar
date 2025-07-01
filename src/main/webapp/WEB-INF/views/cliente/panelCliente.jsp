<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Conecta Hogar - Panel Cliente</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">    
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

        <link href="${pageContext.request.contextPath}/static/css/panelCliente.css" rel="stylesheet" type="text/css"/>

    </head>
    <body class="d-flex flex-column">

        <div id="panel-cliente-page" class="d-flex flex-column flex-md-row">

            <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
                <h1 class="navbar-brand p-3">
                    <i class="bi bi-house-door-fill"></i> PanelCliente
                </h1>
                <ul class="navbar-nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-section="inicio" id="nav-inicio">
                            <i class="bi bi-person-circle"></i> Inicio
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-section="misSolicitudes" id="nav-misSolicitudes">
                            <i class="bi bi-list-task"></i> Mis Solicitudes
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#" data-section="crearSolicitud" id="nav-crearSolicitud">
                            <i class="bi bi-plus-circle"></i> Crear Nueva Solicitud
                        </a>
                    </li>
                    <li class="nav-item">
                        <form action="${pageContext.request.contextPath}/panelCliente" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="logout"/>
                            <button type="submit" class="nav-link nav-link-button">
                                <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                            </button>
                        </form>
                    </li>
                </ul>
            </nav>

            <main class="flex-grow-1 p-4">
                <c:set var="clienteActual" value="${sessionScope.usuario}"/>
                <c:if test="${empty clienteActual}">
                    <c:redirect url="${pageContext.request.contextPath}/login"/>
                </c:if>

                <h1 class="mb-4 welcome-heading">Bienvenido, <c:out value="${clienteActual.nombre}"/> <c:out value="${clienteActual.apellido}"/> (Cliente)</h1>
                <p class="welcome-subheading">Correo: <c:out value="${clienteActual.correoElectronico}"/></p>

                <c:if test="${not empty mensajeExito}">
                    <div class="alert alert-success mt-3" role="alert">
                        <c:out value="${mensajeExito}"/>
                    </div>
                </c:if>
                <c:if test="${not empty mensajeError}">
                    <div class="alert alert-danger mt-3" role="alert">
                        <c:out value="${mensajeError}"/>
                    </div>
                </c:if>

                <hr>

                <div id="inicio-section" class="content-section">
                    <h2><i class="bi bi-person-circle"></i> Datos de tu Perfil</h2>
                    <div class="card p-3">
                        <p><strong>ID de Usuario:</strong> <c:out value="${clienteActual.id_Usuario}"/></p>
                        <p><strong>Nombre Completo:</strong> <c:out value="${clienteActual.nombre}"/> <c:out value="${clienteActual.apellido}"/></p>
                        <p><strong>Email:</strong> <c:out value="${clienteActual.correoElectronico}"/></p>
                        <p><strong>Teléfono:</strong> <c:out value="${clienteActual.telefono}"/></p>
                        <p><strong>Dirección:</strong> <c:out value="${clienteActual.direccion}"/></p>
                        <p><strong>DNI:</strong> <c:out value="${clienteActual.dni}"/></p>
                        <p><strong>Fecha de Registro:</strong> <fmt:formatDate pattern="yyyy-MM-dd" value="${clienteActual.fechaRegistro}" /></p>
                    </div>
                </div>

                <div id="crearSolicitud-section" class="content-section">
                    <h2><i class="bi bi-plus-circle"></i> Crear Nueva Solicitud</h2>
                    <form action="${pageContext.request.contextPath}/crearSolicitudServlet" method="post">
                        <div class="mb-3">
                            <label for="tipoEspecialista" class="form-label">Tipo de Especialista:</label>
                            <select class="form-select" id="tipoEspecialista" name="tipoEspecialista">
                                <option value="">Seleccione un especialista</option>
                                <option value="electricista">Electricista</option>
                                <option value="gasfitero">Gasfitero</option>
                                <option value="jardinero">Jardinero</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="tipoServicioEspecifico" class="form-label">Tipo de Servicio Específico:</label>
                            <select class="form-select" id="tipoServicioEspecifico" name="tipoServicioEspecifico">
                                <option value="">Seleccione un tipo de servicio</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="descripcionSolicitud" class="form-label">Descripción Detallada:</label>
                            <textarea class="form-control" id="descripcionSolicitud" name="descripcionSolicitud" rows="3" required></textarea>
                        </div>

                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-send-fill"></i> Enviar Solicitud
                        </button>
                    </form>
                </div>

                <div id="misSolicitudes-section" class="content-section">
                    <h2><i class="bi bi-list-task"></i> Mis Solicitudes de Trabajo</h2>
                    <c:if test="${empty solicitudes}">
                        <p class="alert alert-info">No tienes solicitudes de trabajo aún. ¡Haz clic en "Crear Nueva Solicitud" para empezar!</p>
                    </c:if>
                    <c:if test="${not empty solicitudes}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped mt-3">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Servicio</th>
                                        <th>Descripción</th>
                                        <th>Precio Sugerido</th>
                                        <th>Precio Final</th>
                                        <th>Estado</th>
                                        <th>Fecha Creación</th>
                                        <th>Fecha Finalización</th>
                                        <th>Técnico Asignado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="solicitud" items="${solicitudes}">
                                        <tr>
                                            <td><c:out value="${solicitud.id}"/></td>
                                            <td><c:out value="${solicitud.servicio}"/></td>
                                            <td><c:out value="${solicitud.descripcion}"/></td>
                                            <td>S/. <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${solicitud.precioSugerido}"/></td>
                                            <td>
                                                <c:if test="${solicitud.precioFinal != null}">
                                                    S/. <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${solicitud.precioFinal}"/>
                                                </c:if>
                                                <c:if test="${solicitud.precioFinal == null}">N/A</c:if>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${solicitud.estado eq 'PENDIENTE'}"><span class="badge bg-warning text-dark"><c:out value="${solicitud.estado}"/></span></c:when>
                                                    <c:when test="${solicitud.estado eq 'ACEPTADA'}"><span class="badge bg-primary"><c:out value="${solicitud.estado}"/></span></c:when>
                                                    <c:when test="${solicitud.estado eq 'FINALIZADO'}"><span class="badge bg-success"><c:out value="${solicitud.estado}"/></span></c:when>
                                                    <c:when test="${solicitud.estado eq 'CANCELADA'}"><span class="badge bg-danger"><c:out value="${solicitud.estado}"/></span></c:when>
                                                    <c:otherwise><c:out value="${solicitud.estado}"/></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${solicitud.fechaCreacion}" /></td>
                                            <td>
                                                <c:if test="${solicitud.fechaFinalizacion != null}">
                                                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${solicitud.fechaFinalizacion}" />
                                                </c:if>
                                                <c:if test="${solicitud.fechaFinalizacion == null}">Pendiente</c:if>
                                            </td>
                                            <td>
                                                <c:if test="${solicitud.idTecnico != null}">
                                                    ID Técnico: <c:out value="${solicitud.idTecnico}"/>
                                                </c:if>
                                                <c:if test="${solicitud.idTecnico == null}">No asignado</c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>

            </main>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gybF5kXkgL9x0+M5pL5dM2FvVXxePHJ6NfX4QgJv6h5UuUe2hD" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js" integrity="sha384-pzjw8f+ua7Kw1TIq0tp1vS8l0g4mB6vFbcF6cv6FjcLddc5ts9p6oAZf/3S6v9t8" crossorigin="anonymous"></script>

        <script src="${pageContext.request.contextPath}/static/js/panelCliente.js"></script>
    </body>
</html>