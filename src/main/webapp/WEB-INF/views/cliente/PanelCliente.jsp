<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conecta Hogar - Panel Cliente</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <c:set var="clienteActual" value="${sessionScope.clienteActual}"/>
    <c:if test="${empty clienteActual}">
        <c:redirect url="login.jsp"/>
    </c:if>

    <h1>Bienvenido, ${clienteActual.nombre} ${clienteActual.apellido} (Cliente)</h1>
    <p>Correo: ${clienteActual.correoElectronico}</p>
    <p>Dirección: ${clienteActual.direccion}</p>
    <p><a href="logout.jsp">Cerrar Sesión</a></p>

    <hr>

    <h2>Crear Nueva Solicitud de Trabajo</h2>
    <form action="PanelClienteServlet" method="post">
        <input type="hidden" name="accion" value="crearSolicitud">

        <label for="descripcion">Descripción del Problema:</label><br>
        <textarea id="descripcion" name="descripcion" rows="4" cols="50" required></textarea><br><br>

        <label for="servicio">Tipo de Servicio:</label><br>
        <select id="servicio" name="servicio" required>
            <option value="">Seleccione un servicio</option>
            <c:forEach var="servicio" items="${requestScope.serviciosDisponibles}">
                <option value="${servicio}">${servicio}</option>
            </c:forEach>
        </select><br><br>

        <label for="precioSugerido">Precio Sugerido (S/.):</label><br>
        <input type="number" id="precioSugerido" name="precioSugerido" step="0.01" min="0" required><br><br>

        <input type="submit" value="Enviar Solicitud">
    </form>

    <c:if test="${not empty requestScope.mensajeExito}">
        <p style="color: green;">${requestScope.mensajeExito}</p>
    </c:if>
    <c:if test="${not empty requestScope.mensajeError}">
        <p style="color: red;">${requestScope.mensajeError}</p>
    </c:if>

    <hr>

    <h2>Mis Solicitudes de Trabajo</h2>
    <c:if test="${empty requestScope.solicitudes}">
        <p>No tienes solicitudes de trabajo aún.</p>
    </c:if>
    <c:if test="${not empty requestScope.solicitudes}">
        <table border="1">
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
                <c:forEach var="solicitud" items="${requestScope.solicitudes}">
                    <tr>
                        <td>${solicitud.id}</td>
                        <td>${solicitud.servicio}</td>
                        <td>${solicitud.descripcion}</td>
                        <td>S/. ${solicitud.precioSugerido}</td>
                        <td>
                            <c:choose>
                                <c:when test="${solicitud.precioFinal != null}">
                                    S/. ${solicitud.precioFinal}
                                </c:when>
                                <c:otherwise>
                                    N/A
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${solicitud.estado}</td>
                        <td>${solicitud.fechaCreacion}</td>
                        <td>
                            <c:choose>
                                <c:when test="${solicitud.fechaFinalizacion != null}">
                                    ${solicitud.fechaFinalizacion}
                                </c:when>
                                <c:otherwise>
                                    Pendiente
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${solicitud.idTecnico != null}">
                                    ID Técnico: ${solicitud.idTecnico} 
                                    </c:when>
                                <c:otherwise>
                                    No asignado
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>