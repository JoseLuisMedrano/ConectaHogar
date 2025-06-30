<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Conecta Hogar - Panel Cliente</title>

    <!-- Vincula Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

    <!-- Vincula tu archivo CSS personalizado -->
    <link href="${pageContext.request.contextPath}/static/css/panelCliente.css" rel="stylesheet" type="text/css"/>
</head>
<body class="d-flex flex-column">

    <!-- Panel de Cliente -->
    <div id="panel-cliente-page" class="d-flex flex-column flex-md-row">

        <!-- Barra de navegación -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm" style="width: 300px; position: fixed; top: 0; left: 0; bottom: 0; z-index: 10;">
            <h1 class="navbar-brand">
                <i class="fas fa-home"></i> Panel de Cliente
            </h1>
            <ul class="navbar-nav flex-column">
                <li class="nav-item">
                    <a class="nav-link" href="#">Inicio</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Mis Solicitudes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Crear Nueva Solicitud</a>
                </li>
            </ul>
        </nav>

        <!-- Contenido Principal -->
        <main class="flex-grow-1 p-4" style="margin-left: 300px; overflow-y: auto; background-color: #f9f9f9;">
            <c:set var="clienteActual" value="${sessionScope.usuario}"/>
            <c:if test="${empty clienteActual}">
                <c:redirect url="/login"/>
            </c:if>

            <h1>Bienvenido, <c:out value="${clienteActual.nombre}"/> <c:out value="${clienteActual.apellido}"/> (Cliente)</h1>
            <p>Correo: <c:out value="${clienteActual.correoElectronico}"/></p>
            <p><a href="${pageContext.request.contextPath}/login" class="btn btn-danger">Cerrar Sesión</a></p>

            <hr>

            <!-- Crear Nueva Solicitud -->
            <h2>Crear Nueva Solicitud de Trabajo</h2>
            <form action="${pageContext.request.contextPath}/panelCliente" method="post" class="form">
                <input type="hidden" name="accion" value="crearSolicitud">

                <div class="form-group">
                    <label for="descripcion">Descripción del Problema:</label>
                    <textarea id="descripcion" name="descripcion" rows="4" class="form-control" required></textarea>
                </div>

                <div class="form-group">
                    <label for="servicio">Tipo de Servicio:</label>
                    <select id="servicio" name="servicio" class="form-control" required>
                        <option value="">Seleccione un servicio</option>
                        <c:forEach var="servicio" items="${serviciosDisponibles}">
                            <option value="${servicio}"><c:out value="${servicio}"/></option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="precioSugerido">Precio Sugerido (S/.):</label>
                    <input type="number" id="precioSugerido" name="precioSugerido" step="0.01" min="0" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-primary mt-3">Enviar Solicitud</button>
            </form>

            <c:if test="${not empty mensajeExito}">
                <p class="success mt-3"><c:out value="${mensajeExito}"/></p>
            </c:if>
            <c:if test="${not empty mensajeError}">
                <p class="error mt-3"><c:out value="${mensajeError}"/></p>
            </c:if>

            <hr>

            <!-- Mis Solicitudes -->
            <h2>Mis Solicitudes de Trabajo</h2>
            <c:if test="${empty solicitudes}">
                <p>No tienes solicitudes de trabajo aún.</p>
            </c:if>
            <c:if test="${not empty solicitudes}">
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
                                <td><c:out value="${solicitud.estado}"/></td>
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
            </c:if>
        </main>
    </div>

    <!-- Agregar Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gybF5kXkgL9x0+M5pL5dM2FvVXxePHJ6NfX4QgJv6h5UuUe2hD" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js" integrity="sha384-pzjw8f+ua7Kw1TIq0tp1vS8l0g4mB6vFbcF6cv6FjcLddc5ts9p6oAZf/3S6v9t8" crossorigin="anonymous"></script>
</body>
</html>
