<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Técnico - ConectaHogar</title>
    <!-- Vincula Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

    <!-- Vincula tu archivo CSS personalizado -->
    <link href="${pageContext.request.contextPath}/static/css/panelTecnico.css" rel="stylesheet" type="text/css"/>
</head>
<body>
  <div id="panel-tecnico-page">
    <nav>
      <h1><i class="fas fa-user-hard-hat"></i> Panel Técnico</h1>
      <p>Bienvenido, <c:out value="${tecnico.nombre}"/></p>
      <ul>
        <li><a onclick="mostrarContenido('trabajos')" href="javascript:void(0)"><i class="fas fa-briefcase"></i> Trabajos Disponibles</a></li>
        <li><a onclick="mostrarContenido('historial')" href="javascript:void(0)"><i class="fas fa-history"></i> Mis Trabajos</a></li>
      </ul>
      <a href="${pageContext.request.contextPath}/login" style="color: #ffc107;">Cerrar Sesión</a>
    </nav>

    <main>
      <div id="trabajos-content">
        <h2><i class="fas fa-briefcase"></i> Trabajos Disponibles</h2>
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

      <div id="historial-content" style="display:none;">
        <h2><i class="fas fa-history"></i> Mis Trabajos Asignados</h2>
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
                        <td><c:out value="${solicitud.estado}"/></td>
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

  <script>
    function mostrarContenido(id) {
      document.getElementById('trabajos-content').style.display = 'none';
      document.getElementById('historial-content').style.display = 'none';
      document.getElementById(id + '-content').style.display = 'block';
    }
    // Mostrar la primera pestaña por defecto al cargar la página
    document.addEventListener('DOMContentLoaded', function() {
      mostrarContenido('trabajos');
    });
  </script>
</body>
</html>