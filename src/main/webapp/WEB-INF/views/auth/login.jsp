<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(to right, #ff7e5f, #feb47b);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: Arial, sans-serif;
            margin: 0;
        }

        .login-container {
            background: rgba(255, 255, 255, 0.95);
            padding: 3rem 2rem 2rem;
            border-radius: 12px;
            position: relative;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 350px;
        }

        .circle-icon {
            position: absolute;
            top: -40px;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 80px;
            border-radius: 50%;
            background-color: #ff7e5f;
            display: flex;
            justify-content: center;
            align-items: center;
            border: 5px solid #fff;
        }

        .circle-icon img {
            width: 70px;
            height: 70px;
            border-radius: 50%;
            object-fit: cover;
        }

        .login-title {
            margin-top: 50px;
            margin-bottom: 25px;
            text-align: center;
            color: #333;
        }

        .form-control::placeholder {
            color: #aaa;
        }

        .forgot-password {
            text-align: center;
            margin-top: 10px;
        }

        .btn-login {
            background-color: #ff7e5f;
            color: white;
        }

        .btn-login:hover {
            background-color: #feb47b;
            color: white;
        }

        .form-check-label {
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

    <div class="login-container">
        <div class="circle-icon">
            <img src="usuario.jpg" alt="Usuario">
        </div>

        <h4 class="login-title">Iniciar Sesión</h4>

        <form action="${pageContext.request.contextPath}/LoginServlet" method="POST">
            <div class="mb-3 position-relative">
                <i class="bi bi-person-fill position-absolute top-50 translate-middle-y ms-2 text-secondary"></i>
                <input type="email" class="form-control ps-5" name="correo" placeholder="Correo Electrónico" required>
            </div>

            <div class="mb-3 position-relative">
                <i class="bi bi-lock-fill position-absolute top-50 translate-middle-y ms-2 text-secondary"></i>
                <input type="password" class="form-control ps-5" name="contrasena" placeholder="Contraseña" required>
            </div>

            <div class="form-check mb-3">
                <input type="checkbox" class="form-check-input" id="rememberMe">
                <label class="form-check-label" for="rememberMe">Recordar contraseña</label>
            </div>

            <button type="submit" class="btn btn-login w-100">Ingresar</button>

            <div class="forgot-password mt-2">
                <a href="registro.jsp">¿No estas registrado?</a>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS (opcional, para componentes dinámicos) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
