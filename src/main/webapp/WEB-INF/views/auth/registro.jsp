<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ConectaHogar - Registro</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>
    <div class="container">
        <div class="form-box register">
            <form action="${pageContext.request.contextPath}/registro" method="POST" id="registerForm">
                <h1>Registro</h1>
                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                
                <div class="input-box">
                    <input type="text" name="nombre" placeholder="Nombre" required>
                    <i class="bx bxs-user"></i>
                </div>
                <div class="input-box">
                    <input type="text" name="apellido" placeholder="Apellido" required>
                    <i class="bx bxs-user"></i>
                </div>
                <div class="input-box">
                    <input type="email" name="email" placeholder="Correo Electrónico" required>
                    <i class="bx bx-envelope"></i>
                </div>
                <div class="input-box">
                    <input type="password" name="password" placeholder="Contraseña" required>
                    <i class="bx bxs-lock-alt"></i>
                </div>
                <div class="input-box">
                    <input type="tel" name="telefono" placeholder="Teléfono (+51)" 
                           pattern="^\+51\d{9}$" 
                           title="Ingrese un número peruano válido (+51 seguido de 9 dígitos)" required>
                    <i class="bx bxs-phone"></i>
                </div>
                <div class="input-box">
                    <input type="text" name="dni" placeholder="DNI" pattern="[0-9]{8}" 
                           title="Ingrese un DNI válido (8 dígitos)" required>
                    <i class="bx bxs-id-card"></i>
                </div>
                
                <div class="input-box">
                    <select name="tipoUsuario" required>
                        <option value="" disabled selected>Seleccione su tipo de usuario</option>
                        <option value="cliente">Cliente</option>
                        <option value="tecnico">Técnico</option>
                    </select>
                    <i class="bx bxs-user-detail"></i>
                </div>
                
                <div class="forgot-link">
                    <input type="checkbox" id="terminos" name="terminos" required>
                    <label for="terminos">
                        Acepto los <a href="#" class="modal-trigger">términos y condiciones</a>
                    </label>
                </div>

                <button type="submit" class="btn">Registrarse</button>
                <p>¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/login">Inicia sesión</a></p>
            </form>
        </div>
    </div>

    <!-- Modal para términos y condiciones -->
    <div id="termsModal" class="modal">
        <div class="modal-content">
            <h2>Términos y Condiciones</h2>
            <p>Aquí van los términos y condiciones de ConectaHogar...</p>
            <button class="btn modal-close">Aceptar</button>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/auth.js"></script>
</body>
</html>
