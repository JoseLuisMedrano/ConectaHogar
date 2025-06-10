<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Cliente - ConectaHogar</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/panelTecnico.css">
    
</head>
<body id="panel-cliente-page" class="${sessionScope.tema}">

  <nav>
    <h1><i class="fas fa-user-hard-hat"></i> Panel Trabajador - <%= trabajadorNombre %></h1>
    <ul>
      <li><a onclick="mostrarContenido('inicio')" href="javascript:void(0)"><i class="fas fa-home"></i> Inicio</a></li>
      <li><a onclick="mostrarContenido('trabajos')" href="javascript:void(0)"><i class="fas fa-briefcase"></i> Trabajos Disponibles</a></li>
      <li><a onclick="mostrarContenido('historial')" href="javascript:void(0)"><i class="fas fa-history"></i> Historial</a></li>
      <li><a onclick="mostrarContenido('perfil')" href="javascript:void(0)"><i class="fas fa-user-edit"></i> Mi Perfil</a></li>
      <li><a onclick="mostrarContenido('notificaciones')" href="javascript:void(0)"><i class="fas fa-bell"></i> Notificaciones</a></li>
    </ul>
    
    <div class="theme-selector">
      <p style="color: white; margin-bottom: 0.5rem;">Tema:</p>
      <div class="theme-options">
        <div class="theme-btn theme-default" onclick="cambiarTema('default')" title="Tema claro"></div>
        <div class="theme-btn theme-verde" onclick="cambiarTema('verde')" title="Tema verde"></div>
        <div class="theme-btn theme-azul" onclick="cambiarTema('azul')" title="Tema azul"></div>
        <div class="theme-btn theme-oscuro" onclick="cambiarTema('oscuro')" title="Tema oscuro"></div>
      </div>
    </div>
    
    <form action="${pageContext.request.contextPath}/logout" method="post" style="margin-top:1rem;">
      <button class="logout-btn" type="submit">
        <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
      </button>
    </form>
  </nav>

  <main id="main">
    <!-- Aquí se cargará contenido dinámico -->
  </main>

  <!-- CONTENIDOS OCULTOS PARA CARGAR DINÁMICAMENTE -->
  <div id="inicio-content" style="display:none;">
    <h2>Bienvenido, <%= trabajadorNombre %>!</h2>
    <p>Este es el panel de inicio para trabajadores.</p>
    <!-- Más contenido que quieras mostrar en inicio -->
  </div>

  <div id="trabajos-content" style="display:none;">
    <h2>Trabajos Disponibles</h2>
    <p>Aquí verás los trabajos que puedes tomar.</p>
    <!-- Ejemplo de listado de trabajos -->
    <ul>
      <li>Trabajo 1: Reparación eléctrica</li>
      <li>Trabajo 2: Instalación de redes</li>
      <li>Trabajo 3: Mantenimiento general</li>
    </ul>
  </div>

  <div id="historial-content" style="display:none;">
    <h2>Historial de Trabajos</h2>
    <p>Lista de trabajos realizados anteriormente.</p>
    <!-- Ejemplo: tabla o listado -->
    <table>
      <thead>
        <tr><th>Trabajo</th><th>Fecha</th><th>Estado</th></tr>
      </thead>
      <tbody>
        <tr><td>Reparación eléctrica</td><td>01/06/2025</td><td>Completado</td></tr>
        <tr><td>Instalación de redes</td><td>15/05/2025</td><td>Completado</td></tr>
      </tbody>
    </table>
  </div>

  <div id="perfil-content" style="display:none;">
    <h2>Mi Perfil</h2>
    <form id="formPerfil">
      <label for="nombre">Nombre:</label>
      <input type="text" id="nombre" name="nombre" value="<%= trabajadorNombre %>" required />
      
      <label for="telefono">Teléfono:</label>
      <input type="tel" id="telefono" name="telefono" placeholder="Tu teléfono" />
      
      <label for="email">Correo Electrónico:</label>
      <input type="email" id="email" name="email" placeholder="tu@email.com" />
      
      <button type="submit">Guardar Perfil</button>
    </form>
    <div id="mensajePerfil"></div>
  </div>

  <div id="notificaciones-content" style="display:none;">
    <h2>Notificaciones</h2>
    <p>No tienes nuevas notificaciones.</p>
  </div>

  <script src="${pageContext.request.contextPath}/js/panelTecnico.js"></script>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
      mostrarContenido('inicio');
    });
  </script>
</body>
</html>
