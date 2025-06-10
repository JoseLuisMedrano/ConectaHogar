<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - ConectaHogar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/auth.css">
</head>
<body>
    <main class="auth-container">
        <h1>Iniciar Sesión</h1>
        
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/auth/login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            
            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required>
            </div>
            
            <button type="submit" class="btn-primary">Ingresar</button>
        </form>
        
        <p>¿No tienes cuenta? <a href="${pageContext.request.contextPath}/auth/registro">Regístrate</a></p>
    </main>
</body>
</html>