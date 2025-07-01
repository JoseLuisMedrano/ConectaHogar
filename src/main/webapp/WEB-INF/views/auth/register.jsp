<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>ConectaHogar - Registro</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/static/css/auth.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card shadow-sm">
                        <div class="card-body p-4 p-md-5">
                            <div class="text-center mb-4">
                                <i class="fas fa-home fa-3x text-primary mb-3"></i>
                                <h2 class="h3 mb-2">ConectaHogar</h2>
                                <p class="text-muted">Crea tu cuenta</p>
                            </div>

                            <form id="registerForm" action="${pageContext.request.contextPath}/register" method="post">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="nombre" name="nombre" placeholder="Nombre" required>
                                            <label for="nombre">Nombre</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="apellido" name="apellido" placeholder="Apellido" required>
                                            <label for="apellido">Apellido</label>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-floating">
                                            <input type="email" class="form-control" id="correo" name="correo" placeholder="Correo Electrónico" required>
                                            <label for="email">Correo Electrónico</label>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-floating">
                                            <input type="password" class="form-control" id="contrasena" name="contrasena" placeholder="Contraseña" required>
                                            <label for="password">Contraseña</label>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="tel" class="form-control" id="telefono" name="telefono" 
                                                   pattern="^\+51\d{9}$" 
                                                   title="Ingrese un número peruano válido (+51 seguido de 9 dígitos)" 
                                                   placeholder="Teléfono" required>
                                            <label for="telefono">Teléfono (+51)</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="number" class="form-control" id="edad" name="edad" min="16" max="100" placeholder="Edad" required>
                                            <label for="edad">Edad</label>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-floating">
                                            <select class="form-select" id="sexo" name="sexo" required>
                                                <option value="" disabled selected>Seleccione su sexo</option>
                                                <option value="masculino">Masculino</option>
                                                <option value="femenino">Femenino</option>
                                                <option value="otro">Otro</option>
                                            </select>
                                            <label for="sexo">Sexo</label>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label">Tipo de cuenta:</label>
                                        <div class="card mb-3">
                                            <div class="card-body p-3">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="tipoUsuario" id="cliente" value="cliente" required>
                                                    <label class="form-check-label d-flex align-items-center" for="cliente">
                                                        <i class="fas fa-home me-2 text-primary"></i>
                                                        <div>
                                                            <div class="fw-bold">Cliente (Ama de casa)</div>
                                                            <small class="text-muted">Busco servicios para mi hogar</small>
                                                        </div>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="card">
                                            <div class="card-body p-3">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="tipoUsuario" id="tecnico" value="tecnico">
                                                    <label class="form-check-label d-flex align-items-center" for="tecnico">
                                                        <i class="fas fa-tools me-2 text-primary"></i>
                                                        <div>
                                                            <div class="fw-bold">Técnico</div>
                                                            <small class="text-muted">Ofrezco servicios de mantenimiento</small>
                                                        </div>
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="terminos" name="terminos" required>
                                            <label class="form-check-label" for="terminos">
                                                Acepto los <a href="#" class="text-decoration-none">términos y condiciones</a>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <button type="submit" class="btn btn-primary w-100 py-2">
                                            <i class="fas fa-user-plus me-2"></i>
                                            Registrarse
                                        </button>
                                    </div>
                                </div>
                            </form>

                            <div class="text-center mt-3">
                                <p class="mb-0">¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Inicia sesión</a></p>
                            </div>

                            <div class="text-center mt-4">
                                <p class="small text-muted">¿Tienes problemas para registrarte?<br>
                                    <a href="mailto:soporte@conectahogar.com" class="text-decoration-none">Contáctanos</a></p>
                            </div>

                            <div class="d-flex justify-content-center gap-3 mt-4">
                                <a href="#" class="text-decoration-none text-muted"><i class="fab fa-facebook-f fa-lg"></i></a>
                                <a href="#" class="text-decoration-none text-muted"><i class="fab fa-whatsapp fa-lg"></i></a>
                                <a href="#" class="text-decoration-none text-muted"><i class="fab fa-instagram fa-lg"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.getElementById('registerForm').addEventListener('submit', function (e) {
                if (!document.getElementById('terminos').checked) {
                    e.preventDefault();
                    alert('Debes aceptar los términos y condiciones');
                }
            });
        </script>
    </body>
</html>